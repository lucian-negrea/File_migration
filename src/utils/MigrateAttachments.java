/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import beans.Attachments;
import beans.Bugs;
import controller.MainController;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;
import obspattern.BugListener;
import obspattern.BugSubiect;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author RO100051
 */
public class MigrateAttachments extends SwingWorker<Integer, String>{

    Bugs bug;
    String path;
    DefaultListModel model = new DefaultListModel();
    ArrayList<String> filesName;
    
    String bugzillaPath;
    int FILESIZELIMIT = 100000;
    
    

    public MigrateAttachments(String bugzillaPath, Bugs bug, String path, DefaultListModel model) {
        this.bug = bug;
        this.bugzillaPath = bugzillaPath;
        this.path = path;
        this.model = model;
    }

    public ArrayList<String> getFilesName(String path) {
        ArrayList<String> filesName = new ArrayList();
        File[] files = new File(path).listFiles();
        for (int i = 0; i < files.length; i++) {
            filesName.add(files[i].getName());
        }
        MainController.getInstance().processed(bug);
        return filesName;
    }

    public long getFileSize(String file) {
        String filePath = path + "\\" + file;
        File f = new File(filePath);
        //System.out.println("File " + filePath + " has " + f.length()/1000000);
        return f.length() / 1000;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        ArrayList<String> fileNames = getFilesName(path);
        for (String s : fileNames) {
            //System.out.println("Checking " + s);
            long size = getFileSize(s);
            if (size > FILESIZELIMIT) {
                publish(bug.toString() + ": file " + s + " will not be transffered due to it size: " + size + " kb.");
            } else {
                ArrayList<Attachments> attachments = MainController.getInstance().getByBugIdFilename(bug, s);
                if (!attachments.isEmpty()) {
                    //publish(bug + " has " + attachments.size() + " attachments entries in DB.Checking if was already migrated!");
                    for (Attachments a : attachments) {
                        String id = a.getAttachId().toString();
                        String groupId = "group." + id.substring(id.length() - 2);
                        String fileId = "attachment." + id;
                        try {
                            File sourceFile = new File(path + "\\" + s);
                            File destFile = new File(bugzillaPath + "attachments\\" + groupId + "\\" + fileId);
                            if (!destFile.exists()) {
                                //File defined in database but not present on disk. Cleanup to be performed
                                publish("Attachment " + s + "/" + bug + " is present in DB(" + a.getAttachId() + ") but " + destFile.toString() + " is missing from disk. DB cleanup performed.");
                                MainController.getInstance().deleteAttachments(a.getAttachId());
                            }
                            if (FileUtils.contentEquals(destFile, sourceFile)) {
                                publish(bug + " :" + s + " is present in DB(" + a.getAttachId() + ") and " + destFile.toString() + " is already present disk => already migrated");
                            } else {
                                long startTime = new Date().getTime();
                                MainController.getInstance().migrateAttachment(s, path, bugzillaPath, bug, size);
                                long elapsedTime = new Date().getTime() - startTime;
                                publish(bug.toString() + ": file " + s + " (" + size + " kb) transffered in: " + elapsedTime / 1000 + "sec.");
                                
                            }

                        } catch (Exception exp) {
                            publish(exp.toString());
                        }
                    }
                } else {
                    long startTime = new Date().getTime();
                    MainController.getInstance().migrateAttachment(s, path, bugzillaPath, bug,size);
                    long elapsedTime = new Date().getTime() - startTime;
                    publish(bug.toString() + ": file " + s + " (" + size + " kb) transffered in: " + elapsedTime / 1000 + "sec.");

                }
            }
        }
        return bug.getBugId();
    }

    @Override
    protected void process(final List<String> list) {
        for (String s : list) {
            model.insertElementAt(s, 0);
        }
    }

    @Override
    public void done() {
        try{
           //System.out.println(bug.getBugId() + " migrated = " + get());
            
        }catch(Exception exp){
            //System.out.println(bug.getBugId() + " " + exp);
        }
    }
}
