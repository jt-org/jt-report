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

	public TestClassResult(final String testClassName,
			final Collection<TestMethodResult> testMethodResultList,
			final String descriptionSummary, final long runningTime,
			final int totalRun, final int totalPassed, final int totalFailed,
			final int totalError, final int totalIgnored) {
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

	public TestClassResult(final String testClassName,
			final String descriptionSummary) {
		this.descriptionSummary = descriptionSummary;
		this.testClassName = testClassName;
	}

	public void addRunningTime(final long time) {
		this.runningTime += time;
	}

	public void addTestError() {
		this.totalError++;
	}

	public void addTestFailed() {
		this.totalFailed++;
	}

	public void addTestIgnored() {
		this.totalIgnored++;
	}

	public void addTestMethodResult(final TestMethodResult testMethodResult) {
		if (testMethodResult != null) {
			this.testMethodResultList.add(testMethodResult);
		}
	}

	public void addTestPassed() {
		this.totalPassed++;
	}

	public void addTestRun() {
		this.totalRun++;
	}

	public String getDescriptionSummary() {
		return this.descriptionSummary;
	}

	public long getRunningTime() {
		return this.runningTime;
	}

	public String getTestClassName() {
		return this.testClassName;
	}

	public Collection<TestMethodResult> getTestMethodResultList() {
		return this.testMethodResultList;
	}

	public int getTotalError() {
		return this.totalError;
	}

	public int getTotalFailed() {
		return this.totalFailed;
	}

	public int getTotalIgnored() {
		return this.totalIgnored;
	}

	public int getTotalPassed() {
		return this.totalPassed;
	}

	public int getTotalRun() {
		return this.totalRun;
	}
}