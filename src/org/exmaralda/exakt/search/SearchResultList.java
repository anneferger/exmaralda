/*
 * SearchResultList.java
 *
 * Created on 9. Januar 2007, 15:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.exmaralda.exakt.search;

import java.util.*;
import org.exmaralda.partitureditor.jexmaralda.JexmaraldaException;
import org.jdom.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.exmaralda.exakt.exmaraldaSearch.COMACorpus;
import org.exmaralda.exakt.exmaraldaSearch.COMACorpusInterface;
import org.exmaralda.exakt.exmaraldaSearch.swing.COMASearchResultListTableModel;
import org.exmaralda.exakt.search.analyses.*;
import org.exmaralda.partitureditor.jexmaralda.Annotation;
import org.exmaralda.partitureditor.jexmaralda.BasicTranscription;
import org.exmaralda.partitureditor.jexmaralda.Event;
import org.exmaralda.partitureditor.jexmaralda.SegmentedTier;
import org.exmaralda.partitureditor.jexmaralda.SegmentedTranscription;
import org.exmaralda.partitureditor.jexmaralda.Tier;
import org.exmaralda.partitureditor.jexmaralda.TimedAnnotation;
import org.exmaralda.partitureditor.jexmaralda.Timeline;
import org.exmaralda.partitureditor.jexmaralda.sax.SegmentedTranscriptionSaxReader;
import org.xml.sax.SAXException;

/**
 *
 * @author thomas
 */
public class SearchResultList extends Vector<SearchResultInterface> {

    String baseDirectory = "";
    Vector<AnalysisInterface> analyses = new Vector<AnalysisInterface>();
    COMASearchResultListTableModel tableModel;

    /**
     * Creates a new instance of SearchResultList
     */
    public SearchResultList() {
    }

    public Vector<AnalysisInterface> getAnalyses() {
        return analyses;
    }

    public void setAnalyses(Vector<AnalysisInterface> an) {
        analyses = an;
    }

    public void setAnalyses(AnalysisInterface[] an) {
        analyses.clear();
        for (AnalysisInterface s : an) {
            analyses.add(s);
        }
    }

    public void setBaseDirectory(String bd) {
        baseDirectory = bd;
    }

    public void read(File file) throws JDOMException, IOException {
        Document xmlDocument = org.exmaralda.exakt.utilities.FileIO.readDocumentFromLocalFile(file);
        read(xmlDocument);
    }

    public void read(File file, String u) throws JDOMException, IOException {
        Document xmlDocument = org.exmaralda.exakt.utilities.FileIO.readDocumentFromLocalFile(file);
        read(xmlDocument, u);
    }

    public void read(Document xmlDocument, String u) throws JDOMException, IOException {
        baseDirectory = u;
        // new 21-11-2011: need to distinguish Annotation Search Results
        String resultType = xmlDocument.getRootElement().getAttributeValue("type");
        boolean isAnnotationSearchResult = ((resultType != null) && ("annotation-search-result".equals(resultType)));

        Element ae = xmlDocument.getRootElement().getChild("analyses");
        if (ae != null) {
            List a = ae.getChildren("analysis");
            for (Object o : a) {
                Element ana = (Element) o;
                String type = ana.getAttributeValue("type");
                AnalysisInterface analysis = null;
                if (type.equals("ClosedCategoryList")) {
                    analysis = new ClosedCategoryListAnalysis(ana);
                } else if (type.equals("Binary")) {
                    analysis = new BinaryAnalysis(ana);
                } else {
                    analysis = new FreeAnalysis(ana);
                }
                getAnalyses().addElement(analysis);
            }
        }
        List l = xmlDocument.getRootElement().getChildren("search-result");
        for (Object o : l) {
            Element e = (Element) o;
            // new 21-11-2011: need to distinguish Annotation Search Results
            SearchResultInterface sri = null;
            if (!isAnnotationSearchResult) {
                sri = new SimpleSearchResult(e, u);
            } else {
                sri = new AnnotationSearchResult(e, u);
            }
            addSearchResult(sri);
        }
    }

    public void read(Document xmlDocument) throws JDOMException, IOException {
        String u = xmlDocument.getRootElement().getChild("base-directory").getAttributeValue("url");
        baseDirectory = u;
        read(xmlDocument, baseDirectory);
    }

