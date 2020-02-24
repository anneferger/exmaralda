/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.exmaralda.common.corpusbuild;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.exmaralda.common.jdomutilities.IOUtilities;
import org.exmaralda.exakt.exmaraldaSearch.COMACorpus;
import org.exmaralda.exakt.exmaraldaSearch.COMACorpusInterface;
import org.exmaralda.exakt.exmaraldaSearch.swing.COMASearchResultListTableModel;
import org.exmaralda.exakt.search.RegularExpressionSearchParameters;
import org.exmaralda.exakt.search.Search;
import org.exmaralda.exakt.search.SearchResultList;
import org.exmaralda.exakt.search.analyses.AnalysisInterface;
import org.exmaralda.exakt.search.analyses.FreeAnalysis;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Element;
import org.xml.sax.SAXException;
import org.exmaralda.exakt.utilities.FileIO;
import static org.exmaralda.exakt.utilities.FileIO.COMASearchResultListToXML;

/**
 *
 * @author Anne
 */
public class Inel_Search {
    
    SearchResultList srl;
    String[][] ADDITIONAL_DATA_LOCATORS = {{"tier-id", "../../@id"}, {"speaker", "../../@speaker"}, {"start", "@s"}};
    String corpusPath = "E:\\Anne\\NganasanCorpus_nganproject\\NSLC.coma";
    String firstRegEx;
    String secondRegEx;
    
    COMACorpus corpus;
    String outputLocation = "C:\\Users\\fsnv625\\Desktop\\output-srl-Inel.xml";

    //String[][] metaArray;

    Vector<String[]> meta = new Vector<String[]>();
    
    Thread searchThread;
    Search search;
    Thread searchThread2;
    Search search2;

    public Inel_Search(String corpusPath, String firstRegEx, String secondRegEx, String outputLocation) {
        this.firstRegEx = firstRegEx;
        this.secondRegEx = secondRegEx;
        this.corpusPath = corpusPath;
        this.outputLocation = outputLocation;
    }

    public Inel_Search() {
        this.firstRegEx = firstRegEx;
        this.secondRegEx = secondRegEx;
        this.corpusPath = corpusPath;
        this.outputLocation = outputLocation;
    }

