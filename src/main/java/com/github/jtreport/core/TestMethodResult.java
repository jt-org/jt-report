package com.github.jtreport.core;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.runner.Description;

/**
 * Class that represents the result of the single test.
 * 
 * @author Fabio Rubino.
 * 
 */
public class TestMethodResult {

	private final Description descriptionResult;
	private final String testDescription;
	private final DateTime executionDate;
	private StateTestEnum testState;
	private String resultDescription;
	private final String testClassName;
	private final String testMethodName;
	private final String testExpectations;
	private String testKey;
	private final double runningTime;

	/**
	 * Default Constructor
	 * 
	 * @param descriptionResult
	 *            {@link Description} of this test.
	 * @param testClassName
	 *            test class name.
	 * @param testMethodName
	 *            test method name.
	 * @param testDescription
	 *            description of the test.
	 * @param testExpectations
	 *            expectations for this test.
	 * @param executionDate
	 *            test execution time Date.
	 * @param testState
	 *            steate of the test.
	 * @param resultDescription
	 *            description result of this test.
	 * @param runningTime
	 *            test execution time.
	 * @param testKey
	 *            use test with key for velocity type of report.
	 */
	public TestMethodResult(final Description descriptionResult, final String testClassName,
			final String testMethodName, final String testDescription, final String testExpectations,
			final DateTime executionDate, final StateTestEnum testState, final String resultDescription,
			final double runningTime, final String testKey) {
		this.testDescription = testDescription;
		this.executionDate = executionDate;
		this.testState = testState;
		this.resultDescription = resultDescription;
		this.testClassName = testClassName;
		this.testMethodName = testMethodName;
		this.descriptionResult = descriptionResult;
		this.runningTime = runningTime;
		this.testKey = testKey;
		this.testExpectations = testExpectations;

	}

	public Description getDescriptionResult() {
		return descriptionResult;
	}

	public DateTime getExecutionDate() {
		return executionDate;
	}

	public String getFormattdDate() {
		return executionDate.toString((ISODateTimeFormat.basicDateTimeNoMillis()));
	}

	public String getResultDescription() {
		return resultDescription;
	}

	public double getRunningTime() {
		return runningTime;
	}

	public String getTestClassName() {
		return testClassName;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public String getTestExpectations() {
		return testExpectations;
	}

	public String getTestKey() {
		return testKey;
	}

	public String getTestMethodName() {
		return testMethodName;
	}

	public StateTestEnum getTestState() {
		return testState;
	}

	public void setResultDescription(final String resultDescription) {
		this.resultDescription = resultDescription;
	}

	public void setTestKey(final String testKey) {
		this.testKey = testKey;
	}

	public void setTestState(final StateTestEnum testState) {
		this.testState = testState;
	}

}
