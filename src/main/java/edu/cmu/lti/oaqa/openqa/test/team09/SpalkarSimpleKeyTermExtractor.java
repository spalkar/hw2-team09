package edu.cmu.lti.oaqa.openqa.test.team09;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermExtractor;
import edu.cmu.lti.oaqa.framework.data.Keyterm;


public class SpalkarSimpleKeyTermExtractor extends AbstractKeytermExtractor {

  private String [] patRules = {"[A-Z][a-z0-9]*", "[A-Z0-9-]*", "[A-Za-z0-9-]*[0-9]", "[0-9][A-Za-z-]*"};
  
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
  }
  
  @Override
  protected List<Keyterm> getKeyterms(String arg0) {
    String docText = arg0;
    String [] words = docText.split(" ");
    List<Keyterm> keyterms = new ArrayList<Keyterm>();
    for (int i = 0; i < words.length; i++){
      String wd = words[i];
      if (mayBeGene(wd, i)){
        //store keyterm
        keyterms.add(new Keyterm(wd));
      }
    }
    return keyterms; 
  }

  public Boolean mayBeGene(String w, int idx){
    for(int i=0; i<patRules.length; i++){
      if (idx == 0 && i == 0){
        continue;
      }
      if (Pattern.matches(patRules[i], w)){
        //System.out.println(w);
        return true;
      }
    }
    return false;
  }

}
  
  
