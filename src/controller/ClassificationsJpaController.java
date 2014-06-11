/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.Classifications;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import beans.Products;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author RO100051
 */
public class ClassificationsJpaController implements Serializable {

    public ClassificationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Classifications classifications) {
        if (classifications.getProductsCollection() == null) {
            classifications.setProductsCollection(new ArrayList<Products>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Products> attachedProductsCollection = new ArrayList<Products>();
            for (Products productsCollectionProductsToAttach : classifications.getProductsCollection()) {
                productsCollectionProductsToAttach = em.getReference(productsCollectionProductsToAttach.getClass(), productsCollectionProductsToAttach.getId());
                attachedProductsCollection.add(productsCollectionProductsToAttach);
            }
            classifications.setProductsCollection(attachedProductsCollection);
            em.persist(classifications);
            for (Products productsCollectionProducts : classifications.getProductsCollection()) {
                Classifications oldClassificationIdOfProductsCollectionProducts = productsCollectionProducts.getClassificationId();
                productsCollectionProducts.setClassificationId(classifications);
                productsCollectionProducts = em.merge(productsCollectionProducts);
                if (oldClassificationIdOfProductsCollectionProducts != null) {
                    oldClassificationIdOfProductsCollectionProducts.getProductsCollection().remove(productsCollectionProducts);
                    oldClassificationIdOfProductsCollectionProducts = em.merge(oldClassificationIdOfProductsCollectionProducts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Classifications classifications) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Classifications persistentClassifications = em.find(Classifications.class, classifications.getId());
            Collection<Products> productsCollectionOld = persistentClassifications.getProductsCollection();
            Collection<Products> productsCollectionNew = classifications.getProductsCollection();
            List<String> illegalOrphanMessages = null;
            for (Products productsCollectionOldProducts : productsCollectionOld) {
                if (!productsCollectionNew.contains(productsCollectionOldProducts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Products " + productsCollectionOldProducts + " since its classificationId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Products> attachedProductsCollectionNew = new ArrayList<Products>();
            for (Products productsCollectionNewProductsToAttach : productsCollectionNew) {
                productsCollectionNewProductsToAttach = em.getReference(productsCollectionNewProductsToAttach.getClass(), productsCollectionNewProductsToAttach.getId());
                attachedProductsCollectionNew.add(productsCollectionNewProductsToAttach);
            }
            productsCollectionNew = attachedProductsCollectionNew;
            classifications.setProductsCollection(productsCollectionNew);
            classifications = em.merge(classifications);
            for (Products productsCollectionNewProducts : productsCollectionNew) {
                if (!productsCollectionOld.contains(productsCollectionNewProducts)) {
                    Classifications oldClassificationIdOfProductsCollectionNewProducts = productsCollectionNewProducts.getClassificationId();
                    productsCollectionNewProducts.setClassificationId(classifications);
                    productsCollectionNewProducts = em.merge(productsCollectionNewProducts);
                    if (oldClassificationIdOfProductsCollectionNewProducts != null && !oldClassificationIdOfProductsCollectionNewProducts.equals(classifications)) {
                        oldClassificationIdOfProductsCollectionNewProducts.getProductsCollection().remove(productsCollectionNewProducts);
                        oldClassificationIdOfProductsCollectionNewProducts = em.merge(oldClassificationIdOfProductsCollectionNewProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = classifications.getId();
                if (findClassifications(id) == null) {
                    throw new NonexistentEntityException("The classifications with id " + id + " no longer exists.");
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
            Classifications classifications;
            try {
                classifications = em.getReference(Classifications.class, id);
                classifications.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The classifications with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Products> productsCollectionOrphanCheck = classifications.getProductsCollection();
            for (Products productsCollectionOrphanCheckProducts : productsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Classifications (" + classifications + ") cannot be destroyed since the Products " + productsCollectionOrphanCheckProducts + " in its productsCollection field has a non-nullable classificationId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(classifications);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Classifications> findClassificationsEntities() {
        return findClassificationsEntities(true, -1, -1);
    }

    public List<Classifications> findClassificationsEntities(int maxResults, int firstResult) {
        return findClassificationsEntities(false, maxResults, firstResult);
    }

    private List<Classifications> findClassificationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Classifications.class));
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

    public Classifications findClassifications(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Classifications.class, id);
        } finally {
            em.close();
        }
    }

    public int getClassificationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Classifications> rt = cq.from(Classifications.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