    public void addSearchResult(SearchResultInterface searchResult) {
        addElement(searchResult);
    }

    public void addSearchResults(SearchResultList resultList) {
        // this is truly weird - adding this line 
        // makes a bug disappear which I, the great programmer of this tool,
        // discovered when searching descriptions in the HAMATAC
        // well, no it does not
        // the behaviour is unpredictable
        // I am goin to add results one by one
        // and see if that helps
        // no it doesn't
        // I'm adding the synchronized word before the method
        // no idea what that is good for, though
        // removed it, did not help
        // Finally found it: it was in the GUI
        // text field for description search expression
        // had two action listeners
        // two searches were carried out simultaneously - DING DONG
        //System.out.println("Adding " + resultList.size() + " results.");
        addAll(resultList);
        //for (SearchResultInterface searchResult : resultList){
        //    addSearchResult(searchResult);
        //}
    }

    public HashSet getTypes() {
        HashSet<String> retValue = new HashSet<String>();
        for (SearchResultInterface sr : this) {
            retValue.add(sr.getMatchTextAsString());
        }
        return retValue;
    }

    public void filterLeftContext(java.util.regex.Pattern filter, boolean invert) {
        for (SearchResultInterface r : this) {
            java.util.regex.Matcher matcher = filter.matcher(r.getLeftContextAsString());
            matcher.reset();
            r.setSelected((r.isSelected()) && (matcher.find() ^ invert));
        }
    }

    public void filterRightContext(java.util.regex.Pattern filter, boolean invert) {
        for (SearchResultInterface r : this) {
            java.util.regex.Matcher matcher = filter.matcher(r.getRightContextAsString());
            r.setSelected((r.isSelected()) && (matcher.find() ^ invert));
        }
    }

    public void filterMatchText(java.util.regex.Pattern filter, boolean invert) {
        for (SearchResultInterface r : this) {
            java.util.regex.Matcher matcher = filter.matcher(r.getMatchTextAsString());
            r.setSelected((r.isSelected()) && (matcher.find() ^ invert));
        }
    }

    public void removeFilter() {
        for (SearchResultInterface r : this) {
            r.setSelected(true);
        }
    }

    public void removeUnselected() {
        Vector<SearchResultInterface> toBeRemoved = new Vector<SearchResultInterface>();
        for (SearchResultInterface r : this) {
            if (!r.isSelected()) {
                toBeRemoved.addElement(r);
            }
        }
        this.removeAll(toBeRemoved);
    }

    public int getSelectedCount() {
        int count = 0;
        for (SearchResultInterface r : this) {
            if (r.isSelected()) {
                count++;
            }
        }
        return count;
    }

    public void sample(int howMany) {
        Random random = new Random(new java.util.Date().getTime());
        HashSet<Integer> numbers = new HashSet<Integer>();
        while ((numbers.size() < howMany) && numbers.size() < size()) {
            int number = random.nextInt(size());
            numbers.add(new Integer(number));
        }
        for (int pos = 0; pos < size(); pos++) {
            SearchResultInterface r = elementAt(pos);
            r.setSelected(numbers.contains(pos));
        }
    }

    public Element toXML() {
        Element returnValue = new Element("search-result-list");

        Element bd = new Element("base-directory");
        bd.setAttribute("url", baseDirectory);
        returnValue.addContent(bd);
        System.out.println("*********** ADDED Base Directory Element");

        Element a = new Element("analyses");
        for (AnalysisInterface ai : analyses) {
            a.addContent(ai.toXML());
        }
        returnValue.addContent(a);

        for (SearchResultInterface r : this) {
            Element resultElement = r.toXML(baseDirectory);
            returnValue.addContent(resultElement);
        }
        return returnValue;
    }

    public void writeXML(File file) throws IOException {
        Document document = new Document();
        document.setRootElement(toXML());
        org.exmaralda.exakt.utilities.FileIO.writeDocumentToLocalFile(file, document);
    }

    public SearchResultList merge(SearchResultList other) {
        return merge(other, false);
    }

