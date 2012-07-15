package com.github.jtreport.printer.core;

import static net.sf.dynamicreports.report.builder.DynamicReports.concatenatedReport;
import static net.sf.dynamicreports.report.builder.DynamicReports.field;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.exception.DRException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.core.StateTestEnum;
import com.github.jtreport.core.TestClassResult;
import com.github.jtreport.datasource.DataSourceUtils;

/**
 * Abstract printer strategy class to be extended.
 * 
 * @author Fabio Rubino.
 * 
 */
public abstract class AbstractPrinterStrategyTemaplate implements
		IPrinterStrategy {

	// private class CustomTableOfContentsCustomizer extends
	// TableOfContentsCustomizer {
	//
	// private static final long serialVersionUID = 1L;
	//
	// @Override
	// protected ComponentBuilder<?, ?> headingComponent(final int level) {
	// if (level == 0) {
	// final VerticalListBuilder verticalList = cmp.verticalList();
	// verticalList.add(super.headingComponent(level));
	// verticalList.add(cmp.line());
	// return verticalList;
	// } else {
	// return super.headingComponent(level);
	// }
	// }
	//
	// @Override
	// protected ComponentBuilder<?, ?> title() {
	// final VerticalListBuilder verticalList = cmp.verticalList();
	// verticalList.add(cmp.line());
	// verticalList.add(super.title());
	// verticalList.add(cmp.line());
	// return verticalList;
	// }
	// }

	private boolean margeReport = false;

	private static final Logger L = LoggerFactory
			.getLogger(AbstractPrinterStrategyTemaplate.class);

	protected abstract void addColumn(JasperReportBuilder report);

	protected abstract void addTitle(JasperReportBuilder report, String title);

	public JasperReportBuilder createReport(final TestClassResult testResult,
			final JasperReportBuilder report) {
		final Map<StateTestEnum, String> resultTestMap = new HashMap<StateTestEnum, String>();
		resultTestMap.put(StateTestEnum.PASSED, testResult.getTotalPassed()
				+ "");
		resultTestMap.put(StateTestEnum.ERROR, testResult.getTotalError() + "");
		resultTestMap.put(StateTestEnum.FAILED, testResult.getTotalFailed()
				+ "");
		resultTestMap.put(StateTestEnum.IGNORED, testResult.getTotalIgnored()
				+ "");
		report.addTitle(Templates.createTitleComponent(
				testResult.getTestClassName(), false));
		report.title(Templates.createSubTitleComponent(testResult
				.getDescriptionSummary()));
		report.title(Templates.createSubTitleTestResult(resultTestMap,
				testResult.getTotalRun(), ""));
		report.fields(field("state", String.class));
		report.setTemplate(this.setReportTemplate());
		this.addColumn(report);
		report.addColumn(Columns.column("Test name", "testName",
				DataTypes.stringType()));
		report.addColumn(Columns.column("Test description", "testDescription",
				DataTypes.stringType()));
		report.addColumn(Columns.column("Expectations: ", "expectations",
				DataTypes.stringType()));
		report.addColumn(Columns.column("Result: ", "resultDescription",
				DataTypes.stringType()));
		// report.addColumn(Columns.column("Status Comment", "statusComment",
		// DataTypes.stringType()));
		report.addColumn(Columns.column("Running Time:", "runningTime",
				DataTypes.stringType()));
		report.addColumn(Columns.column("Date:", "formattedDate",
				DataTypes.stringType()));
		report.setDataSource(DataSourceUtils.createDataSource(testResult
				.getTestMethodResultList()));
		report.highlightDetailEvenRows();
		report.addPageFooter(Components.pageNumber());

		return report;
	}

	// private CustomTableOfContentsCustomizer createTableOfContent() {
	// final StyleBuilder titleTocStyle =
	// stl.style().setForegroundColor(Color.BLUE).setFontSize(18).bold()
	// .setHorizontalAlignment(HorizontalAlignment.CENTER);
	// final StyleBuilder headingToc0Style =
	// stl.style(Templates.rootStyle).setFontSize(12).bold();
	// final StyleBuilder headingToc1Style =
	// stl.style(Templates.rootStyle).italic();
	//
	// final CustomTableOfContentsCustomizer tableOfContentsCustomizer = new
	// CustomTableOfContentsCustomizer();
	// tableOfContentsCustomizer.setTitleStyle(titleTocStyle);
	// tableOfContentsCustomizer.setHeadingStyle(0, headingToc0Style);
	// tableOfContentsCustomizer.setHeadingStyle(1, headingToc1Style);
	// tableOfContentsCustomizer.setTextFixedWidth(100);
	// tableOfContentsCustomizer.setPageIndexFixedWidth(30);
	// return tableOfContentsCustomizer;
	// }

	public boolean isMargeReport() {
		return this.margeReport;
	}

	@Override
	public void print(final Collection<TestClassResult> globalTestResults,
			final String dirOutPath) {
		L.info("Number of TestClassReport [" + globalTestResults.size() + "]");

		if (this.isMargeReport() && (globalTestResults != null)
				&& StringUtils.isNotBlank(dirOutPath)) {
			// StringUtils.endsWithAny(dirOutPath, new String[] { "/", "\\" });
			this.printMarge(globalTestResults, dirOutPath);
		} else if ((globalTestResults != null)
				&& StringUtils.isNotBlank(dirOutPath)) {
			this.printNoMarge(globalTestResults, dirOutPath);
		} else {
			L.warn("Not print the report because globalTestResults ["
					+ globalTestResults + "], dirOutPath [" + dirOutPath + "]");
		}
	}

	protected abstract void printConcatenateReport(
			JasperConcatenatedReportBuilder report, OutputStream os)
			throws DRException;

	private void printMarge(
			final Collection<TestClassResult> globalTestResults,
			final String dirOutPath) {

		String filename = "Jtreport"
				+ new DateTime().toString(ISODateTimeFormat
						.basicDateTimeNoMillis()) + "_"
				+ new Random().nextInt(1000) + this.setExtension();
		try {
			final File dirs = new File(dirOutPath);
			if (!dirs.exists()) {
				dirs.mkdirs();
			}
			filename = dirs.getPath() + "/" + filename;
			L.debug("Report saved in [" + filename + "]");

			final File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			final FileOutputStream fOs = new FileOutputStream(file);

			final JasperReportBuilder report = DynamicReports.report();
			this.setBackground(report);

			final ArrayList<TestClassResult> testResultList = new ArrayList<TestClassResult>(
					globalTestResults);
			final TestClassResult firstClassTest = testResultList.get(0);

			// final String testClassName = firstClassTest.getTestClassName();
			this.addTitle(report, "");// FIXME

			final Collection<JasperReportBuilder> reportList = new ArrayList<JasperReportBuilder>();
			final int testResultLitSize = testResultList.size();
			if (testResultLitSize > 1) {
				final JasperReportBuilder subReport = this.createReport(
						firstClassTest, report);
				reportList.add(subReport);
				testResultList.remove(0);
			}
			for (final TestClassResult globalTestResult : testResultList) {
				final JasperReportBuilder subReport = this.createReport(
						globalTestResult, DynamicReports.report());
				reportList.add(subReport);
			}

			final JasperConcatenatedReportBuilder concatenateReport = concatenatedReport()
					.continuousPageNumbering().concatenate(
							reportList
									.toArray(new JasperReportBuilder[reportList
											.size()]));
			// .toPdf(Exporters.pdfExporter("c:/report.pdf"));
			this.printConcatenateReport(concatenateReport, fOs);

		} catch (final DRException e) {
			L.error("Error during the report creation.", e);
		} catch (final FileNotFoundException e) {
			L.error("File not found [" + dirOutPath + "]", e);
		} catch (final IOException e) {
			L.error("IOException during the report creation.", e);
		}

	}

	private void printNoMarge(
			final Collection<TestClassResult> globalTestResults,
			final String dirOutPath) {

		for (final TestClassResult globalTestResult : globalTestResults) {
			try {

				final String testClassName = globalTestResult
						.getTestClassName();
				String filename = testClassName
						+ new DateTime().toString(ISODateTimeFormat
								.basicDateTimeNoMillis()) + "_"
						+ new Random().nextInt(1000) + this.setExtension();

				final File dirs = new File(dirOutPath);
				if (!dirs.exists()) {
					dirs.mkdirs();
				}
				filename = dirs.getPath() + "/" + filename;
				L.debug("Report saved in [" + filename + "]");

				final File file = new File(filename);
				if (!file.exists()) {
					file.createNewFile();
				}
				final FileOutputStream fOs = new FileOutputStream(file);

				// ImageBuilder background1 = Components.image(
				// this.getClass().getClassLoader().getSystemResourceAsStream("img/background.png")).setImageScale(
				// ImageScale.FILL);

				final JasperReportBuilder report = DynamicReports.report();
				this.setBackground(report);
				// FIXME
				this.addTitle(report, "");
				this.createReport(globalTestResult, report);
				this.printReport(report, fOs);
			} catch (final DRException e) {
				L.error("Error during the report creation.", e);
			} catch (final FileNotFoundException e) {
				L.error("File not found [" + dirOutPath + "]", e);
			} catch (final IOException e) {
				L.error("IOException during the report creation.", e);
			}
		}
	}

	public abstract void printReport(JasperReportBuilder report, OutputStream os)
			throws DRException;

	protected abstract void setBackground(JasperReportBuilder report);

	protected abstract String setExtension();

	public void setMargeReport(final boolean margeReport) {
		this.margeReport = margeReport;
	}

	protected abstract ReportTemplateBuilder setReportTemplate();
}
