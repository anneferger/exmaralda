/*
 * TransferMetaData.java
 *
 * Created on 18. April 2007, 16:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.exmaralda.common.corpusbuild;

import org.exmaralda.partitureditor.jexmaralda.UDInformationHashtable;
import org.exmaralda.partitureditor.jexmaralda.Speaker;
import org.exmaralda.partitureditor.jexmaralda.MetaInformation;
import org.exmaralda.partitureditor.jexmaralda.JexmaraldaException;
import org.exmaralda.partitureditor.jexmaralda.BasicTranscription;
import java.io.IOException;
import org.exmaralda.partitureditor.jexmaralda.*;
import java.util.*;
import org.jdom.*;
import org.xml.sax.SAXException;
import org.jdom.xpath.*;

/**
 *
 * @author thomas
 */
public class TransferMetaData extends AbstractBasicTranscriptionProcessor {
    
    XPath uniqueSpeakerDistinction;

    /** Creates a new instance of TransferMetaData */
    public TransferMetaData(String corpusName) {
        super(corpusName);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String corpusFilename = "E:\\Anne\\exmaralda-demo-corpus\\EXMARaLDA_DemoKorpus.coma";
            String output = "C:\\Users\\fsnv625\\Desktop\\output.txt";
            //String corpusFilename = args[0];
            //String output = args[1];
            TransferMetaData tmd = new TransferMetaData(corpusFilename);
            tmd.doIt();
            tmd.output(output);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JDOMException ex) {
            ex.printStackTrace();
        } catch (JexmaraldaException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
    }

    public void transfer(List meta, UDInformationHashtable ud, String suffix){
        for (Object o : meta){
            Element key = (Element)o;
            String keyname = key.getAttributeValue("Name");
            String keyvalue = key.getText();
            //ud.setAttribute(suffix + ":" + keyname, keyvalue);
            ud.setAttribute(keyname, keyvalue);
            outappend(keyname + "\t" + keyvalue + "\n");
        }
    }
    
    public void processTranscription(BasicTranscription bt) {
        try {
            String usd = ((Attribute)(getSingle("/Corpus/@uniqueSpeakerDistinction"))).getValue();
            uniqueSpeakerDistinction = XPath.newInstance(usd);
        } catch (JDOMException ex) {
            ex.printStackTrace();
        }

        MetaInformation meta = bt.getHead().getMetaInformation();
        UDInformationHashtable udMeta = meta.getUDMetaInformation();
        meta.getUDMetaInformation().clear();

        String tID = ((Attribute)(getSingle("../@Id", getCurrentCorpusNode()))).getValue();
        String cID = ((Attribute)(getSingle("../../@Id", getCurrentCorpusNode()))).getValue();
        udMeta.setAttribute("COMA-Transcription-ID", tID);
        udMeta.setAttribute("COMA-Communication-ID", cID);
        
//        List transcriptionMeta = get("../Description/Key", getCurrentCorpusNode());
//        out.append(transcriptionMeta.size() + " transcription attributes\n");
//        transfer(transcriptionMeta, udMeta, "t");
        
        List communicationMeta = get("../../Description/Key", getCurrentCorpusNode());
        List communicationLocationMeta = get("../../Location", getCurrentCorpusNode());
        communicationMeta.addAll(communicationLocationMeta);
        out.append(communicationMeta.size() + " communication attributes\n");
        //transfer(communicationMeta, udMeta, "c");
        transfer(communicationMeta, udMeta, "");
        try {            
            Document tDocument = FileIO.readDocumentFromLocalFile(getCurrentFilename());
            List speakers = uniqueSpeakerDistinction.selectNodes(tDocument);
            for (Object o : speakers){
                Element s = (Element)o;
                String speakerID = ((Attribute)(getSingle("ancestor::speaker/@id",s))).getValue();
                try {
                    Speaker speaker = bt.getHead().getSpeakertable().getSpeakerWithID(speakerID);
                    UDInformationHashtable udinfo = speaker.getUDSpeakerInformation();
                    udinfo.clear();
                    String value = s.getText();
                    System.out.println("***" + value);
                    Element speakerInComa = (Element)(getSingle("//Speaker[Sigle='" + value + "']"));
                    String comaID = speakerInComa.getAttributeValue("Id");
                    udinfo.setAttribute("COMA-Speaker-ID", comaID);
                    Element pseudo = (Element)(getSingle("Pseudo",speakerInComa));
                    String pseudoName = pseudo.getText();
                    udinfo.setAttribute("Pseudo", pseudoName);          
                    System.out.println(pseudoName);
                    List speakerMeta = get("Description/Key",speakerInComa);
                    //transfer(speakerMeta,udinfo,"s");
                    transfer(speakerMeta,udinfo,"");
                    List firstLanguages = get("Language[@Type='L1']/LanguageCode",speakerInComa);
                    speaker.getL1().clear();
                    for (Object l : firstLanguages){
                        Element l1 = (Element)l;
                        String lang = l1.getText();
                        System.out.println("Language1: " + lang);
                        speaker.getL1().addLanguage(lang);
                    }
                    List secondLanguages = get("Language[@Type='L2']/LanguageCode",speakerInComa);
                    speaker.getL2().clear();
                    for (Object l : secondLanguages){
                        Element l2 = (Element)l;
                        String lang = l2.getText();
                        System.out.println("Language2: " + lang);
                        speaker.getL2().addLanguage(lang);
                    }
                   Element sexOfSpeaker = (Element)(getSingle("Sex",speakerInComa));
                   speaker.getSex();
                    char sexChar;
                    if(sexOfSpeaker.getText().equals("female")){
                        sexChar = 'f';
                    }
                    else if (sexOfSpeaker.getText().equals("male")){
                        sexChar = 'm';
                    }
                    else {
                        sexChar = 'u';                       
                    }                  
                    speaker.setSex(sexChar); 
                    System.out.println(sexChar);
                    
                } catch (JexmaraldaException ex) {
                    ex.printStackTrace();
                }
                
            }
        } catch (JDOMException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            bt.writeXMLToFile(this.getCurrentFilename(), "none");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