    public SearchResultList merge(SearchResultList other, boolean removeDuplicates) {
        SearchResultList result = new SearchResultList();
        result.setBaseDirectory(this.baseDirectory);
        result.setAnalyses(this.getAnalyses());
        for (SearchResultInterface r : this) {
            result.addSearchResult(r);
        }
        for (SearchResultInterface r : other) {
            result.addSearchResult(r);
        }
        if (removeDuplicates) {
            SearchResultComparator src = new SearchResultComparator();
            Collections.sort(result, src);
            for (int pos = 0; pos < result.size() - 1; pos++) {
                SearchResultInterface sr1 = result.elementAt(pos);
                SearchResultInterface sr2 = result.elementAt(pos + 1);
                if (src.compare(sr1, sr2) == 0) {
                    result.remove(sr2);
                    pos--;
                }
            }
        }
        return result;
    }

    public static final int EXACT_OVERLAP = 0;
    public static final int INCLUDED_IN = 1;
    public static final int ANY_OVERLAP = 2;

    public SearchResultList addAnnotationAsAnalysis(String annotationCategory, COMASearchResultListTableModel tm, int overlapType) throws SAXException {
        tableModel = tm;
        COMACorpusInterface corpus = tableModel.getCorpus();
        return addAnnotationAsAnalysis(annotationCategory, corpus, overlapType);
    }

