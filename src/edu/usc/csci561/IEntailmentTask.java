/**
 * 
 */
package edu.usc.csci561;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author mohit aggarwl
 * 
 */
public abstract class IEntailmentTask {

	private FileWriter logsWriter;
	private FileWriter resultsWriter;

	public IEntailmentTask(FileWriter logsWriter, FileWriter resultsWriter) {
		this.logsWriter = logsWriter;
		this.resultsWriter = resultsWriter;
	}

	public abstract void processEntailment(List<HornClause> clauses,
			List<String> query);

	protected void printLog(String msg) {
		try {
			logsWriter.write(msg);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	protected void printResult(String msg) {
		try {
			resultsWriter.write(msg);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
