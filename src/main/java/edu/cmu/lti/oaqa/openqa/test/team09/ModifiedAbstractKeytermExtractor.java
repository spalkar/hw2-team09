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

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.oaqa.ecd.log.AbstractLoggedComponent;
import edu.cmu.lti.oaqa.framework.BaseJCasHelper;
import edu.cmu.lti.oaqa.framework.QALogEntry;
import edu.cmu.lti.oaqa.framework.types.InputElement;

/**
 * A modified version of the abstract keyterm extractor we started with. Unlike the
 * original, which tokenized a string and stored all the found entries in a list,
 * we also tokenize but instead store patterns of gene named entities.
 */
public abstract class ModifiedAbstractKeytermExtractor extends AbstractLoggedComponent 
{
	protected abstract List<GenePattern> getKeyterms(String question);

	/**
	 *
	 */
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException 
	{
		super.process(jcas);
    
		try 
		{
			//prepare input
			InputElement input = (InputElement) BaseJCasHelper.getAnnotation (jcas, InputElement.type);
			String question = input.getQuestion();
			
			// do task
			List<GenePattern> keyterms = getKeyterms(question);
			
			log(keyterms.toString());
			
			for (int i=0;i<keyterms.size();i++)
			{
				GenePattern pat=keyterms.get(i);
				
				pat.addToIndexes(jcas);
			}
		} 
		catch (Exception e) 
		{
			throw new AnalysisEngineProcessException(e);
		}
	}
	/** 
	 * @param message
	 */
	protected final void log(String message) 
	{
		super.log(QALogEntry.KEYTERM, message);
	}
}
