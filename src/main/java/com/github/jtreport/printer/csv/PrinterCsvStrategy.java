package com.github.jtreport.printer.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collection;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.exception.DRException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.core.TestClassResult;
import com.github.jtreport.printer.core.AbstractPrinterStrategyTemaplate;

/**
 * 
 * @author Fabio Rubino.
 * 
 */
public class PrinterCsvStrategy extends AbstractPrinterStrategyTemaplate {

	private static final Logger L = LoggerFactory.getLogger(PrinterCsvStrategy.class);

	private static final String DEFAULT_TEMPLATE = "csv-template-resources/csvTemplate.csv";

	@Override
	protected void addColumn(final JasperReportBuilder report) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addTitle(final JasperReportBuilder report, final String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(final Collection<TestClassResult> globalTestResultList, final String dirOutPath) {
		for (TestClassResult globalTestResult : globalTestResultList) {

			final String testClassName = globalTestResult.getTestClassName();
			final int totalTestRun = globalTestResult.getTotalRun();
			final int totalTestFailure = globalTestResult.getTotalFailed();
			final int totalTestIgnored = globalTestResult.getTotalIgnored();
			final int totalTestPassed = globalTestResult.getTotalPassed();
			final int totalError = globalTestResult.getTotalError();

			final long runTime = globalTestResult.getRunningTime();

			final InputStream input = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_TEMPLATE);

			try {
				if (input != null) {

					final InputStreamReader reader = new InputStreamReader(input);

					final VelocityEngine ve = new VelocityEngine();

					ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
					ve.setProperty("class.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

					ve.init();

					final VelocityContext context = new VelocityContext();

					final String fileName = testClassName
							+ new DateTime().toString(ISODateTimeFormat.basicDateTimeNoMillis()) + ".csv";
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
					context.put("testList", globalTestResult.getTestMethodResultList());
					if (!ve.evaluate(context, writer, DEFAULT_TEMPLATE, reader)) {
						L.error("Failed to convert the template into csv.");
					} else {
						writer.flush();
						writer.close();
					}
				} else {
					L.error("Template [" + DEFAULT_TEMPLATE + "] is null");
				}
			} catch (final IOException e) {
				L.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void printReport(final JasperReportBuilder report, final OutputStream os) throws DRException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setBackground(final JasperReportBuilder report) {
		// TODO Auto-generated method stub

	}

	@Override
	public String setExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ReportTemplateBuilder setReportTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void printConcatenateReport(JasperConcatenatedReportBuilder report, OutputStream os) throws DRException {
		report.toCsv(os);
	}
}
