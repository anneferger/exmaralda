/*
 * BookmarksDialog.java
 *
 * Created on 11. November 2004, 13:58
 */

package org.exmaralda.partitureditor.jexmaraldaswing;

import org.exmaralda.common.helpers.Internationalizer;
import org.exmaralda.partitureditor.search.EventSearchResult;
import org.exmaralda.partitureditor.search.SearchResultListener;
import org.exmaralda.partitureditor.jexmaralda.Timeline;
import org.exmaralda.partitureditor.jexmaralda.TimelineItem;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author  thomas
 */
public class BookmarksDialog extends javax.swing.JDialog {
    
    DefaultListModel bookmarksListModel = new DefaultListModel();
    Timeline timeline;
    Vector allBookmarks;
    javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    
    
    /** Creates new form BookmarksDialog
     * @param parent
     * @param modal
     * @param tl */
    public BookmarksDialog(java.awt.Frame parent, boolean modal, Timeline tl) {
        super(parent, modal);
        timeline = tl;
        initComponents();
        allBookmarks = tl.getAllBookmarks();
        for (int pos=0; pos<allBookmarks.size(); pos++){
            TimelineItem tli = (TimelineItem)(allBookmarks.elementAt(pos));
            bookmarksListModel.addElement(tli.getBookmark());
        }
        bookmarksList.setModel(bookmarksListModel);
        if (!allBookmarks.isEmpty()){
            bookmarksList.setSelectedIndex(0);
        }
        Internationalizer.internationalizeDialogToolTips(this);        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        bookmarksList = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        gotoButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bookmarks");

        bookmarksList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookmarksListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(bookmarksList);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gotoButton.setText("Go to...");
        gotoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gotoButtonActionPerformed(evt);
            }
        });
        jPanel1.add(gotoButton);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gotoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gotoButtonActionPerformed
        // TODO add your handling code here:
        if (bookmarksList.getSelectedIndex()<0) return;
        EventSearchResult evr = new EventSearchResult();
        evr.tierName="###TIMELINE###";
        TimelineItem tli = (TimelineItem)(allBookmarks.elementAt(bookmarksList.getSelectedIndex()));
        evr.tierID=tli.getID();
        fireSearchResult(evr);
    }//GEN-LAST:event_gotoButtonActionPerformed

    private void bookmarksListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookmarksListMouseClicked
        if (evt.getClickCount()==2){
            gotoButtonActionPerformed(null);
        }
    }//GEN-LAST:event_bookmarksListMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //new BookmarksDialog(new javax.swing.JFrame(), true).show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList bookmarksList;
    private javax.swing.JButton gotoButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void addSearchResultListener(SearchResultListener l) {
         listenerList.add(SearchResultListener.class, l);
    }
    
    public void removeAllListeners(){
        listenerList = new javax.swing.event.EventListenerList();
    }
      
    protected void fireSearchResult(EventSearchResult esr) {
         // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
             if (listeners[i]==SearchResultListener.class) {                
                ((SearchResultListener)listeners[i+1]).processSearchResult(esr);             
            }
         }
    }
    
    public void show(){
        java.awt.Dimension dialogSize = this.getPreferredSize();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - dialogSize.width/2, screenSize.height/2 - dialogSize.height/2);
        super.show();
    }
    
}
