/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GotoDialog.java
 *
 * Created on 07.04.2009, 13:59:32
 */

package org.exmaralda.partitureditor.jexmaraldaswing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import org.exmaralda.folker.utilities.TimeStringFormatter;
import org.exmaralda.partitureditor.partiture.PartitureTableWithActions;

/**
 *
 * @author thomas
 */
public class GotoDialog extends javax.swing.JDialog {

    PartitureTableWithActions partitur;

    /** Creates new form GotoDialog
     * @param parent
     * @param modal
     * @param p */
    public GotoDialog(java.awt.Frame parent, boolean modal, PartitureTableWithActions p) {
        super(parent, modal);
        initComponents();
        partitur = p;
        
        javax.swing.SpinnerNumberModel model
                = new javax.swing.SpinnerNumberModel(0,0, partitur.getModel().getNumColumns(), 1);
        timelineItemSpinner.setModel(model);

        if (partitur.selectionStartCol>=0){
            timelineItemSpinner.getModel().setValue(partitur.selectionStartCol);
        } 
        getRootPane().setDefaultButton(okButton);    
        ((JSpinner.DefaultEditor) timelineItemSpinner.getEditor()).getTextField().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                apply();
            }
            
        });
    }

    private void apply() {
        if (timeRadioButton.isSelected()){
            try {
                double time = TimeStringFormatter.parseString(timeTextField.getText()) / 1000.0;
                //int col = partitur.getModel().getTranscription().getBody().getCommonTimeline().getPositionForTime(time);
                partitur.makeVisible(time);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Could not parse time string");
            }
        } else {
            int col = ((Integer)(timelineItemSpinner.getValue()));
            partitur.makeColumnVisible(col);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
        timePanel = new javax.swing.JPanel();
        timeRadioButton = new javax.swing.JRadioButton();
        timeTextField = new org.exmaralda.folker.gui.TimeTextField();
        timeLineItemPanel = new javax.swing.JPanel();
        timelineItemRadioButton = new javax.swing.JRadioButton();
        timelineItemSpinner = new javax.swing.JSpinner();
        buttonPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Go to");

        mainPanel.setLayout(new java.awt.GridLayout(2, 1));

        timePanel.setLayout(new javax.swing.BoxLayout(timePanel, javax.swing.BoxLayout.LINE_AXIS));

        buttonGroup.add(timeRadioButton);
        timeRadioButton.setText("Go to time: ");
        timeRadioButton.setMaximumSize(new java.awt.Dimension(160, 23));
        timeRadioButton.setPreferredSize(new java.awt.Dimension(160, 23));
        timePanel.add(timeRadioButton);

        timeTextField.setFont(new java.awt.Font("Tahoma", 0, 14));
        timeTextField.setText("0:00.00");
        timeTextField.setMaximumSize(new java.awt.Dimension(150, 23));
        timeTextField.setPreferredSize(new java.awt.Dimension(150, 23));
        timeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                timeTextFieldFocusGained(evt);
            }
        });
        timePanel.add(timeTextField);

        mainPanel.add(timePanel);

        timeLineItemPanel.setLayout(new javax.swing.BoxLayout(timeLineItemPanel, javax.swing.BoxLayout.LINE_AXIS));

        buttonGroup.add(timelineItemRadioButton);
        timelineItemRadioButton.setSelected(true);
        timelineItemRadioButton.setLabel("Go to timeline item: ");
        timelineItemRadioButton.setMaximumSize(new java.awt.Dimension(160, 23));
        timelineItemRadioButton.setPreferredSize(new java.awt.Dimension(160, 23));
        timeLineItemPanel.add(timelineItemRadioButton);

        timelineItemSpinner.setMaximumSize(new java.awt.Dimension(150, 23));
        timelineItemSpinner.setMinimumSize(new java.awt.Dimension(150, 23));
        timelineItemSpinner.setPreferredSize(new java.awt.Dimension(150, 23));
        timelineItemSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                timelineItemSpinnerStateChanged(evt);
            }
        });
        timelineItemSpinner.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                timelineItemSpinnerFocusGained(evt);
            }
        });
        timeLineItemPanel.add(timelineItemSpinner);

        mainPanel.add(timeLineItemPanel);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(okButton);

        applyButton.setText("Apply");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(applyButton);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(closeButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void timeTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_timeTextFieldFocusGained
        timeRadioButton.setSelected(true);
    }//GEN-LAST:event_timeTextFieldFocusGained

    private void timelineItemSpinnerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_timelineItemSpinnerFocusGained
        timelineItemRadioButton.setSelected(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFormattedTextField textField = ((JSpinner.DefaultEditor) timelineItemSpinner.getEditor()).getTextField();
                textField.setText(textField.getText());
                textField.requestFocus();
                textField.selectAll();
            }
        });        
        
}//GEN-LAST:event_timelineItemSpinnerFocusGained

    private void timelineItemSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_timelineItemSpinnerStateChanged
        timelineItemRadioButton.setSelected(true);        // TODO add your handling code here:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFormattedTextField textField = ((JSpinner.DefaultEditor) timelineItemSpinner.getEditor()).getTextField();
                textField.setText(textField.getText());
                textField.requestFocus();
                textField.selectAll();
            }
        });        
        
}//GEN-LAST:event_timelineItemSpinnerStateChanged

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        apply();
    }//GEN-LAST:event_applyButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        apply();
        setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_closeButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyButton;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel timeLineItemPanel;
    private javax.swing.JPanel timePanel;
    private javax.swing.JRadioButton timeRadioButton;
    private javax.swing.JTextField timeTextField;
    private javax.swing.JRadioButton timelineItemRadioButton;
    private javax.swing.JSpinner timelineItemSpinner;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setVisible(boolean b) {
        if (b){
            ((javax.swing.SpinnerNumberModel)timelineItemSpinner.getModel()).setMaximum(partitur.getModel().getNumColumns());
            setLocationRelativeTo(partitur);
            if (timelineItemRadioButton.isSelected()){
                timelineItemSpinner.requestFocus();                                    
            }
            
        }
        super.setVisible(b);
    }





}
