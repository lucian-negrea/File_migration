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
import beans.Components;
import beans.Bugs;
import java.util.ArrayList;
import java.util.Collection;
import beans.Attachments;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author RO100051
 */
public class BugsJpaController implements Serializable {

    public BugsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bugs bugs) {
        if (bugs.getBugsCollection() == null) {
            bugs.setBugsCollection(new ArrayList<Bugs>());
        }
        if (bugs.getBugsCollection1() == null) {
            bugs.setBugsCollection1(new ArrayList<Bugs>());
        }
        if (bugs.getProfilesCollection() == null) {
            bugs.setProfilesCollection(new ArrayList<Profiles>());
        }
        if (bugs.getAttachmentsCollection() == null) {
            bugs.setAttachmentsCollection(new ArrayList<Attachments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profiles reporter = bugs.getReporter();
            if (reporter != null) {
                reporter = em.getReference(reporter.getClass(), reporter.getUserid());
                bugs.setReporter(reporter);
            }
            Profiles qaContact = bugs.getQaContact();
            if (qaContact != null) {
                qaContact = em.getReference(qaContact.getClass(), qaContact.getUserid());
                bugs.setQaContact(qaContact);
            }
            Profiles assignedTo = bugs.getAssignedTo();
            if (assignedTo != null) {
                assignedTo = em.getReference(assignedTo.getClass(), assignedTo.getUserid());
                bugs.setAssignedTo(assignedTo);
            }
            Products productId = bugs.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                bugs.setProductId(productId);
            }
            Components componentId = bugs.getComponentId();
            if (componentId != null) {
                componentId = em.getReference(componentId.getClass(), componentId.getId());
                bugs.setComponentId(componentId);
            }
            Collection<Bugs> attachedBugsCollection = new ArrayList<Bugs>();
            for (Bugs bugsCollectionBugsToAttach : bugs.getBugsCollection()) {
                bugsCollectionBugsToAttach = em.getReference(bugsCollectionBugsToAttach.getClass(), bugsCollectionBugsToAttach.getBugId());
                attachedBugsCollection.add(bugsCollectionBugsToAttach);
            }
            bugs.setBugsCollection(attachedBugsCollection);
            Collection<Bugs> attachedBugsCollection1 = new ArrayList<Bugs>();
            for (Bugs bugsCollection1BugsToAttach : bugs.getBugsCollection1()) {
                bugsCollection1BugsToAttach = em.getReference(bugsCollection1BugsToAttach.getClass(), bugsCollection1BugsToAttach.getBugId());
                attachedBugsCollection1.add(bugsCollection1BugsToAttach);
            }
            bugs.setBugsCollection1(attachedBugsCollection1);
            Collection<Profiles> attachedProfilesCollection = new ArrayList<Profiles>();
            for (Profiles profilesCollectionProfilesToAttach : bugs.getProfilesCollection()) {
                profilesCollectionProfilesToAttach = em.getReference(profilesCollectionProfilesToAttach.getClass(), profilesCollectionProfilesToAttach.getUserid());
                attachedProfilesCollection.add(profilesCollectionProfilesToAttach);
            }
            bugs.setProfilesCollection(attachedProfilesCollection);
            Collection<Attachments> attachedAttachmentsCollection = new ArrayList<Attachments>();
            for (Attachments attachmentsCollectionAttachmentsToAttach : bugs.getAttachmentsCollection()) {
                attachmentsCollectionAttachmentsToAttach = em.getReference(attachmentsCollectionAttachmentsToAttach.getClass(), attachmentsCollectionAttachmentsToAttach.getAttachId());
                attachedAttachmentsCollection.add(attachmentsCollectionAttachmentsToAttach);
            }
            bugs.setAttachmentsCollection(attachedAttachmentsCollection);
            em.persist(bugs);
            if (reporter != null) {
                reporter.getBugsCollection().add(bugs);
                reporter = em.merge(reporter);
            }
            if (qaContact != null) {
                qaContact.getBugsCollection().add(bugs);
                qaContact = em.merge(qaContact);
            }
            if (assignedTo != null) {
                assignedTo.getBugsCollection().add(bugs);
                assignedTo = em.merge(assignedTo);
            }
            if (productId != null) {
                productId.getBugsCollection().add(bugs);
                productId = em.merge(productId);
            }
            if (componentId != null) {
                componentId.getBugsCollection().add(bugs);
                componentId = em.merge(componentId);
            }
            for (Bugs bugsCollectionBugs : bugs.getBugsCollection()) {
                bugsCollectionBugs.getBugsCollection().add(bugs);
                bugsCollectionBugs = em.merge(bugsCollectionBugs);
            }
            for (Bugs bugsCollection1Bugs : bugs.getBugsCollection1()) {
                bugsCollection1Bugs.getBugsCollection().add(bugs);
                bugsCollection1Bugs = em.merge(bugsCollection1Bugs);
            }
            for (Profiles profilesCollectionProfiles : bugs.getProfilesCollection()) {
                profilesCollectionProfiles.getBugsCollection().add(bugs);
                profilesCollectionProfiles = em.merge(profilesCollectionProfiles);
            }
            for (Attachments attachmentsCollectionAttachments : bugs.getAttachmentsCollection()) {
                Bugs oldBugIdOfAttachmentsCollectionAttachments = attachmentsCollectionAttachments.getBugId();
                attachmentsCollectionAttachments.setBugId(bugs);
                attachmentsCollectionAttachments = em.merge(attachmentsCollectionAttachments);
                if (oldBugIdOfAttachmentsCollectionAttachments != null) {
                    oldBugIdOfAttachmentsCollectionAttachments.getAttachmentsCollection().remove(attachmentsCollectionAttachments);
                    oldBugIdOfAttachmentsCollectionAttachments = em.merge(oldBugIdOfAttachmentsCollectionAttachments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bugs bugs) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bugs persistentBugs = em.find(Bugs.class, bugs.getBugId());
            Profiles reporterOld = persistentBugs.getReporter();
            Profiles reporterNew = bugs.getReporter();
            Profiles qaContactOld = persistentBugs.getQaContact();
            Profiles qaContactNew = bugs.getQaContact();
            Profiles assignedToOld = persistentBugs.getAssignedTo();
            Profiles assignedToNew = bugs.getAssignedTo();
            Products productIdOld = persistentBugs.getProductId();
            Products productIdNew = bugs.getProductId();
            Components componentIdOld = persistentBugs.getComponentId();
            Components componentIdNew = bugs.getComponentId();
            Collection<Bugs> bugsCollectionOld = persistentBugs.getBugsCollection();
            Collection<Bugs> bugsCollectionNew = bugs.getBugsCollection();
            Collection<Bugs> bugsCollection1Old = persistentBugs.getBugsCollection1();
            Collection<Bugs> bugsCollection1New = bugs.getBugsCollection1();
            Collection<Profiles> profilesCollectionOld = persistentBugs.getProfilesCollection();
            Collection<Profiles> profilesCollectionNew = bugs.getProfilesCollection();
            Collection<Attachments> attachmentsCollectionOld = persistentBugs.getAttachmentsCollection();
            Collection<Attachments> attachmentsCollectionNew = bugs.getAttachmentsCollection();
            List<String> illegalOrphanMessages = null;
            for (Attachments attachmentsCollectionOldAttachments : attachmentsCollectionOld) {
                if (!attachmentsCollectionNew.contains(attachmentsCollectionOldAttachments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Attachments " + attachmentsCollectionOldAttachments + " since its bugId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (reporterNew != null) {
                reporterNew = em.getReference(reporterNew.getClass(), reporterNew.getUserid());
                bugs.setReporter(reporterNew);
            }
            if (qaContactNew != null) {
                qaContactNew = em.getReference(qaContactNew.getClass(), qaContactNew.getUserid());
                bugs.setQaContact(qaContactNew);
            }
            if (assignedToNew != null) {
                assignedToNew = em.getReference(assignedToNew.getClass(), assignedToNew.getUserid());
                bugs.setAssignedTo(assignedToNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                bugs.setProductId(productIdNew);
            }
            if (componentIdNew != null) {
                componentIdNew = em.getReference(componentIdNew.getClass(), componentIdNew.getId());
                bugs.setComponentId(componentIdNew);
            }
            Collection<Bugs> attachedBugsCollectionNew = new ArrayList<Bugs>();
            for (Bugs bugsCollectionNewBugsToAttach : bugsCollectionNew) {
                bugsCollectionNewBugsToAttach = em.getReference(bugsCollectionNewBugsToAttach.getClass(), bugsCollectionNewBugsToAttach.getBugId());
                attachedBugsCollectionNew.add(bugsCollectionNewBugsToAttach);
            }
            bugsCollectionNew = attachedBugsCollectionNew;
            bugs.setBugsCollection(bugsCollectionNew);
            Collection<Bugs> attachedBugsCollection1New = new ArrayList<Bugs>();
            for (Bugs bugsCollection1NewBugsToAttach : bugsCollection1New) {
                bugsCollection1NewBugsToAttach = em.getReference(bugsCollection1NewBugsToAttach.getClass(), bugsCollection1NewBugsToAttach.getBugId());
                attachedBugsCollection1New.add(bugsCollection1NewBugsToAttach);
            }
            bugsCollection1New = attachedBugsCollection1New;
            bugs.setBugsCollection1(bugsCollection1New);
            Collection<Profiles> attachedProfilesCollectionNew = new ArrayList<Profiles>();
            for (Profiles profilesCollectionNewProfilesToAttach : profilesCollectionNew) {
                profilesCollectionNewProfilesToAttach = em.getReference(profilesCollectionNewProfilesToAttach.getClass(), profilesCollectionNewProfilesToAttach.getUserid());
                attachedProfilesCollectionNew.add(profilesCollectionNewProfilesToAttach);
            }
            profilesCollectionNew = attachedProfilesCollectionNew;
            bugs.setProfilesCollection(profilesCollectionNew);
            Collection<Attachments> attachedAttachmentsCollectionNew = new ArrayList<Attachments>();
            for (Attachments attachmentsCollectionNewAttachmentsToAttach : attachmentsCollectionNew) {
                attachmentsCollectionNewAttachmentsToAttach = em.getReference(attachmentsCollectionNewAttachmentsToAttach.getClass(), attachmentsCollectionNewAttachmentsToAttach.getAttachId());
                attachedAttachmentsCollectionNew.add(attachmentsCollectionNewAttachmentsToAttach);
            }
            attachmentsCollectionNew = attachedAttachmentsCollectionNew;
            bugs.setAttachmentsCollection(attachmentsCollectionNew);
            bugs = em.merge(bugs);
            if (reporterOld != null && !reporterOld.equals(reporterNew)) {
                reporterOld.getBugsCollection().remove(bugs);
                reporterOld = em.merge(reporterOld);
            }
            if (reporterNew != null && !reporterNew.equals(reporterOld)) {
                reporterNew.getBugsCollection().add(bugs);
                reporterNew = em.merge(reporterNew);
            }
            if (qaContactOld != null && !qaContactOld.equals(qaContactNew)) {
                qaContactOld.getBugsCollection().remove(bugs);
                qaContactOld = em.merge(qaContactOld);
            }
            if (qaContactNew != null && !qaContactNew.equals(qaContactOld)) {
                qaContactNew.getBugsCollection().add(bugs);
                qaContactNew = em.merge(qaContactNew);
            }
            if (assignedToOld != null && !assignedToOld.equals(assignedToNew)) {
                assignedToOld.getBugsCollection().remove(bugs);
                assignedToOld = em.merge(assignedToOld);
            }
            if (assignedToNew != null && !assignedToNew.equals(assignedToOld)) {
                assignedToNew.getBugsCollection().add(bugs);
                assignedToNew = em.merge(assignedToNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getBugsCollection().remove(bugs);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getBugsCollection().add(bugs);
                productIdNew = em.merge(productIdNew);
            }
            if (componentIdOld != null && !componentIdOld.equals(componentIdNew)) {
                componentIdOld.getBugsCollection().remove(bugs);
                componentIdOld = em.merge(componentIdOld);
            }
            if (componentIdNew != null && !componentIdNew.equals(componentIdOld)) {
                componentIdNew.getBugsCollection().add(bugs);
                componentIdNew = em.merge(componentIdNew);
            }
            for (Bugs bugsCollectionOldBugs : bugsCollectionOld) {
                if (!bugsCollectionNew.contains(bugsCollectionOldBugs)) {
                    bugsCollectionOldBugs.getBugsCollection().remove(bugs);
                    bugsCollectionOldBugs = em.merge(bugsCollectionOldBugs);
                }
            }
            for (Bugs bugsCollectionNewBugs : bugsCollectionNew) {
                if (!bugsCollectionOld.contains(bugsCollectionNewBugs)) {
                    bugsCollectionNewBugs.getBugsCollection().add(bugs);
                    bugsCollectionNewBugs = em.merge(bugsCollectionNewBugs);
                }
            }
            for (Bugs bugsCollection1OldBugs : bugsCollection1Old) {
                if (!bugsCollection1New.contains(bugsCollection1OldBugs)) {
                    bugsCollection1OldBugs.getBugsCollection().remove(bugs);
                    bugsCollection1OldBugs = em.merge(bugsCollection1OldBugs);
                }
            }
            for (Bugs bugsCollection1NewBugs : bugsCollection1New) {
                if (!bugsCollection1Old.contains(bugsCollection1NewBugs)) {
                    bugsCollection1NewBugs.getBugsCollection().add(bugs);
                    bugsCollection1NewBugs = em.merge(bugsCollection1NewBugs);
                }
            }
            for (Profiles profilesCollectionOldProfiles : profilesCollectionOld) {
                if (!profilesCollectionNew.contains(profilesCollectionOldProfiles)) {
                    profilesCollectionOldProfiles.getBugsCollection().remove(bugs);
                    profilesCollectionOldProfiles = em.merge(profilesCollectionOldProfiles);
                }
            }
            for (Profiles profilesCollectionNewProfiles : profilesCollectionNew) {
                if (!profilesCollectionOld.contains(profilesCollectionNewProfiles)) {
                    profilesCollectionNewProfiles.getBugsCollection().add(bugs);
                    profilesCollectionNewProfiles = em.merge(profilesCollectionNewProfiles);
                }
            }
            for (Attachments attachmentsCollectionNewAttachments : attachmentsCollectionNew) {
                if (!attachmentsCollectionOld.contains(attachmentsCollectionNewAttachments)) {
                    Bugs oldBugIdOfAttachmentsCollectionNewAttachments = attachmentsCollectionNewAttachments.getBugId();
                    attachmentsCollectionNewAttachments.setBugId(bugs);
                    attachmentsCollectionNewAttachments = em.merge(attachmentsCollectionNewAttachments);
                    if (oldBugIdOfAttachmentsCollectionNewAttachments != null && !oldBugIdOfAttachmentsCollectionNewAttachments.equals(bugs)) {
                        oldBugIdOfAttachmentsCollectionNewAttachments.getAttachmentsCollection().remove(attachmentsCollectionNewAttachments);
                        oldBugIdOfAttachmentsCollectionNewAttachments = em.merge(oldBugIdOfAttachmentsCollectionNewAttachments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bugs.getBugId();
                if (findBugs(id) == null) {
                    throw new NonexistentEntityException("The bugs with id " + id + " no longer exists.");
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
            Bugs bugs;
            try {
                bugs = em.getReference(Bugs.class, id);
                bugs.getBugId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bugs with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Attachments> attachmentsCollectionOrphanCheck = bugs.getAttachmentsCollection();
            for (Attachments attachmentsCollectionOrphanCheckAttachments : attachmentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bugs (" + bugs + ") cannot be destroyed since the Attachments " + attachmentsCollectionOrphanCheckAttachments + " in its attachmentsCollection field has a non-nullable bugId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Profiles reporter = bugs.getReporter();
            if (reporter != null) {
                reporter.getBugsCollection().remove(bugs);
                reporter = em.merge(reporter);
            }
            Profiles qaContact = bugs.getQaContact();
            if (qaContact != null) {
                qaContact.getBugsCollection().remove(bugs);
                qaContact = em.merge(qaContact);
            }
            Profiles assignedTo = bugs.getAssignedTo();
            if (assignedTo != null) {
                assignedTo.getBugsCollection().remove(bugs);
                assignedTo = em.merge(assignedTo);
            }
            Products productId = bugs.getProductId();
            if (productId != null) {
                productId.getBugsCollection().remove(bugs);
                productId = em.merge(productId);
            }
            Components componentId = bugs.getComponentId();
            if (componentId != null) {
                componentId.getBugsCollection().remove(bugs);
                componentId = em.merge(componentId);
            }
            Collection<Bugs> bugsCollection = bugs.getBugsCollection();
            for (Bugs bugsCollectionBugs : bugsCollection) {
                bugsCollectionBugs.getBugsCollection().remove(bugs);
                bugsCollectionBugs = em.merge(bugsCollectionBugs);
            }
            Collection<Bugs> bugsCollection1 = bugs.getBugsCollection1();
            for (Bugs bugsCollection1Bugs : bugsCollection1) {
                bugsCollection1Bugs.getBugsCollection().remove(bugs);
                bugsCollection1Bugs = em.merge(bugsCollection1Bugs);
            }
            Collection<Profiles> profilesCollection = bugs.getProfilesCollection();
            for (Profiles profilesCollectionProfiles : profilesCollection) {
                profilesCollectionProfiles.getBugsCollection().remove(bugs);
                profilesCollectionProfiles = em.merge(profilesCollectionProfiles);
            }
            em.remove(bugs);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bugs> findBugsEntities() {
        return findBugsEntities(true, -1, -1);
    }

    public List<Bugs> findBugsEntities(int maxResults, int firstResult) {
        return findBugsEntities(false, maxResults, firstResult);
    }

    private List<Bugs> findBugsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bugs.class));
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

    public Bugs findBugs(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bugs.class, id);
        } finally {
            em.close();
        }
    }

    public int getBugsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bugs> rt = cq.from(Bugs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
