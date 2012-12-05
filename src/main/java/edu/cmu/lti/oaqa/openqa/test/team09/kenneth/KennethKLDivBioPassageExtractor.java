package edu.cmu.lti.oaqa.openqa.test.team09.kenneth;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
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

public class KennethKLDivBioPassageExtractor extends SimplePassageExtractor {

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
        List<PassageCandidate> passageSpans = finder.extractPassages(keytermStrings
                .toArray(new String[0]));
        for (PassageCandidate passageSpan : passageSpans)
          result.add(passageSpan);
      } catch (SolrServerException e) {
        e.printStackTrace();
      }
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
