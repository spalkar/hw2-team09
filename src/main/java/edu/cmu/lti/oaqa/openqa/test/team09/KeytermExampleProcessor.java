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
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.framework.data.KeytermList;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenBase;

/**
 * 
 */
public class KeytermExampleProcessor extends JCasAnnotator_ImplBase 
{		
	/**
	 * Internal method accessing Hoop debugging
	 */
	private void debug(String aMessage) 
	{
		GenBase.debug("KeytermExampleProcessor", aMessage);
	}	
	/** 
	 * @param aContext
	 * @throws ResourceInitializationException
	 */
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException 
	{
		debug ("initialize ()");		
		super.initialize(aContext);				
	}		
	/**
	 * 
	 */
	public void process(JCas aJCas) throws AnalysisEngineProcessException 
	{
		debug("process ()");
				
		try
		{	
			List<Keyterm> oldStyleKeyterms = new ArrayList<Keyterm>();
			
			FSIndex patternIndex = aJCas.getAnnotationIndex(GenePattern.type);
			Iterator patternIter = patternIndex.iterator();

			while (patternIter.hasNext()) 
			{
				GenePattern aPattern = (GenePattern) patternIter.next();

				debug("Processing pattern for sentence: " +  aPattern.getSentence());
		
				// REPLACE WITH YOUR CODE ...
				
				debug ("Pattern score: " + aPattern.getScore());
				
				StringArray keyterms=aPattern.getKeyterms();
				
				// As you iterate through the terms please be aware that
				// order is important!
				
				for (int i=0;i<keyterms.size();i++)
				{
					String aTerm=keyterms.get(i);
					
					Keyterm newTerm=new Keyterm (aTerm);
					oldStyleKeyterms.add(newTerm);
				}
			}
			
			KeytermList.storeKeyterms(aJCas, oldStyleKeyterms);
		} 
		catch (Exception e) 
		{
			throw new AnalysisEngineProcessException(e);
		}						      	
	}
}
