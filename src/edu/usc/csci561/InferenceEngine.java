/**
 * 
 */
package edu.usc.csci561;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mohit aggarwl
 * 
 */
public class InferenceEngine {

	private static int task;
	private static String kbInputFile;
	private static String queryInputFile;
	private static String outputEntail;
	private static String outputLog;

	/**
	 * List of symbols to be inferred from the KB
	 */
	private static List<String> queryList = new ArrayList<String>();
	/**
	 * List of horn clauses
	 */
	private static List<HornClause> clauseList = new ArrayList<HornClause>();

	public static void main(String[] args) {

		parseCommanLineArguements(args);

		parseKbInputFile();

		parseQueryInputFile();
	}

	/**
	 * This method populates the
	 */
	private static void parseQueryInputFile() {
		File f = new File(queryInputFile);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String str = null;
			while ((str = reader.readLine()) != null) {
				queryList.add(str);
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private static void parseKbInputFile() {
		File f = new File(kbInputFile);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String str = null;
			while ((str = reader.readLine()) != null) {
				String[] arr = str.split(" :- ");
				HornClause clause = new HornClause();
				if (arr.length == 1) {
					clause.addToBody(arr[0]);
				} else if (arr.length > 1) {
					clause.addHead(arr[0]);
					String[] symbols = arr[1].split(",");
					for (String s : symbols) {
						clause.addToBody(s);
					}
				}
				clauseList.add(clause);
			}
		} catch (IOException e) {
			System.out
					.println("Exception occurred while parsing the Map file- "
							+ e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.out
							.println("Exception occurred while closing the Map file- "
									+ e.getMessage());
				}
			}
		}

	}

	/**
	 * This method will parse the command line arguments and populate
	 * corresponding place holders.
	 * 
	 * @param args
	 */
	private static void parseCommanLineArguements(String[] args) {

		int i = 0;
		String tmp = null;
		while (i < args.length && args[i].startsWith("-")) {
			tmp = args[i++];

			if (tmp.equals("-t")) {
				try {
					task = Integer.parseInt(args[i++]);
				} catch (NumberFormatException e) {
					System.out.println("Invalid command line argument!! - ("
							+ e.getMessage() + ")");
				}
			} else if (tmp.equals("-kb")) {
				kbInputFile = args[i++];
			} else if (tmp.equals("-q")) {
				queryInputFile = args[i++];
			} else if (tmp.equals("-oe")) {
				outputEntail = args[i++];
			} else if (tmp.equals("-ol")) {
				outputLog = args[i++];
			}
		}

		System.out.println("Test command line parsing logic\n");
		System.out.println("task=" + task + "\t kbInputFile=" + kbInputFile
				+ "\t queryInputFile=" + queryInputFile + "\t outputEntail="
				+ outputEntail + "\t outputLogfile=" + outputLog);

	}
}
