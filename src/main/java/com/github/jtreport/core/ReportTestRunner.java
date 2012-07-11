package com.github.jtreport.core;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JUnit Runner for test report.
 * 
 * @author Fabio Rubino.
 * 
 */
public class ReportTestRunner extends BlockJUnit4ClassRunner {

	private static final Logger L = LoggerFactory
			.getLogger(ReportTestRunner.class);

	public ReportTestRunner(final Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	public void run(final RunNotifier notifier) {
		notifier.addListener(new DefaultReportTestListner());
		L.debug("Notifyer, added new ReportTestListner");
		super.run(notifier);

	}

}
