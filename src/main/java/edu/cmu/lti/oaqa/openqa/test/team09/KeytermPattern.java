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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermExtractor;
import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenBase;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenPatternTools;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenResourceLoader;

/**
 * 
 */
public class KeytermPattern extends AbstractKeytermExtractor 
{
	public class GenTokenSequence implements Serializable 
	{
		private static final long serialVersionUID = 7870075841370519160L;

		public ArrayList<String> tokens = null;

		public GenTokenSequence() 
		{
			tokens = new ArrayList<String>();
		}
	}	
	
	private static final String stubPatternMain ="[A-Z]+-*[A-Z]*[0-9]*"; //alpha-numeric uppercase
	private static final String stubPatternSecondary ="[a-zA-Z]+-[0-9]*"; //alpha-numeric uppercase
	
	private String stopwordFile = "data/stoplist.txt";
	private String patternFile = "data/patterns-raw.txt";
	private String stubFile = "data/stubs.txt";

	private HashMap<String, Integer> stopList = null;
	private ArrayList<GenTokenSequence> matchTokens = null;
	private ArrayList<String> stubs = null;
		
	/**
	 * Internal method accessing Hoop debugging
	 */
	private void debug(String aMessage) 
	{
		GenBase.debug("KeytermPattern", aMessage);
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
		
		GenResourceLoader loader = new GenResourceLoader();

		int i = 0;

		String text = loader.getTextResource2(stopwordFile);

		if (text == null) {
			debug("Input error, unable to read stopword file: " + stopwordFile);
			return;
		}

		stopList = new HashMap<String, Integer>();

		String lines[] = text.split("\\n");

		for (i = 0; i < lines.length; i++) 
		{
			String aWord = lines[i];
			stopList.put(aWord.toLowerCase().trim(), i);
		}

		debug("Loaded " + stopList.size() + " stopwords");

		// >----------------------------------------------------------

		text = loader.getTextResource2(stubFile);

		if (text == null) {
			debug("Input error, unable to read stub file: " + stubFile);
			return;
		}

		stubs = new ArrayList<String>();

		lines = text.split("\\n");

		for (i = 0; i < lines.length; i++) {
			String aWord = lines[i];
			stubs.add(aWord.toLowerCase());
		}

		debug("Loaded " + stubs.size() + " stubs");

		// >----------------------------------------------------------

		text = loader.getTextResource2(patternFile);
		
		//debug (text);

		if (text == null) {
			debug("Input error, unable to read pattern file: " + patternFile);
			return;
		}

		matchTokens = new ArrayList<GenTokenSequence>();

		lines = text.split("\\n");

		for (i = 0; i < lines.length; i++) 
		{
			String aSeq = lines[i];

			GenTokenSequence newTokenSequence = new GenTokenSequence();

			String terms[] = aSeq.split("\\s+");

			for (int j = 0; j < terms.length; j++) 
			{
				newTokenSequence.tokens.add(terms[j]);
			}

			matchTokens.add(newTokenSequence);
		}

		debug("Loaded " + matchTokens.size() + " patterns");		
		
		//printGenePatterns ();
		
		//printStopwords (stopList);
	}	
	/**
	 * 
	 * @param question
	 * @return
	 */
	@Override
	protected List<Keyterm> getKeyterms(String question) 
	{			
		String cleaned = KeytermStringTools.stringExpansion (question);
		
		debug ("getKeyterms ("+cleaned+")");
		
		List<Keyterm> keyterms = new ArrayList<Keyterm>();
												
		String[] split = cleaned.split("\\s+");

		ArrayList<String> cleanedTokens = cleanTokens(split);

		int index = 0;

		for (int i = 0; i < cleanedTokens.size(); i++) 
		{
			String aToken = cleanedTokens.get(i);

			//debug (aToken);

			// First pass, remove garbage ...

			if (isGarbage(aToken) == false) 
			{
				String fullGene = isMatchedGene(aToken, cleanedTokens,	i);

				// Nothing found, go for the backup ...
				
				if (fullGene == null)
				{
					if (isStub(aToken) == true) 
					{						
						debug ("Found keyword: " + aToken);
												
						ArrayList<String> termList=new ArrayList<String> ();
						termList.add(aToken);
						
						Keyterm aPattern=GenPatternTools.encodeKeyterm(cleaned,(float) 1.0,termList);
						
						keyterms.add(aPattern);						
					}
				} 
				else 
				{
					debug ("Found full pattern: " + fullGene);
					
					ArrayList<String> termList=new ArrayList<String> ();
					termList.add(fullGene);
					
					Keyterm aPattern=GenPatternTools.encodeKeyterm(cleaned,(float) 1.0,termList);
					
					keyterms.add(aPattern);
				}
			}

			index += aToken.length();
			index++; // don't forget the white space
		}		
		
	    return keyterms;
	}
	/**
	 * 
	 */
	private String cleanRawToken(String aToken) {
		String clean1 = aToken;

		if (aToken.endsWith(",") == true) {
			clean1 = aToken.substring(0, aToken.length() - 1);
		}

		String clean2 = clean1;

		if (clean1.endsWith(".") == true) {
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

		for (int i = 0; i < aList.length; i++) {
			newList.add(cleanRawToken(aList[i]));
		}

		return (newList);
	}	
	/**
	 * 
	 */
	private Boolean isUpperCase(String str)
	{
		//debug ("isUpperCase ("+str+")");
	
		boolean result=Pattern.matches(stubPatternMain,str);
		
		if (result==false)
			result=Pattern.matches(stubPatternSecondary, str);
		
		//debug ("Result: " + result);

		return (result);
	}		
	/**
	 * 
	 */
	private Boolean isStub(String aToken) 
	{
		//debug ("isStub ("+aToken+")");
		
		if (stubs != null) 
		{
			for (int i = 0; i < stubs.size(); i++) 
			{
				if (aToken.toLowerCase().indexOf(stubs.get(i)) != -1)
				{
					debug ("MATCH Stub: " + stubs.get(i));
					return (true);
				}
			}
		}

		if (isUpperCase (aToken))
		{
			debug ("MATCH: uppercase/alphanumeric heuristic");
			return (true);
		}
		
		return (false);
	}

	/**
	 * 
	 */
	private Boolean isGarbage(String aToken) 
	{
		//debug ("isGarbage ("+aToken.toLowerCase()+") -> " + stopList.size());
		
		if (stopList != null) 
		{			
			/*
			if (stopList.containsKey(aToken.toLowerCase())==true)
			{
				debug ("Determined that " + aToken + " is a stopword");
				return (true);
			}
			*/
			
		    Iterator it = stopList.entrySet().iterator();
		    while (it.hasNext()) 
		    {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        String checker=(String) pairs.getKey();
		        		        if (checker.equalsIgnoreCase(aToken)==true)
		        {
		        	//debug ("Determined that " + aToken + " is a stopword");
		        	return (true);
		        }		       
		    }			
		}
		else
			debug ("Error: no stop list available!");
		
		return (false);
	}

	/**
	 * 
	 */
	private String isMatchedGene(String aToken, ArrayList<String> aTokenList,int anIndex) 
	{
		//debug ("isMatchedGene ("+aToken+" <aTokenList> " + anIndex + ")");

		//debug ("Matching against " + matchTokens.size() + " gene patterns");

		for (int i = 0; i < matchTokens.size(); i++) {
			GenTokenSequence seq = matchTokens.get(i);

			int ptSize = seq.tokens.size();

			//debug ("Looking at pattern of size: " + ptSize);

			int matched = 0;

			for (int j = 0; j < ptSize; j++) {
				if ((anIndex + j) < aTokenList.size()) {
					String fromList = aTokenList.get(anIndex + j);
					String toList = seq.tokens.get(j);

					//debug ("Comparing " + fromList + " to " + toList);

					if (fromList.equals(toList))
						matched++;
				}
			}

			// See if we've matched all the tokens. If there is a partial match
			// we can see if we can incorporate that score at some point

			if (matched == seq.tokens.size()) 
			{
				debug ("MATCHED!");

				StringBuffer formatter = new StringBuffer();

				for (int t = 0; t < seq.tokens.size(); t++) {
					if (t > 0)
						formatter.append(" ");

					formatter.append(seq.tokens.get(t));
				}

				return (formatter.toString());
			}
		}

		return (null);
	}
	/**
	 * 
	 */
	public void printGenePatterns() 
	{
		debug("printGenePatterns ()");

		for (int i = 0; i < matchTokens.size(); i++) 
		{
			GenTokenSequence seq = matchTokens.get(i);

			StringBuffer formatter = new StringBuffer();

			for (int j = 0; j < seq.tokens.size(); j++) 
			{
				if (j > 0)
					formatter.append(",");

				formatter.append(seq.tokens.get(j));
			}

			debug("Pattern: " + formatter.toString());
		}
	}	
	/**
	 * 
	 * @param mp
	 */
	public void printStopwords(Map mp) 
	{
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) 
	    {
	        Map.Entry pairs = (Map.Entry)it.next();
	        debug ("["+pairs.getKey() + "] = " + pairs.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}		
}
