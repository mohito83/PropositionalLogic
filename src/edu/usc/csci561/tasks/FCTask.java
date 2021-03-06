/**
 * 
 */
package edu.usc.csci561.tasks;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import edu.usc.csci561.HornClause;
import edu.usc.csci561.IEntailmentTask;

/**
 * @author mohit aggarwl
 * 
 */
public class FCTask extends IEntailmentTask {

	public FCTask(FileWriter logsWriter, FileWriter resultsWriter) {
		super(logsWriter, resultsWriter);
	}

	@Override
	public void processEntailment(List<HornClause> clauses, List<String> query) {
		StringBuffer buff = new StringBuffer();
		printLog("<Known/Deducted facts>#Rules Fires#NewlyEntailedFacts");
		printLog(System.getProperty("line.separator"));
		for (String q : query) {
			boolean result = plFCEntailment(clauses, q);
			buff.append(result ? "YES" : "NO");
			buff.append(System.getProperty("line.separator"));

			// for the output log files
			printLog("-------------------------------------------------------------");
			printLog(System.getProperty("line.separator"));
		}
		printResult(buff.toString());
	}

	private boolean plFCEntailment(List<HornClause> clauses, String q) {
		boolean isEntails = false;

		// initialize the local variables to be used in the forward chaining
		// algorithm
		Map<HornClause, Integer> count = new HashMap<HornClause, Integer>();
		Map<String, Boolean> inferred = new HashMap<String, Boolean>();
		Stack<String> agenda = new Stack<String>();
		List<String> known = new ArrayList<String>();
		initLocalVars(clauses, count, inferred, known);

		// populate the agenda
		Iterator<String> iterator = known.iterator();
		while (iterator.hasNext()) {
			agenda.push(iterator.next());
		}

		while (!agenda.isEmpty()) {
			String p = agenda.pop();
			if (inferred.containsKey(p)) {
				inferred.put(p, true);
				Iterator<HornClause> iter = count.keySet().iterator();
				while (iter.hasNext()) {
					HornClause c = iter.next();
					if (c.contains(p)) {
						Integer i = count.get(c);
						int v = i.intValue();
						v--;
						if (v > 0) {
							count.put(c, v);
						} else {
							// print the logs
							generateLog(known, c);
							known.add(c.getHead());
							if (c.getHead().equals(q)) {
								return true;
							} else {
								agenda.push(c.getHead());
							}
						}

					}
				}
			}
		}

		return isEntails;
	}

	private void generateLog(List<String> known, HornClause c) {
		Collections.sort(known);
		StringBuffer buff = new StringBuffer();
		int i = 0;
		for (String s : known) {
			i++;
			buff.append(s);
			if (i < known.size()) {
				buff.append(", ");
			}
		}
		buff.append("#");
		buff.append(c.getClauseInfo());
		buff.append(" # ");
		buff.append(c.getHead());
		buff.append(System.getProperty("line.separator"));
		printLog(buff.toString());
	}

	private void initLocalVars(List<HornClause> clauses,
			Map<HornClause, Integer> count, Map<String, Boolean> inferred,
			List<String> known) {
		Iterator<HornClause> iter = clauses.iterator();
		while (iter.hasNext()) {
			HornClause c = iter.next();
			if (c.noOfPremises() == 1 && c.getHead() == null) {
				known.add(c.getBody().get(0));
			} else {
				count.put(c, c.noOfPremises());
				List<String> premises = c.getBody();
				for (String s : premises) {
					inferred.put(s, false);
				}
			}
		}
	}

}
