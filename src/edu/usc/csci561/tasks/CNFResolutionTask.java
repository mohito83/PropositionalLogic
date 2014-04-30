/**
 * 
 */
package edu.usc.csci561.tasks;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.usc.csci561.HornClause;
import edu.usc.csci561.IEntailmentTask;
import edu.usc.csci561.data.CNFSentence;
import edu.usc.csci561.data.Symbol;
import edu.usc.csci561.utils.CNFConverter;
import edu.usc.csci561.utils.CNFSymbols;
import edu.usc.csci561.utils.CartesianSetUtils;
import edu.usc.csci561.utils.CNFSymbolComparator;

/**
 * @author mohit aggarwl
 * 
 *         Please Note:- In this class I have adapted some of the methods
 *         mentioned in the following source code.
 *         https://code.google.com/p/aima-java/source
 *         /browse/trunk/src/aima/logic
 *         /propositional/algorithms/PLResolution.java?r=193
 * 
 */
public class CNFResolutionTask extends IEntailmentTask {

	private static final String EMPTY_STRING = "Empty";

	/**
	 * 
	 * @param logsWriter
	 * @param resultsFileWriter
	 */
	public CNFResolutionTask(FileWriter logsWriter, FileWriter resultsFileWriter) {
		super(logsWriter, resultsFileWriter);
	}

	@Override
	public void processEntailment(List<HornClause> clauses, List<String> query) {
		StringBuffer buff = new StringBuffer();
		printLog("Resolving clause 1#Resolving clause 2#Added clause");
		printLog(System.getProperty("line.separator"));
		for (String q : query) {
			Set<CNFSentence> kb = CNFConverter.convertToCNF(clauses);
			// add the -q to the knowledge base
			Symbol s = new Symbol(q, false);
			CNFSentence sentence = new CNFSentence();
			sentence.addSymbol(s);
			kb.add(sentence);
			boolean result = plCNFEntailment(kb);
			buff.append(result ? "YES" : "NO");
			buff.append(System.getProperty("line.separator"));

			// for the output log files
			printLog("-------------------------------------------------------------");
			printLog(System.getProperty("line.separator"));
		}
		printResult(buff.toString());
	}

	/**
	 * 
	 * @param clauses
	 * @param q
	 * @return
	 */
	private boolean plCNFEntailment(Set<CNFSentence> kb) {
		Set<CNFSentence> newClauses = new LinkedHashSet<CNFSentence>();
		int k = 1;
		while (true) {
			printLog("ITERATION = " + k);
			printLog(System.getProperty("line.separator"));
			List<List<CNFSentence>> pairs = getCombinationPairs(new ArrayList<CNFSentence>(
					kb));

			for (int i = 0; i < pairs.size(); i++) {
				List<CNFSentence> pair = pairs.get(i);
				Set<CNFSentence> resolvents = plResolve(pair.get(0),
						pair.get(1));
				resolvents = filterSentencesWithTwoComplementarySymbols(resolvents);

				if (!resolvents.isEmpty()) {
					// generate print logs
					StringBuffer buff = new StringBuffer();
					int j = 1;
					for (Symbol s : pair.get(0).getSymbols()) {
						buff.append(s);
						if (j < pair.get(0).getSymbols().size())
							buff.append(" OR ");
						j++;
					}
					buff.append(" # ");
					j = 1;
					for (Symbol s : pair.get(1).getSymbols()) {
						buff.append(s);
						if (j < pair.get(1).getSymbols().size())
							buff.append(" OR ");
						j++;
					}
					buff.append(" # ");
					j = 1;
					Iterator<CNFSentence> iter1 = resolvents.iterator();
					if (iter1.hasNext()) {
						CNFSentence sentence3 = iter1.next();
						for (Symbol s : sentence3.getSymbols()) {
							buff.append(s);
							if (j < sentence3.getSymbols().size())
								buff.append(" OR ");
							j++;
						}
					}
					buff.append(System.getProperty("line.separator"));
					printLog(buff.toString());

					if (isEmptyClause(resolvents)) {
						return true;
					}
					newClauses = CartesianSetUtils
							.union(newClauses, resolvents);
				}
			}
			if (CartesianSetUtils.intersection(newClauses, kb).size() == newClauses
					.size()) {
				return false;
			}
			kb = CartesianSetUtils.union(kb, newClauses);
			k++;
		}
	}

