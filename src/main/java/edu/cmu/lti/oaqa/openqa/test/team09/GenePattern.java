

/* First created by JCasGen Fri Nov 23 20:01:32 EST 2012 */
package edu.cmu.lti.oaqa.openqa.test.team09;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringList;
import org.apache.uima.jcas.tcas.Annotation;


import org.apache.uima.jcas.cas.StringArray;


/** 
 * Updated by JCasGen Fri Nov 23 20:07:02 EST 2012
 * XML source: C:/Martin/Echidne/Hydra (Science)/Academics/Coursework/SEIT/Homework/hw2-team09/src/main/resources/HW1GenAnnotator.xml
 * @generated */
public class GenePattern extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GenePattern.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected GenePattern() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GenePattern(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GenePattern(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GenePattern(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: sentence

  /** getter for sentence - gets 
   * @generated */
  public String getSentence() {
    if (GenePattern_Type.featOkTst && ((GenePattern_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GenePattern_Type)jcasType).casFeatCode_sentence);}
    
  /** setter for sentence - sets  
   * @generated */
  public void setSentence(String v) {
    if (GenePattern_Type.featOkTst && ((GenePattern_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    jcasType.ll_cas.ll_setStringValue(addr, ((GenePattern_Type)jcasType).casFeatCode_sentence, v);}    
   
    
  //*--------------*
  //* Feature: score

  /** getter for score - gets 
   * @generated */
  public float getScore() {
    if (GenePattern_Type.featOkTst && ((GenePattern_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((GenePattern_Type)jcasType).casFeatCode_score);}
    
  /** setter for score - sets  
   * @generated */
  public void setScore(float v) {
    if (GenePattern_Type.featOkTst && ((GenePattern_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    jcasType.ll_cas.ll_setFloatValue(addr, ((GenePattern_Type)jcasType).casFeatCode_score, v);}    
   
    
  //*--------------*
  //* Feature: keyterms

  /** getter for keyterms - gets 
   * @generated */
  public StringArray getKeyterms() {
    if (GenePattern_Type.featOkTst && ((GenePattern_Type)jcasType).casFeat_keyterms == null)
      jcasType.jcas.throwFeatMissing("keyterms", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GenePattern_Type)jcasType).casFeatCode_keyterms)));}
    
  /** setter for keyterms - sets  
   * @generated */
  public void setKeyterms(StringArray v) {
    if (GenePattern_Type.featOkTst && ((GenePattern_Type)jcasType).casFeat_keyterms == null)
      jcasType.jcas.throwFeatMissing("keyterms", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    jcasType.ll_cas.ll_setRefValue(addr, ((GenePattern_Type)jcasType).casFeatCode_keyterms, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for keyterms - gets an indexed value - 
   * @generated */
  public String getKeyterms(int i) {
    if (GenePattern_Type.featOkTst && ((GenePattern_Type)jcasType).casFeat_keyterms == null)
      jcasType.jcas.throwFeatMissing("keyterms", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((GenePattern_Type)jcasType).casFeatCode_keyterms), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((GenePattern_Type)jcasType).casFeatCode_keyterms), i);}

  /** indexed setter for keyterms - sets an indexed value - 
   * @generated */
  public void setKeyterms(int i, String v) { 
    if (GenePattern_Type.featOkTst && ((GenePattern_Type)jcasType).casFeat_keyterms == null)
      jcasType.jcas.throwFeatMissing("keyterms", "edu.cmu.lti.oaqa.openqa.test.team09.GenePattern");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((GenePattern_Type)jcasType).casFeatCode_keyterms), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((GenePattern_Type)jcasType).casFeatCode_keyterms), i, v);}
  }

    