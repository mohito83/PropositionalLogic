/**
 * 
 */
package edu.usc.csci561.tasks;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import edu.usc.csci561.HornClause;
import edu.usc.csci561.IEntailmentTask;

/**
 * @author mohit aggarwl
 * 
 */
public class BCTask extends IEntailmentTask {


	public BCTask(FileWriter logsWriter, FileWriter resultsWriter) {
		super(logsWriter, resultsWriter);
	}

	@Override
	public void processEntailment(List<HornClause> clauses, List<String> query) {
		StringBuffer buff = new StringBuffer();
		printLog("<Queue of Goals>#Relevant Rules/Fact#New Goal Introduced");
		printLog(System.getProperty("line.separator"));
		for (String q : query) {
			boolean result = plBCEntailment(clauses, q);
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
	private boolean plBCEntailment(List<HornClause> clauses, String q) {
		boolean result = true;
		Stack<String> agenda = new Stack<String>();
		agenda.push(q);

		int count = 0;
		while (!agenda.isEmpty()) {
			StringBuffer buff = new StringBuffer();
			String p = agenda.pop();

			if (count > 0 && p.equals(q)) {
				buff.append(q);
				buff.append(" # CYCLE DETECTED # N/A");
				buff.append(System.getProperty("line.separator"));
				printLog(buff.toString());
				continue;
			}

			List<HornClause> matchingClauseList = getClausesforHead(clauses, p);

			if (matchingClauseList.size() == 0) {
				buff.append(p);
				buff.append(" # N/A # N/A");
				buff.append(System.getProperty("line.separator"));
				printLog(buff.toString());
				result = false;
			} else {
				 for (int k = matchingClauseList.size() - 1; k >= 0; k--) {
				 HornClause c = matchingClauseList.get(k);
				//for (HornClause c : matchingClauseList) {
					buff = new StringBuffer();
					if (c.getHead() != null) {
						List<String> body = c.getBody();
						for (int i = body.size() - 1; i >= 0; i--) {
							agenda.push(body.get(i));
						}
						 //if (k == 0) {
						buff.append(p);
						buff.append(" # ");
						buff.append(c.getClauseInfo());
						buff.append(" # ");
						int j = 0;
						for (String s : body) {
							j++;
							buff.append(s);
							if (j < body.size()) {
								buff.append(", ");
							}
						}
						buff.append(System.getProperty("line.separator"));
						printLog(buff.toString());
						 //}
					} else {
						buff.append(p);
						buff.append(" # ");
						buff.append(p);
						buff.append(" # N/A");
						buff.append(System.getProperty("line.separator"));
						printLog(buff.toString());
						continue;
					}
				}
			}
			count++;
		}

		return result;
	}

	private List<HornClause> getClausesforHead(List<HornClause> clauses,
			String q) {
		List<HornClause> c = new ArrayList<HornClause>();
		Iterator<HornClause> iter = clauses.iterator();
		while (iter.hasNext()) {
			HornClause x = iter.next();
			if (x.getHead() != null) {
				if (x.getHead().equals(q)) {
					c.add(x);
				}
			} else {
				if (x.getBody().contains(q)) {
					c.add(x);
				}
			}
		}

		return c;
	}

}
