package edu.cmu.lti.oaqa.openqa.test.team09;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermExtractor;
import edu.cmu.lti.oaqa.framework.data.Keyterm;

public class SpalkarPOSKeyTermExtractor extends AbstractKeytermExtractor {
  
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
  }
  
  @Override
  protected List<Keyterm> getKeyterms(String arg0) {
    List<Keyterm> keyterms = new ArrayList<Keyterm>();
    try {
      PosTagNamedEntityRecognizer posTgr = new PosTagNamedEntityRecognizer();
      String docText = arg0;
      Map<Integer, Integer> postags = posTgr.getGeneSpans(docText);
      Iterator<Integer> nnit = postags.keySet().iterator();
      while (nnit.hasNext()){
        Integer key = (Integer) nnit.next();
        int beg = key.intValue();
        int end = postags.get(key);
        String wd = docText.substring(beg, end);
        keyterms.add(new Keyterm(wd));
      }
    } catch (ResourceInitializationException e) {
      // TODO Auto-generated catch block
    }
    return keyterms;
  }
}