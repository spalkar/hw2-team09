/*
 *  Copyright 2012 Carnegie Mellon University
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package edu.cmu.lti.oaqa.openqa.test.team09;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.framework.data.KeytermList;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenBase;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenPattern;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenPatternTools;

/**
 * 
 */
public class KeytermExampleProcessor extends KeytermExampleProcessorBase
{
	/**
	 * Internal method accessing Hoop debugging
	 */
	private void debug(String aMessage) 
	{
		GenBase.debug("KeytermExampleProcessor", aMessage);
	}		
	/**
	 * 
	 */
	protected void processPattern(JCas aJCas,GenPattern aPattern) 
	{
		debug ("processPattern ()");
		
		List<Keyterm> keytermResults = new ArrayList<Keyterm>();
		
		Keyterm newTerm=null;
		
		// REPLACE WITH YOUR CODE ...
		
		if (aPattern.questionType==GenPattern.QUESTIONWHAT)
		{
			//debug ("Pattern question type is: what");
			
			// Very basic question reformulation by adding keywords, 
			// replace or take out if you like
			
			newTerm=new Keyterm ("What");
			keytermResults.add(newTerm);
			
			newTerm=new Keyterm ("is");
			keytermResults.add(newTerm);
		}
		
		if (aPattern.questionType==GenPattern.QUESTIONHOW)
		{
			//debug ("Pattern question type is: how");

			// Very basic question reformulation by adding keywords, 
			// replace or take out if you like
			
			newTerm=new Keyterm ("How");
			keytermResults.add(newTerm);
			
			newTerm=new Keyterm ("does");
			keytermResults.add(newTerm);			
		}
		
		if (aPattern.questionType==GenPattern.QUESTIONGENERIC)
		{
			//debug ("Pattern question type is: generic");
			
			// Very basic question reformulation by adding keywords, 
			// replace or take out if you like
			
			/*
			newTerm=new Keyterm ("What");
			keytermResults.add(newTerm);
			
			newTerm=new Keyterm ("is");
			keytermResults.add(newTerm);
			*/
		}
		
		//debug ("Pattern score: " + aPattern.score);
		//debug ("Pattern sentence: " + aPattern.sentence);

		ArrayList <String> terms=aPattern.keyterms;
		
		// As you iterate through the terms please be aware that
		// order is important!
		
		for (int i=0;i<terms.size();i++)
		{
			String aTerm=terms.get(i);
			
			newTerm=new Keyterm (aTerm);
			keytermResults.add(newTerm);
		}

		try 
		{
			KeytermList.storeKeyterms(aJCas, keytermResults);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}		

}
