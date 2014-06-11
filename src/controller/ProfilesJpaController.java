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
import java.util.ArrayList;
import java.util.Collection;
import beans.Components;
import beans.Bugs;
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
public class ProfilesJpaController implements Serializable {

    public ProfilesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profiles profiles) {
        if (profiles.getProfilesCollection() == null) {
            profiles.setProfilesCollection(new ArrayList<Profiles>());
        }
        if (profiles.getProfilesCollection1() == null) {
            profiles.setProfilesCollection1(new ArrayList<Profiles>());
        }
        if (profiles.getComponentsCollection() == null) {
            profiles.setComponentsCollection(new ArrayList<Components>());
        }
        if (profiles.getBugsCollection() == null) {
            profiles.setBugsCollection(new ArrayList<Bugs>());
        }
        if (profiles.getBugsCollection1() == null) {
            profiles.setBugsCollection1(new ArrayList<Bugs>());
        }
        if (profiles.getBugsCollection2() == null) {
            profiles.setBugsCollection2(new ArrayList<Bugs>());
        }
        if (profiles.getBugsCollection3() == null) {
            profiles.setBugsCollection3(new ArrayList<Bugs>());
        }
        if (profiles.getAttachmentsCollection() == null) {
            profiles.setAttachmentsCollection(new ArrayList<Attachments>());
        }
        if (profiles.getComponentsCollection1() == null) {
            profiles.setComponentsCollection1(new ArrayList<Components>());
        }
        if (profiles.getComponentsCollection2() == null) {
            profiles.setComponentsCollection2(new ArrayList<Components>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Profiles> attachedProfilesCollection = new ArrayList<Profiles>();
            for (Profiles profilesCollectionProfilesToAttach : profiles.getProfilesCollection()) {
                profilesCollectionProfilesToAttach = em.getReference(profilesCollectionProfilesToAttach.getClass(), profilesCollectionProfilesToAttach.getUserid());
                attachedProfilesCollection.add(profilesCollectionProfilesToAttach);
            }
            profiles.setProfilesCollection(attachedProfilesCollection);
            Collection<Profiles> attachedProfilesCollection1 = new ArrayList<Profiles>();
            for (Profiles profilesCollection1ProfilesToAttach : profiles.getProfilesCollection1()) {
                profilesCollection1ProfilesToAttach = em.getReference(profilesCollection1ProfilesToAttach.getClass(), profilesCollection1ProfilesToAttach.getUserid());
                attachedProfilesCollection1.add(profilesCollection1ProfilesToAttach);
            }
            profiles.setProfilesCollection1(attachedProfilesCollection1);
            Collection<Components> attachedComponentsCollection = new ArrayList<Components>();
            for (Components componentsCollectionComponentsToAttach : profiles.getComponentsCollection()) {
                componentsCollectionComponentsToAttach = em.getReference(componentsCollectionComponentsToAttach.getClass(), componentsCollectionComponentsToAttach.getId());
                attachedComponentsCollection.add(componentsCollectionComponentsToAttach);
            }
            profiles.setComponentsCollection(attachedComponentsCollection);
            Collection<Bugs> attachedBugsCollection = new ArrayList<Bugs>();
            for (Bugs bugsCollectionBugsToAttach : profiles.getBugsCollection()) {
                bugsCollectionBugsToAttach = em.getReference(bugsCollectionBugsToAttach.getClass(), bugsCollectionBugsToAttach.getBugId());
                attachedBugsCollection.add(bugsCollectionBugsToAttach);
            }
            profiles.setBugsCollection(attachedBugsCollection);
            Collection<Bugs> attachedBugsCollection1 = new ArrayList<Bugs>();
            for (Bugs bugsCollection1BugsToAttach : profiles.getBugsCollection1()) {
                bugsCollection1BugsToAttach = em.getReference(bugsCollection1BugsToAttach.getClass(), bugsCollection1BugsToAttach.getBugId());
                attachedBugsCollection1.add(bugsCollection1BugsToAttach);
            }
            profiles.setBugsCollection1(attachedBugsCollection1);
            Collection<Bugs> attachedBugsCollection2 = new ArrayList<Bugs>();
            for (Bugs bugsCollection2BugsToAttach : profiles.getBugsCollection2()) {
                bugsCollection2BugsToAttach = em.getReference(bugsCollection2BugsToAttach.getClass(), bugsCollection2BugsToAttach.getBugId());
                attachedBugsCollection2.add(bugsCollection2BugsToAttach);
            }
            profiles.setBugsCollection2(attachedBugsCollection2);
            Collection<Bugs> attachedBugsCollection3 = new ArrayList<Bugs>();
            for (Bugs bugsCollection3BugsToAttach : profiles.getBugsCollection3()) {
                bugsCollection3BugsToAttach = em.getReference(bugsCollection3BugsToAttach.getClass(), bugsCollection3BugsToAttach.getBugId());
                attachedBugsCollection3.add(bugsCollection3BugsToAttach);
            }
            profiles.setBugsCollection3(attachedBugsCollection3);
            Collection<Attachments> attachedAttachmentsCollection = new ArrayList<Attachments>();
            for (Attachments attachmentsCollectionAttachmentsToAttach : profiles.getAttachmentsCollection()) {
                attachmentsCollectionAttachmentsToAttach = em.getReference(attachmentsCollectionAttachmentsToAttach.getClass(), attachmentsCollectionAttachmentsToAttach.getAttachId());
                attachedAttachmentsCollection.add(attachmentsCollectionAttachmentsToAttach);
            }
            profiles.setAttachmentsCollection(attachedAttachmentsCollection);
            Collection<Components> attachedComponentsCollection1 = new ArrayList<Components>();
            for (Components componentsCollection1ComponentsToAttach : profiles.getComponentsCollection1()) {
                componentsCollection1ComponentsToAttach = em.getReference(componentsCollection1ComponentsToAttach.getClass(), componentsCollection1ComponentsToAttach.getId());
                attachedComponentsCollection1.add(componentsCollection1ComponentsToAttach);
            }
            profiles.setComponentsCollection1(attachedComponentsCollection1);
            Collection<Components> attachedComponentsCollection2 = new ArrayList<Components>();
            for (Components componentsCollection2ComponentsToAttach : profiles.getComponentsCollection2()) {
                componentsCollection2ComponentsToAttach = em.getReference(componentsCollection2ComponentsToAttach.getClass(), componentsCollection2ComponentsToAttach.getId());
                attachedComponentsCollection2.add(componentsCollection2ComponentsToAttach);
            }
            profiles.setComponentsCollection2(attachedComponentsCollection2);
            em.persist(profiles);
            for (Profiles profilesCollectionProfiles : profiles.getProfilesCollection()) {
                profilesCollectionProfiles.getProfilesCollection().add(profiles);
                profilesCollectionProfiles = em.merge(profilesCollectionProfiles);
            }
            for (Profiles profilesCollection1Profiles : profiles.getProfilesCollection1()) {
                profilesCollection1Profiles.getProfilesCollection().add(profiles);
                profilesCollection1Profiles = em.merge(profilesCollection1Profiles);
            }
            for (Components componentsCollectionComponents : profiles.getComponentsCollection()) {
                componentsCollectionComponents.getProfilesCollection().add(profiles);
                componentsCollectionComponents = em.merge(componentsCollectionComponents);
            }
            for (Bugs bugsCollectionBugs : profiles.getBugsCollection()) {
                bugsCollectionBugs.getProfilesCollection().add(profiles);
                bugsCollectionBugs = em.merge(bugsCollectionBugs);
            }
            for (Bugs bugsCollection1Bugs : profiles.getBugsCollection1()) {
                Profiles oldReporterOfBugsCollection1Bugs = bugsCollection1Bugs.getReporter();
                bugsCollection1Bugs.setReporter(profiles);
                bugsCollection1Bugs = em.merge(bugsCollection1Bugs);
                if (oldReporterOfBugsCollection1Bugs != null) {
                    oldReporterOfBugsCollection1Bugs.getBugsCollection1().remove(bugsCollection1Bugs);
                    oldReporterOfBugsCollection1Bugs = em.merge(oldReporterOfBugsCollection1Bugs);
                }
            }
            for (Bugs bugsCollection2Bugs : profiles.getBugsCollection2()) {
                Profiles oldQaContactOfBugsCollection2Bugs = bugsCollection2Bugs.getQaContact();
                bugsCollection2Bugs.setQaContact(profiles);
                bugsCollection2Bugs = em.merge(bugsCollection2Bugs);
                if (oldQaContactOfBugsCollection2Bugs != null) {
                    oldQaContactOfBugsCollection2Bugs.getBugsCollection2().remove(bugsCollection2Bugs);
                    oldQaContactOfBugsCollection2Bugs = em.merge(oldQaContactOfBugsCollection2Bugs);
                }
            }
            for (Bugs bugsCollection3Bugs : profiles.getBugsCollection3()) {
                Profiles oldAssignedToOfBugsCollection3Bugs = bugsCollection3Bugs.getAssignedTo();
                bugsCollection3Bugs.setAssignedTo(profiles);
                bugsCollection3Bugs = em.merge(bugsCollection3Bugs);
                if (oldAssignedToOfBugsCollection3Bugs != null) {
                    oldAssignedToOfBugsCollection3Bugs.getBugsCollection3().remove(bugsCollection3Bugs);
                    oldAssignedToOfBugsCollection3Bugs = em.merge(oldAssignedToOfBugsCollection3Bugs);
                }
            }
            for (Attachments attachmentsCollectionAttachments : profiles.getAttachmentsCollection()) {
                Profiles oldSubmitterIdOfAttachmentsCollectionAttachments = attachmentsCollectionAttachments.getSubmitterId();
                attachmentsCollectionAttachments.setSubmitterId(profiles);
                attachmentsCollectionAttachments = em.merge(attachmentsCollectionAttachments);
                if (oldSubmitterIdOfAttachmentsCollectionAttachments != null) {
                    oldSubmitterIdOfAttachmentsCollectionAttachments.getAttachmentsCollection().remove(attachmentsCollectionAttachments);
                    oldSubmitterIdOfAttachmentsCollectionAttachments = em.merge(oldSubmitterIdOfAttachmentsCollectionAttachments);
                }
            }
            for (Components componentsCollection1Components : profiles.getComponentsCollection1()) {
                Profiles oldInitialqacontactOfComponentsCollection1Components = componentsCollection1Components.getInitialqacontact();
                componentsCollection1Components.setInitialqacontact(profiles);
                componentsCollection1Components = em.merge(componentsCollection1Components);
                if (oldInitialqacontactOfComponentsCollection1Components != null) {
                    oldInitialqacontactOfComponentsCollection1Components.getComponentsCollection1().remove(componentsCollection1Components);
                    oldInitialqacontactOfComponentsCollection1Components = em.merge(oldInitialqacontactOfComponentsCollection1Components);
                }
            }
            for (Components componentsCollection2Components : profiles.getComponentsCollection2()) {
                Profiles oldInitialownerOfComponentsCollection2Components = componentsCollection2Components.getInitialowner();
                componentsCollection2Components.setInitialowner(profiles);
                componentsCollection2Components = em.merge(componentsCollection2Components);
                if (oldInitialownerOfComponentsCollection2Components != null) {
                    oldInitialownerOfComponentsCollection2Components.getComponentsCollection2().remove(componentsCollection2Components);
                    oldInitialownerOfComponentsCollection2Components = em.merge(oldInitialownerOfComponentsCollection2Components);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Profiles profiles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profiles persistentProfiles = em.find(Profiles.class, profiles.getUserid());
            Collection<Profiles> profilesCollectionOld = persistentProfiles.getProfilesCollection();
            Collection<Profiles> profilesCollectionNew = profiles.getProfilesCollection();
            Collection<Profiles> profilesCollection1Old = persistentProfiles.getProfilesCollection1();
            Collection<Profiles> profilesCollection1New = profiles.getProfilesCollection1();
            Collection<Components> componentsCollectionOld = persistentProfiles.getComponentsCollection();
            Collection<Components> componentsCollectionNew = profiles.getComponentsCollection();
            Collection<Bugs> bugsCollectionOld = persistentProfiles.getBugsCollection();
            Collection<Bugs> bugsCollectionNew = profiles.getBugsCollection();
            Collection<Bugs> bugsCollection1Old = persistentProfiles.getBugsCollection1();
            Collection<Bugs> bugsCollection1New = profiles.getBugsCollection1();
            Collection<Bugs> bugsCollection2Old = persistentProfiles.getBugsCollection2();
            Collection<Bugs> bugsCollection2New = profiles.getBugsCollection2();
            Collection<Bugs> bugsCollection3Old = persistentProfiles.getBugsCollection3();
            Collection<Bugs> bugsCollection3New = profiles.getBugsCollection3();
            Collection<Attachments> attachmentsCollectionOld = persistentProfiles.getAttachmentsCollection();
            Collection<Attachments> attachmentsCollectionNew = profiles.getAttachmentsCollection();
            Collection<Components> componentsCollection1Old = persistentProfiles.getComponentsCollection1();
            Collection<Components> componentsCollection1New = profiles.getComponentsCollection1();
            Collection<Components> componentsCollection2Old = persistentProfiles.getComponentsCollection2();
            Collection<Components> componentsCollection2New = profiles.getComponentsCollection2();
            List<String> illegalOrphanMessages = null;
            for (Bugs bugsCollection1OldBugs : bugsCollection1Old) {
                if (!bugsCollection1New.contains(bugsCollection1OldBugs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bugs " + bugsCollection1OldBugs + " since its reporter field is not nullable.");
                }
            }
            for (Bugs bugsCollection3OldBugs : bugsCollection3Old) {
                if (!bugsCollection3New.contains(bugsCollection3OldBugs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bugs " + bugsCollection3OldBugs + " since its assignedTo field is not nullable.");
                }
            }
            for (Attachments attachmentsCollectionOldAttachments : attachmentsCollectionOld) {
                if (!attachmentsCollectionNew.contains(attachmentsCollectionOldAttachments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Attachments " + attachmentsCollectionOldAttachments + " since its submitterId field is not nullable.");
                }
            }
            for (Components componentsCollection2OldComponents : componentsCollection2Old) {
                if (!componentsCollection2New.contains(componentsCollection2OldComponents)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Components " + componentsCollection2OldComponents + " since its initialowner field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Profiles> attachedProfilesCollectionNew = new ArrayList<Profiles>();
            for (Profiles profilesCollectionNewProfilesToAttach : profilesCollectionNew) {
                profilesCollectionNewProfilesToAttach = em.getReference(profilesCollectionNewProfilesToAttach.getClass(), profilesCollectionNewProfilesToAttach.getUserid());
                attachedProfilesCollectionNew.add(profilesCollectionNewProfilesToAttach);
            }
            profilesCollectionNew = attachedProfilesCollectionNew;
            profiles.setProfilesCollection(profilesCollectionNew);
            Collection<Profiles> attachedProfilesCollection1New = new ArrayList<Profiles>();
            for (Profiles profilesCollection1NewProfilesToAttach : profilesCollection1New) {
                profilesCollection1NewProfilesToAttach = em.getReference(profilesCollection1NewProfilesToAttach.getClass(), profilesCollection1NewProfilesToAttach.getUserid());
                attachedProfilesCollection1New.add(profilesCollection1NewProfilesToAttach);
            }
            profilesCollection1New = attachedProfilesCollection1New;
            profiles.setProfilesCollection1(profilesCollection1New);
            Collection<Components> attachedComponentsCollectionNew = new ArrayList<Components>();
            for (Components componentsCollectionNewComponentsToAttach : componentsCollectionNew) {
                componentsCollectionNewComponentsToAttach = em.getReference(componentsCollectionNewComponentsToAttach.getClass(), componentsCollectionNewComponentsToAttach.getId());
                attachedComponentsCollectionNew.add(componentsCollectionNewComponentsToAttach);
            }
            componentsCollectionNew = attachedComponentsCollectionNew;
            profiles.setComponentsCollection(componentsCollectionNew);
            Collection<Bugs> attachedBugsCollectionNew = new ArrayList<Bugs>();
            for (Bugs bugsCollectionNewBugsToAttach : bugsCollectionNew) {
                bugsCollectionNewBugsToAttach = em.getReference(bugsCollectionNewBugsToAttach.getClass(), bugsCollectionNewBugsToAttach.getBugId());
                attachedBugsCollectionNew.add(bugsCollectionNewBugsToAttach);
            }
            bugsCollectionNew = attachedBugsCollectionNew;
            profiles.setBugsCollection(bugsCollectionNew);
            Collection<Bugs> attachedBugsCollection1New = new ArrayList<Bugs>();
            for (Bugs bugsCollection1NewBugsToAttach : bugsCollection1New) {
                bugsCollection1NewBugsToAttach = em.getReference(bugsCollection1NewBugsToAttach.getClass(), bugsCollection1NewBugsToAttach.getBugId());
                attachedBugsCollection1New.add(bugsCollection1NewBugsToAttach);
            }
            bugsCollection1New = attachedBugsCollection1New;
            profiles.setBugsCollection1(bugsCollection1New);
            Collection<Bugs> attachedBugsCollection2New = new ArrayList<Bugs>();
            for (Bugs bugsCollection2NewBugsToAttach : bugsCollection2New) {
                bugsCollection2NewBugsToAttach = em.getReference(bugsCollection2NewBugsToAttach.getClass(), bugsCollection2NewBugsToAttach.getBugId());
                attachedBugsCollection2New.add(bugsCollection2NewBugsToAttach);
            }
            bugsCollection2New = attachedBugsCollection2New;
            profiles.setBugsCollection2(bugsCollection2New);
            Collection<Bugs> attachedBugsCollection3New = new ArrayList<Bugs>();
            for (Bugs bugsCollection3NewBugsToAttach : bugsCollection3New) {
                bugsCollection3NewBugsToAttach = em.getReference(bugsCollection3NewBugsToAttach.getClass(), bugsCollection3NewBugsToAttach.getBugId());
                attachedBugsCollection3New.add(bugsCollection3NewBugsToAttach);
            }
            bugsCollection3New = attachedBugsCollection3New;
            profiles.setBugsCollection3(bugsCollection3New);
            Collection<Attachments> attachedAttachmentsCollectionNew = new ArrayList<Attachments>();
            for (Attachments attachmentsCollectionNewAttachmentsToAttach : attachmentsCollectionNew) {
                attachmentsCollectionNewAttachmentsToAttach = em.getReference(attachmentsCollectionNewAttachmentsToAttach.getClass(), attachmentsCollectionNewAttachmentsToAttach.getAttachId());
                attachedAttachmentsCollectionNew.add(attachmentsCollectionNewAttachmentsToAttach);
            }
            attachmentsCollectionNew = attachedAttachmentsCollectionNew;
            profiles.setAttachmentsCollection(attachmentsCollectionNew);
            Collection<Components> attachedComponentsCollection1New = new ArrayList<Components>();
            for (Components componentsCollection1NewComponentsToAttach : componentsCollection1New) {
                componentsCollection1NewComponentsToAttach = em.getReference(componentsCollection1NewComponentsToAttach.getClass(), componentsCollection1NewComponentsToAttach.getId());
                attachedComponentsCollection1New.add(componentsCollection1NewComponentsToAttach);
            }
            componentsCollection1New = attachedComponentsCollection1New;
            profiles.setComponentsCollection1(componentsCollection1New);
            Collection<Components> attachedComponentsCollection2New = new ArrayList<Components>();
            for (Components componentsCollection2NewComponentsToAttach : componentsCollection2New) {
                componentsCollection2NewComponentsToAttach = em.getReference(componentsCollection2NewComponentsToAttach.getClass(), componentsCollection2NewComponentsToAttach.getId());
                attachedComponentsCollection2New.add(componentsCollection2NewComponentsToAttach);
            }
            componentsCollection2New = attachedComponentsCollection2New;
            profiles.setComponentsCollection2(componentsCollection2New);
            profiles = em.merge(profiles);
            for (Profiles profilesCollectionOldProfiles : profilesCollectionOld) {
                if (!profilesCollectionNew.contains(profilesCollectionOldProfiles)) {
                    profilesCollectionOldProfiles.getProfilesCollection().remove(profiles);
                    profilesCollectionOldProfiles = em.merge(profilesCollectionOldProfiles);
                }
            }
            for (Profiles profilesCollectionNewProfiles : profilesCollectionNew) {
                if (!profilesCollectionOld.contains(profilesCollectionNewProfiles)) {
                    profilesCollectionNewProfiles.getProfilesCollection().add(profiles);
                    profilesCollectionNewProfiles = em.merge(profilesCollectionNewProfiles);
                }
            }
            for (Profiles profilesCollection1OldProfiles : profilesCollection1Old) {
                if (!profilesCollection1New.contains(profilesCollection1OldProfiles)) {
                    profilesCollection1OldProfiles.getProfilesCollection().remove(profiles);
                    profilesCollection1OldProfiles = em.merge(profilesCollection1OldProfiles);
                }
            }
            for (Profiles profilesCollection1NewProfiles : profilesCollection1New) {
                if (!profilesCollection1Old.contains(profilesCollection1NewProfiles)) {
                    profilesCollection1NewProfiles.getProfilesCollection().add(profiles);
                    profilesCollection1NewProfiles = em.merge(profilesCollection1NewProfiles);
                }
            }
            for (Components componentsCollectionOldComponents : componentsCollectionOld) {
                if (!componentsCollectionNew.contains(componentsCollectionOldComponents)) {
                    componentsCollectionOldComponents.getProfilesCollection().remove(profiles);
                    componentsCollectionOldComponents = em.merge(componentsCollectionOldComponents);
                }
            }
            for (Components componentsCollectionNewComponents : componentsCollectionNew) {
                if (!componentsCollectionOld.contains(componentsCollectionNewComponents)) {
                    componentsCollectionNewComponents.getProfilesCollection().add(profiles);
                    componentsCollectionNewComponents = em.merge(componentsCollectionNewComponents);
                }
            }
            for (Bugs bugsCollectionOldBugs : bugsCollectionOld) {
                if (!bugsCollectionNew.contains(bugsCollectionOldBugs)) {
                    bugsCollectionOldBugs.getProfilesCollection().remove(profiles);
                    bugsCollectionOldBugs = em.merge(bugsCollectionOldBugs);
                }
            }
            for (Bugs bugsCollectionNewBugs : bugsCollectionNew) {
                if (!bugsCollectionOld.contains(bugsCollectionNewBugs)) {
                    bugsCollectionNewBugs.getProfilesCollection().add(profiles);
                    bugsCollectionNewBugs = em.merge(bugsCollectionNewBugs);
                }
            }
            for (Bugs bugsCollection1NewBugs : bugsCollection1New) {
                if (!bugsCollection1Old.contains(bugsCollection1NewBugs)) {
                    Profiles oldReporterOfBugsCollection1NewBugs = bugsCollection1NewBugs.getReporter();
                    bugsCollection1NewBugs.setReporter(profiles);
                    bugsCollection1NewBugs = em.merge(bugsCollection1NewBugs);
                    if (oldReporterOfBugsCollection1NewBugs != null && !oldReporterOfBugsCollection1NewBugs.equals(profiles)) {
                        oldReporterOfBugsCollection1NewBugs.getBugsCollection1().remove(bugsCollection1NewBugs);
                        oldReporterOfBugsCollection1NewBugs = em.merge(oldReporterOfBugsCollection1NewBugs);
                    }
                }
            }
            for (Bugs bugsCollection2OldBugs : bugsCollection2Old) {
                if (!bugsCollection2New.contains(bugsCollection2OldBugs)) {
                    bugsCollection2OldBugs.setQaContact(null);
                    bugsCollection2OldBugs = em.merge(bugsCollection2OldBugs);
                }
            }
            for (Bugs bugsCollection2NewBugs : bugsCollection2New) {
                if (!bugsCollection2Old.contains(bugsCollection2NewBugs)) {
                    Profiles oldQaContactOfBugsCollection2NewBugs = bugsCollection2NewBugs.getQaContact();
                    bugsCollection2NewBugs.setQaContact(profiles);
                    bugsCollection2NewBugs = em.merge(bugsCollection2NewBugs);
                    if (oldQaContactOfBugsCollection2NewBugs != null && !oldQaContactOfBugsCollection2NewBugs.equals(profiles)) {
                        oldQaContactOfBugsCollection2NewBugs.getBugsCollection2().remove(bugsCollection2NewBugs);
                        oldQaContactOfBugsCollection2NewBugs = em.merge(oldQaContactOfBugsCollection2NewBugs);
                    }
                }
            }
            for (Bugs bugsCollection3NewBugs : bugsCollection3New) {
                if (!bugsCollection3Old.contains(bugsCollection3NewBugs)) {
                    Profiles oldAssignedToOfBugsCollection3NewBugs = bugsCollection3NewBugs.getAssignedTo();
                    bugsCollection3NewBugs.setAssignedTo(profiles);
                    bugsCollection3NewBugs = em.merge(bugsCollection3NewBugs);
                    if (oldAssignedToOfBugsCollection3NewBugs != null && !oldAssignedToOfBugsCollection3NewBugs.equals(profiles)) {
                        oldAssignedToOfBugsCollection3NewBugs.getBugsCollection3().remove(bugsCollection3NewBugs);
                        oldAssignedToOfBugsCollection3NewBugs = em.merge(oldAssignedToOfBugsCollection3NewBugs);
                    }
                }
            }
            for (Attachments attachmentsCollectionNewAttachments : attachmentsCollectionNew) {
                if (!attachmentsCollectionOld.contains(attachmentsCollectionNewAttachments)) {
                    Profiles oldSubmitterIdOfAttachmentsCollectionNewAttachments = attachmentsCollectionNewAttachments.getSubmitterId();
                    attachmentsCollectionNewAttachments.setSubmitterId(profiles);
                    attachmentsCollectionNewAttachments = em.merge(attachmentsCollectionNewAttachments);
                    if (oldSubmitterIdOfAttachmentsCollectionNewAttachments != null && !oldSubmitterIdOfAttachmentsCollectionNewAttachments.equals(profiles)) {
                        oldSubmitterIdOfAttachmentsCollectionNewAttachments.getAttachmentsCollection().remove(attachmentsCollectionNewAttachments);
                        oldSubmitterIdOfAttachmentsCollectionNewAttachments = em.merge(oldSubmitterIdOfAttachmentsCollectionNewAttachments);
                    }
                }
            }
            for (Components componentsCollection1OldComponents : componentsCollection1Old) {
                if (!componentsCollection1New.contains(componentsCollection1OldComponents)) {
                    componentsCollection1OldComponents.setInitialqacontact(null);
                    componentsCollection1OldComponents = em.merge(componentsCollection1OldComponents);
                }
            }
            for (Components componentsCollection1NewComponents : componentsCollection1New) {
                if (!componentsCollection1Old.contains(componentsCollection1NewComponents)) {
                    Profiles oldInitialqacontactOfComponentsCollection1NewComponents = componentsCollection1NewComponents.getInitialqacontact();
                    componentsCollection1NewComponents.setInitialqacontact(profiles);
                    componentsCollection1NewComponents = em.merge(componentsCollection1NewComponents);
                    if (oldInitialqacontactOfComponentsCollection1NewComponents != null && !oldInitialqacontactOfComponentsCollection1NewComponents.equals(profiles)) {
                        oldInitialqacontactOfComponentsCollection1NewComponents.getComponentsCollection1().remove(componentsCollection1NewComponents);
                        oldInitialqacontactOfComponentsCollection1NewComponents = em.merge(oldInitialqacontactOfComponentsCollection1NewComponents);
                    }
                }
            }
            for (Components componentsCollection2NewComponents : componentsCollection2New) {
                if (!componentsCollection2Old.contains(componentsCollection2NewComponents)) {
                    Profiles oldInitialownerOfComponentsCollection2NewComponents = componentsCollection2NewComponents.getInitialowner();
                    componentsCollection2NewComponents.setInitialowner(profiles);
                    componentsCollection2NewComponents = em.merge(componentsCollection2NewComponents);
                    if (oldInitialownerOfComponentsCollection2NewComponents != null && !oldInitialownerOfComponentsCollection2NewComponents.equals(profiles)) {
                        oldInitialownerOfComponentsCollection2NewComponents.getComponentsCollection2().remove(componentsCollection2NewComponents);
                        oldInitialownerOfComponentsCollection2NewComponents = em.merge(oldInitialownerOfComponentsCollection2NewComponents);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = profiles.getUserid();
                if (findProfiles(id) == null) {
                    throw new NonexistentEntityException("The profiles with id " + id + " no longer exists.");
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
            Profiles profiles;
            try {
                profiles = em.getReference(Profiles.class, id);
                profiles.getUserid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profiles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bugs> bugsCollection1OrphanCheck = profiles.getBugsCollection1();
            for (Bugs bugsCollection1OrphanCheckBugs : bugsCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profiles (" + profiles + ") cannot be destroyed since the Bugs " + bugsCollection1OrphanCheckBugs + " in its bugsCollection1 field has a non-nullable reporter field.");
            }
            Collection<Bugs> bugsCollection3OrphanCheck = profiles.getBugsCollection3();
            for (Bugs bugsCollection3OrphanCheckBugs : bugsCollection3OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profiles (" + profiles + ") cannot be destroyed since the Bugs " + bugsCollection3OrphanCheckBugs + " in its bugsCollection3 field has a non-nullable assignedTo field.");
            }
            Collection<Attachments> attachmentsCollectionOrphanCheck = profiles.getAttachmentsCollection();
            for (Attachments attachmentsCollectionOrphanCheckAttachments : attachmentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profiles (" + profiles + ") cannot be destroyed since the Attachments " + attachmentsCollectionOrphanCheckAttachments + " in its attachmentsCollection field has a non-nullable submitterId field.");
            }
            Collection<Components> componentsCollection2OrphanCheck = profiles.getComponentsCollection2();
            for (Components componentsCollection2OrphanCheckComponents : componentsCollection2OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profiles (" + profiles + ") cannot be destroyed since the Components " + componentsCollection2OrphanCheckComponents + " in its componentsCollection2 field has a non-nullable initialowner field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Profiles> profilesCollection = profiles.getProfilesCollection();
            for (Profiles profilesCollectionProfiles : profilesCollection) {
                profilesCollectionProfiles.getProfilesCollection().remove(profiles);
                profilesCollectionProfiles = em.merge(profilesCollectionProfiles);
            }
            Collection<Profiles> profilesCollection1 = profiles.getProfilesCollection1();
            for (Profiles profilesCollection1Profiles : profilesCollection1) {
                profilesCollection1Profiles.getProfilesCollection().remove(profiles);
                profilesCollection1Profiles = em.merge(profilesCollection1Profiles);
            }
            Collection<Components> componentsCollection = profiles.getComponentsCollection();
            for (Components componentsCollectionComponents : componentsCollection) {
                componentsCollectionComponents.getProfilesCollection().remove(profiles);
                componentsCollectionComponents = em.merge(componentsCollectionComponents);
            }
            Collection<Bugs> bugsCollection = profiles.getBugsCollection();
            for (Bugs bugsCollectionBugs : bugsCollection) {
                bugsCollectionBugs.getProfilesCollection().remove(profiles);
                bugsCollectionBugs = em.merge(bugsCollectionBugs);
            }
            Collection<Bugs> bugsCollection2 = profiles.getBugsCollection2();
            for (Bugs bugsCollection2Bugs : bugsCollection2) {
                bugsCollection2Bugs.setQaContact(null);
                bugsCollection2Bugs = em.merge(bugsCollection2Bugs);
            }
            Collection<Components> componentsCollection1 = profiles.getComponentsCollection1();
            for (Components componentsCollection1Components : componentsCollection1) {
                componentsCollection1Components.setInitialqacontact(null);
                componentsCollection1Components = em.merge(componentsCollection1Components);
            }
            em.remove(profiles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Profiles> findProfilesEntities() {
        return findProfilesEntities(true, -1, -1);
    }

    public List<Profiles> findProfilesEntities(int maxResults, int firstResult) {
        return findProfilesEntities(false, maxResults, firstResult);
    }

    private List<Profiles> findProfilesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profiles.class));
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

    public Profiles findProfiles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profiles.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfilesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profiles> rt = cq.from(Profiles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
