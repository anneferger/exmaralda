/*
 * RemoveTierAction.java
 *
 * Created on 17. Juni 2003, 14:46
 */

package org.exmaralda.partitureditor.partiture.tierActions;

import javax.swing.JOptionPane;
import org.exmaralda.partitureditor.jexmaralda.Tier;
import org.exmaralda.partitureditor.partiture.*;
import org.exmaralda.partitureditor.partiture.undo.UndoInformation;

/**
 *
 * @author  thomas
 */
public class RemoveTierAction extends org.exmaralda.partitureditor.partiture.AbstractTableAction {
    
    /** Creates a new instance of RemoveTierAction
     * @param t
     * @param icon */
    public RemoveTierAction(PartitureTableWithActions t, javax.swing.ImageIcon icon) {
        super("Remove tier...", icon, t);
    }
    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        System.out.println("removeTierAction!");
        table.commitEdit(true);
        removeTier();
        table.transcriptionChanged = true;
    }
    
    private void removeTier(){
        //javax.swing.JOptionPane askDialog = new javax.swing.JOptionPane();
        String htmlMessage = "<html>Are you sure you want to remove the selected tier(s)? <br/><br/>";
        for (int r=table.selectionStartRow; r<=table.selectionEndRow; r++){
            Tier tier = table.getModel().getTier(r);
            htmlMessage+="Tier '" + tier.getDisplayName() + "' has <b>" + Integer.toString(tier.getNumberOfEvents()) + "</b> events.<br/>";
        }
        htmlMessage+="<br/></html>";
        
        int confirmation = JOptionPane.showConfirmDialog( table,
            //"Are you sure you want to remove the selected tier(s)? ",
            htmlMessage,
            "Confirm removal of tier(s)",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null);
        if (confirmation==javax.swing.JOptionPane.YES_OPTION) {
            boolean aSeriesOfRowsIsSelected = ((table.selectionStartRow != table.selectionEndRow) && (table.selectionStartRow != -1) && (table.selectionEndRow != -1));
            if (table.undoEnabled){
                UndoInformation undoInfo = new UndoInformation(table, "Remove tier");
                undoInfo.memorizeTranscription(table);
                table.addUndo(undoInfo);
            }
            if (!aSeriesOfRowsIsSelected){
                int row = table.selectionStartRow;
                table.getModel().removeTier(row);
                table.status("Tier " + row + " removed");
                if (row <= table.getFrameEndPosition()){
                    table.setFrameEndPosition(table.getFrameEndPosition()-1);
                    table.getModel().fireRowLabelsFormatChanged();                    
                }
            }
            else {
                int firstRow = table.selectionStartRow;
                int lastRow = table.selectionEndRow;
                table.getModel().removeTiers(firstRow, lastRow);
                table.status("Tiers " + firstRow + "-" + lastRow + " removed");
                if (firstRow <= table.getFrameEndPosition()){
                    table.setFrameEndPosition(table.getFrameEndPosition() - (Math.min(table.getFrameEndPosition(), lastRow) - firstRow +1));
                    table.getModel().fireRowLabelsFormatChanged();                                        
                }
            }
            
        }
    }
    
    
}
