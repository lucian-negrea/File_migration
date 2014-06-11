/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Bugs;
import controller.MainController;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import obspattern.BugListener;
import obspattern.SizeListener;
import utils.MigrateAttachments;

/**
 *
 * @author RO100051
 */
public class ProcessingForm extends javax.swing.JFrame {

    DefaultTableModel tmodel = new DefaultTableModel();
    ArrayList<String> directoryList;
    ArrayList<Bugs> bugs;
    String path;
    String bugzillaPath;
    int threads;
    DefaultListModel<String> model = new DefaultListModel<>();
    ArrayList<Bugs> processed = new ArrayList<>();
    int bugsWithAttachmentsOnTrack;
    int transferred = 0;

    /**
     * Creates new form MainForm
     */
    public ProcessingForm(final int threads, final String bugzillaPath, final String path, final ArrayList<Bugs> bugs, final ArrayList<String> directoryList) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Attachments migration");
        jTable1.setModel(tmodel);
        String[] columns = new String[4];
        columns[0] = "Bug Id";
        columns[1] = "Track directory";
        columns[2] = "No of attachments";
        columns[3] = "Total size";
        tmodel.setColumnIdentifiers(columns);
        jPanel1.setVisible(true);
        jProgressBar1.setVisible(true);
        jList1.setModel(model);
        this.threads = threads;
        this.path = path;
        this.bugzillaPath = bugzillaPath;
        this.bugs = bugs;
        this.directoryList = directoryList;

        MainController.getInstance().addBugListener(
                new BugListener() {

                    @Override
                    public void performAction(Bugs bug) {
                        processed.add(bug);
                        int progress = processed.size() * 100 / bugsWithAttachmentsOnTrack;
                        jProgressBar1.setValue(progress);
                        jProgressBar1.setString(String.valueOf(processed.size() * 100 / bugsWithAttachmentsOnTrack));
                        jProgressBar1.setStringPainted(true);
                        String[] line = new String[4];
                        line[0] = String.valueOf(bug.getBugId());
                        long length = 0;
                        for (String s : directoryList) {
                            if (s.endsWith(bug.getBugId().toString())) {
                                File file = new File(path + "\\" + s);
                                for (File f : file.listFiles()) {
                                    if (f.isFile()) {
                                        length += f.length();
                                    }
                                }
                                line[1] = s;
                                line[2] = String.valueOf(new File(path + "\\" + s).listFiles().length);
                                transferred += length;
                                line[3] = String.valueOf(length / 1000000) + " Mb";

                            }
                        }

                        tmodel.insertRow(0, line);
                        jLabel3.setText("Processed: " + processed.size() + "/" + bugsWithAttachmentsOnTrack);
                    }
                }
        );

        MainController.getInstance().addListener(
                new SizeListener() {

                    @Override
                    public void performAction(long size) {
                        jLabel2.setText("Transffered: " + size / 1000000 + " Mb");
                    }
                }
        );

        model.addElement("Collecting detailed bug informations. This may take a while.");
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                ExecutorService executor = Executors.newFixedThreadPool(threads);
                for (Bugs b : bugs) {
                    for (String s : directoryList) {
                        if (s.endsWith(b.getBugId().toString())) {
                            File file = new File(path + "\\" + s);
                            //System.out.println("Starting the migration of " + b.toString() + " " + file.getPath());
                            bugsWithAttachmentsOnTrack++;
                            Runnable worker = new MigrateAttachments(bugzillaPath, b, file.getPath(), model);
                            executor.execute(worker);

                        }
                    }
                }
                executor.shutdown();
                while (!executor.isTerminated()) {
                }
                JOptionPane.showMessageDialog(null, processed.size() + "attachments succesfully migrated!");
                System.out.println("Finished all threads");
            }
        });
        t.start();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                long startTime = new Date().getTime();
                int processed = 0;

                while (true) {
                    long elapseTime = new Date().getTime() - startTime;
                    setTitle("Running for: " + elapseTime / 1000 + " seconds");
                    jLabel1.setText("Active threads: " + (Thread.activeCount() - 6));
                    jLabel4.setText("Bugs with attachments on Track: " + bugsWithAttachmentsOnTrack);
                    if (model.getSize() > 5000) {
                        model.clear();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProcessingForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
        t1.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jProgressBar1.setName("Mapping bugs ID with attachments directory"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane3.setViewportView(jList1);

        jLabel1.setText("Active threads: 0");

        jLabel2.setText("Transffered: 0 Mb");

        jLabel3.setText("Processed: 0");

        jLabel4.setText("Bugs with attachments on Track: 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3))
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
