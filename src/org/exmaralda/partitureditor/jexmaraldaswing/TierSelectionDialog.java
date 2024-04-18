/*
 * TierSelectionDialog.java
 *
 * Created on 19. Juni 2003, 13:18
 */

package org.exmaralda.partitureditor.jexmaraldaswing;

import org.exmaralda.common.helpers.Internationalizer;
import org.exmaralda.partitureditor.jexmaralda.JexmaraldaException;
import org.exmaralda.partitureditor.jexmaralda.BasicTranscription;
import javax.swing.*;

/**
 *
 * @author  thomas
 */
public class TierSelectionDialog extends javax.swing.JDialog {
    
    private BasicTranscription transcription;
    private DefaultListModel selectedTiersListModel = new DefaultListModel();
    private DefaultListModel unselectedTiersListModel = new DefaultListModel();
    public boolean change;
    
        
    /** Creates new form TierSelectionDialog */
    public TierSelectionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        unselectedTiersList.setCellRenderer(new MyCellRenderer());
        selectedTiersList.setCellRenderer(new MyCellRenderer());    
        Internationalizer.internationalizeDialogToolTips(this);        
    }
    
    public String[] getSelectedTierIDs(){
        String[] result = new String[selectedTiersListModel.size()];
        for (int pos=0; pos<selectedTiersListModel.size(); pos++){
            result[pos] = (String)(selectedTiersListModel.elementAt(pos));
        }
        return result;
    }

    public String[] getUnselectedTierIDs(){
        String[] result = new String[unselectedTiersListModel.size()];
        for (int pos=0; pos<unselectedTiersListModel.size(); pos++){
            result[pos] = (String)(unselectedTiersListModel.elementAt(pos));
        }
        return result;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        unselectedTiersList = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        selectButton = new javax.swing.JButton();
        selectAllButton = new javax.swing.JButton();
        deselectButton = new javax.swing.JButton();
        deselectAllButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectedTiersList = new javax.swing.JList();

        setTitle("Select tiers");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        getAccessibleContext().setAccessibleParent(null);
        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jPanel1.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel1.add(cancelButton);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.X_AXIS));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 100));
        unselectedTiersList.setBorder(new javax.swing.border.TitledBorder("Unselected tiers"));
        jScrollPane1.setViewportView(unselectedTiersList);

        jPanel3.add(jScrollPane1);

        jPanel2.setMaximumSize(new java.awt.Dimension(100, 10000));
        jPanel2.setMinimumSize(new java.awt.Dimension(60, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(60, 200));
        selectButton.setText(">");
        selectButton.setMaximumSize(new java.awt.Dimension(50, 26));
        selectButton.setMinimumSize(new java.awt.Dimension(50, 26));
        selectButton.setPreferredSize(new java.awt.Dimension(50, 26));
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        jPanel2.add(selectButton);

        selectAllButton.setText(">>");
        selectAllButton.setMaximumSize(new java.awt.Dimension(50, 26));
        selectAllButton.setMinimumSize(new java.awt.Dimension(50, 26));
        selectAllButton.setPreferredSize(new java.awt.Dimension(50, 26));
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        jPanel2.add(selectAllButton);

        deselectButton.setText("<");
        deselectButton.setMaximumSize(new java.awt.Dimension(50, 26));
        deselectButton.setMinimumSize(new java.awt.Dimension(50, 26));
        deselectButton.setPreferredSize(new java.awt.Dimension(50, 26));
        deselectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselectButtonActionPerformed(evt);
            }
        });

        jPanel2.add(deselectButton);

        deselectAllButton.setText("<<");
        deselectAllButton.setMaximumSize(new java.awt.Dimension(50, 26));
        deselectAllButton.setMinimumSize(new java.awt.Dimension(50, 26));
        deselectAllButton.setPreferredSize(new java.awt.Dimension(50, 26));
        deselectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselectAllButtonActionPerformed(evt);
            }
        });

        jPanel2.add(deselectAllButton);

        jPanel3.add(jPanel2);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(200, 100));
        selectedTiersList.setBorder(new javax.swing.border.TitledBorder("Selected tiers"));
        jScrollPane2.setViewportView(selectedTiersList);

        jPanel3.add(jScrollPane2);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // Add your handling code here:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // Add your handling code here:
        change = true;
        setVisible(false);
        dispose();        
    }//GEN-LAST:event_okButtonActionPerformed

    private void deselectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectAllButtonActionPerformed
        // Add your handling code here:
        for (int pos=0; pos<selectedTiersListModel.size(); pos++){
            unselectedTiersListModel.addElement(selectedTiersListModel.elementAt(pos));
        }
        selectedTiersListModel.clear();
    }//GEN-LAST:event_deselectAllButtonActionPerformed

    private void deselectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectButtonActionPerformed
        // Add your handling code here:
        if (selectedTiersList.getSelectedIndex()>=0){
            unselectedTiersListModel.addElement(selectedTiersList.getSelectedValue());
            selectedTiersListModel.removeElementAt(selectedTiersList.getSelectedIndex());
        }
        
    }//GEN-LAST:event_deselectButtonActionPerformed

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
        // Add your handling code here:
        for (int pos=0; pos<unselectedTiersListModel.size(); pos++){
            selectedTiersListModel.addElement(unselectedTiersListModel.elementAt(pos));
        }
        unselectedTiersListModel.clear();
    }//GEN-LAST:event_selectAllButtonActionPerformed

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        // Add your handling code here:
        /*if (unselectedTiersList.getSelectedIndex()>=0){
            selectedTiersListModel.addElement(unselectedTiersList.getSelectedValue());
            unselectedTiersListModel.removeElementAt(unselectedTiersList.getSelectedIndex());
        }*/

        for (Object o : unselectedTiersList.getSelectedValues()){
            selectedTiersListModel.addElement(o);
        }

        for (Object o : unselectedTiersList.getSelectedValues()){
            unselectedTiersListModel.removeElement(o);
        }

    }//GEN-LAST:event_selectButtonActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        TierSelectionDialog tsd = new TierSelectionDialog(new javax.swing.JFrame(), true);
        try{
            String[] t = {"TIE1", "TIE7", "TIE6"};
            BasicTranscription bt = new BasicTranscription("D:\\AAA_Beispiele\\Helge_neu\\Helge_Basic.xml");
            tsd.selectTiers(bt, t);
        } catch (Throwable t){}
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deselectAllButton;
    private javax.swing.JButton deselectButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton okButton;
    private javax.swing.JButton selectAllButton;
    private javax.swing.JButton selectButton;
    private javax.swing.JList selectedTiersList;
    private javax.swing.JList unselectedTiersList;
    // End of variables declaration//GEN-END:variables
    
    public boolean selectTiers(BasicTranscription t, String[] selectedTierIDs){
        transcription = t;
        if (selectedTierIDs!=null){
            for (int pos=0; pos<selectedTierIDs.length; pos++){
                selectedTiersListModel.addElement(selectedTierIDs[pos]);
            }
        }
        selectedTiersList.setModel(selectedTiersListModel);
        if (selectedTiersListModel.size()>0){
            selectedTiersList.setSelectedIndex(0);
        }
        String[] allTierIDs = transcription.getBody().getAllTierIDs();
        for (int pos=0; pos<allTierIDs.length; pos++){
            if (!selectedTiersListModel.contains(allTierIDs[pos])){
                unselectedTiersListModel.addElement(allTierIDs[pos]);
            }
        }
        unselectedTiersList.setModel(unselectedTiersListModel);
        if (unselectedTiersListModel.size()>0){
            unselectedTiersList.setSelectedIndex(0);
        }
        this.pack();
        java.awt.Dimension dialogSize = this.getPreferredSize();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - dialogSize.width/2, screenSize.height/2 - dialogSize.height/2);
        this.setVisible(true);
        return change;
    }
    
class MyCellRenderer implements ListCellRenderer {

    public MyCellRenderer() {
    }
    
    @Override
    public java.awt.Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
     {         
         JLabel result = new JLabel();
         result.setOpaque(true);
         String tierID = (String)value;
         try{
             String desc = transcription.getBody().getTierWithID(tierID).getDescription(transcription.getHead().getSpeakertable());             
             result.setText(desc += " (" + tierID + ")");
             if (!isSelected){
                 result.setBackground(java.awt.Color.white);
             } else {
                 result.setBackground(java.awt.Color.lightGray);
             }
             return result;
         } catch (JexmaraldaException je){
            // should never get here
            return null;
         }         
    }    
}

}
