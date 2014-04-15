/**
 * 
 */
package edu.usc.csci561;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

	private static List<String> queryList;

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
		// TODO Auto-generated method stub

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