    // 06-08-2010: this is the next big thing
    // Timm aka Dart Vader wants it:
    // go through an existing list and add all annotations
    // from a transcription and put it into an additional analysis
    // column: this will enable cross-level filtering
    public SearchResultList addAnnotationAsAnalysis(String annotationCategory, COMACorpusInterface corpus, int overlapType) throws SAXException {
        SearchResultList returnValue = new SearchResultList();
        // go through all search results
        // copy all the information
        // and for each entry
        // look into the transcription
        // and find annotations of the specified category
        Vector<AnalysisInterface> newAnalyses = new Vector<AnalysisInterface>();
        newAnalyses.addAll(this.getAnalyses());
        FreeAnalysis additionalAnalysis = new FreeAnalysis(annotationCategory);
        newAnalyses.add(additionalAnalysis);
        returnValue.setAnalyses(newAnalyses);

        // changed 18-10-2011 - bugfix
        if (tableModel != null) {
            tableModel.addAnalysis(additionalAnalysis);
        }

        String filename = "";
        BasicTranscription bt = null;
        SegmentedTranscription st = null;
        SegmentedTranscriptionSaxReader str = new SegmentedTranscriptionSaxReader();
        for (SearchResultInterface sr : this) {
            String annotationValue = "";
            String newfilename = (String) (sr.getSearchableSegmentLocator().getCorpusComponentLocator());
            //System.out.println("FILENAME: " + newfilename);
            if ((!filename.equals(newfilename))) {
                filename = newfilename;
                if (corpus instanceof COMACorpus) {
                    st = str.readFromFile(filename);
                } else {
                    System.out.println("==============" + filename);
                    st = str.readFromURL(filename);
                }
                try {
                    // make a basic version from the segmented one
                    bt = st.toBasicTranscription();
                } catch (JexmaraldaException ex) {
                    ex.printStackTrace();
                }
            }
            String tierID = sr.getAdditionalData()[0];
            String speakerID = sr.getAdditionalData()[1];
            String startID = sr.getAdditionalData()[2];
            String endID = "";

            if (!(sr instanceof AnnotationSearchResult)) {
                try {
                    Tier tier = bt.getBody().getTierWithID(tierID);
                    // find the exact startpoint
                    SimpleSearchResult ssr = (SimpleSearchResult) sr;
                    int count = 0;
                    int s = ssr.getOriginalMatchStart();
                    while (count + tier.getEventAtStartPoint(startID).getDescription().length() <= s) {
                        count += tier.getEventAtStartPoint(startID).getDescription().length();
                        startID = tier.getEventAtStartPoint(startID).getEnd();
                    }
                    endID = tier.getEventAtStartPoint(startID).getEnd();
                } catch (JexmaraldaException ex) {
                    ex.printStackTrace();
                }
            } else {
                // have to find the annotation again
                // this is awkward
                AnnotationSearchResult asr = (AnnotationSearchResult) sr;
                String existingAnnotationName = this.getAnalyses().elementAt(0).getName();

                ArrayList<Tier> tiers = new ArrayList<Tier>();
                //Tier tier = null;
                for (int pos = 0; pos < bt.getBody().getNumberOfTiers(); pos++) {
                    Tier t = bt.getBody().getTierAt(pos);
                    if ((t.getType().equals("a"))
                            && (t.getCategory().equals(existingAnnotationName))
                            && (t.getSpeaker() != null)
                            && (t.getSpeaker().equals(speakerID))) {
                        //tier = t;
                        //break;
                        tiers.add(t);
                    }
                }
                // ATTENTION: Maybe the tier is null for POS annotations etc.
                //if (tier==null){
                if (tiers.isEmpty()) {
                    // WHAT TO DO????
                    // asketh the programmer, 21-06-2011
                    // Now also available as an issue (#36), 22-11-2016
                    // to get here: search for POS in a POS-tagged corpus
                    // then add "annotation as analysis": LEMMA 
                    System.out.println("Here we have issue #36!");
                    // this code addresses issue #36
                    // it is for sure the worst code that any vertebrate 
                    // has ever come up with
                    // Shame one me!
                    for (int pos = 0; pos < st.getBody().getNumberOfTiers(); pos++) {
                        SegmentedTier t = st.getBody().getSegmentedTierAt(pos);
                        if ((t.getSpeaker() != null) && (t.getSpeaker().equals(speakerID))) {
                            Annotation a = t.getAnnotationWithName(annotationCategory);
                            for (int i = 0; i < a.size(); i++) {
                                TimedAnnotation ta = (TimedAnnotation) (a.get(i));
                                if (ta.getStart().equals(startID)) {
                                    annotationValue = ta.getDescription();
                                    break;
                                }
                            }
                            sr.setAdditionalData(getAnalyses().size() - 1 + 3, annotationValue);
                            returnValue.add(sr);
                        }
                    }
                    continue;
                }
                for (Tier tier : tiers) {
                    try {
                        // changed 21-06-2011 to account for annotations not in the common timeline
                        if (tier.containsEventAtStartPoint(startID)) {
                            endID = tier.getEventAtStartPoint(startID).getEnd();
                            break;
                        } else {
                            // changed 14-10-2013
                            String commonTimelineMatch = st.getBody().getCommonTimelineMatch(startID);
                            if (tier.containsEventAtStartPoint(commonTimelineMatch)) {
                                endID = tier.getEventAtStartPoint(commonTimelineMatch).getEnd();
                                break;
                            } else {
                                System.out.println("Hanna's problem!!!");
                            }
                        }
                    } catch (JexmaraldaException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            String startID2 = st.getBody().getCommonTimelineMatch(startID);
            String endID2 = st.getBody().getCommonTimelineMatch(endID);
            //System.out.println(tierID +  "  "  + startID2 + "  " + endID2);

            // now find the right tier
            Tier annotationTier = null;
            for (int pos = 0; pos < bt.getBody().getNumberOfTiers(); pos++) {
                Tier t = bt.getBody().getTierAt(pos);
                if ((t.getType().equals("a"))
                        && (t.getCategory().equals(annotationCategory))
                        && (t.getSpeaker() != null)
                        && (t.getSpeaker().equals(speakerID))) {
                    annotationTier = t;
                    break;
                }
            }
            if (annotationTier != null) {
                // need to adapt this according to the intersection type
                switch (overlapType) {
                    case EXACT_OVERLAP:
                        Event annotation = null;
                        try {
                            annotation = annotationTier.getEventAtStartPoint(startID2);
                        } catch (JexmaraldaException ex) {
                            // this simply means there was no such event
                            //ex.printStackTrace();
                            //System.out.print("*");
                        }
                        if ((annotation != null) && (annotation.getEnd().equals(endID2))) {
                            annotationValue = annotation.getDescription();
                        }
                        break;
                    case INCLUDED_IN:
                        Vector<Event> annotations = annotationTier.getEventsIntersecting(bt.getBody().getCommonTimeline(), startID2, endID2);
                        Timeline tl = bt.getBody().getCommonTimeline();
                        int startIndex = tl.lookupID(startID2);
                        int endIndex = tl.lookupID(endID2);
                        for (Event e : annotations) {
                            int index1 = tl.lookupID(e.getStart());
                            int index2 = tl.lookupID(e.getEnd());
                            if ((index1 <= startIndex) && (index2 >= endIndex)) {
                                annotationValue = e.getDescription();
                                break;
                            }
                        }
                        break;
                    case ANY_OVERLAP:
                        Vector<Event> annotations2 = annotationTier.getEventsIntersecting(bt.getBody().getCommonTimeline(), startID2, endID2);
                        for (Event e : annotations2) {
                            annotationValue += e.getDescription() + " ";
                        }
                        break;
                }

            }

            // now add it to the new search result
            // this is wrong (22-11-2012)
            //sr.setAdditionalData(sr.getAdditionalData().length, annotationValue);
            // is this right???
            sr.setAdditionalData(getAnalyses().size() - 1 + 3, annotationValue);
            returnValue.add(sr);
        }
        return returnValue;
    }

    //this adds an indirect annotation to a KWIC table, this annotation can also be 
    //Overlap ints can be used from the default addAnnotationAsAnalysis
    //public static final int EXACT_OVERLAP = 0;
    //public static final int INCLUDED_IN = 1;
    //public static final int ANY_OVERLAP = 2;
    public static final int PRECEDING = 3;
    public static final int FOLLOWING = 4;
    //MAX_DISTANCE is still to do
    //Hm, I'm not sure if it's enough to choose the tier category and the speaker - because there 
    //could be more than one of this type of tier in the transcription? - Probably need to 
    //solve/check this later

    public SearchResultList addIndirectAnnotationAsAnalysis(String tierCategory, COMASearchResultListTableModel tm, int overlapType, int maxDistance, boolean findAll) throws SAXException {
        tableModel = tm;
        COMACorpusInterface corpus = tableModel.getCorpus();
        return addIndirectAnnotationAsAnalysis(tierCategory, corpus, overlapType, maxDistance, findAll, "");
    }

    public SearchResultList addIndirectAnnotationAsAnalysis(String tierCategory, COMACorpusInterface corpus, int overlapType, int maxDistance, boolean findAll) throws SAXException {
        return addIndirectAnnotationAsAnalysis(tierCategory, corpus, overlapType, maxDistance, findAll, "");
    }

    //copied from AddAnnotationAsAnalysis:
    // 06-08-2010: this is the next big thing
    // Timm aka Dart Vader wants it:
    // go through an existing list and add all annotations
    // from a transcription and put it into an additional analysis
    // column: this will enable cross-level filtering
    /*
    *
     */
    public SearchResultList addIndirectAnnotationAsAnalysis(String tierCategory, COMACorpusInterface corpus, int overlapType, int maxDistance, boolean findAll, String annotatedCategory) throws SAXException {
        SearchResultList returnValue = new SearchResultList();
        // go through all search results
        // copy all the information
        // and for each entry
        // look into the transcription
        // and find annotations of the specified category
        // but not directly there, but depending on the ovelap or distance type
        Vector<AnalysisInterface> newAnalyses = new Vector<AnalysisInterface>();
        newAnalyses.addAll(this.getAnalyses());
        FreeAnalysis additionalAnalysis = new FreeAnalysis(tierCategory);
        newAnalyses.add(additionalAnalysis);
        returnValue.setAnalyses(newAnalyses);

        // changed 18-10-2011 - bugfix
        if (tableModel != null) {
            tableModel.addAnalysis(additionalAnalysis);
        }

        String filename = "";
        BasicTranscription bt = null;
        SegmentedTranscription st = null;
        SegmentedTranscriptionSaxReader str = new SegmentedTranscriptionSaxReader();
        for (SearchResultInterface sr : this) {
            String annotationValue = "";
            String newfilename = (String) (sr.getSearchableSegmentLocator().getCorpusComponentLocator());
            //System.out.println("FILENAME: " + newfilename);
            if ((!filename.equals(newfilename))) {
                filename = newfilename;
                if (corpus instanceof COMACorpus) {
                    st = str.readFromFile(filename);
                } else {
                    System.out.println("==============" + filename);
                    st = str.readFromURL(filename);
                }
                try {
                    // make a basic version from the segmented one
                    bt = st.toBasicTranscription();
                } catch (JexmaraldaException ex) {
                    ex.printStackTrace();
                }
            }
            String tierID = sr.getAdditionalData()[0];
            String speakerID = sr.getAdditionalData()[1];
            String startID = sr.getAdditionalData()[2];
            String endID = "";

            if (!(sr instanceof AnnotationSearchResult)) {
                System.out.println("It's not an AnnotationSearchResult.");
                try {
                    Tier tier = bt.getBody().getTierWithID(tierID);
                    // find the exact startpoint
                    SimpleSearchResult ssr = (SimpleSearchResult) sr;
                    int count = 0;
                    int s = ssr.getOriginalMatchStart();
                    while (count + tier.getEventAtStartPoint(startID).getDescription().length() <= s) {
                        count += tier.getEventAtStartPoint(startID).getDescription().length();
                        startID = tier.getEventAtStartPoint(startID).getEnd();
                    }
                    endID = tier.getEventAtStartPoint(startID).getEnd();
                } catch (JexmaraldaException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("It is an AnnotationSearchResult.");

                // have to find the annotation again
                // this is awkward
                AnnotationSearchResult asr = (AnnotationSearchResult) sr;
                String existingAnnotationName = this.getAnalyses().elementAt(0).getName();

                ArrayList<Tier> tiers = new ArrayList<Tier>();
                //Tier tier = null;
                //now we are searching on the basic transcription!
                //and need to find the already added annotation tier?? Not sure
                for (int pos = 0; pos < bt.getBody().getNumberOfTiers(); pos++) {
                    Tier t = bt.getBody().getTierAt(pos);
                    if ((t.getType().equals("a"))
                            && (t.getCategory().equals(existingAnnotationName))
                            && (t.getSpeaker() != null)
                            && (t.getSpeaker().equals(speakerID))) {
                        //tier = t;
                        //break;
                        tiers.add(t);
                    }
                }
                // ATTENTION: Maybe the tier is null for POS annotations etc.
                //if (tier==null){
                if (tiers.isEmpty()) {
                    // WHAT TO DO????
                    // asketh the programmer, 21-06-2011
                    // Now also available as an issue (#36), 22-11-2016
                    // to get here: search for POS in a POS-tagged corpus
                    // then add "annotation as analysis": LEMMA 
                    System.out.println("Here we have issue #36!");
                    // this code addresses issue #36
                    // it is for sure the worst code that any vertebrate 
                    // has ever come up with
                    // Shame one me!
                    for (int pos = 0; pos < st.getBody().getNumberOfTiers(); pos++) {
                        SegmentedTier t = st.getBody().getSegmentedTierAt(pos);
                        if ((t.getSpeaker() != null) && (t.getSpeaker().equals(speakerID))) {
                            Annotation a = t.getAnnotationWithName(tierCategory);
                            for (int i = 0; i < a.size(); i++) {
                                TimedAnnotation ta = (TimedAnnotation) (a.get(i));
                                if (ta.getStart().equals(startID)) {
                                    annotationValue = ta.getDescription();
                                    break;
                                }
                            }
                            sr.setAdditionalData(getAnalyses().size() - 1 + 3, annotationValue);
                            returnValue.add(sr);
                        }
                    }
                    continue;
                }
                for (Tier tier : tiers) {
                    try {
                        // changed 21-06-2011 to account for annotations not in the common timeline
                        if (tier.containsEventAtStartPoint(startID)) {
                            endID = tier.getEventAtStartPoint(startID).getEnd();
                            break;
                        } else {
                            // changed 14-10-2013
                            String commonTimelineMatch = st.getBody().getCommonTimelineMatch(startID);
                            if (tier.containsEventAtStartPoint(commonTimelineMatch)) {
                                endID = tier.getEventAtStartPoint(commonTimelineMatch).getEnd();
                                break;
                            } else {
                                System.out.println("Hanna's problem!!!");
                            }
                        }
                    } catch (JexmaraldaException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            String startID2 = st.getBody().getCommonTimelineMatch(startID);
            String endID2 = st.getBody().getCommonTimelineMatch(endID);
            //System.out.println(tierID + "  " + startID2 + "  " + endID2);

            // now find the right tier
            Tier annotationTier = null;
            for (int pos = 0; pos < bt.getBody().getNumberOfTiers(); pos++) {
                Tier t = bt.getBody().getTierAt(pos);
                if ((t.getType().equals("a"))
                        // right now this only works if there is only one tier in the
                        // transcription with the
                        // tiercategory that is chosen, but that is true for the test case I guess? 
                        // At least for EV and AU
                        && (t.getCategory().equals(tierCategory)) //&& (t.getSpeaker()!=null)
                        //&& (t.getSpeaker().equals(speakerID))
                        ) {
                    annotationTier = t;
                    break;
                }
            }
            if (annotationTier != null) {
                switch (overlapType) {
                    case EXACT_OVERLAP:
                        Event annotation = null;
                        try {
                            annotation = annotationTier.getEventAtStartPoint(startID2);
                        } catch (JexmaraldaException ex) {
                            // this simply means there was no such event
                            //ex.printStackTrace();
                            //System.out.print("*");
                        }
                        if ((annotation != null) && (annotation.getEnd().equals(endID2))) {
                            annotationValue = annotation.getDescription();
                        }
                        break;
                    case INCLUDED_IN:
                        Vector<Event> annotations = annotationTier.getEventsIntersecting(bt.getBody().getCommonTimeline(), startID2, endID2);
                        Timeline tl = bt.getBody().getCommonTimeline();
                        int startIndex = tl.lookupID(startID2);
                        int endIndex = tl.lookupID(endID2);
                        for (Event e : annotations) {
                            int index1 = tl.lookupID(e.getStart());
                            int index2 = tl.lookupID(e.getEnd());
                            if ((index1 <= startIndex) && (index2 >= endIndex)) {
                                annotationValue = e.getDescription();
                                break;
                            }
                        }
                        break;
                    case ANY_OVERLAP:
                        Vector<Event> annotations2 = annotationTier.getEventsIntersecting(bt.getBody().getCommonTimeline(), startID2, endID2);
                        for (Event e : annotations2) {
                            annotationValue += e.getDescription() + " ";
                        }
                        break;
                    case PRECEDING:
                        tl = bt.getBody().getCommonTimeline();
                        //now we want all the Events before the start of the match
                        annotations = annotationTier.getEventsBetween(bt.getBody().getCommonTimeline(), tl.getTimelineItemAt(0).getID(), endID2);
                        //System.out.println(annotations);
                        //startID of the match
                        startIndex = tl.lookupID(startID2);
                        //System.out.println(startIndex);
                        //got trough all the annotatins (starting with the closest) 
                        //and check if the end of them is before the start of the match
                        //System.out.println(annotations.size());
                        for (int i = annotations.size() - 1; i >= 0; i--) {
                            Event e = annotations.get(i);
                            int index1 = tl.lookupID(e.getStart());
                            //System.out.println(index1);
                            int index2 = tl.lookupID(e.getEnd());
                            //System.out.println(index2 + " " + startIndex);
                            //now take that if the endindex of the annotation is smaller or same than the startindex of the match
                            if (index2 <= startIndex && annotatedCategory.equals("")) {
                                annotationValue = e.getDescription() + " " + e.getStart();
                                if (!findAll) {
                                    break;
                                }
                                //now this checks if there is a direct annotation in another tier wanted as output (very special case)
                            } else if (!annotatedCategory.equals("")) {
                                //System.out.println("Arrived in the if loop");
                                Tier annotationTier2 = null;
                                for (int pos = 0; pos < bt.getBody().getNumberOfTiers(); pos++) {
                                    Tier t = bt.getBody().getTierAt(pos);
                                    if ((t.getType().equals("a"))
                                            // right now this only works if there is only one tier in the
                                            // transcription with the
                                            // tiercategory that is chosen, but that is true for the test case I guess? 
                                            // At least for EV and AU
                                            && (t.getCategory().equals(annotatedCategory)) //&& (t.getSpeaker()!=null)
                                            //&& (t.getSpeaker().equals(speakerID))
                                            ) {
                                        annotationTier2 = t;
                                        break;
                                    }
                                }
                                //System.out.println(annotationTier2);
                                //these are the additional direct annotations
                                Event annotation2 = null;
                                if (annotationTier2 != null) {
                                    try {
                                        //System.out.println(e.getStart());
                                        if (annotationTier2.getEventAtStartPoint(e.getStart()) != null) {
                                            annotation2 = annotationTier2.getEventAtStartPoint(e.getStart());
                                            //System.out.println(annotation2.getDescription());
                                            if ((annotation2 != null) && (annotation2.getEnd().equals(e.getEnd()))) {
                                                annotationValue = annotation2.getDescription() + " " + annotation2.getStart();
                                                System.out.println(annotationValue + annotation2.getStart());
                                            }
                                        }
                                        break;
                                    } catch (JexmaraldaException ex) {
                                        // this simply means there was no such event
                                        //ex.printStackTrace();
                                        //System.out.print("*");
                                    }
                                }
                            }
                        }
                        break;

                    case FOLLOWING:
                        tl = bt.getBody().getCommonTimeline();
                        //now we want all the Events after the end of the match
                        annotations = annotationTier.getEventsBetween(bt.getBody().getCommonTimeline(), endID2, tl.getTimelineItemAt(size()).getID());
                        //endID of the match
                        endIndex = tl.lookupID(endID2);
                        //System.out.println(endIndex);
                        //got trough all the annotations (starting with the closest) 
                        //and check if the end of them is before the start of the match
                        //System.out.println(annotations.size());
                        for (Event e : annotations) {
                            int index2 = tl.lookupID(e.getStart());
                            //now take that if the endindex of the annotation is smaller(or same) than the startindex of the match
                            if (index2 >= endIndex && annotatedCategory.equals("")) {
                                annotationValue = e.getDescription() + " " + e.getStart();
                                if (!findAll) {
                                    break;
                                }
                             //now this checks if there is a direct annotation in another tier wanted as output (very special case)
                            } else if (!annotatedCategory.equals("")) {
                                //System.out.println("Arrived in the if loop");
                                Tier annotationTier2 = null;
                                for (int pos = 0; pos < bt.getBody().getNumberOfTiers(); pos++) {
                                    Tier t = bt.getBody().getTierAt(pos);
                                    if ((t.getType().equals("a"))
                                            // right now this only works if there is only one tier in the
                                            // transcription with the
                                            // tiercategory that is chosen, but that is true for the test case I guess? 
                                            // At least for EV and AU
                                            && (t.getCategory().equals(annotatedCategory)) //&& (t.getSpeaker()!=null)
                                            //&& (t.getSpeaker().equals(speakerID))
                                            ) {
                                        annotationTier2 = t;
                                        break;
                                    }
                                }
                                //System.out.println(annotationTier2);
                                //these are the additional direct annotations
                                Event annotation2 = null;
                                if (annotationTier2 != null) {
                                    //System.out.println(e.getStart());
                                    try {
                                        if (annotationTier2.getEventAtStartPoint(e.getStart()) != null) {
                                            annotation2 = annotationTier2.getEventAtStartPoint(e.getStart());
                                            //System.out.println(annotation2.getDescription());
                                            if ((annotation2 != null) && (annotation2.getEnd().equals(e.getEnd()))) {
                                                annotationValue = annotation2.getDescription() + " " + annotation2.getStart();
                                                System.out.println(annotationValue);
                                            }
                                        }
                                    } catch (JexmaraldaException ex) {
                                        Logger.getLogger(SearchResultList.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    break;
                                }
                            }
                            break;

                        }
                }
                // now add it to the new search result
                // this is wrong (22-11-2012)
                //sr.setAdditionalData(sr.getAdditionalData().length, annotationValue);
                // is this right???
                sr.setAdditionalData(getAnalyses().size() - 1 + 3, annotationValue);
                returnValue.add(sr);
            }
        }
        return returnValue;
    }

    public SearchResultList addTimesAsAnalysis(COMASearchResultListTableModel wrappedModel) {
        SearchResultList returnValue = new SearchResultList();
        // go through all search results
        // copy all the information
        // and for each entry
        // look into the transcription
        // and find annotations of the specified category
        Vector<AnalysisInterface> newAnalyses = new Vector<AnalysisInterface>();
        newAnalyses.addAll(this.getAnalyses());
        FreeAnalysis additionalAnalysis = new FreeAnalysis("time");
        newAnalyses.add(additionalAnalysis);
        returnValue.setAnalyses(newAnalyses);

        // changed 18-10-2011 - bugfix
        if (tableModel != null) {
            tableModel.addAnalysis(additionalAnalysis);
        }

        String filename = "";
        BasicTranscription bt = null;
        SegmentedTranscription st = null;
        SegmentedTranscriptionSaxReader str = new SegmentedTranscriptionSaxReader();
        for (SearchResultInterface sr : this) {
            sr.getSearchableSegmentLocator().getSearchableSegmentLocator();
        }

        return returnValue;
    }

}