    public static void main(String[] args) {

        try {
            //Inel_Search mul = new Inel_Search(args[0], args[1], args[2], args [3]);
            Inel_Search inl = new Inel_Search();
            //inl.doIt(args[0]);
            inl.doIt();
            inl.output();
            inl.writetofile();           
        } catch (IOException ex) {
            Logger.getLogger(Inel_Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(Inel_Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Inel_Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Inel_Search.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void doIt() throws JDOMException, IOException, InterruptedException, SAXException{
    doIt(this.corpusPath);
    }
    
    public void doIt(String corpusPath) throws IOException, JDOMException, InterruptedException, SAXException {
        corpus = new COMACorpus();
        //read the corpus in Exakt
        corpus.readCorpus(new File(corpusPath));
        //An annotation search, search for "C." in Annotation tier C, and get asr
        //String xpath = "//segmentation[@name=\'SpeakerContribution_Utterance_Word\']/ts/ts[@n=\'HIAT:u\']/ts";       
        String xpath = "//annotation[@name='ge']/ta";
        //String xpath = "//segmentation[@name=\'SpeakerContribution_Utterance_Word\']/ts/ts[@n=\'HIAT:u\']";
        //ta/contains(text(), 'probably' and 'INFER')"
        corpus.setXPathToSearchableSegment(xpath);
        //this RegEx finds all the C and only another letter ignoring spaces before, after and 
        //inbetween
        //String getier = ".*probably.*";
        String getier = ".*CVB.*";
        //Search parameters get int search type(3) - new INDIRECT_ANNOTATION_SEARCH
        //Search parameters get int search type(0) - default search
        //ANNOTATION_SEARCH = 2;
        RegularExpressionSearchParameters parameters = new RegularExpressionSearchParameters(getier, ADDITIONAL_DATA_LOCATORS, 3, "ge");
        parameters.setContextLimit(10);
        search = new Search(corpus, parameters);
        final Runnable doUpdateSearchResult = new Runnable() {
            public void run() {
                output();
            }
        };
        searchThread = new Thread() {
            @Override
            public void run() {
                search.doSearch();
                javax.swing.SwingUtilities.invokeLater(doUpdateSearchResult);
            }
        };

        //search.doSearch();
        searchThread.start();

        /*search2 = new Search(corpus,parameters);
        searchThread2 = new Thread(){
           @Override
            public void run(){
               search2.doSearch();
               javax.swing.SwingUtilities.invokeLater(doUpdateSearchResult);
           }
        };

       //search.doSearch();
        searchThread2.start();*/
        
        searchThread.join();
        srl = search.getSearchResult();
        //add the analysis column for ge and psN
        FreeAnalysis fa = new FreeAnalysis("ge");
        FreeAnalysis fa2 = new FreeAnalysis("psN");
        AnalysisInterface[] an = {fa, fa2};
        srl.setAnalyses(an);  
         //AddIndirectAnnotation for tier ps overlapType: preceding(3), maxDistance:10, findAll:false      
         //Das stimmt noch nicht, es dürfen nur die gefunden werden, die auch "N" haben als Annotation
         //und es darf auch nur innerhalb des Satzes gesucht werden
         //This here sucks - need to have a better distinction between the parameters for additional annotatioin category and RegEx
        srl.addIndirectAnnotationAsAnalysis("ps", corpus, 3, 10, false, ".*N.*");      
        //Und hiernach kann man davon (psN) noch die ge Annotation und am besten auch noch die tx Transcription angeben             
        FreeAnalysis fa3 = new FreeAnalysis("gepsN");
        FreeAnalysis fa4 = new FreeAnalysis("txpsN");
        AnalysisInterface[] an2 = {fa, fa2, fa3, fa4};
        srl.setAnalyses(an2);     
        srl.addIndirectAnnotationAsAnalysis("ps", corpus, 3, 10, false,"ge");    
        srl.addIndirectAnnotationAsAnalysis("ps", corpus, 3, 10, false, "tx");  
//        srl.addIndirectAnnotationAsAnalysis("AU", corpus, 3, 10, false);        
//        //Add the cs-Annotation for that AU annotation
//        //seems like I would need to have the tier ID for the AU match for that - then I can "add annotation as analysis" for that one...
//        FreeAnalysis fa4 = new FreeAnalysis("AU_cs");
//        AnalysisInterface[] an3 = {fa, fa2, fa3, fa4};
//        srl.setAnalyses(an3);   
//        
//        srl.addIndirectAnnotationAsAnalysis("AU", corpus, 3, 10, false, "cs");
//        //AddIndirectAnnotation for tier EV overlapType: following(4), maxDistance:10, findAll:false
//        FreeAnalysis fa5 = new FreeAnalysis("EV");
//        AnalysisInterface[] an4 = {fa, fa2, fa3, fa4, fa5};
//        srl.setAnalyses(an4);     
//        srl.addIndirectAnnotationAsAnalysis("EV", corpus, 4, 10, false);
//        //Add the cs-Annotation for that EV annotation
//        FreeAnalysis fa6 = new FreeAnalysis("EV_cs");
//        AnalysisInterface[] an5 = {fa, fa2, fa3, fa4, fa5, fa6};
//        srl.setAnalyses(an5);     
//        srl.addIndirectAnnotationAsAnalysis("EV", corpus, 4, 10, false, "cs");
        //Add a column where those results are put together as strings with '-' inbetween
    }
//

    void output() {
        System.out.println("====" + search.getSearchResult().size());
//        /*for (SearchResultInterface sri : search.getSearchResult()){
//            System.out.println(sri.getMatchTextAsString());
//        }*/
//        /*for (SearchResultInterface sri : search2.getSearchResult()){
//            System.out.println(sri.getMatchTextAsString());
//        }*/
//
    }
    
    //now write the KWIC to a file (where it can be opened with Exakt again)
    void writetofile() throws IOException {
        //wait for the search thread to finish before writing the file        
        //srl = search.getSearchResult();
        System.out.println("====" + search.getSearchResult().size());
        //Document doc = org.exmaralda.exakt.utilities.FileIO.COMASearchResultListToXML(list, corpus, meta, exaktFrame.getActiveSearchPanel().getCorpus().getCorpusPath()); 
        System.out.println("started writing document...");
        Document doc = COMASearchResultListToXML(srl, corpus,
                                                 meta,
                                                 corpusPath);
        org.exmaralda.exakt.utilities.FileIO.writeDocumentToLocalFile(new File(outputLocation),doc);
        //srl.writeXML(new File(outputLocation));
        System.out.println("document written.");
        System.out.println("====" + search.getSearchResult().size());
    }

}
