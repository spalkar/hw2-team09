package edu.cmu.lti.oaqa.openqa.test.team09;


import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;
import edu.cmu.lti.oaqa.cse.basephase.retrieval.AbstractRetrievalStrategist;
import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.framework.data.RetrievalResult;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenBase;

public class MvVSolrRetrievalStrategist extends AbstractRetrievalStrategist 
{
	protected Integer hitListSize;

	protected SolrWrapper wrapper;
  
	// the scoreThreshold is used to discard retrieved documents with a low match score 
	private float scoreThreshold = (float) 0.35;
	// queryConfig can be DEFAULT which is one query with all keyterms whitespace separated
	// queryConfig can also be CUSTOM which calls the retrieveDocumentsIndv function that 
	// searches documents one keyterm based query at a time (with or without wildcards)
	private String queryConfig = "DEFAULT";

	/**
	 * Internal method accessing Hoop debugging
	 */
	private void debug(String aMessage) 
	{
		GenBase.debug("MvVSolrRetrievalStrategist", aMessage);
	}	  
	/**
	 * 
	 */
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException 
	{	  
		super.initialize(aContext);
    
		 debug ("initialize ()");
	  
		 try 
		 {
			 this.hitListSize = (Integer) aContext.getConfigParameterValue("hit-list-size");
		 } 
		 catch (ClassCastException e) 
		 { 
			 // all cross-opts are strings?
			 this.hitListSize = Integer.parseInt((String) aContext.getConfigParameterValue("hit-list-size"));
		 }
		 
		 String serverUrl = (String) aContext.getConfigParameterValue("server");
		 Integer serverPort = (Integer) aContext.getConfigParameterValue("port");
		 Boolean embedded = (Boolean) aContext.getConfigParameterValue("embedded");
		 String core = (String) aContext.getConfigParameterValue("core");
		 
		 try 
		 {
			 this.wrapper = new SolrWrapper(serverUrl, serverPort, embedded, core);
		 } 
		 catch (Exception e) 
		 {
			 throw new ResourceInitializationException(e);
		 }
	}
	/**
	 * 
	 */
	@Override
	protected final List<RetrievalResult> retrieveDocuments(String questionText,List<Keyterm> keyterms) 
	{
		debug ("retrieveDocuments ("+questionText+")");
		
		// TODO collapse the two retrieve documents into one function and change the list of 
		// queries passed to them based on queryConfig for elegance
		
		if (queryConfig == "CUSTOM")
		{
			return retrieveDocumentsIndv(keyterms);
		}
		else
		{ //DEFAULT
			String query = formulateQuery(keyterms);
			return retrieveDocuments(query);
		}
	};
	/**
	 * 
	 */
	protected String formulateQuery(List<Keyterm> keyterms) 
	{
		debug ("formulateQuery ()");
		
		StringBuffer result = new StringBuffer();
		
		for (Keyterm keyterm : keyterms) 
		{
			result.append(keyterm.getText() + " ");
		}
		
		String query = result.toString();
		
		debug (" QUERY: " + query);
		
		return query;
	}
	/**
	 * 
	 */
	protected List<RetrievalResult> retrieveDocuments(String query) 
	{
		debug ("retrieveDocuments ("+query+")");
		
		List<RetrievalResult> result = new ArrayList<RetrievalResult>();
		
		try 
		{			
			SolrDocumentList docs = wrapper.runQuery(query, hitListSize);
			
			for (SolrDocument doc : docs) 
			{
				Float score = (Float)doc.getFieldValue("score");
				
				if (score.floatValue() < scoreThreshold) 
				{
					continue;
				}
				
				RetrievalResult r = new RetrievalResult((String) doc.getFieldValue("id"),(Float) doc.getFieldValue("score"), query);
        
				result.add(r);
        
				System.out.println(doc.getFieldValue("id"));
			}
		} 
		catch (Exception e) 
		{
			System.err.println("Error retrieving documents from Solr: " + e);
		}
		
		return result;
	}
	/**
	 * 
	 */
	protected List<RetrievalResult> retrieveDocumentsIndv(List<Keyterm> keyterms) 
	{
		debug ("retrieveDocumentsIndv ()");
		
		List<RetrievalResult> result = new ArrayList<RetrievalResult>();
		List<String> docIds = new ArrayList<String>();
		
		for (Keyterm keyterm : keyterms) 
		{
			// Change this line to try whichever type of query you want
			String query = keyterm.getText();
			
			debug (" QUERY: " + query);
			
			try 
			{
				SolrDocumentList docs = wrapper.runQuery(query, hitListSize);
				
				for (SolrDocument doc : docs) 
				{
					String dId = (String) doc.getFieldValue("id");
					
					if (docIds.contains(dId))
					{
						continue;
					}
					else 
					{
						docIds.add(dId);
					}
					
					Float score = (Float) doc.getFieldValue("score");
					
					if (score.floatValue() < scoreThreshold) 
					{
						continue;
					}
					
					RetrievalResult r = new RetrievalResult(dId, score, query);
					result.add(r);
					debug (dId);
					// System.out.println(score);
				}
			} 
			catch (Exception e) 
			{
				debug ("Error retrieving documents from Solr: " + e);
			}
		}
		
		return result;
	}
	/**
	 * 
	 */
	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException 
	{
		super.collectionProcessComplete();
		wrapper.close();
	}
}
