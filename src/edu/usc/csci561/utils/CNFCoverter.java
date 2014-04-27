/**
 * 
 */
package edu.usc.csci561.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.usc.csci561.HornClause;
import edu.usc.csci561.data.CNFSentence;
import edu.usc.csci561.data.Symbol;

/**
 * This class defines the methods to convert given knowledge base sentences to
 * CNF form
 * 
 * @author mohit aggarwl
 * 
 */
public class CNFCoverter {

	/**
	 * This method returns the CNF sentence for the given HornClause sentences
	 * 
	 * @param clauses
	 * @return
	 */
	public static List<CNFSentence> convertToCNF(List<HornClause> clauses) {
		List<CNFSentence> cnfSentences = new ArrayList<CNFSentence>();
		Iterator<HornClause> iter = clauses.iterator();
		while (iter.hasNext()) {
			HornClause c = iter.next();
			CNFSentence cnf = new CNFSentence();
			if (c.getHead() == null) {
				Symbol s = new Symbol(c.getBody().get(0), true);
				cnf.addSymbol(s);
			} else {
				Symbol s = new Symbol(c.getHead(), true);
				cnf.addSymbol(s);
				for (String x : c.getBody()) {
					s = new Symbol(x, false);
					cnf.addSymbol(s);
				}
			}
			cnfSentences.add(cnf);
		}
		return cnfSentences;
	}
}
