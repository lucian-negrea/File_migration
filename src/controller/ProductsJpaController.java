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
import beans.Classifications;
import beans.Bugs;
import java.util.ArrayList;
import java.util.Collection;
import beans.Components;
import beans.Products;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author RO100051
 */
public class ProductsJpaController implements Serializable {

    public ProductsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Products products) {
        if (products.getBugsCollection() == null) {
            products.setBugsCollection(new ArrayList<Bugs>());
        }
        if (products.getComponentsCollection() == null) {
            products.setComponentsCollection(new ArrayList<Components>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Classifications classificationId = products.getClassificationId();
            if (classificationId != null) {
                classificationId = em.getReference(classificationId.getClass(), classificationId.getId());
                products.setClassificationId(classificationId);
            }
            Collection<Bugs> attachedBugsCollection = new ArrayList<Bugs>();
            for (Bugs bugsCollectionBugsToAttach : products.getBugsCollection()) {
                bugsCollectionBugsToAttach = em.getReference(bugsCollectionBugsToAttach.getClass(), bugsCollectionBugsToAttach.getBugId());
                attachedBugsCollection.add(bugsCollectionBugsToAttach);
            }
            products.setBugsCollection(attachedBugsCollection);
            Collection<Components> attachedComponentsCollection = new ArrayList<Components>();
            for (Components componentsCollectionComponentsToAttach : products.getComponentsCollection()) {
                componentsCollectionComponentsToAttach = em.getReference(componentsCollectionComponentsToAttach.getClass(), componentsCollectionComponentsToAttach.getId());
                attachedComponentsCollection.add(componentsCollectionComponentsToAttach);
            }
            products.setComponentsCollection(attachedComponentsCollection);
            em.persist(products);
            if (classificationId != null) {
                classificationId.getProductsCollection().add(products);
                classificationId = em.merge(classificationId);
            }
            for (Bugs bugsCollectionBugs : products.getBugsCollection()) {
                Products oldProductIdOfBugsCollectionBugs = bugsCollectionBugs.getProductId();
                bugsCollectionBugs.setProductId(products);
                bugsCollectionBugs = em.merge(bugsCollectionBugs);
                if (oldProductIdOfBugsCollectionBugs != null) {
                    oldProductIdOfBugsCollectionBugs.getBugsCollection().remove(bugsCollectionBugs);
                    oldProductIdOfBugsCollectionBugs = em.merge(oldProductIdOfBugsCollectionBugs);
                }
            }
            for (Components componentsCollectionComponents : products.getComponentsCollection()) {
                Products oldProductIdOfComponentsCollectionComponents = componentsCollectionComponents.getProductId();
                componentsCollectionComponents.setProductId(products);
                componentsCollectionComponents = em.merge(componentsCollectionComponents);
                if (oldProductIdOfComponentsCollectionComponents != null) {
                    oldProductIdOfComponentsCollectionComponents.getComponentsCollection().remove(componentsCollectionComponents);
                    oldProductIdOfComponentsCollectionComponents = em.merge(oldProductIdOfComponentsCollectionComponents);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Products products) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products persistentProducts = em.find(Products.class, products.getId());
            Classifications classificationIdOld = persistentProducts.getClassificationId();
            Classifications classificationIdNew = products.getClassificationId();
            Collection<Bugs> bugsCollectionOld = persistentProducts.getBugsCollection();
            Collection<Bugs> bugsCollectionNew = products.getBugsCollection();
            Collection<Components> componentsCollectionOld = persistentProducts.getComponentsCollection();
            Collection<Components> componentsCollectionNew = products.getComponentsCollection();
            List<String> illegalOrphanMessages = null;
            for (Bugs bugsCollectionOldBugs : bugsCollectionOld) {
                if (!bugsCollectionNew.contains(bugsCollectionOldBugs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bugs " + bugsCollectionOldBugs + " since its productId field is not nullable.");
                }
            }
            for (Components componentsCollectionOldComponents : componentsCollectionOld) {
                if (!componentsCollectionNew.contains(componentsCollectionOldComponents)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Components " + componentsCollectionOldComponents + " since its productId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (classificationIdNew != null) {
                classificationIdNew = em.getReference(classificationIdNew.getClass(), classificationIdNew.getId());
                products.setClassificationId(classificationIdNew);
            }
            Collection<Bugs> attachedBugsCollectionNew = new ArrayList<Bugs>();
            for (Bugs bugsCollectionNewBugsToAttach : bugsCollectionNew) {
                bugsCollectionNewBugsToAttach = em.getReference(bugsCollectionNewBugsToAttach.getClass(), bugsCollectionNewBugsToAttach.getBugId());
                attachedBugsCollectionNew.add(bugsCollectionNewBugsToAttach);
            }
            bugsCollectionNew = attachedBugsCollectionNew;
            products.setBugsCollection(bugsCollectionNew);
            Collection<Components> attachedComponentsCollectionNew = new ArrayList<Components>();
            for (Components componentsCollectionNewComponentsToAttach : componentsCollectionNew) {
                componentsCollectionNewComponentsToAttach = em.getReference(componentsCollectionNewComponentsToAttach.getClass(), componentsCollectionNewComponentsToAttach.getId());
                attachedComponentsCollectionNew.add(componentsCollectionNewComponentsToAttach);
            }
            componentsCollectionNew = attachedComponentsCollectionNew;
            products.setComponentsCollection(componentsCollectionNew);
            products = em.merge(products);
            if (classificationIdOld != null && !classificationIdOld.equals(classificationIdNew)) {
                classificationIdOld.getProductsCollection().remove(products);
                classificationIdOld = em.merge(classificationIdOld);
            }
            if (classificationIdNew != null && !classificationIdNew.equals(classificationIdOld)) {
                classificationIdNew.getProductsCollection().add(products);
                classificationIdNew = em.merge(classificationIdNew);
            }
            for (Bugs bugsCollectionNewBugs : bugsCollectionNew) {
                if (!bugsCollectionOld.contains(bugsCollectionNewBugs)) {
                    Products oldProductIdOfBugsCollectionNewBugs = bugsCollectionNewBugs.getProductId();
                    bugsCollectionNewBugs.setProductId(products);
                    bugsCollectionNewBugs = em.merge(bugsCollectionNewBugs);
                    if (oldProductIdOfBugsCollectionNewBugs != null && !oldProductIdOfBugsCollectionNewBugs.equals(products)) {
                        oldProductIdOfBugsCollectionNewBugs.getBugsCollection().remove(bugsCollectionNewBugs);
                        oldProductIdOfBugsCollectionNewBugs = em.merge(oldProductIdOfBugsCollectionNewBugs);
                    }
                }
            }
            for (Components componentsCollectionNewComponents : componentsCollectionNew) {
                if (!componentsCollectionOld.contains(componentsCollectionNewComponents)) {
                    Products oldProductIdOfComponentsCollectionNewComponents = componentsCollectionNewComponents.getProductId();
                    componentsCollectionNewComponents.setProductId(products);
                    componentsCollectionNewComponents = em.merge(componentsCollectionNewComponents);
                    if (oldProductIdOfComponentsCollectionNewComponents != null && !oldProductIdOfComponentsCollectionNewComponents.equals(products)) {
                        oldProductIdOfComponentsCollectionNewComponents.getComponentsCollection().remove(componentsCollectionNewComponents);
                        oldProductIdOfComponentsCollectionNewComponents = em.merge(oldProductIdOfComponentsCollectionNewComponents);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = products.getId();
                if (findProducts(id) == null) {
                    throw new NonexistentEntityException("The products with id " + id + " no longer exists.");
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
            Products products;
            try {
                products = em.getReference(Products.class, id);
                products.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The products with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bugs> bugsCollectionOrphanCheck = products.getBugsCollection();
            for (Bugs bugsCollectionOrphanCheckBugs : bugsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Products (" + products + ") cannot be destroyed since the Bugs " + bugsCollectionOrphanCheckBugs + " in its bugsCollection field has a non-nullable productId field.");
            }
            Collection<Components> componentsCollectionOrphanCheck = products.getComponentsCollection();
            for (Components componentsCollectionOrphanCheckComponents : componentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Products (" + products + ") cannot be destroyed since the Components " + componentsCollectionOrphanCheckComponents + " in its componentsCollection field has a non-nullable productId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Classifications classificationId = products.getClassificationId();
            if (classificationId != null) {
                classificationId.getProductsCollection().remove(products);
                classificationId = em.merge(classificationId);
            }
            em.remove(products);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Products> findProductsEntities() {
        return findProductsEntities(true, -1, -1);
    }

    public List<Products> findProductsEntities(int maxResults, int firstResult) {
        return findProductsEntities(false, maxResults, firstResult);
    }

    private List<Products> findProductsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Products.class));
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

    public Products findProducts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Products.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Products> rt = cq.from(Products.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
