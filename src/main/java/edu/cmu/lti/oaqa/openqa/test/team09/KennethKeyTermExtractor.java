package edu.cmu.lti.oaqa.openqa.test.team09;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermExtractor;
import edu.cmu.lti.oaqa.framework.data.Keyterm;


public class KennethKeyTermExtractor extends AbstractKeytermExtractor {

  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
  }
  
  @Override
  protected List<Keyterm> getKeyterms(String arg0) {
	
	  List<Keyterm> result = new ArrayList<Keyterm>();
	  
	  String nowStr = arg0;
	  String[] wordArray = nowStr.split("[\\s]+");
	  for(int i=0;i<wordArray.length;i++){
		  String nowWord = wordArray[i];
		  if(isKeyWord(nowWord)){
			  result.add(new Keyterm(nowWord));
		  }
		  
	  }
	  
	  return result;

  }

  private boolean isKeyWord(String nowWord){
	  if(containsAlpha(nowWord)&&!containsVowl(nowWord)){
		  return true;
	  }else if(matchsPattern(nowWord)){
		  return true;
	  }
	  return false;
  }
  
  private boolean matchsPattern(String nowWord){
	  String[] nowPatterns = getPatterns();
	  for(int i=0;i<nowPatterns.length;i++){
		  String nowPattern = nowPatterns[i];
		  Matcher nowMatcher = Pattern.compile(nowPattern).matcher(nowWord);
		  if(nowMatcher.matches()){
			  return true;
		  }
	  }
	  return false;
  }
  
  private String[] getPatterns(){
	  String[] patterns = {
			  "[A-Z]+[-]*[0-9]*", //MMS2, APC
			  "[a-zA-Z]+[-]+[a-zA-Z0-9]+", //Nurr-77, TGF-beta1
			  "[a-zA-Z]+[0-9]+" //p53, Sec61
			  };
	  return patterns;
  }
  
  
  private boolean containsAlpha(String nowWord){
	  Matcher nowMatcher = Pattern.compile("[a-zA-Z]+").matcher(nowWord);
	  return nowMatcher.find();
  }
  
  private boolean containsVowl(String nowWord){
	  nowWord = nowWord.toLowerCase();
	  String[] vowls = {"a", "e", "i", "o", "y", "u"};
	  for(int i=0;i<vowls.length;i++){
		  if(nowWord.contains(vowls[i])){
			  return true;
		  }
	  }
	  return false;
  }

}
  
  
