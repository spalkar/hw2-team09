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

package edu.cmu.lti.oaqa.openqa.test.team09.kenneth;

import java.util.Hashtable;

import edu.cmu.lti.oaqa.openqa.hello.passage.KeytermWindowScorer;

public class KeytermWindowScorerKennethKLDiv implements KeytermWindowScorer {
	
	public double getKLDiv(String[] wordArray, String[] keyterms){

		Hashtable<String, Integer> wordHash = buildWordHash(wordArray, keyterms);
		int[] countP = wordToCount(wordArray, wordHash);
		int[] countQ = wordToCount(keyterms, wordHash);
		return calKLDiv(new LM(countP), new LM(countQ));
		
	}
	
	private double calKLDiv(LM p, LM q){
		double result = 0;
		for(int i=0;i<p.size();i++){
			result += Math.log(p.getProb(i)/q.getProb(i))*p.getProb(i);
		}
		return result; 
	}
	
	private int[] wordToCount(String[] wordArray, Hashtable<String, Integer> wordHash){
		int[] result = new int[wordHash.size()];
		for(int i=0;i<result.length;i++){
			result[i] = 0;
		}
		for(String nowWord: wordArray){
			nowWord = nowWord.toLowerCase().trim();
			int nowIndex = wordHash.get(nowWord).intValue();
			result[nowIndex]++;
		}
		return result;
	}
	
	private Hashtable<String, Integer> buildWordHash(String[] wordArray, String[] keyterms){
		Hashtable<String, Integer> result = new Hashtable<String, Integer>();
		result = maintainWordHash(result, wordArray);
		result = maintainWordHash(result, keyterms);
		return result;
	}
	
	private Hashtable<String, Integer> maintainWordHash(Hashtable<String, Integer> result, String[] wordArray){
		for(int i=0;i<wordArray.length;i++){
			String nowWord = wordArray[i].toLowerCase().trim();
			if(result.get(nowWord)==null){
				int nowWordId = result.size();
				result.put(nowWord, new Integer(nowWordId));
			}
		}
		return result;	
	}
	
	
	public double scoreWindow ( int begin , int end , int matchesFound , int totalMatches , int keytermsFound , int totalKeyterms , int textSize ){
		int windowSize = end - begin;
		double offsetScore = ( (double)textSize - (double)begin ) / (double)textSize;
		return ( .25d * (double)matchesFound / (double)totalMatches ) + .25d * ( (double)keytermsFound / (double)totalKeyterms) + .25d * ( 1 - ( (double)windowSize / (double)textSize ) + .25d * offsetScore );
	}

}

class LM{
	
	private int[] count;
	private double[] prob;
	private int size;
	
	final double addConst = 0.001;
	
	public LM(int[] count){
		this.count = count;
		this.size = count.length;
		this.prob = new double[this.size];
		double sum = 0.0;
		for(int nowCount: count){
			sum += addConst+(double)nowCount;
		}
		for(int i=0;i<this.size;i++){
			prob[i] = ((double)count[i]+this.addConst)/(double)sum;
		}
	}
	
	public double getProb(int index){
		return prob[index];
	}
	
	public int size(){
		return this.size;
	}
	
	
}


