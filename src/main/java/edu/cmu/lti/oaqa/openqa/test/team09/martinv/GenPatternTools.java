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

import java.util.ArrayList;

import edu.cmu.lti.oaqa.framework.data.Keyterm;

/**
 * 
 */
public class GenPatternTools extends GenBase 
{
	/**
	 *
	 */
	public GenPatternTools() 
	{
		setClassName("GenPatternTools");
		debug("GenPatternTools ()");
	}
	/**
	 * 
	 */
	public static Keyterm encodeKeyterm (String aSentence,float aScore,ArrayList <String>aTerms)
	{		
		//GenBase.debug("GenPatternTools","encodeKeyterm ()");
		
		StringBuffer formatter=new StringBuffer ();
		
		if (aSentence.toLowerCase().indexOf("what")==0)
		{
			formatter.append("w");
		}
		else
		{
			if (aSentence.toLowerCase().indexOf("how")==0)
			{
				formatter.append("h");
			}
			else
				formatter.append("?");
		}
		
		formatter.append("|");
		
		formatter.append(aSentence);
		formatter.append("|");
		formatter.append (aScore);
		formatter.append("|");
		
		for (int i=0;i<aTerms.size();i++)
		{
			if (i>0)
				formatter.append(",");
			
			formatter.append(aTerms.get (i));
		}
		
		return (new Keyterm (formatter.toString()));
	}
	/**
	 * 
	 */
	public static GenPattern decodeKeyterm (Keyterm aKeyterm)
	{
		//GenBase.debug("GenPatternTools","decodeKeyterm ("+aKeyterm.getText()+")");
		
		GenPattern newPattern=new GenPattern ();
		
		String raw=aKeyterm.getText();
		
		String parts []=raw.split("\\|");
		
		String qType=parts [0];
		
		if (qType.equals("w")==true)
			newPattern.questionType=GenPattern.QUESTIONWHAT;
		
		if (qType.equals("h")==true)
			newPattern.questionType=GenPattern.QUESTIONWHAT;
		
		if (qType.equals("?")==true)
			newPattern.questionType=GenPattern.QUESTIONWHAT;
		
		newPattern.sentence=parts [1];
		newPattern.score=Float.parseFloat(parts [2]);
		String termsRaw=parts [3];
		
		String terms []=termsRaw.split("\\,");
						
		for (int i=0;i<terms.length;i++)
		{
			newPattern.keyterms.add(terms [i]);
		}
		
		return (newPattern);
	}
}
