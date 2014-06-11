/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.AttachData;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import beans.Attachments;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author RO100051
 */
public class AttachDataJpaController implements Serializable {

    public AttachDataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AttachData attachData) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Attachments attachmentsOrphanCheck = attachData.getAttachments();
        if (attachmentsOrphanCheck != null) {
            AttachData oldAttachDataOfAttachments = attachmentsOrphanCheck.getAttachData();
            if (oldAttachDataOfAttachments != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Attachments " + attachmentsOrphanCheck + " already has an item of type AttachData whose attachments column cannot be null. Please make another selection for the attachments field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attachments attachments = attachData.getAttachments();
            if (attachments != null) {
                attachments = em.getReference(attachments.getClass(), attachments.getAttachId());
                attachData.setAttachments(attachments);
            }
            em.persist(attachData);
            if (attachments != null) {
                attachments.setAttachData(attachData);
                attachments = em.merge(attachments);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAttachData(attachData.getId()) != null) {
                throw new PreexistingEntityException("AttachData " + attachData + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AttachData attachData) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AttachData persistentAttachData = em.find(AttachData.class, attachData.getId());
            Attachments attachmentsOld = persistentAttachData.getAttachments();
            Attachments attachmentsNew = attachData.getAttachments();
            List<String> illegalOrphanMessages = null;
            if (attachmentsNew != null && !attachmentsNew.equals(attachmentsOld)) {
                AttachData oldAttachDataOfAttachments = attachmentsNew.getAttachData();
                if (oldAttachDataOfAttachments != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Attachments " + attachmentsNew + " already has an item of type AttachData whose attachments column cannot be null. Please make another selection for the attachments field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (attachmentsNew != null) {
                attachmentsNew = em.getReference(attachmentsNew.getClass(), attachmentsNew.getAttachId());
                attachData.setAttachments(attachmentsNew);
            }
            attachData = em.merge(attachData);
            if (attachmentsOld != null && !attachmentsOld.equals(attachmentsNew)) {
                attachmentsOld.setAttachData(null);
                attachmentsOld = em.merge(attachmentsOld);
            }
            if (attachmentsNew != null && !attachmentsNew.equals(attachmentsOld)) {
                attachmentsNew.setAttachData(attachData);
                attachmentsNew = em.merge(attachmentsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = attachData.getId();
                if (findAttachData(id) == null) {
                    throw new NonexistentEntityException("The attachData with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AttachData attachData;
            try {
                attachData = em.getReference(AttachData.class, id);
                attachData.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attachData with id " + id + " no longer exists.", enfe);
            }
            Attachments attachments = attachData.getAttachments();
            if (attachments != null) {
                attachments.setAttachData(null);
                attachments = em.merge(attachments);
            }
            em.remove(attachData);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AttachData> findAttachDataEntities() {
        return findAttachDataEntities(true, -1, -1);
    }

    public List<AttachData> findAttachDataEntities(int maxResults, int firstResult) {
        return findAttachDataEntities(false, maxResults, firstResult);
    }

    private List<AttachData> findAttachDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AttachData.class));
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

    public AttachData findAttachData(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AttachData.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttachDataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AttachData> rt = cq.from(AttachData.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
