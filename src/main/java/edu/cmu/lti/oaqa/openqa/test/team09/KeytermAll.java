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
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermExtractor;
import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenBase;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenPatternTools;

/**
 * 
 */
public class KeytermAll extends AbstractKeytermExtractor 
{		
	/**
	 * Internal method accessing Hoop debugging
	 */
	private void debug(String aMessage) 
	{
		GenBase.debug("KeytermStopword", aMessage);
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
	 * @param question
	 * @return
	 */
	@Override
	protected List<Keyterm> getKeyterms(String question) 
	{						
		debug ("getKeyterms ("+question+")");
		
		List<Keyterm> keyterms = new ArrayList<Keyterm>();
		
		// Let's add a basic sanity check by changing the whole sentence
		// to lower case

		String cleaned = question.toLowerCase();

		String[] split = cleaned.split("\\s+");

		ArrayList<String> cleanedTokens = cleanTokens(split);
		
		ArrayList<String> termList=new ArrayList<String> ();

		int index = 0;

		for (int i = 0; i < cleanedTokens.size(); i++) 
		{
			String aToken = cleanedTokens.get(i);
													
			termList.add(aToken);				

			index += aToken.length();
			index++; // don't forget the white space
		}		
		
		Keyterm aPattern=GenPatternTools.encodeKeyterm(question,(float) 1.0,termList);
		
		keyterms.add(aPattern);
		
	    return keyterms;
	}
	/**
	 * 
	 */
	private String cleanRawToken(String aToken) 
	{
		String clean1 = aToken;

		if (aToken.endsWith(",") == true) 
		{
			clean1 = aToken.substring(0, aToken.length() - 1);
		}

		String clean2 = clean1;

		if (clean1.endsWith(".") == true) 
		{
			clean2 = clean1.substring(0, aToken.length() - 1);
		}

		String cleaned = clean2;

		return (cleaned);
	}
	/**
	 * 
	 */
	private ArrayList<String> cleanTokens(String[] aList) 
	{
		ArrayList<String> newList = new ArrayList<String>();

		for (int i = 0; i < aList.length; i++) 
		{
			newList.add(cleanRawToken(aList[i]));
		}

		return (newList);
	}	
}
