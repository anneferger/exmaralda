/*
 * EditTimelineItemDialog.java
 *
 * Created on 29. Oktober 2001, 14:47
 
 
 */

package org.exmaralda.partitureditor.jexmaraldaswing;

import javax.swing.JOptionPane;

/**
 *
 * @author  Thomas
 * @version 
 */
public class EditTimelineItemDialog extends JEscapeDialog {

    org.exmaralda.partitureditor.jexmaralda.TimelineItem timelineItem;
    
    /** Creates new form EditTimelineItemDialog
     * @param parent
     * @param modal
     * @param tli */
    public EditTimelineItemDialog(java.awt.Frame parent,boolean modal, org.exmaralda.partitureditor.jexmaralda.TimelineItem tli) {
        super (parent, modal);
        timelineItem = tli.makeCopy();
        change = false;
        initComponents ();
        this.getRootPane().setDefaultButton(okButton);
        if (timelineItem.getTime()<0){
            numberLabel.setText("No value");
            timeLabel.setText("Enter number (sec) or hh:mm:ss.xxx");
        }
        else {
            numberLabel.setText(Double.toString(timelineItem.getTime()) + " [seconds]");
            timeLabel.setText(TimeUtilities.makeTimeString(timelineItem.getTime(),"hh:mm:ss.xxx"));
        }
        pack ();
        absoluteTimeTextField.requestFocus();
        org.exmaralda.common.helpers.Internationalizer.internationalizeDialogToolTips(this);
    }

    public org.exmaralda.partitureditor.jexmaralda.TimelineItem getTimelineItem(){
        timelineItem.setType("user");
        return timelineItem;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        okButton.setMnemonic('O');
        cancelButton = new javax.swing.JButton();
        cancelButton.setMnemonic('C');
        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        numberLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        absoluteTimeTextField = new javax.swing.JTextField();

        setTitle("Edit timeline item");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.setMaximumSize(new java.awt.Dimension(80, 27));
        okButton.setMinimumSize(new java.awt.Dimension(80, 27));
        okButton.setPreferredSize(new java.awt.Dimension(80, 27));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.setMaximumSize(new java.awt.Dimension(80, 27));
        cancelButton.setMinimumSize(new java.awt.Dimension(80, 27));
        cancelButton.setPreferredSize(new java.awt.Dimension(80, 27));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(cancelButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Absolute time value:");
        mainPanel.add(jLabel1);

        numberLabel.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        mainPanel.add(numberLabel);

        timeLabel.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        mainPanel.add(timeLabel);

        double time = timelineItem.getTime();
        if (time>=0){
            absoluteTimeTextField.setText(Double.toString(time));
        }
        absoluteTimeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                absoluteTimeTextFieldActionPerformed(evt);
            }
        });
        mainPanel.add(absoluteTimeTextField);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void hoursRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoursRadioButtonActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_hoursRadioButtonActionPerformed

    private void hoursTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoursTextFieldActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_hoursTextFieldActionPerformed

  private void cancelButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
// Add your handling code here:
    change = false;
    setVisible (false);
    dispose ();            
  }//GEN-LAST:event_cancelButtonActionPerformed

  private void okButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
// Add your handling code here:
      evaluateTextField();
  }//GEN-LAST:event_okButtonActionPerformed

  private void absoluteTimeTextFieldActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_absoluteTimeTextFieldActionPerformed
// Add your handling code here:
    evaluateTextField();
  }//GEN-LAST:event_absoluteTimeTextFieldActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        change = false;
        setVisible (false);
        dispose ();
    }//GEN-LAST:event_closeDialog

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
       absoluteTimeTextField.requestFocus();
    }//GEN-LAST:event_formComponentShown

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
 //       new EditTimelineItemDialog (new javax.swing.JFrame (), true).show ();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField absoluteTimeTextField;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables

    public boolean editTimelineItem(){
        java.awt.Dimension dialogSize = this.getPreferredSize();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - dialogSize.width/2, screenSize.height/2 - dialogSize.height/2);
        //show();
        setVisible(true);
        //requestFocus();
        //absoluteTimeTextField.requestFocus();
        return change;
    }
    
    private void evaluateTextField(){
        String text = absoluteTimeTextField.getText();
        try{
            double value = TimeUtilities.parseTimeString(text);
            timelineItem.setTime(value);
            change=true;
            setVisible (false);
            dispose ();
        }
        catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(this, "The number format is not correct." + nfe.getLocalizedMessage());       
            absoluteTimeTextField.setText(new String());
        }   
    }
    
    
    
}