/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.AttachData;
import beans.Attachments;
import beans.Bugs;
import beans.Profiles;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import obspattern.BugListener;
import obspattern.BugSubiect;
import obspattern.SizeListener;
import obspattern.SizeSubject;
import utils.MigrateAttachments;

/**
 *
 * @author RO100051
 */
public class MainController implements SizeSubject, BugSubiect{
    private static MainController singleton;
    
    EntityManagerFactory emf;
    BugsJpaController bugsController;
    AttachDataJpaController attachController;
    AttachmentsJpaController attachementsController;
    ProfilesJpaController profilesController;
    public static Map props;
    String DEFAULT_MIME = "application/octet-stream";

   
    private MainController(){
        emf = Persistence.createEntityManagerFactory("File_migrationPU", props);
        bugsController = new BugsJpaController(emf);
        attachController = new AttachDataJpaController(emf);
        attachementsController = new AttachmentsJpaController(emf);
        profilesController = new ProfilesJpaController(emf);
        
    }
    public static MainController getInstance(){
        if(singleton==null) singleton = new MainController();
        return singleton;
    }
    
    public ArrayList<Bugs> getBugs(){
        ArrayList<Bugs> bugs = new ArrayList<>();
        List<Bugs> list = bugsController.findBugsEntities();
        for(Bugs b:list){
            bugs.add(b);
        }
        return bugs;
    }
    
    public void addAttachments(Attachments attachment){
        attachementsController.create(attachment);
        //System.out.println("Attachments: "  + attachment.toString());
    }
    public Profiles getProfile(int id){
        return profilesController.findProfiles(id);
    }
    
    public void addAttachData(AttachData attachData){
        try {
            attachController.create(attachData);
            //System.out.println("AttacData: "  + attachData.toString());
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Attachments> getByBugIdFilename(Bugs bug, String filename){
        return attachementsController.findByBugIdFilename(bug, filename);
    }
    
    public void deleteAttachments(int id){
        try {
            attachementsController.destroy(id);
            //System.out.println("AttachId: " + id + " was succesfully deleted from attachments DB!");
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
           // System.out.println("AttachId: " + id + " was NOT succesfully deleted from attachments DB!(Orph)");
        } catch (NonexistentEntityException ex){
            //System.out.println("AttachId: " + id + " was NOT succesfully deleted from attachments DB!");
        }
    }
    
    public void deleteAttachData(int id){
        try {
            attachController.destroy(id);
            //System.out.println("AttachId: " + id + " was succesfully deleted from attach_data DB!");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("AttachId: " + id + " was NOT succesfully deleted from attach_data DB!");
        }
    }
    
    public void migrateAttachment(String s, String path, String bugzillaPath, Bugs bug, long size){
        //add_attachments
        short isPatch = 0;
        Attachments attach = new Attachments();
        attach.setBugId(bug);
        attach.setCreationTs(new Date());
        attach.setModificationTime(new Date());
        attach.setDescription(s);
        attach.setMimetype(getMimeType(path,s));
        attach.setIspatch(isPatch);
        attach.setFilename(s);
        attach.setSubmitterId(MainController.getInstance().getProfile(1));
        attach.setIsobsolete(isPatch);
        attach.setIsprivate(isPatch);
        MainController.getInstance().addAttachments(attach);
        String id = String.valueOf(attach.getAttachId());
        //add attach_data
        byte[] thedata = new byte[0];
        AttachData attachData = new AttachData();
        attachData.setId(Integer.parseInt(id));
        attachData.setThedata(thedata);
        MainController.getInstance().addAttachData(attachData);

        //copy_file
        File sourceFile = new File(path + "\\" + s);
        String groupId = "group." + id.substring(id.length() - 2);
        String fileId = "attachment." + id;
        File destDir = new File(bugzillaPath + "attachments\\" + groupId);
        destDir.mkdirs();

        File destFile = new File(bugzillaPath + "attachments\\" + groupId + "\\" + fileId);

        Path sourcePath = sourceFile.toPath();
        Path destPath = destFile.toPath();

        try {
            Files.copy(sourcePath, destPath);
            notificaListener(size);
        } catch (IOException ex) {
            Logger.getLogger(MigrateAttachments.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void processed(Bugs bug){
        notificaListener(bug);
    }
    
    public String getMimeType(String path, String file) {
        String filePath = path + "\\" + file;
        try {
            if (Files.probeContentType(Paths.get(filePath)) != null) {
                //System.out.println("File " + filePath + " is " + Files.probeContentType(Paths.get(filePath)));
                return Files.probeContentType(Paths.get(filePath));
            } else {
                return DEFAULT_MIME;
            }
        } catch (IOException ex) {
            Logger.getLogger(MigrateAttachments.class.getName()).log(Level.SEVERE, null, ex);
            return DEFAULT_MIME;
        }

    }

    @Override
    public void addListener(SizeListener sl) {
        listener.add(sl);
    }

    @Override
    public void notificaListener(long size) {
        for(SizeListener sl: listener){
            sl.performAction(size);
        }
    }

    @Override
    public void addBugListener(BugListener list) {
        listeners.add(list);
    }

    @Override
    public void notificaListener(Bugs bug) {
        for(BugListener b: listeners){
            b.performAction(bug);
        }
    }
   
}