	/**
	 * 
	 * @param sentence1
	 * @param sentence2
	 * @return
	 */
	private Set<CNFSentence> plResolve(CNFSentence sentence1,
			CNFSentence sentence2) {
		Set<CNFSentence> resolvents = new HashSet<CNFSentence>();
		CNFSymbols cs = new CNFSymbols(sentence1, sentence2);
		Iterator<Symbol> iter = cs.getComplementedSymbols().iterator();
		while (iter.hasNext()) {
			Symbol symbol = iter.next();
			resolvents.add(createResolventSentence(cs, symbol));
		}

		return resolvents;
	}

	/**
	 * 
	 * @param clausesList
	 * @return
	 */
	private List<List<CNFSentence>> getCombinationPairs(
			List<CNFSentence> clausesList) {
		List<List<CNFSentence>> pairs = new ArrayList<List<CNFSentence>>();
		for (int i = 0; i < clausesList.size(); i++) {
			for (int j = i; j < clausesList.size(); j++) {
				List<CNFSentence> pair = new ArrayList<CNFSentence>();
				CNFSentence first = clausesList.get(i);
				CNFSentence second = clausesList.get(j);

				// if the pairs have one or more symbols which are complementary
				// then add them as pairs.
				if (!first.isSame(second)) {
					pair.add(first);
					pair.add(second);
					pairs.add(pair);
				}
			}
		}
		return pairs;
	}

	/**
	 * 
	 * @param cs
	 * @param toRemove
	 * @return
	 */
	private CNFSentence createResolventSentence(CNFSymbols cs, Symbol toRemove) {
		List<Symbol> positiveSymbols = new ArrayList<Symbol>(
				CartesianSetUtils.union(cs.getClause1PositiveSymbols(),
						cs.getClause2PositiveSymbols()));
		List<Symbol> negativeSymbols = new ArrayList<Symbol>(
				CartesianSetUtils.union(cs.getClause1NegativeSymbols(),
						cs.getClause2NegativeSymbols()));
		if (positiveSymbols.contains(toRemove)) {
			positiveSymbols.remove(toRemove);
		}
		if (negativeSymbols.contains(toRemove)) {
			negativeSymbols.remove(toRemove);
		}

		Collections.sort(positiveSymbols, new CNFSymbolComparator());
		Collections.sort(negativeSymbols, new CNFSymbolComparator());

		List<Symbol> sentences = new ArrayList<Symbol>();
		for (int i = 0; i < positiveSymbols.size(); i++) {
			sentences.add(positiveSymbols.get(i));
		}
		for (int i = 0; i < negativeSymbols.size(); i++) {
			sentences.add(negativeSymbols.get(i));
		}

		CNFSentence cnf = new CNFSentence();
		if (sentences.size() == 0) {
			cnf.addSymbol(new Symbol(EMPTY_STRING, true));
			return cnf;
		} else {
			for (Symbol s : sentences) {
				if (!cnf.contains(s)) {
					cnf.addSymbol(s);
				} else {
					cnf.removeAll();
					cnf.setValid(false);
					return cnf;
				}
			}
			return cnf;
		}

	}

	/**
	 * 
	 * @param clauses
	 * @return
	 */
	private Set<CNFSentence> filterSentencesWithTwoComplementarySymbols(
			Set<CNFSentence> clauses) {
		Set<CNFSentence> result = new LinkedHashSet<CNFSentence>();
		Iterator<CNFSentence> iter = clauses.iterator();
		while (iter.hasNext()) {
			CNFSentence clause = iter.next();
			if (clause.isValid()) {
				result.add(clause);
			}
		}
		return result;
	}

	/**
	 * This method checks if there is an CNF Sentence with Empty symbol.
	 * 
	 * @param resolvents
	 * @return
	 */
	private boolean isEmptyClause(Set<CNFSentence> resolvents) {
		Iterator<CNFSentence> iter = resolvents.iterator();
		while (iter.hasNext()) {
			CNFSentence s = iter.next();
			Iterator<Symbol> symIter = s.getSymbols().iterator();
			while (symIter.hasNext()) {
				Symbol c = symIter.next();
				if (c.getValue().equals(EMPTY_STRING)) {
					return true;
				}
			}
		}

		return false;
	}

}
