package com.github.jtreport.printer.velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.core.TestClassResult;
import com.github.jtreport.core.TestMethodResult;

/**
 * 
 * @author Fabio Rubino.
 * 
 */
public class PrintVelocityResult {

	private static final Logger L = LoggerFactory.getLogger(PrintVelocityResult.class);

	public void print(final Collection<TestClassResult> globalTestResultList, final String vtemplate,
			final String dirOutPath, final boolean useKey) throws IOException {
		for (TestClassResult globalTestResult : globalTestResultList) {

			final String testClassName = globalTestResult.getTestClassName();
			final int totalTestRun = globalTestResult.getTotalRun();
			final int totalTestFailure = globalTestResult.getTotalFailed();
			final int totalTestIgnored = globalTestResult.getTotalIgnored();
			final int totalTestPassed = globalTestResult.getTotalPassed();
			final int totalError = globalTestResult.getTotalError();

			final long runTime = globalTestResult.getRunningTime();

			final InputStream input = new FileInputStream(vtemplate);

			if (input != null) {

				final InputStreamReader reader = new InputStreamReader(input);

				final VelocityEngine ve = new VelocityEngine();

				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
				ve.setProperty("class.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

				ve.init();

				final VelocityContext context = new VelocityContext();

				final String fileName = testClassName
						+ new DateTime().toString(ISODateTimeFormat.basicDateTimeNoMillis()) + ".vm";
				final String pathname = dirOutPath + fileName;
				final File dirs = new File(dirOutPath);
				if (!dirs.exists()) {
					dirs.mkdirs();
				}

				final File file = new File(pathname);
				if (!file.exists()) {
					file.createNewFile();
				}
				final BufferedWriter writer = new BufferedWriter(new FileWriter(file));

				context.put("testClassName", testClassName);
				context.put("testDate", new DateTime().toString());
				context.put("totalDuration", runTime + "");
				context.put("totalRun", totalTestRun);
				context.put("totalPassed", totalTestPassed);
				context.put("totalFail", totalTestFailure);
				context.put("totalError", totalError);
				context.put("totalIgnore", totalTestIgnored);
				context.put("summaryDescription", globalTestResult.getDescriptionSummary());
				context.put("StringEscapeUtils", StringEscapeUtils.class);

				final Collection<TestMethodResult> testMethodResultList = globalTestResult.getTestMethodResultList();
				if (useKey) {
					for (final TestMethodResult testMethodResult : testMethodResultList) {
						final String testKey = testMethodResult.getTestKey();
						if (StringUtils.isNotBlank(testKey)) {
							context.put(testKey + "_descriptionComment", testMethodResult.getTestDescription());
							context.put(testKey + "_descriptionResult", testMethodResult.getDescriptionResult());
							context.put(testKey + "_resultDescription", testMethodResult.getResultDescription());
							context.put(testKey + "_testClassName", testMethodResult.getTestClassName());
							context.put(testKey + "_testDisplayName", testMethodResult.getDescriptionResult()
									.getDisplayName());
							context.put(testKey + "_testMethodName", testMethodResult.getTestMethodName());
							context.put(testKey + "_testState", testMethodResult.getTestState().name());
							context.put(testKey + "_formattdDate", testMethodResult.getFormattdDate());
						}
					}
				} else {
					context.put("testList", testMethodResultList);
				}
				if (!ve.evaluate(context, writer, vtemplate, reader)) {
					L.error("Failed to convert the template.");
				} else {
					writer.flush();
					writer.close();
				}
			}
		}
	}
}
