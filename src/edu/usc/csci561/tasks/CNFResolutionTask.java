/**
 * 
 */
package edu.usc.csci561.tasks;

import java.io.FileWriter;
import java.util.ArrayList;
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
import edu.usc.csci561.utils.ClauseSymbols;
import edu.usc.csci561.utils.SetUtils;

/**
 * @author mohit aggarwl
 * 
 */
public class CNFResolutionTask extends IEntailmentTask {

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
			Symbol s = new Symbol(q, false);
			CNFSentence sentence = new CNFSentence();
			sentence.addSymbol(s);
			kb.add(sentence);
			boolean result = plResolution(kb);
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
	private boolean plResolution(Set<CNFSentence> kb) {
		Set<CNFSentence> clauses = filterOutClausesWithTwoComplementaryLiterals(kb);
		Set<CNFSentence> newClauses = new LinkedHashSet<CNFSentence>();
		int k = 1;
		while (true) {
			printLog("ITERATION = " + k);
			printLog(System.getProperty("line.separator"));
			List<List<CNFSentence>> pairs = getCombinationPairs(new ArrayList<CNFSentence>(
					clauses));

			for (int i = 0; i < pairs.size(); i++) {
				List<CNFSentence> pair = pairs.get(i);
				Set<CNFSentence> resolvents = plResolve(pair.get(0),
						pair.get(1));
				resolvents = filterOutClausesWithTwoComplementaryLiterals(resolvents);

				if (resolvents.contains(new Symbol("EMPTY_CLAUSE", true))) {
					return true;
				}
				newClauses = SetUtils.union(newClauses, resolvents);
			}
			if (SetUtils.intersection(newClauses, clauses).size() == newClauses
					.size()) {// subset test
				return false;
			}
			clauses = SetUtils.union(newClauses, clauses);
			clauses = filterOutClausesWithTwoComplementaryLiterals(clauses);
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
		ClauseSymbols cs = new ClauseSymbols(sentence1, sentence2);
		Iterator<Symbol> iter = cs.getComplementedSymbols().iterator();
		while (iter.hasNext()) {
			Symbol symbol = iter.next();
			resolvents.add(createResolventClause(cs, symbol));
		}

		// generate print logs
		StringBuffer buff = new StringBuffer();
		int i = 1;
		for (Symbol s : sentence1.getSymbols()) {
			buff.append(s);
			if (i < sentence1.getSymbols().size())
				buff.append(" OR ");
			i++;
		}
		buff.append(" # ");
		i = 1;
		for (Symbol s : sentence2.getSymbols()) {
			buff.append(s);
			if (i < sentence2.getSymbols().size())
				buff.append(" OR ");
			i++;
		}
		buff.append(" # ");
		i = 1;
		Iterator<CNFSentence> iter1 = resolvents.iterator();
		if (iter1.hasNext()) {
			for (Symbol s : iter1.next().getSymbols()) {
				buff.append(s);
				if (i < sentence2.getSymbols().size())
					buff.append(" OR ");
				i++;
			}
		}
		buff.append(System.getProperty("line.separator"));
		printLog(buff.toString());
		return resolvents;
	}

	private List<List<CNFSentence>> getCombinationPairs(
			List<CNFSentence> clausesList) {
		List<List<CNFSentence>> pairs = new ArrayList<List<CNFSentence>>();
		for (int i = 0; i < clausesList.size(); i++) {
			for (int j = i; j < clausesList.size(); j++) {
				List<CNFSentence> pair = new ArrayList<CNFSentence>();
				CNFSentence first = clausesList.get(i);
				CNFSentence second = clausesList.get(j);

				// if (!isSame(first, second)) {
				if (!first.isSame(second)) {
					pair.add(first);
					pair.add(second);
					pairs.add(pair);
				}
			}
		}
		return pairs;
	}

	private CNFSentence createResolventClause(ClauseSymbols cs, Symbol toRemove) {
		/*List<Symbol> positiveSymbols = new ArrayList<Symbol>(SetUtils.union(
				cs.getClause1PositiveSymbols(), cs.getClause2PositiveSymbols()));
		List<Symbol> negativeSymbols = new ArrayList<Symbol>(SetUtils.union(
				cs.getClause1NegativeSymbols(), cs.getClause2NegativeSymbols()));
		if (positiveSymbols.contains(toRemove)) {
			positiveSymbols.remove(toRemove);
		}
		if (negativeSymbols.contains(toRemove)) {
			negativeSymbols.remove(toRemove);
		}*/
		List<Symbol> clause1Symbols = new ArrayList<Symbol>(cs.getClause1Symbols());
		List<Symbol> clause2Symbols = new ArrayList<Symbol>(cs.getClause2Symbols());
		
		clause1Symbols.remove(toRemove);
		clause2Symbols.remove(toRemove);

		/*
		 * Collections.sort(positiveSymbols, new SymbolComparator());
		 * Collections.sort(negativeSymbols, new SymbolComparator());
		 */

		List<Symbol> sentences = new ArrayList<Symbol>();
		/*for (int i = 0; i < positiveSymbols.size(); i++) {
			sentences.add(positiveSymbols.get(i));
		}
		for (int i = 0; i < negativeSymbols.size(); i++) {
			sentences.add(negativeSymbols.get(i));
		}*/
		for (int i = 0; i < clause1Symbols.size(); i++) {
			sentences.add(clause1Symbols.get(i));
		}
		for (int i = 0; i < clause2Symbols.size(); i++) {
			sentences.add(clause2Symbols.get(i));
		}
		CNFSentence cnf = new CNFSentence();
		if (sentences.size() == 0) {
			cnf.addSymbol(new Symbol("EMPTY_CLAUSE", true));
			return cnf; // == empty clause
		} else {
			for (Symbol s : sentences) {
				cnf.addSymbol(s);
			}
			return cnf;
		}

	}

	private Set<CNFSentence> filterOutClausesWithTwoComplementaryLiterals(
			Set<CNFSentence> clauses) {
		Set<CNFSentence> filtered = new LinkedHashSet<CNFSentence>();
		Iterator<CNFSentence> iter = clauses.iterator();
		while (iter.hasNext()) {
			CNFSentence clause = iter.next();
			Set<Symbol> positiveSymbols = clause.getPositiveSymbols();
			Set<Symbol> negativeSymbols = clause.getNegativeSymbols();
			if ((SetUtils.intersection(positiveSymbols, negativeSymbols).size() == 0)) {
				filtered.add(clause);
			}
		}
		return filtered;
	}

}
