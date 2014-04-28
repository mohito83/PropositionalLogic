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

	public String toString() {
		return isPositive ? value : "-" + value;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Symbol sym = (Symbol) o;
		return (sym.getValue().equals(getValue()));

	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + value.hashCode();
		return result;
	}
}
