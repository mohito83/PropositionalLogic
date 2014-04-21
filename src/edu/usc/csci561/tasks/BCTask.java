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
public class BCTask extends IEntailmentTask {

	public BCTask(FileWriter logsWriter, FileWriter resultsWriter) {
		super(logsWriter, resultsWriter);
	}

	@Override
	public void processEntailment(List<HornClause> clauses, List<String> query) {
		// TODO Auto-generated method stub

	}

}
