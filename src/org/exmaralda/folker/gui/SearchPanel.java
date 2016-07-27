/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SearchPanel.java
 *
 * Created on 27.05.2009, 13:32:07
 */

package org.exmaralda.folker.gui;

import java.awt.Color;
import java.awt.Container;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JDialog;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.exmaralda.folker.application.ApplicationControl;
import org.exmaralda.folker.utilities.FOLKERInternationalizer;

/**
 *
 * @author thomas
 */
public class SearchPanel extends javax.swing.JPanel implements DocumentListener {

    ApplicationControl applicationControl;

    public static short SEARCH_MODE = 0;
    public static short REPLACE_MODE = 1;

    /** Creates new form SearchPanel */
    public SearchPanel() {
        initComponents();
        searchStringTextField.getDocument().addDocumentListener(this);
    }

    public void setApplicationControl(ApplicationControl ac) {
        applicationControl = ac;
    }

    public void setMode(short searchMode) {
        replaceStringPanel.setVisible(searchMode==REPLACE_MODE);
        replaceButton.setVisible(searchMode==REPLACE_MODE);
        replaceAllButton.setVisible(searchMode==REPLACE_MODE);
        Container c = getTopLevelAncestor();
        if (c instanceof JDialog){
            ((JDialog)c).pack();
        }
    }

