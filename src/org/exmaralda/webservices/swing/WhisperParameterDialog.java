/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package org.exmaralda.webservices.swing;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bernd
 */
public class WhisperParameterDialog extends javax.swing.JDialog {

    public boolean approved = false;

    /**
     * Creates new form WhisperParameterDialog
     * @param parent
     * @param modal
     */
    public WhisperParameterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        sourceLanguageComboBox.setRenderer(new MAUSLanguagesComboBoxRenderer());                
    }

    /* We currently support the following languages through both the transcriptions and translations endpoint:
        Afrikaans, Arabic, Armenian, Azerbaijani, Belarusian, Bosnian, Bulgarian, Catalan, Chinese, Croatian, Czech, 
        Danish, Dutch, English, Estonian, Finnish, French, Galician, German, Greek, Hebrew, Hindi, Hungarian, Icelandic, 
        Indonesian, Italian, Japanese, Kannada, Kazakh, Korean, Latvian, Lithuanian, Macedonian, Malay, Marathi, Maori, 
        Nepali, Norwegian, Persian, Polish, Portuguese, Romanian, Russian, Serbian, Slovak, Slovenian, Spanish, Swahili, 
        Swedish, Tagalog, Tamil, Thai, Turkish, Ukrainian, Urdu, Vietnamese, and Welsh.
        While the underlying model was trained on 98 languages, we only list the languages that exceeded <50% word error rate (WER) 
        which is an industry standard benchmark for speech to text model accuracy. The model will return results for languages not 
        listed above but the quality will be low. */
    
    static String[][] SOURCE_LANGUAGES = {
        {"auto_detect", "Auto detect"},
        {"XX","Afrikaans"},
        {"XX","Arabic"},
        {"XX","Armenian"},
        {"XX","Azerbaijani"},
        {"XX","Belarusian"},
        {"XX","Bosnian"},
        {"BG","Bulgarian"},
        
        {"XX","Catalan"},
        {"ZH","Chinese"},        
        {"XX","Croatian"},
        {"CS","Czech"},
        
        {"DA","Danish"},
        {"NL","Dutch"},
        {"EN","English"},
        {"ET","Estonian"},
        {"FI","Finnish"},
        {"FR","French"},
        {"XX","Galician"},
        {"DE","German"},
        {"EL","Greek"},

        {"XX","Hebrew"},
        {"XX","Hindu"},
        {"HU","Hungarian"},

        {"XX","Icelandic"},
        {"XX","Indonesian"},
        {"IT","Italian"},
        {"JA","Japanese"},

        {"ES","Spanish"},
        {"LT","Lithuanian"},
        {"LV","Latvian"},
        {"PL","Polish"},
        {"PT","Portuguese"},
        {"RO","Romanian"},
        {"RU","Russian"},
        {"SK","Slovak"},
        {"SL","Slovenian"},
        {"SV","Swedish"},
    };   
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sourceLanguagePanel = new javax.swing.JPanel();
        sourceLanguageLabel = new javax.swing.JLabel();
        sourceLanguageComboBox = new javax.swing.JComboBox();
        promptsPanel = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        apikeyPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        apiKeyTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        getAPIKeyButton = new javax.swing.JButton();
        okCancelPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Whisper Parameters");

        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.Y_AXIS));

        sourceLanguagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Source Language"));
        sourceLanguagePanel.setLayout(new java.awt.BorderLayout());

        sourceLanguageLabel.setText("What is the language to be recognized?");
        sourceLanguagePanel.add(sourceLanguageLabel, java.awt.BorderLayout.NORTH);

        sourceLanguageComboBox.setModel(new javax.swing.DefaultComboBoxModel(SOURCE_LANGUAGES));
        sourceLanguagePanel.add(sourceLanguageComboBox, java.awt.BorderLayout.CENTER);

        mainPanel.add(sourceLanguagePanel);

        promptsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Prompts"));
        promptsPanel.setLayout(new java.awt.BorderLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        promptsPanel.add(jComboBox1, java.awt.BorderLayout.NORTH);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        promptsPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        mainPanel.add(promptsPanel);

        apikeyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("API key"));
        apikeyPanel.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        apiKeyTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                apiKeyTextFieldFocusLost(evt);
            }
        });
        apiKeyTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apiKeyTextFieldActionPerformed(evt);
            }
        });
        jPanel6.add(apiKeyTextField);

        apikeyPanel.add(jPanel6, java.awt.BorderLayout.CENTER);

        jLabel6.setText("Please provide a valid API key for OpenAI Whisper");
        apikeyPanel.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        getAPIKeyButton.setBackground(java.awt.Color.blue);
        getAPIKeyButton.setText("Register with OpenAI for an API Key");
        getAPIKeyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getAPIKeyButtonActionPerformed(evt);
            }
        });
        apikeyPanel.add(getAPIKeyButton, java.awt.BorderLayout.PAGE_END);

        mainPanel.add(apikeyPanel);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        okButton.setText("OK");
        okButton.setEnabled(false);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        okCancelPanel.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        okCancelPanel.add(cancelButton);

        getContentPane().add(okCancelPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        approved = true;
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        approved = false;
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void apiKeyTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_apiKeyTextFieldFocusLost
        updateOK();
    }//GEN-LAST:event_apiKeyTextFieldFocusLost

    private void apiKeyTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apiKeyTextFieldActionPerformed
        updateOK();
    }//GEN-LAST:event_apiKeyTextFieldActionPerformed

    private void getAPIKeyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getAPIKeyButtonActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("https://platform.openai.com/signup?launch"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(DeepLParameterDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_getAPIKeyButtonActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WhisperParameterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WhisperParameterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WhisperParameterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WhisperParameterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WhisperParameterDialog dialog = new WhisperParameterDialog(new javax.swing.JFrame(), true);
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
    
    public void setParameters(String apiKey){
        apiKeyTextField.setText(apiKey);
        updateOK();
    }
    
    public HashMap<String, Object> getWhisperParameters() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("SOURCE-LANGUAGE", ((String[])sourceLanguageComboBox.getSelectedItem())[0]);
        result.put("API-KEY", apiKeyTextField.getText());
        return result;
    }
    
    
    
    private void updateOK() {
        boolean parametersSuffice = 
                apiKeyTextField.getText().length()>0;
        okButton.setEnabled(parametersSuffice);
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apiKeyTextField;
    private javax.swing.JPanel apikeyPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton getAPIKeyButton;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel okCancelPanel;
    private javax.swing.JPanel promptsPanel;
    private javax.swing.JComboBox sourceLanguageComboBox;
    private javax.swing.JLabel sourceLanguageLabel;
    private javax.swing.JPanel sourceLanguagePanel;
    // End of variables declaration//GEN-END:variables
}
