/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.exmaralda.exakt.exmaraldaSearch.KWICTableActions;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.exmaralda.exakt.exmaraldaSearch.swing.AddIndirectAnnotationDialog;
import org.exmaralda.exakt.exmaraldaSearch.swing.COMAKWICTable;
import org.exmaralda.exakt.search.SearchResultList;

/**
 * This class works similiar to AddAnnotationAction, but has more parameters and can add any tier
 * of the transcription (annotation, description, transcription) and not only take the one
 * that is directly annotatedt ("happening at the same time"), but also in a speciefied range before 
 * or after the match. 
 * 
 * @author Anne
 */
public class AddIndirectAnnotationAction extends AbstractKWICTableAction {
     int count=0;
    
    
    /** Creates a new instance of WordWiseReversedSortAction
     * @param t
     * @param title */
    public AddIndirectAnnotationAction(COMAKWICTable t, String title) {
        super(t,title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            HashSet<String> tierNames = table.getWrappedModel().getCorpus().getAnnotationNames();
            tierNames.addAll(table.getWrappedModel().getCorpus().getDescriptionNames());
            tierNames.addAll(table.getWrappedModel().getCorpus().getSegmentationNames());
            //getAnnotationNames();
            //getDescriptionNames()
            //getSegmentationNames()
            //Info where the names come from: 
            //static String XPATH_TO_SEGMENTATION_NAMES = "//segmented-tier[@type='t']/segmentation/@name";
            //static String XPATH_TO_ANNOTATION_NAMES = "//annotation/@name";
            //static String XPATH_TO_DESCRIPTION_NAMES = "//segmented-tier[@type='d']/@category";
            //String XPATH_TO_SEARCHABLE_SEGMENT = "//segmentation[@name='SpeakerContribution_Event']/ts";
            //static String XPATH_TO_SEGMENT_NAMES = "//*[self::ts or self::ats or self::nts]/@n";
            if (tierNames.size()<=0){
            JOptionPane.showMessageDialog(table, "No tiers in this corpus.");
                return;
            }
            AddIndirectAnnotationDialog aad = new AddIndirectAnnotationDialog((JFrame)(table.getTopLevelAncestor()), true);
            //aad.setCategories(tierNames);
            //aad.setLocationRelativeTo(table);
            //aad.setVisible(true);

            table.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            SearchResultList srl = table.getWrappedModel().getData();
            // changed 18-10-2011 - bug fix
            //SearchResultList newsrl = srl.addIndirectAnnotationAsAnalysis(aad.getCategory(), table.getWrappedModel(), aad.getOverlapType());
            //SearchResultList newsrl = srl.addAnnotationAsAnalysis(aad.getCategory(), table.getWrappedModel().getCorpus(), aad.getType());
            //table.getWrappedModel().setData(newsrl);
            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(table, ex.getLocalizedMessage());
        }
    }
    
}
