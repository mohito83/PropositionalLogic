/**
 * 
 */
package edu.usc.csci561.tasks;

import java.io.FileWriter;
import java.util.List;

import edu.usc.csci561.HornClause;
import edu.usc.csci561.IEntailmentTask;

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
			boolean result = plResolution(clauses, q);
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
	private boolean plResolution(List<HornClause> clauses, String q) {

		return false;
	}

}
