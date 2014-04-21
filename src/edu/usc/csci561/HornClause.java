/**
 * 
 */
package edu.usc.csci561;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines methods and member variables which represents the Horn
 * Clause
 * 
 * @author mohit aggarwl
 * 
 */
public class HornClause {

	private List<String> body;
	private String head;

	public HornClause() {
		super();
		body = new ArrayList<String>();
		head = null;
	}

	public void addToBody(String symbol) {
		body.add(symbol);
	}

	public void addHead(String head) {
		this.head = head;
	}

	public String getClauseInfo() {
		StringBuffer buff = new StringBuffer();
		if (head != null) {
			buff.append(head);
			buff.append(" :- ");
		}
		int i = 0;
		for (String s : body) {
			i++;
			buff.append(s);
			if (i != body.size())
				buff.append(",");
		}
		return buff.toString();
	}

	public int noOfPremises() {
		return body.size();
	}
}
