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
public class MuM_multi_Search {
    
    SearchResultList srl;
    String[][] ADDITIONAL_DATA_LOCATORS = {{"tier-id", "../../@id"}, {"speaker", "../../@speaker"}, {"start", "@s"}};
    String corpusPath = "E:\\Anne\\MuM-multi_Daten\\TestMuM_multi.coma";
    String firstRegEx;
    String secondRegEx;
    
    COMACorpus corpus;
    String outputLocation = "C:\\Users\\fsnv625\\Desktop\\output-srl.xml";

    //String[][] metaArray;

    Vector<String[]> meta = new Vector<String[]>();
    
    Thread searchThread;
    Search search;
    Thread searchThread2;
    Search search2;

    public MuM_multi_Search(String corpusPath, String firstRegEx, String secondRegEx, String outputLocation) {
        this.firstRegEx = firstRegEx;
        this.secondRegEx = secondRegEx;
        this.corpusPath = corpusPath;
        this.outputLocation = outputLocation;
    }

    public MuM_multi_Search() {
        this.firstRegEx = firstRegEx;
        this.secondRegEx = secondRegEx;
        this.corpusPath = corpusPath;
        this.outputLocation = outputLocation;
    }

    public static void main(String[] args) {

        try {
            //MuM_multi_Search mul = new MuM_multi_Search(args[0], args[1], args[2], args [3]);
            MuM_multi_Search mul = new MuM_multi_Search();
            //mul.doIt(args[0]);
            mul.doIt();
            mul.output();
            mul.writetofile();           
        } catch (IOException ex) {
            Logger.getLogger(MuM_multi_Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(MuM_multi_Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MuM_multi_Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MuM_multi_Search.class.getName()).log(Level.SEVERE, null, ex);
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
        String xpath = "//annotation[@name='C']/ta";
        corpus.setXPathToSearchableSegment(xpath);
        //this RegEx finds all the C and only another letter ignoring spaces before, after and 
        //inbetween
        String ctier = "^\\s*C\\s*.\\s*$";
        //Search parameters get int search type(2) because they are an annotation search(ANNOTATION_SEARCH = 2)
        RegularExpressionSearchParameters parameters = new RegularExpressionSearchParameters(ctier, ADDITIONAL_DATA_LOCATORS, 2);
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
        //add the analysis column for cs
        FreeAnalysis fa = new FreeAnalysis("C");
        FreeAnalysis fa2 = new FreeAnalysis("C_cs");
        AnalysisInterface[] an = {fa, fa2};
        srl.setAnalyses(an);  
        //add the cs Annotation to the C-Annotation, overlapType exact(0)        
        srl.addAnnotationAsAnalysis("cs", corpus, 0);      
        //AddIndirectAnnotation for tier AU overlapType: preceding(3), maxDistance:10, findAll:false
        FreeAnalysis fa3 = new FreeAnalysis("AU");
        AnalysisInterface[] an2 = {fa, fa2, fa3};
        srl.setAnalyses(an2);     
        srl.addIndirectAnnotationAsAnalysis("AU", corpus, 3, 10, false);        
        //Add the cs-Annotation for that AU annotation
        //seems like I would need to have the tier ID for the AU match for that - then I can "add annotation as analysis" for that one...
        FreeAnalysis fa4 = new FreeAnalysis("AU_cs");
        AnalysisInterface[] an3 = {fa, fa2, fa3, fa4};
        srl.setAnalyses(an3);   
        //right now this only takes the closest preceding CS Value - but it needs to be the one annotated with the AU tag
        srl.addIndirectAnnotationAsAnalysis("AU", corpus, 3, 10, false, "cs");
        //AddIndirectAnnotation for tier EV overlapType: following(4), maxDistance:10, findAll:false
        FreeAnalysis fa5 = new FreeAnalysis("EV");
        AnalysisInterface[] an4 = {fa, fa2, fa3, fa4, fa5};
        srl.setAnalyses(an4);     
        srl.addIndirectAnnotationAsAnalysis("EV", corpus, 4, 10, false);
        //Add the cs-Annotation for that EV annotation
        FreeAnalysis fa6 = new FreeAnalysis("EV_cs");
        AnalysisInterface[] an5 = {fa, fa2, fa3, fa4, fa5, fa6};
        srl.setAnalyses(an5);     
        srl.addIndirectAnnotationAsAnalysis("EV", corpus, 4, 10, false, "cs");
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
