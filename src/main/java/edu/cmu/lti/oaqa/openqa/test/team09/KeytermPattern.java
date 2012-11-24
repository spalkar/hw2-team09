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
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenBase;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenResourceLoader;

/**
 * 
 */
public class KeytermPattern extends ModifiedAbstractKeytermExtractor 
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
	
	// private String stopwordFile="src/main/resources/data/stoplist.txt";
	// private String patternFile="src/main/resources/data/patterns-raw.txt";
	// private String stubFile="src/main/resources/data/stubs.txt";

	private String stopwordFile = "data/stoplist.txt";
	private String patternFile = "data/patterns-raw.txt";
	private String stubFile = "data/stubs.txt";

	private HashMap<String, Integer> stopList = null;
	private ArrayList<GenTokenSequence> matchTokens = null;
	private ArrayList<String> stubs = null;
	
	/**
	 * Internal method accessing Hoop debugging
	 */
	private void debug(String aMessage) {
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

		for (i = 0; i < lines.length; i++) {
			String aWord = lines[i];
			stopList.put(aWord.toLowerCase(), i);
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
	}	
	/**
	 * 
	 */
	
	/**
	 * 
	 * @param question
	 * @return
	 */
	@Override
	protected List<GenePattern> getKeyterms(String question) 
	{						
		debug ("getKeyterms ("+question+")");
		
		List<GenePattern> keyterms = new ArrayList<GenePattern>();
				
		// Let's add a basic sanity check by changing the whole sentence
		// to lower case

		String cleaned = question.toLowerCase();

		String[] split = cleaned.split("\\s+");

		ArrayList<String> cleanedTokens = cleanTokens(split);

		int index = 0;

		for (int i = 0; i < cleanedTokens.size(); i++) 
		{
			String aToken = cleanedTokens.get(i);

			// debug (aToken);

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
						
						GenePattern aPattern=new GenePattern ();
						aPattern.setSentence(question);
						aPattern.setScore((float) 1.0);
						
						StringArray terms=aPattern.getKeyterms();
						terms.set(0,aToken);

						keyterms.add(aPattern);					
					}
				} 
				else 
				{
					debug ("Found keyword: " + fullGene);
					
					GenePattern aPattern=new GenePattern ();
					aPattern.setScore((float) 1.0);
					
					StringArray terms=aPattern.getKeyterms();
					terms.set(0,fullGene);

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
	private ArrayList<String> cleanTokens(String[] aList) {
		ArrayList<String> newList = new ArrayList<String>();

		for (int i = 0; i < aList.length; i++) {
			newList.add(cleanRawToken(aList[i]));
		}

		return (newList);
	}	
	/**
	 * 
	 */
	private void printGenePatterns() {
		debug("printGenePatterns ()");

		for (int i = 0; i < matchTokens.size(); i++) {
			GenTokenSequence seq = matchTokens.get(i);

			StringBuffer formatter = new StringBuffer();

			for (int j = 0; j < seq.tokens.size(); j++) {
				if (j > 0)
					formatter.append(",");

				formatter.append(seq.tokens.get(j));
			}

			debug("Pattern: " + formatter.toString());
		}
	}	
	/**
	 * 
	 */
	private Boolean isStub(String aToken) {
		if (stubs != null) {
			for (int i = 0; i < stubs.size(); i++) {
				if (aToken.indexOf(stubs.get(i)) != -1)
					return (true);
			}
		}

		return (false);
	}

	/**
	 * 
	 */
	private Boolean isGarbage(String aToken) {
		if (stopList != null) {
			if (stopList.get(aToken) != null)
				return (true);
		}

		return (false);
	}

	/**
	 * 
	 */
	private String isMatchedGene(String aToken, ArrayList<String> aTokenList,
			int anIndex) {
		// debug ("isMatchedGene ("+aToken+" <aTokenList> " + anIndex + ")");

		// debug ("Matching against " + matchTokens.size() + " gene patterns");

		for (int i = 0; i < matchTokens.size(); i++) {
			GenTokenSequence seq = matchTokens.get(i);

			int ptSize = seq.tokens.size();

			// debug ("Looking at pattern of size: " + ptSize);

			int matched = 0;

			for (int j = 0; j < ptSize; j++) {
				if ((anIndex + j) < aTokenList.size()) {
					String fromList = aTokenList.get(anIndex + j);
					String toList = seq.tokens.get(j);

					// debug ("Comparing " + fromList + " to " + toList);

					if (fromList.equals(toList))
						matched++;
				}
			}

			// See if we've matched all the tokens. If there is a partial match
			// we can see if we can incorporate that score at some point

			if (matched == seq.tokens.size()) {
				// debug ("MATCHED!");

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
}
