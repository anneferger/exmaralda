/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package org.exmaralda.texgut.gui;

import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author bernd
 */
public class NewEAFDialog extends javax.swing.JDialog {

    public boolean approved = false;
    
    /**
     * Creates new form NewEAFDialog
     */
    public NewEAFDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        interviewerNumbersTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {check();}
            @Override
            public void removeUpdate(DocumentEvent e) {check();}
            @Override
            public void changedUpdate(DocumentEvent e) {check();}
        });
        
        speakerNumbersTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {check();}
            @Override
            public void removeUpdate(DocumentEvent e) {check();}
            @Override
            public void changedUpdate(DocumentEvent e) {check();}
        });
        
        check();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        northPanel = new javax.swing.JPanel();
        audioFileLabel = new javax.swing.JLabel();
        audioFileTextField = new javax.swing.JTextField();
        audioFileBrowseButton = new javax.swing.JButton();
        centralPanel = new javax.swing.JPanel();
        interviewerNumbersLabel = new javax.swing.JLabel();
        interviewerNumbersTextField = new javax.swing.JTextField();
        speakerNumbersLabel = new javax.swing.JLabel();
        speakerNumbersTextField = new javax.swing.JTextField();
        southPanel = new javax.swing.JPanel();
        transcriptFileLabel = new javax.swing.JLabel();
        transcriptFileTextField = new javax.swing.JTextField();
        transcriptFileBrowseButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        createButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New ELAN Transcription");

        mainPanel.setLayout(new java.awt.BorderLayout());

        northPanel.setLayout(new javax.swing.BoxLayout(northPanel, javax.swing.BoxLayout.LINE_AXIS));

        audioFileLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        audioFileLabel.setText("Audio file: ");
        northPanel.add(audioFileLabel);

        audioFileTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        audioFileTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioFileTextFieldActionPerformed(evt);
            }
        });
        northPanel.add(audioFileTextField);

        audioFileBrowseButton.setText("Browse...");
        audioFileBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioFileBrowseButtonActionPerformed(evt);
            }
        });
        northPanel.add(audioFileBrowseButton);

        mainPanel.add(northPanel, java.awt.BorderLayout.NORTH);

        centralPanel.setPreferredSize(new java.awt.Dimension(600, 120));
        centralPanel.setLayout(new java.awt.GridLayout(2, 2));

        interviewerNumbersLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        interviewerNumbersLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        interviewerNumbersLabel.setText("Interviewer Number(s): ");
        interviewerNumbersLabel.setToolTipText("Enter interviewer number(s) with up to 4 digits, separated by comma");
        centralPanel.add(interviewerNumbersLabel);

        interviewerNumbersTextField.setMaximumSize(new java.awt.Dimension(200, 30));
        interviewerNumbersTextField.setPreferredSize(new java.awt.Dimension(200, 30));
        interviewerNumbersTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interviewerNumbersTextFieldActionPerformed(evt);
            }
        });
        centralPanel.add(interviewerNumbersTextField);

        speakerNumbersLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        speakerNumbersLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        speakerNumbersLabel.setText("Speaker Number(s): ");
        speakerNumbersLabel.setToolTipText("Enter speaker number(s) with up to 4 digits, \"extra\" speakers with an initial 'N' separated by comma");
        speakerNumbersLabel.setMaximumSize(new java.awt.Dimension(124, 16));
        speakerNumbersLabel.setMinimumSize(new java.awt.Dimension(124, 16));
        speakerNumbersLabel.setPreferredSize(new java.awt.Dimension(124, 16));
        centralPanel.add(speakerNumbersLabel);

        speakerNumbersTextField.setMaximumSize(new java.awt.Dimension(200, 30));
        speakerNumbersTextField.setPreferredSize(new java.awt.Dimension(200, 30));
        speakerNumbersTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speakerNumbersTextFieldActionPerformed(evt);
            }
        });
        centralPanel.add(speakerNumbersTextField);

        mainPanel.add(centralPanel, java.awt.BorderLayout.CENTER);

        southPanel.setLayout(new javax.swing.BoxLayout(southPanel, javax.swing.BoxLayout.LINE_AXIS));

        transcriptFileLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        transcriptFileLabel.setText("Transcription file: ");
        southPanel.add(transcriptFileLabel);

        transcriptFileTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        southPanel.add(transcriptFileTextField);

        transcriptFileBrowseButton.setText("Browse...");
        transcriptFileBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transcriptFileBrowseButtonActionPerformed(evt);
            }
        });
        southPanel.add(transcriptFileBrowseButton);

        mainPanel.add(southPanel, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        createButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        createButton.setText("Create EAF transcription");
        createButton.setEnabled(false);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });
        jPanel1.add(createButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(cancelButton);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void audioFileBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioFileBrowseButtonActionPerformed
          JFileChooser jfc = new JFileChooser();
          jfc.setFileFilter(new FileFilter(){
              @Override
              public boolean accept(File file) {
                  return file.getName().toLowerCase().endsWith(".wav") || file.isDirectory();
              }

              @Override
              public String getDescription() {
                  return "WAV audio files (*.wav)";
              }              
          });
          jfc.setDialogTitle("Choose WAV audio file");
          String audioFolder = java.util.prefs.Preferences.userRoot().node("org.exmaralda.texgut").get("audio-folder", "");
          jfc.setCurrentDirectory(new File(audioFolder));
          int r = jfc.showOpenDialog(this.mainPanel);
          if (r==JFileChooser.APPROVE_OPTION){
              this.audioFileTextField.setText(jfc.getSelectedFile().getAbsolutePath());
            if (interviewerNumbersTextField.getText().length()==0 && speakerNumbersTextField.getText().length()==0 && transcriptFileTextField.getText().length()==0){
                // try to parse the audio filename
                String name = jfc.getSelectedFile().getName();
                // 1-1-1-1-a.wav
                if (name.matches("^\\d{1,4}-\\d{1,4}-\\d{1,4}-\\d{1,4}-[a-z]\\.wav")){
                    String[] bits = name.split("-");
                    interviewerNumbersTextField.setText(bits[0]);
                    speakerNumbersTextField.setText(bits[1]);
                    
                    String folderName = bits[0] + "-" + bits[1] + "-" + bits[2];
                    String transcriptFolder = java.util.prefs.Preferences.userRoot().node("org.exmaralda.texgut").get("transcript-folder", "");
                    File folder = new File(new File(transcriptFolder), folderName);
                    File transcriptFile = new File(folder, name.replaceAll("\\.wav", ".eaf"));
                    transcriptFileTextField.setText(transcriptFile.getAbsolutePath());
                    check();
                }
            }
            check();
          }
          
          
          
    }//GEN-LAST:event_audioFileBrowseButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        approved = false;
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        approved = true;
        dispose();
    }//GEN-LAST:event_createButtonActionPerformed

    private void audioFileTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioFileTextFieldActionPerformed
        check();
    }//GEN-LAST:event_audioFileTextFieldActionPerformed

    private void interviewerNumbersTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interviewerNumbersTextFieldActionPerformed
        check();
    }//GEN-LAST:event_interviewerNumbersTextFieldActionPerformed

    private void speakerNumbersTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speakerNumbersTextFieldActionPerformed
        check();
    }//GEN-LAST:event_speakerNumbersTextFieldActionPerformed

    private void transcriptFileBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transcriptFileBrowseButtonActionPerformed
          JFileChooser jfc = new JFileChooser();
          jfc.setFileFilter(new FileFilter(){
              @Override
              public boolean accept(File file) {
                  return file.getName().toLowerCase().endsWith(".eaf") || file.isDirectory();
              }

              @Override
              public String getDescription() {
                  return "WAV audio files (*.wav)";
              }              
          });
          jfc.setDialogTitle("Choose WAV audio file");
          String transcriptFolder = java.util.prefs.Preferences.userRoot().node("org.exmaralda.texgut").get("transcript-folder", "");
          jfc.setCurrentDirectory(new File(transcriptFolder));
          int r = jfc.showSaveDialog(this.mainPanel);
          if (r==JFileChooser.APPROVE_OPTION){
              this.transcriptFileTextField.setText(jfc.getSelectedFile().getAbsolutePath());
          }
          check();

    }//GEN-LAST:event_transcriptFileBrowseButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewEAFDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewEAFDialog dialog = new NewEAFDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton audioFileBrowseButton;
    private javax.swing.JLabel audioFileLabel;
    private javax.swing.JTextField audioFileTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel centralPanel;
    private javax.swing.JButton createButton;
    private javax.swing.JLabel interviewerNumbersLabel;
    private javax.swing.JTextField interviewerNumbersTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel northPanel;
    private javax.swing.JPanel southPanel;
    private javax.swing.JLabel speakerNumbersLabel;
    private javax.swing.JTextField speakerNumbersTextField;
    private javax.swing.JButton transcriptFileBrowseButton;
    private javax.swing.JLabel transcriptFileLabel;
    private javax.swing.JTextField transcriptFileTextField;
    // End of variables declaration//GEN-END:variables

    private void check() {
        String interviewerNumbers = interviewerNumbersTextField.getText();
        String speakerNumbers = speakerNumbersTextField.getText();
        String transcriptFile = transcriptFileTextField.getText();
        
        boolean interviewerNumbersMatch = interviewerNumbers.matches("^\\d{1,4}(, ?\\d{1,4})*$");
        if (!interviewerNumbersMatch){
            interviewerNumbersTextField.setBackground(Color.red);
        } else {            
            interviewerNumbersTextField.setBackground(Color.white);
        }
        boolean speakerNumbersMatch = speakerNumbers.matches("^(\\d|N)(\\d{1,3})?(, ?(\\d|N)(\\d{1,3})?)*$");
        if (!speakerNumbersMatch){
            speakerNumbersTextField.setBackground(Color.red);
        } else {
            speakerNumbersTextField.setBackground(Color.white);            
        }
        if (!(interviewerNumbersMatch) || !(speakerNumbersMatch) || transcriptFile.trim().length()==0){
            createButton.setEnabled(false);
            return;
        }
        
        createButton.setEnabled(true);
        File file = new File(transcriptFile);
        if (file.exists()){
            transcriptFileTextField.setToolTipText("File " + transcriptFile + " exists.");
            transcriptFileTextField.setBackground(Color.yellow);
        } else {
            transcriptFileTextField.setToolTipText("OK");
            transcriptFileTextField.setBackground(Color.white);
        }
        
        
    }

    public String[] getSpeakerNumbers() {
        String text = this.speakerNumbersTextField.getText();
        return text.split(", +");
    }
    
    public String[] getInterviewerNumbers() {
        String text = this.interviewerNumbersTextField.getText();
        return text.split(", +");
    }

    public String getAudioFilepath() {
        return this.audioFileTextField.getText();
    }
    
    public String getTranscriptionFilepath(){
        return this.transcriptFileTextField.getText();
    }
    
    
}
