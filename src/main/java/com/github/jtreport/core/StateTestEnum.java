package com.github.jtreport.core;

/**
 * State for test result <li>PASSED</li> <li>FAILED</li> <li>ERROR</li> <li>
 * IGNORED</li>
 * 
 * @author Fabio Rubino.
 * 
 */
public enum StateTestEnum {
	/**
	 * Test passed.
	 */
	PASSED,
	/**
	 * Test failed.
	 */
	FAILED,
	/**
	 * Test error.
	 */
	ERROR,
	/**
	 * Test ignored.
	 */
	IGNORED;

}
