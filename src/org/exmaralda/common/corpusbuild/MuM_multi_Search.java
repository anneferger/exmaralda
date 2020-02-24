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
    Thread searchThread3;
    Search search3;

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

    public void doIt() throws JDOMException, IOException, InterruptedException, SAXException {
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
        String ctier = "^\\s*C\\s*.\\s*.?\\s*$";
        //Search parameters get int search type(2) because they are an annotation search(ANNOTATION_SEARCH = 2)
        RegularExpressionSearchParameters parametersC = new RegularExpressionSearchParameters(ctier, ADDITIONAL_DATA_LOCATORS, 2);

        search = new Search(corpus, parametersC);
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
        searchThread.start();

        /*
        //now we also need to search in two different tiers and add those as matches:
        xpath = "//annotation[@name='PD']/ta";
        corpus.setXPathToSearchableSegment(xpath);
        RegularExpressionSearchParameters parametersAL = new RegularExpressionSearchParameters("^\\s*PD\\s*$", ADDITIONAL_DATA_LOCATORS, 2);

       

        search2 = new Search(corpus, parametersAL);
        searchThread2 = new Thread() {
            @Override
            public void run() {
                search2.doSearch();
                javax.swing.SwingUtilities.invokeLater(doUpdateSearchResult);
            }
        };

        xpath = "//annotation[@name='AL']/ta";
        corpus.setXPathToSearchableSegment(xpath);
        RegularExpressionSearchParameters parametersPD = new RegularExpressionSearchParameters("^\\s*AL\\s*$", ADDITIONAL_DATA_LOCATORS, 2);
        searchThread2.start();

        search3 = new Search(corpus, parametersPD);
        searchThread3 = new Thread() {
            @Override
            public void run() {
                search3.doSearch();
                javax.swing.SwingUtilities.invokeLater(doUpdateSearchResult);
            }
        };

        searchThread3.start();
        */

        searchThread.join();
        srl = search.getSearchResult();
        /*
        searchThread2.join();
        srl.addSearchResults(search2.getSearchResult());
        searchThread3.join();
        srl.addSearchResults(search3.getSearchResult());
        */

        //now we want to ad all the complicated annotations in columns
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
        FreeAnalysis fa4 = new FreeAnalysis("AU_cs");
        AnalysisInterface[] an3 = {fa, fa2, fa3, fa4};
        srl.setAnalyses(an3);
        //now search for the recently found AU annotation again (I know, ugly) and find the corresponding cs annotation
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
        org.exmaralda.exakt.utilities.FileIO.writeDocumentToLocalFile(new File(outputLocation), doc);
        //srl.writeXML(new File(outputLocation));
        System.out.println("document written.");
        System.out.println("====" + search.getSearchResult().size());
    }

}
