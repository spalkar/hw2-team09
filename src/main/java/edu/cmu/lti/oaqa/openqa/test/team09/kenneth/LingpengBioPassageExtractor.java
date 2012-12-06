package edu.cmu.lti.oaqa.openqa.test.team09.kenneth;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.uima.UimaContext;
import org.apache.uima.resource.ResourceInitializationException;
import org.jsoup.Jsoup;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;
import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.framework.data.PassageCandidate;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;
import edu.cmu.lti.oaqa.openqa.hello.passage.KeytermWindowScorerSum;
import edu.cmu.lti.oaqa.openqa.hello.passage.PassageCandidateFinder;
import edu.cmu.lti.oaqa.openqa.hello.passage.SimplePassageExtractor;

public class LingpengBioPassageExtractor extends SimplePassageExtractor {

  @Override
  protected List<PassageCandidate> extractPassages(String question, List<Keyterm> keyterms,
          List<RetrievalResult> documents) {
    List<PassageCandidate> result = new ArrayList<PassageCandidate>();
    for (RetrievalResult document : documents) {
      System.out.println("RetrievalResult: " + document.toString());
      String id = document.getDocID();
      try {
        String htmlText = wrapper.getDocText(id);

        // cleaning HTML text
        String text = Jsoup.parse(htmlText).text().replaceAll("([\177-\377\0-\32]*)", "")/* .trim() */;
        // for now, making sure the text isn't too long
        text = text.substring(0, Math.min(5000, text.length()));
        System.out.println(text);

        System.out.println("Build finder...");
        KennethKLDivPassageCandidateFinder finder = new KennethKLDivPassageCandidateFinder(id, text,
                new KeytermWindowScorerKennethKLDiv());
        List<String> keytermStrings = Lists.transform(keyterms, new Function<Keyterm, String>() {
          public String apply(Keyterm keyterm) {
            return keyterm.getText();
          }
        });
        String[] nowWordArray = keyTermToWordArray(keytermStrings.toArray(new String[0]));
        //System.out.println("doing passage spans");
        List<PassageCandidate> passageSpans = finder.extractPassages(keytermStrings.toArray(new String[0]), nowWordArray);
        for (PassageCandidate passageSpan : passageSpans)
          result.add(passageSpan);
      } catch (SolrServerException e) {
        e.printStackTrace();
      }
    }
    return result;
  }
  
  private String[] keyTermToWordArray(String[] keyTermList){
    
    ArrayList<String> result = new ArrayList<String>();
    for(int i=0;i<keyTermList.length;i++){
      String keyTerm = keyTermList[i];
      System.out.println("Expand key term: "+keyTerm);
      String[] nowKeyTermSpan = genBioBackGround(keyTerm);
      System.out.println("Get Res");
      for(int j=0;j<nowKeyTermSpan.length;j++){
        if(nowKeyTermSpan[j].equals("")) continue;
        //System.out.println(nowKeyTermSpan[j]);
        result.add(nowKeyTermSpan[j]);
      }
      
    }
    return (String[]) result.toArray(new String[result.size()]);
    //return result.toArray();
  }
  
  public String[] genBioBackGround(String term){
    term = term.replaceAll("\\W+", "").toLowerCase();
    List<RetrievalResult> list = retrieveDocuments(term);
    ArrayList<String> bgList = new ArrayList<String>();
    for(RetrievalResult rs : list){
      try {
        String htmlText = wrapper.getDocText(rs.getDocID());
        String text = Jsoup.parse(htmlText).text().replaceAll("([\177-\377\0-\32]*)", "")/* .trim() */;
        // for now, making sure the text isn't too long
        text = text.substring(0, Math.min(5000, text.length())).toLowerCase();
        text = text.replaceAll("\\W+", "|||");
        String[] words = text.split("|||");
        for(int i = 0; i < words.length; i++){
          if(words[i].equals(term)){
            if(i-10 >= 0){
              for(int j = i-10; j < i; j++){
                bgList.add(words[j]);
              }
            }else{
              for(int j = 0; j < i; j++){
                bgList.add(words[j]);
              }
            }
            if(i+10 < words.length){
              for(int j = i; j < i+10; j++){
                bgList.add(words[j]);
              }
            }else{
              for(int j = i; j < words.length; j++){
                bgList.add(words[j]);
              }
            }
            break;
          }
        }
        if(bgList.size()>1000) break;
        
        //System.out.println(text);
      } catch (SolrServerException e) {
        e.printStackTrace();
      }   
    }
    return (String[]) bgList.toArray(new String[bgList.size()]);
  }
  
  public List<RetrievalResult> retrieveDocuments(String query) {
    List<RetrievalResult> result = new ArrayList<RetrievalResult>();
    try {
      SolrDocumentList docs = wrapper.runQuery(query, 100);

      for (SolrDocument doc : docs) {

        RetrievalResult r = new RetrievalResult((String) doc.getFieldValue("id"),
                (Float) doc.getFieldValue("score"), query);
        result.add(r);
        System.out.println(doc.getFieldValue("id"));
      }
    } catch (Exception e) {
      System.err.println("Error retrieving documents from Solr: " + e);
    }
    return result;
  }
  
  /*public static void main(String[] args){
	  
	  String question = "What is the role of PrnP in mad cow disease?";
	  
	  List<Keyterm> keyterms = new ArrayList<Keyterm>();
	  keyterms.add(new Keyterm("PrnP"));
	  keyterms.add(new Keyterm("mad cow disease"));
	  
      List<RetrievalResult> documents = new ArrayList<RetrievalResult>();
      String text = "PrnP is actually a protein. When a bunch of it folds improperly, it can form aggregates, generally on neural cells, which leads to mad cow. The normal protein won't misfold unless something causes it too. Normally, this is a misfolded protein somehow introduced into the system. That's the mystery about diseases like this (called prion diseases)- the causative agent is a protein. There are some theories about how one misfolded protein causes others to misfold also, but there's no sound data proving any of these theories.";
      documents.add(new RetrievalResult(text)); 
  }*/

}
