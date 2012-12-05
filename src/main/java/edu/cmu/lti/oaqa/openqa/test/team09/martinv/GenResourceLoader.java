/** 
 * Author: Martin van Velsen <vvelsen@cs.cmu.edu>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as 
 *  published by the Free Software Foundation, either version 3 of the 
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package edu.cmu.lti.oaqa.openqa.test.team09.martinv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.uima.resource.ResourceManager;
import org.apache.uima.util.FileUtils;

/**
 * A support class to encapsulate resource loading. It features various methods
 * to load data either directly from the file system (as is needed for the test
 * input file) or to load resources from the distribution jar. Currently the
 * class is mostly used to load files from the main jar, see getTextResource2
 * for the full implementation
 */
public class GenResourceLoader extends GenBase {
	public int fileSize = 0;
	public String fileURI = "";

	/**
	 *
	 */
	public GenResourceLoader() {
		setClassName("GenResourceLoader");
		debug("GenResourceLoader ()");
	}

	/**
	 * 
	 */
	public void testResources() {
		debug("testResources ()");

	}

	/**
	 * 
	 */
	public String getTextFile(String aFile) {
		debug("getTextFile ()");

		// open input stream to file
		File file = new File(aFile);

		if (file.exists() == false) {
			debug("Configuration error, file " + aFile + " does not exist");
			return (null);
		}

		debug("Loading: " + aFile);

		String text = null;

		try {
			text = FileUtils.file2String(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return (null);
		}

		if (text == null) {
			debug("Input error, unable to read: " + aFile);
			return (null);
		}

		// debug (text);

		return (text);
	}

	/**
	 * 
	 */
	public String getTextResource(String aFile, ResourceManager aManager) {
		debug("getTextResouce ()");

		debug("Data path: " + aManager.getDataPath());

		fileURI = aManager.getDataPath() + File.separator + aFile;

		// open input stream to file
		File file = new File(fileURI);

		if (file.exists() == false) {
			debug("Configuration error, file " + aFile + " does not exist");
			return (null);
		}

		debug("Loading: " + aFile);

		String text = null;

		try {
			text = FileUtils.file2String(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return (null);
		}

		if (text == null) {
			debug("Input error, unable to read: " + aFile);
			return (null);
		}

		// debug (text);

		return (text);
	}

	/**
	 * 
	 */
	public String getTextResource2(String aFile) 
	{
		debug("getTextResouce2 ()");

		try 
		{
			debug(getClass().getClassLoader().getResource(aFile).toURI().toString());
		} 
		catch (URISyntaxException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return (null);
		}

		File file = null;

		try 
		{
			file = new File(getClass().getClassLoader().getResource(aFile).toURI());
		} 
		catch (URISyntaxException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (null);
		}

		debug("Loading: " + aFile);

		String text = null;

		try {
			text = FileUtils.file2String(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return (null);
		}

		if (text == null) {
			debug("Input error, unable to read: " + aFile);
			return (null);
		}

		// debug (text);

		return (text);
	}
	/**
	 * 
	 */
	public String loadTextURL (String aResource)
	{
		debug("loadTextURL ("+aResource+")");
		
		StringBuffer formatted=new StringBuffer ();
		
	    try 
	    {
	        // Create a URL for the desired page
	        URL url = new URL(aResource);
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        
	        String str;
	        
	        while ((str = in.readLine()) != null) 
	        {
	        	//GenBase.debug("GenResourceLoader","Error: " + str);
	        	formatted.append(str);
	        	formatted.append("\n");
	        }
	        
	        in.close();
	    } 
	    catch (MalformedURLException e) 
	    {
	        
	    } 
	    catch (IOException e) 
	    {
	    	
	    }
	    
	    return (formatted.toString());
	}
}
