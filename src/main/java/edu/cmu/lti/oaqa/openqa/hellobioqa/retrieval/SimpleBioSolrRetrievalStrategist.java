package edu.cmu.lti.oaqa.openqa.hellobioqa.retrieval;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import edu.cmu.lti.oaqa.framework.data.RetrievalResult;
import edu.cmu.lti.oaqa.openqa.hello.retrieval.SimpleSolrRetrievalStrategist;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenBase;

/**
 *
 */
public class SimpleBioSolrRetrievalStrategist extends SimpleSolrRetrievalStrategist 
{
	/**
	 * Internal method accessing Hoop debugging
	 */
	private void debug(String aMessage) 
	{
		GenBase.debug("SimpleBioSolrRetrievalStrategist", aMessage);
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
				RetrievalResult r = new RetrievalResult((String) doc.getFieldValue("id"),(Float) doc.getFieldValue("score"), query);
				
				result.add(r);
											
				debug ("Doc (ID): " + doc.getFieldValue("id"));
			}
		} 
		catch (Exception e) 
		{
			debug ("Error retrieving documents from Solr: " + e);
		}
    
		return result;
	}
}
