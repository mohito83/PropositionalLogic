/**
 * 
 */
package edu.usc.csci561.tasks;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
			boolean result = plBCEntailment(clauses, q, null);
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
	private boolean plBCEntailment(List<HornClause> clauses, String original,
			String q) {
		if (q == null) {
			q = original;
		} else {
			if (original.equals(q)) {
				StringBuffer buff = new StringBuffer();
				buff.append(q);
				buff.append(" # CYCLE DETECTED # N/A");
				buff.append(System.getProperty("line.separator"));
				printLog(buff.toString());
				return false;
			}
		}

		boolean result = false;
		List<HornClause> matchingClauses = getClausesforHead(clauses, q);
		if (matchingClauses.size() == 0) {
			StringBuffer buff = new StringBuffer();
			buff.append(q);
			buff.append(" # N/A # N/A");
			buff.append(System.getProperty("line.separator"));
			printLog(buff.toString());
			return false;
		}

		for (HornClause c : matchingClauses) {
			List<String> pList = c.getBody();
			if (pList.size() == 1 && pList.get(0).equals(q)) {
				StringBuffer buff = new StringBuffer();
				buff.append(pList.get(0));
				buff.append(" # ");
				buff.append(pList.get(0));
				buff.append(" # N/A");
				buff.append(System.getProperty("line.separator"));
				printLog(buff.toString());
				result = result | true;
			} else {
				StringBuffer buff = new StringBuffer();
				buff.append(q);
				buff.append(" # ");
				buff.append(c.getClauseInfo());
				buff.append(" # ");
				int j = 0;
				for (String s : pList) {
					j++;
					buff.append(s);
					if (j < pList.size()) {
						buff.append(", ");
					}
				}
				buff.append(System.getProperty("line.separator"));
				printLog(buff.toString());
				for (String s : pList) {
					boolean x = plBCEntailment(clauses, original, s);
					if (!x) {
						break;
					}else{
						result = result | x;
					}
				}
			}
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
					c = new ArrayList<HornClause>();
					c.add(x);
					return c;
				}
			}
		}

		return c;
	}

}

class HornClauseWrapper {
	private HornClause clause;
	private String premise;

	public HornClauseWrapper() {
		super();
	}

	public HornClauseWrapper(HornClause c, String p) {
		this.clause = c;
		this.premise = p;
	}

	/**
	 * @return the clause
	 */
	public HornClause getClause() {
		return clause;
	}

	/**
	 * @return the premise
	 */
	public String getPremise() {
		return premise;
	}

}
