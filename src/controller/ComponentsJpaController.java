/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import beans.Profiles;
import beans.Products;
import java.util.ArrayList;
import java.util.Collection;
import beans.Bugs;
import beans.Components;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author RO100051
 */
public class ComponentsJpaController implements Serializable {

    public ComponentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Components components) {
        if (components.getProfilesCollection() == null) {
            components.setProfilesCollection(new ArrayList<Profiles>());
        }
        if (components.getBugsCollection() == null) {
            components.setBugsCollection(new ArrayList<Bugs>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profiles initialqacontact = components.getInitialqacontact();
            if (initialqacontact != null) {
                initialqacontact = em.getReference(initialqacontact.getClass(), initialqacontact.getUserid());
                components.setInitialqacontact(initialqacontact);
            }
            Profiles initialowner = components.getInitialowner();
            if (initialowner != null) {
                initialowner = em.getReference(initialowner.getClass(), initialowner.getUserid());
                components.setInitialowner(initialowner);
            }
            Products productId = components.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                components.setProductId(productId);
            }
            Collection<Profiles> attachedProfilesCollection = new ArrayList<Profiles>();
            for (Profiles profilesCollectionProfilesToAttach : components.getProfilesCollection()) {
                profilesCollectionProfilesToAttach = em.getReference(profilesCollectionProfilesToAttach.getClass(), profilesCollectionProfilesToAttach.getUserid());
                attachedProfilesCollection.add(profilesCollectionProfilesToAttach);
            }
            components.setProfilesCollection(attachedProfilesCollection);
            Collection<Bugs> attachedBugsCollection = new ArrayList<Bugs>();
            for (Bugs bugsCollectionBugsToAttach : components.getBugsCollection()) {
                bugsCollectionBugsToAttach = em.getReference(bugsCollectionBugsToAttach.getClass(), bugsCollectionBugsToAttach.getBugId());
                attachedBugsCollection.add(bugsCollectionBugsToAttach);
            }
            components.setBugsCollection(attachedBugsCollection);
            em.persist(components);
            if (initialqacontact != null) {
                initialqacontact.getComponentsCollection().add(components);
                initialqacontact = em.merge(initialqacontact);
            }
            if (initialowner != null) {
                initialowner.getComponentsCollection().add(components);
                initialowner = em.merge(initialowner);
            }
            if (productId != null) {
                productId.getComponentsCollection().add(components);
                productId = em.merge(productId);
            }
            for (Profiles profilesCollectionProfiles : components.getProfilesCollection()) {
                profilesCollectionProfiles.getComponentsCollection().add(components);
                profilesCollectionProfiles = em.merge(profilesCollectionProfiles);
            }
            for (Bugs bugsCollectionBugs : components.getBugsCollection()) {
                Components oldComponentIdOfBugsCollectionBugs = bugsCollectionBugs.getComponentId();
                bugsCollectionBugs.setComponentId(components);
                bugsCollectionBugs = em.merge(bugsCollectionBugs);
                if (oldComponentIdOfBugsCollectionBugs != null) {
                    oldComponentIdOfBugsCollectionBugs.getBugsCollection().remove(bugsCollectionBugs);
                    oldComponentIdOfBugsCollectionBugs = em.merge(oldComponentIdOfBugsCollectionBugs);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Components components) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Components persistentComponents = em.find(Components.class, components.getId());
            Profiles initialqacontactOld = persistentComponents.getInitialqacontact();
            Profiles initialqacontactNew = components.getInitialqacontact();
            Profiles initialownerOld = persistentComponents.getInitialowner();
            Profiles initialownerNew = components.getInitialowner();
            Products productIdOld = persistentComponents.getProductId();
            Products productIdNew = components.getProductId();
            Collection<Profiles> profilesCollectionOld = persistentComponents.getProfilesCollection();
            Collection<Profiles> profilesCollectionNew = components.getProfilesCollection();
            Collection<Bugs> bugsCollectionOld = persistentComponents.getBugsCollection();
            Collection<Bugs> bugsCollectionNew = components.getBugsCollection();
            List<String> illegalOrphanMessages = null;
            for (Bugs bugsCollectionOldBugs : bugsCollectionOld) {
                if (!bugsCollectionNew.contains(bugsCollectionOldBugs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bugs " + bugsCollectionOldBugs + " since its componentId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (initialqacontactNew != null) {
                initialqacontactNew = em.getReference(initialqacontactNew.getClass(), initialqacontactNew.getUserid());
                components.setInitialqacontact(initialqacontactNew);
            }
            if (initialownerNew != null) {
                initialownerNew = em.getReference(initialownerNew.getClass(), initialownerNew.getUserid());
                components.setInitialowner(initialownerNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                components.setProductId(productIdNew);
            }
            Collection<Profiles> attachedProfilesCollectionNew = new ArrayList<Profiles>();
            for (Profiles profilesCollectionNewProfilesToAttach : profilesCollectionNew) {
                profilesCollectionNewProfilesToAttach = em.getReference(profilesCollectionNewProfilesToAttach.getClass(), profilesCollectionNewProfilesToAttach.getUserid());
                attachedProfilesCollectionNew.add(profilesCollectionNewProfilesToAttach);
            }
            profilesCollectionNew = attachedProfilesCollectionNew;
            components.setProfilesCollection(profilesCollectionNew);
            Collection<Bugs> attachedBugsCollectionNew = new ArrayList<Bugs>();
            for (Bugs bugsCollectionNewBugsToAttach : bugsCollectionNew) {
                bugsCollectionNewBugsToAttach = em.getReference(bugsCollectionNewBugsToAttach.getClass(), bugsCollectionNewBugsToAttach.getBugId());
                attachedBugsCollectionNew.add(bugsCollectionNewBugsToAttach);
            }
            bugsCollectionNew = attachedBugsCollectionNew;
            components.setBugsCollection(bugsCollectionNew);
            components = em.merge(components);
            if (initialqacontactOld != null && !initialqacontactOld.equals(initialqacontactNew)) {
                initialqacontactOld.getComponentsCollection().remove(components);
                initialqacontactOld = em.merge(initialqacontactOld);
            }
            if (initialqacontactNew != null && !initialqacontactNew.equals(initialqacontactOld)) {
                initialqacontactNew.getComponentsCollection().add(components);
                initialqacontactNew = em.merge(initialqacontactNew);
            }
            if (initialownerOld != null && !initialownerOld.equals(initialownerNew)) {
                initialownerOld.getComponentsCollection().remove(components);
                initialownerOld = em.merge(initialownerOld);
            }
            if (initialownerNew != null && !initialownerNew.equals(initialownerOld)) {
                initialownerNew.getComponentsCollection().add(components);
                initialownerNew = em.merge(initialownerNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getComponentsCollection().remove(components);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getComponentsCollection().add(components);
                productIdNew = em.merge(productIdNew);
            }
            for (Profiles profilesCollectionOldProfiles : profilesCollectionOld) {
                if (!profilesCollectionNew.contains(profilesCollectionOldProfiles)) {
                    profilesCollectionOldProfiles.getComponentsCollection().remove(components);
                    profilesCollectionOldProfiles = em.merge(profilesCollectionOldProfiles);
                }
            }
            for (Profiles profilesCollectionNewProfiles : profilesCollectionNew) {
                if (!profilesCollectionOld.contains(profilesCollectionNewProfiles)) {
                    profilesCollectionNewProfiles.getComponentsCollection().add(components);
                    profilesCollectionNewProfiles = em.merge(profilesCollectionNewProfiles);
                }
            }
            for (Bugs bugsCollectionNewBugs : bugsCollectionNew) {
                if (!bugsCollectionOld.contains(bugsCollectionNewBugs)) {
                    Components oldComponentIdOfBugsCollectionNewBugs = bugsCollectionNewBugs.getComponentId();
                    bugsCollectionNewBugs.setComponentId(components);
                    bugsCollectionNewBugs = em.merge(bugsCollectionNewBugs);
                    if (oldComponentIdOfBugsCollectionNewBugs != null && !oldComponentIdOfBugsCollectionNewBugs.equals(components)) {
                        oldComponentIdOfBugsCollectionNewBugs.getBugsCollection().remove(bugsCollectionNewBugs);
                        oldComponentIdOfBugsCollectionNewBugs = em.merge(oldComponentIdOfBugsCollectionNewBugs);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = components.getId();
                if (findComponents(id) == null) {
                    throw new NonexistentEntityException("The components with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Components components;
            try {
                components = em.getReference(Components.class, id);
                components.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The components with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bugs> bugsCollectionOrphanCheck = components.getBugsCollection();
            for (Bugs bugsCollectionOrphanCheckBugs : bugsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Components (" + components + ") cannot be destroyed since the Bugs " + bugsCollectionOrphanCheckBugs + " in its bugsCollection field has a non-nullable componentId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Profiles initialqacontact = components.getInitialqacontact();
            if (initialqacontact != null) {
                initialqacontact.getComponentsCollection().remove(components);
                initialqacontact = em.merge(initialqacontact);
            }
            Profiles initialowner = components.getInitialowner();
            if (initialowner != null) {
                initialowner.getComponentsCollection().remove(components);
                initialowner = em.merge(initialowner);
            }
            Products productId = components.getProductId();
            if (productId != null) {
                productId.getComponentsCollection().remove(components);
                productId = em.merge(productId);
            }
            Collection<Profiles> profilesCollection = components.getProfilesCollection();
            for (Profiles profilesCollectionProfiles : profilesCollection) {
                profilesCollectionProfiles.getComponentsCollection().remove(components);
                profilesCollectionProfiles = em.merge(profilesCollectionProfiles);
            }
            em.remove(components);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Components> findComponentsEntities() {
        return findComponentsEntities(true, -1, -1);
    }

    public List<Components> findComponentsEntities(int maxResults, int firstResult) {
        return findComponentsEntities(false, maxResults, firstResult);
    }

    private List<Components> findComponentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Components.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Components findComponents(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Components.class, id);
        } finally {
            em.close();
        }
    }

    public int getComponentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Components> rt = cq.from(Components.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
