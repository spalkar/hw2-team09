package edu.cmu.lti.oaqa.openqa.test.team09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.resource.ResourceInitializationException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.util.AbstractExternalizable;

import edu.cmu.lti.oaqa.cse.basephase.keyterm.AbstractKeytermExtractor;
import edu.cmu.lti.oaqa.framework.data.Keyterm;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenBase;
import edu.cmu.lti.oaqa.openqa.test.team09.martinv.GenPatternTools;

public class LingpengKeyTermExtractor extends AbstractKeytermExtractor 
{
	Chunker chunker = null;

	/**
	 * Internal method accessing Hoop debugging
	 */
	private void debug(String aMessage) 
	{
		GenBase.debug("LingpengKeyTermExtractor", aMessage);
	}	
	/**
	 * 
	 */
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException 
	{
		super.initialize(aContext);
	}
	/**
	 * 
	 */
	public void init() 
	{
		debug ("init ()");
		
		File modelFile;
		
		//modelFile = new File("src/main/resources/model/GenTagHmm");
		modelFile = new File("model/GenTagHmm");
				
		debug ("Model file absolute path: " + modelFile.getAbsolutePath());
		
		// System.out.println("*************"+modelFile.getAbsolutePath());
		// System.out.println("*************"+Thread.currentThread().getContextClassLoader().getResource(""));
    
		try 
		{
			chunker = (Chunker) AbstractExternalizable.readObject(modelFile);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 */
	@Override
	protected List<Keyterm> getKeyterms(String arg0) 
	{
		debug ("getKeyterms ("+arg0+")");
		
		if (chunker == null) 
		{
			init();
		}
    
		String docText = arg0;
    
		Chunking chunking = chunker.chunk(docText);
    
		List<Keyterm> keyterms = new ArrayList<Keyterm>();
    
		/*
    	for (Chunk ck : chunking.chunkSet()) 
    	{
      		keyterms.add(new Keyterm(docText.substring(ck.start(), ck.end())));
      		//System.out.println("******"+docText.substring(ck.start(), ck.end()));
    	}
		*/
    
		ArrayList<String> termList=new ArrayList<String> ();
    
		for (Chunk ck : chunking.chunkSet()) 
		{      
			termList.add(docText.substring(ck.start(), ck.end()));
		}    
    				
		Keyterm aPattern=GenPatternTools.encodeKeyterm(arg0,(float) 1.0,termList);
	
		keyterms.add(aPattern);
	    
		return keyterms;
	}
}