    private String calculateSearchExpression() {
        String searchExpression = searchStringTextField.getText();
        if (!regexCheckBox.isSelected()){
            String modifiedExpression = "";
            for (int pos=0; pos<searchExpression.length(); pos++){
                char c = searchExpression.charAt(pos);
                if (Character.isLetter(c)){
                    if (!caseCheckBox.isSelected()){
                        char otherChar;
                        if (Character.isUpperCase(c)) otherChar = Character.toLowerCase(c);
                        else otherChar = Character.toUpperCase(c);
                        String add = "[" + c + otherChar + "]";
                        modifiedExpression+=add;
                    } else {
                        modifiedExpression+=c;
                    }
                } else {
                    modifiedExpression+="\\" + c;
                }
            }
            searchExpression = modifiedExpression;
        }
        return searchExpression;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        checkBoxPanel = new javax.swing.JPanel();
        regexCheckBox = new javax.swing.JCheckBox();
        caseCheckBox = new javax.swing.JCheckBox();
        buttonPanel = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        replaceButton = new javax.swing.JButton();
        replaceAllButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        searchStringPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchStringTextField = new javax.swing.JTextField();
        replaceStringPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        replaceStringTextField = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        checkBoxPanel.setLayout(new javax.swing.BoxLayout(checkBoxPanel, javax.swing.BoxLayout.Y_AXIS));

        regexCheckBox.setText(FOLKERInternationalizer.getString("dialog.search.regularexpression"));
        regexCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regexCheckBoxActionPerformed(evt);
            }
        });
        checkBoxPanel.add(regexCheckBox);

        caseCheckBox.setText(FOLKERInternationalizer.getString("dialog.search.case"));
        checkBoxPanel.add(caseCheckBox);

        add(checkBoxPanel, java.awt.BorderLayout.SOUTH);

        buttonPanel.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Panel.background"), 10));
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.Y_AXIS));

        searchButton.setText(FOLKERInternationalizer.getString("dialog.search.findnext"));
        searchButton.setMaximumSize(new java.awt.Dimension(110, 23));
        searchButton.setPreferredSize(new java.awt.Dimension(110, 23));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(searchButton);

        replaceButton.setText(FOLKERInternationalizer.getString("dialog.search.replace"));
        replaceButton.setMaximumSize(new java.awt.Dimension(110, 23));
        replaceButton.setPreferredSize(new java.awt.Dimension(110, 23));
        replaceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(replaceButton);

        replaceAllButton.setText(FOLKERInternationalizer.getString("dialog.search.replaceall"));
        replaceAllButton.setMaximumSize(new java.awt.Dimension(110, 23));
        replaceAllButton.setPreferredSize(new java.awt.Dimension(110, 23));
        replaceAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceAllButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(replaceAllButton);

        cancelButton.setText(FOLKERInternationalizer.getString("dialog.search.cancel"));
        cancelButton.setMaximumSize(new java.awt.Dimension(110, 23));
        cancelButton.setPreferredSize(new java.awt.Dimension(110, 23));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(cancelButton);

        add(buttonPanel, java.awt.BorderLayout.EAST);

        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.Y_AXIS));

        searchStringPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchStringPanel.setLayout(new javax.swing.BoxLayout(searchStringPanel, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setText(FOLKERInternationalizer.getString("dialog.search.searchfor"));
        jLabel1.setMaximumSize(new java.awt.Dimension(80, 14));
        jLabel1.setPreferredSize(new java.awt.Dimension(80, 14));
        searchStringPanel.add(jLabel1);

        searchStringTextField.setMaximumSize(new java.awt.Dimension(600, 25));
        searchStringTextField.setPreferredSize(new java.awt.Dimension(300, 25));
        searchStringPanel.add(searchStringTextField);

        mainPanel.add(searchStringPanel);

        replaceStringPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        replaceStringPanel.setLayout(new javax.swing.BoxLayout(replaceStringPanel, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setText(FOLKERInternationalizer.getString("dialog.search.replacewith"));
        jLabel2.setMaximumSize(new java.awt.Dimension(80, 14));
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 14));
        replaceStringPanel.add(jLabel2);

        replaceStringTextField.setMaximumSize(new java.awt.Dimension(600, 25));
        replaceStringTextField.setPreferredSize(new java.awt.Dimension(300, 25));
        replaceStringPanel.add(replaceStringTextField);

        mainPanel.add(replaceStringPanel);

        add(mainPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void regexCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regexCheckBoxActionPerformed
        caseCheckBox.setEnabled(!regexCheckBox.isSelected());
        if(!regexCheckBox.isSelected()){
            searchStringTextField.setToolTipText("");
            searchStringTextField.setForeground(Color.BLACK);
            searchButton.setEnabled(true);
        } else {
            checkRegex();
        }
    }//GEN-LAST:event_regexCheckBoxActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        String searchExpression = calculateSearchExpression();
        applicationControl.search(searchExpression);
}//GEN-LAST:event_searchButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        Container c = getTopLevelAncestor();
        if (c instanceof JDialog){
            ((JDialog)c).setVisible(false);
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void replaceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceButtonActionPerformed
        applicationControl.replace(replaceStringTextField.getText());
    }//GEN-LAST:event_replaceButtonActionPerformed

    private void replaceAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceAllButtonActionPerformed
        String searchExpression = calculateSearchExpression();
        applicationControl.replaceAll(searchExpression, replaceStringTextField.getText());
    }//GEN-LAST:event_replaceAllButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox caseCheckBox;
    private javax.swing.JPanel checkBoxPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JCheckBox regexCheckBox;
    private javax.swing.JButton replaceAllButton;
    private javax.swing.JButton replaceButton;
    private javax.swing.JPanel replaceStringPanel;
    private javax.swing.JTextField replaceStringTextField;
    public javax.swing.JButton searchButton;
    private javax.swing.JPanel searchStringPanel;
    private javax.swing.JTextField searchStringTextField;
    // End of variables declaration//GEN-END:variables

    public void insertUpdate(DocumentEvent e) {
        checkRegex();
    }

    public void removeUpdate(DocumentEvent e) {
        checkRegex();
    }

    public void changedUpdate(DocumentEvent e) {
        checkRegex();
    }

    void checkRegex(){
        if (!regexCheckBox.isSelected()) return;
        try {
            Pattern.compile(searchStringTextField.getText());
            searchStringTextField.setToolTipText("Regul�rer Ausdruck OK");
            searchStringTextField.setForeground(Color.BLACK);
            searchButton.setEnabled(true);
        } catch (PatternSyntaxException pex){
            searchStringTextField.setToolTipText(pex.getLocalizedMessage());
            searchStringTextField.setForeground(Color.RED);
            searchButton.setEnabled(false);
        }

    }


}
