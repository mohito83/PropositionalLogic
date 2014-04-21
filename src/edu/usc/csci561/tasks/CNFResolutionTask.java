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
		// TODO Auto-generated method stub

	}

}
