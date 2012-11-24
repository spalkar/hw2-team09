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

/**
 * 
 */
public class GenPattern
{
	public static int QUESTIONWHAT=0;
	public static int QUESTIONHOW=1;
	public static int QUESTIONGENERIC=2;
	
	public int questionType=QUESTIONGENERIC;
	public String sentence="";
	public float score=(float) 1.0;
	public ArrayList<String> keyterms=new ArrayList<String> ();
}
