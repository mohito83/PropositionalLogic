/**
 * 
 */
package edu.usc.csci561.data;

/**
 * This class defines the Symbol to be used in the CNF sentences
 * 
 * @author mohit aggarwl
 * 
 */
public class Symbol {

	private String value;
	private boolean isPositive;

	public Symbol(String value, boolean b) {
		this.value = value;
		this.isPositive = b;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the isNegative
	 */
	public boolean isPositive() {
		return isPositive;
	}

	/**
	 * @param isNegative
	 *            the isNegative to set
	 */
	public void setPositive(boolean isPositive) {
		this.isPositive = isPositive;
	}

}
