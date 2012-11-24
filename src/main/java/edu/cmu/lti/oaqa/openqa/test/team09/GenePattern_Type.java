
/* First created by JCasGen Fri Nov 23 20:01:32 EST 2012 */
package edu.cmu.lti.oaqa.openqa.test.team09;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Fri Nov 23 20:07:02 EST 2012
 * @generated */
public class GenePattern_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GenePattern_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GenePattern_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GenePattern(addr, GenePattern_Type.this);
  			   GenePattern_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GenePattern(addr, GenePattern_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GenePattern.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
 
  /** @generated */
  final Feature casFeat_sentence;
  /** @generated */
  final int     casFeatCode_sentence;
  /** @generated */ 
  public String getSentence(int addr) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sentence);
  }
  /** @generated */    
  public void setSentence(int addr, String v) {
        if (featOkTst && casFeat_sentence == null)
      jcas.throwFeatMissing("sentence", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    ll_cas.ll_setStringValue(addr, casFeatCode_sentence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_score;
  /** @generated */
  final int     casFeatCode_score;
  /** @generated */ 
  public float getScore(int addr) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_score);
  }
  /** @generated */    
  public void setScore(int addr, float v) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    ll_cas.ll_setFloatValue(addr, casFeatCode_score, v);}
    
  
 
  /** @generated */
  final Feature casFeat_keyterms;
  /** @generated */
  final int     casFeatCode_keyterms;
  /** @generated */ 
  public int getKeyterms(int addr) {
        if (featOkTst && casFeat_keyterms == null)
      jcas.throwFeatMissing("keyterms", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    return ll_cas.ll_getRefValue(addr, casFeatCode_keyterms);
  }
  /** @generated */    
  public void setKeyterms(int addr, int v) {
        if (featOkTst && casFeat_keyterms == null)
      jcas.throwFeatMissing("keyterms", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    ll_cas.ll_setRefValue(addr, casFeatCode_keyterms, v);}
    
   /** @generated */
  public String getKeyterms(int addr, int i) {
        if (featOkTst && casFeat_keyterms == null)
      jcas.throwFeatMissing("keyterms", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_keyterms), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_keyterms), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_keyterms), i);
  }
   
  /** @generated */ 
  public void setKeyterms(int addr, int i, String v) {
        if (featOkTst && casFeat_keyterms == null)
      jcas.throwFeatMissing("keyterms", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_keyterms), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_keyterms), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_keyterms), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public GenePattern_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_sentence = jcas.getRequiredFeatureDE(casType, "sentence", "uima.cas.String", featOkTst);
    casFeatCode_sentence  = (null == casFeat_sentence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentence).getCode();

 
    casFeat_score = jcas.getRequiredFeatureDE(casType, "score", "uima.cas.Float", featOkTst);
    casFeatCode_score  = (null == casFeat_score) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_score).getCode();

 
    casFeat_keyterms = jcas.getRequiredFeatureDE(casType, "keyterms", "uima.cas.StringArray", featOkTst);
    casFeatCode_keyterms  = (null == casFeat_keyterms) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_keyterms).getCode();

  }
}



    