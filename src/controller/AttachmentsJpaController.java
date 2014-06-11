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
import beans.AttachData;
import beans.Attachments;
import beans.Profiles;
import beans.Bugs;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author RO100051
 */
public class AttachmentsJpaController implements Serializable {

    public AttachmentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attachments attachments) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AttachData attachData = attachments.getAttachData();
            if (attachData != null) {
                attachData = em.getReference(attachData.getClass(), attachData.getId());
                attachments.setAttachData(attachData);
            }
            Profiles submitterId = attachments.getSubmitterId();
            if (submitterId != null) {
                submitterId = em.getReference(submitterId.getClass(), submitterId.getUserid());
                attachments.setSubmitterId(submitterId);
            }
            Bugs bugId = attachments.getBugId();
            if (bugId != null) {
                bugId = em.getReference(bugId.getClass(), bugId.getBugId());
                attachments.setBugId(bugId);
            }
            em.persist(attachments);
            if (attachData != null) {
                Attachments oldAttachmentsOfAttachData = attachData.getAttachments();
                if (oldAttachmentsOfAttachData != null) {
                    oldAttachmentsOfAttachData.setAttachData(null);
                    oldAttachmentsOfAttachData = em.merge(oldAttachmentsOfAttachData);
                }
                attachData.setAttachments(attachments);
                attachData = em.merge(attachData);
            }
            if (submitterId != null) {
                submitterId.getAttachmentsCollection().add(attachments);
                submitterId = em.merge(submitterId);
            }
            if (bugId != null) {
                bugId.getAttachmentsCollection().add(attachments);
                bugId = em.merge(bugId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Attachments attachments) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attachments persistentAttachments = em.find(Attachments.class, attachments.getAttachId());
            AttachData attachDataOld = persistentAttachments.getAttachData();
            AttachData attachDataNew = attachments.getAttachData();
            Profiles submitterIdOld = persistentAttachments.getSubmitterId();
            Profiles submitterIdNew = attachments.getSubmitterId();
            Bugs bugIdOld = persistentAttachments.getBugId();
            Bugs bugIdNew = attachments.getBugId();
            List<String> illegalOrphanMessages = null;
            if (attachDataOld != null && !attachDataOld.equals(attachDataNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AttachData " + attachDataOld + " since its attachments field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (attachDataNew != null) {
                attachDataNew = em.getReference(attachDataNew.getClass(), attachDataNew.getId());
                attachments.setAttachData(attachDataNew);
            }
            if (submitterIdNew != null) {
                submitterIdNew = em.getReference(submitterIdNew.getClass(), submitterIdNew.getUserid());
                attachments.setSubmitterId(submitterIdNew);
            }
            if (bugIdNew != null) {
                bugIdNew = em.getReference(bugIdNew.getClass(), bugIdNew.getBugId());
                attachments.setBugId(bugIdNew);
            }
            attachments = em.merge(attachments);
            if (attachDataNew != null && !attachDataNew.equals(attachDataOld)) {
                Attachments oldAttachmentsOfAttachData = attachDataNew.getAttachments();
                if (oldAttachmentsOfAttachData != null) {
                    oldAttachmentsOfAttachData.setAttachData(null);
                    oldAttachmentsOfAttachData = em.merge(oldAttachmentsOfAttachData);
                }
                attachDataNew.setAttachments(attachments);
                attachDataNew = em.merge(attachDataNew);
            }
            if (submitterIdOld != null && !submitterIdOld.equals(submitterIdNew)) {
                submitterIdOld.getAttachmentsCollection().remove(attachments);
                submitterIdOld = em.merge(submitterIdOld);
            }
            if (submitterIdNew != null && !submitterIdNew.equals(submitterIdOld)) {
                submitterIdNew.getAttachmentsCollection().add(attachments);
                submitterIdNew = em.merge(submitterIdNew);
            }
            if (bugIdOld != null && !bugIdOld.equals(bugIdNew)) {
                bugIdOld.getAttachmentsCollection().remove(attachments);
                bugIdOld = em.merge(bugIdOld);
            }
            if (bugIdNew != null && !bugIdNew.equals(bugIdOld)) {
                bugIdNew.getAttachmentsCollection().add(attachments);
                bugIdNew = em.merge(bugIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = attachments.getAttachId();
                if (findAttachments(id) == null) {
                    throw new NonexistentEntityException("The attachments with id " + id + " no longer exists.");
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
            Attachments attachments;
            try {
                attachments = em.getReference(Attachments.class, id);
                attachments.getAttachId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attachments with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            //AttachData attachDataOrphanCheck = attachments.getAttachData();
            AttachData attachDataOrphanCheck = null;
            if (attachDataOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attachments (" + attachments + ") cannot be destroyed since the AttachData " + attachDataOrphanCheck + " in its attachData field has a non-nullable attachments field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Profiles submitterId = attachments.getSubmitterId();
            if (submitterId != null) {
                submitterId.getAttachmentsCollection().remove(attachments);
                submitterId = em.merge(submitterId);
            }
            Bugs bugId = attachments.getBugId();
            if (bugId != null) {
                bugId.getAttachmentsCollection().remove(attachments);
                bugId = em.merge(bugId);
            }
            em.remove(attachments);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Attachments> findAttachmentsEntities() {
        return findAttachmentsEntities(true, -1, -1);
    }

    public List<Attachments> findAttachmentsEntities(int maxResults, int firstResult) {
        return findAttachmentsEntities(false, maxResults, firstResult);
    }

    private List<Attachments> findAttachmentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attachments.class));
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

    public Attachments findAttachments(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attachments.class, id);
        } finally {
            em.close();
        }
    }
    
    public ArrayList<Attachments> findByBugIdFilename(Bugs bug, String filename){
        ArrayList<Attachments> attachments = new ArrayList<Attachments>();
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Attachments.findByBugIdFileName");
        q.setParameter("bugId", bug);
        q.setParameter("filename", filename);
        try{
            List<Attachments> list =  q.getResultList();
            for(Attachments a: list){
                attachments.add(a);
            }
            return attachments;
        }catch(Exception exp){
            return new ArrayList<Attachments>();
        }
    }

    public int getAttachmentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attachments> rt = cq.from(Attachments.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
