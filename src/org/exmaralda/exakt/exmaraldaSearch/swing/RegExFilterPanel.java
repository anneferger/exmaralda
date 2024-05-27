/*
 * RegExFilterForm.java
 *
 * Created on 1. Maerz 2007, 11:03
 */

package org.exmaralda.exakt.exmaraldaSearch.swing;

import javax.swing.*;
import java.util.*;
import org.exmaralda.exakt.search.analyses.AnalysisInterface;
import org.exmaralda.exakt.search.swing.InputHelperDialog;

/**
 *
 * @author  thomas
 */
public class RegExFilterPanel extends javax.swing.JPanel {
    
    InputHelperDialog inputHelperDialog = new InputHelperDialog(this);
    DefaultComboBoxModel columnSelectionComboBoxModel;
    List<String[]> meta;
    String[] fixedColumns = {"Left context",  "Match text", "Right context", "Communication", "Speaker"};
    COMASearchResultListTableModel tableModel;
    private int selectedColumn = -1;
    
    /** Creates new form RegExFilterForm */
    public RegExFilterPanel(COMASearchResultListTableModel m) {
        initComponents();
        tableModel = m;
        meta = m.getMetaIdentifiers();
        
        columnSelectionComboBoxModel = new DefaultComboBoxModel();
        for (String s : fixedColumns){
            columnSelectionComboBoxModel.addElement(s);
        }
        for (AnalysisInterface ai : m.getData().getAnalyses()){
            columnSelectionComboBoxModel.addElement(ai.getName());
        }
        for (String[] s : meta){
            columnSelectionComboBoxModel.addElement(s[1] + "[" + s[0] + "]");
        }
        columnSelectionComboBox.setModel(columnSelectionComboBoxModel);
    }
    
    public int getColumnIndex(){
        int index = columnSelectionComboBox.getSelectedIndex();
        switch (index){
            case 0 : return 3;
            case 1 : return 4;
            case 2 : return 5;
            case 3 : return 1;
            case 4 : return 2;
            default : return index + 1;
        }
    }
    
    public String getRegularExpression(){
        return filterTextField.getText();
    }
    
    public boolean getInvert(){
        return invertCheckBox.isSelected();
    }

    public void setSelectedColumn(int selectedColumn) {
        this.selectedColumn = selectedColumn;
        int index = 0;
        switch (selectedColumn){
            case -1 : index = 0; break;
            case 0 : index = 0; break;
            case 1 : index = 3; break;
            case 2 : index = 4; break;
            case 3 : index = 0; break;
            case 4 : index = 1; break;
            case 5 : index = 2; break;
            default : index = selectedColumn-1;
        }
        columnSelectionComboBox.setSelectedIndex(index);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        columnSelectionPanel = new javax.swing.JPanel();
        columnSelectionLabel = new javax.swing.JLabel();
        columnSelectionComboBox = new javax.swing.JComboBox();
        filterPanel = new javax.swing.JPanel();
        filterButton = new javax.swing.JButton();
        filterTextField = new org.exmaralda.exakt.search.swing.RegularExpressionTextField();
        invertPanel = new javax.swing.JPanel();
        invertCheckBox = new javax.swing.JCheckBox();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        columnSelectionPanel.setLayout(new javax.swing.BoxLayout(columnSelectionPanel, javax.swing.BoxLayout.X_AXIS));

        columnSelectionLabel.setText("Column: ");
        columnSelectionLabel.setMaximumSize(new java.awt.Dimension(80, 31));
        columnSelectionLabel.setMinimumSize(new java.awt.Dimension(80, 31));
        columnSelectionLabel.setPreferredSize(new java.awt.Dimension(80, 31));
        columnSelectionPanel.add(columnSelectionLabel);

        columnSelectionComboBox.setMaximumSize(new java.awt.Dimension(30000, 31));
        columnSelectionComboBox.setMinimumSize(new java.awt.Dimension(51, 31));
        columnSelectionComboBox.setPreferredSize(new java.awt.Dimension(300, 31));
        columnSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                columnSelectionComboBoxActionPerformed(evt);
            }
        });

        columnSelectionPanel.add(columnSelectionComboBox);

        add(columnSelectionPanel);

        filterPanel.setLayout(new javax.swing.BoxLayout(filterPanel, javax.swing.BoxLayout.X_AXIS));

        filterButton.setText("Filter: ");
        filterButton.setMaximumSize(new java.awt.Dimension(80, 31));
        filterButton.setMinimumSize(new java.awt.Dimension(80, 31));
        filterButton.setPreferredSize(new java.awt.Dimension(80, 31));
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });

        filterPanel.add(filterButton);

        filterTextField.setMaximumSize(new java.awt.Dimension(30000, 31));
        filterTextField.setMinimumSize(new java.awt.Dimension(200, 31));
        filterTextField.setPreferredSize(new java.awt.Dimension(400, 31));
        filterPanel.add(filterTextField);

        add(filterPanel);

        invertPanel.setLayout(new javax.swing.BoxLayout(invertPanel, javax.swing.BoxLayout.X_AXIS));

        invertCheckBox.setText("Invert filter");
        invertCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        invertCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        invertPanel.add(invertCheckBox);

        add(invertPanel);

    }// </editor-fold>//GEN-END:initComponents

    private void columnSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_columnSelectionComboBoxActionPerformed
        int index = columnSelectionComboBox.getSelectedIndex();
        HashSet<String> types = new HashSet<>();
        if (index==1) types = tableModel.getData().getTypes();
        else if (index==3) types = tableModel.getTypes(1);
        else if (index==4) types = tableModel.getTypes(2);
        else if (index>4) types = tableModel.getTypes(index+1);
        inputHelperDialog.inputHelperPanel.setTypes(types);
    }//GEN-LAST:event_columnSelectionComboBoxActionPerformed

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterButtonActionPerformed
        filterTextField.setBackground(java.awt.Color.LIGHT_GRAY);
        java.awt.Point p = filterTextField.getLocationOnScreen();
        int h = filterTextField.getHeight();
        int w = filterTextField.getWidth();
        p.translate(0,h);
        inputHelperDialog.setLocation(p);
        inputHelperDialog.setAlwaysOnTop(true);
        inputHelperDialog.inputHelperPanel.setSearchExpression(filterTextField.getText());
        inputHelperDialog.setSize(w,inputHelperDialog.getHeight());
        inputHelperDialog.setVisible(true);
        if (inputHelperDialog.changed){
            filterTextField.setText(inputHelperDialog.inputHelperPanel.getSearchExpression());
            filterTextField.requestFocus();                
        }
        filterTextField.setBackground(java.awt.Color.WHITE);
    }//GEN-LAST:event_filterButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox columnSelectionComboBox;
    private javax.swing.JLabel columnSelectionLabel;
    private javax.swing.JPanel columnSelectionPanel;
    private javax.swing.JButton filterButton;
    private javax.swing.JPanel filterPanel;
    private javax.swing.JTextField filterTextField;
    private javax.swing.JCheckBox invertCheckBox;
    private javax.swing.JPanel invertPanel;
    // End of variables declaration//GEN-END:variables
    
}
