package com.github.jtreport.core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class that represents the result of all tests performed.
 * 
 * @author Fabio Rubino.
 * 
 */
public class TestClassResult {

	private Collection<TestMethodResult> testMethodResultList = new ArrayList<TestMethodResult>();
	private final String descriptionSummary;
	private long runningTime = 0L;
	private int totalRun = 0;
	private int totalPassed = 0;
	private int totalFailed = 0;
	private int totalError = 0;
	private int totalIgnored = 0;
	private final String testClassName;

	/**
	 * Default Constructor
	 * 
	 * @param testClassName
	 *            test class name.
	 * @param testMethodResultList
	 *            result list of the test in this class.
	 * @param descriptionSummary
	 *            general description for this test.
	 * @param runningTime
	 *            total running time.
	 * @param totalRun
	 *            total test executed.
	 * @param totalPassed
	 *            total test passed.
	 * @param totalFailed
	 *            total test faild.
	 * @param totalError
	 *            total test in error.
	 * @param totalIgnored
	 *            total test ignored.
	 */
	public TestClassResult(final String testClassName, final Collection<TestMethodResult> testMethodResultList,
			final String descriptionSummary, final long runningTime, final int totalRun, final int totalPassed,
			final int totalFailed, final int totalError, final int totalIgnored) {
		this.testMethodResultList = testMethodResultList;
		this.descriptionSummary = descriptionSummary;
		this.runningTime = runningTime;
		this.totalRun = totalRun;
		this.totalPassed = totalPassed;
		this.totalFailed = totalFailed;
		this.totalError = totalError;
		this.totalIgnored = totalIgnored;
		this.testClassName = testClassName;
	}

	public TestClassResult(final String testClassName, final String descriptionSummary) {
		this.descriptionSummary = descriptionSummary;
		this.testClassName = testClassName;
	}

	public void addRunningTime(final long time) {
		runningTime += time;
	}

	public void addTestError() {
		totalError++;
	}

	public void addTestFailed() {
		totalFailed++;
	}

	public void addTestIgnored() {
		totalIgnored++;
	}

	public void addTestMethodResult(final TestMethodResult testMethodResult) {
		if (testMethodResult != null) {
			testMethodResultList.add(testMethodResult);
		}
	}

	public void addTestPassed() {
		totalPassed++;
	}

	public void addTestRun() {
		totalRun++;
	}

	public String getDescriptionSummary() {
		return descriptionSummary;
	}

	public long getRunningTime() {
		return runningTime;
	}

	public String getTestClassName() {
		return testClassName;
	}

	public Collection<TestMethodResult> getTestMethodResultList() {
		return testMethodResultList;
	}

	public int getTotalError() {
		return totalError;
	}

	public int getTotalFailed() {
		return totalFailed;
	}

	public int getTotalIgnored() {
		return totalIgnored;
	}

	public int getTotalPassed() {
		return totalPassed;
	}

	public int getTotalRun() {
		return totalRun;
	}
}
