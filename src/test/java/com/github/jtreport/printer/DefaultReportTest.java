package com.github.jtreport.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.exception.DRException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.github.jtreport.Utility4Test;
import com.github.jtreport.annotation.TestClassReport;
import com.github.jtreport.annotation.TestSingleReport;
import com.github.jtreport.core.TestClassResult;
import com.github.jtreport.core.TestMethodResult;
import com.github.jtreport.printer.core.AbstractPrinterStrategyTemaplate;
import com.github.jtreport.printer.jrxml.PrinterJrxmlStrategy;
import com.github.jtreport.printer.ods.PrinterOdsStrategy;
import com.github.jtreport.printer.odt.PrinterOdtStrategy;
import com.github.jtreport.printer.pdf.PrinterPdfStrategy;
import com.github.jtreport.printer.png.PrinterPngStrategy;
import com.github.jtreport.printer.xhtml.PrinterXHtmlStrategy;

/**
 * 
 * @author Fabio Rubino.
 * 
 */
@RunWith(value = Parameterized.class)
@TestClassReport(description = "Test default type of printer (PDF, XML, ODT, ODS ...).")
public class DefaultReportTest {

	private static ArrayList<TestMethodResult> testMethodResultList;

	@Parameters
	public static Collection<Object[]> data() {
		final Object[][] data = new Object[][]{{new PrinterPdfStrategy()}, {new PrinterPngStrategy()},
				{new PrinterXHtmlStrategy()}, {new PrinterOdtStrategy()}, {new PrinterOdsStrategy()},
				{new PrinterJrxmlStrategy()}};

		return Arrays.asList(data);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final TestMethodResult tMr = Utility4Test.createTestMethodResult();
		testMethodResultList = new ArrayList<TestMethodResult>();
		testMethodResultList.add(tMr);
	}

	private final AbstractPrinterStrategyTemaplate concretePrinter;

	public DefaultReportTest(final AbstractPrinterStrategyTemaplate concretePrinter) {
		this.concretePrinter = concretePrinter;
	}

	@Test
	@TestSingleReport(description = "il primo esempio di test con desrizione")
	public void testReportCretion() throws DRException, IOException {
		final JasperReportBuilder report = DynamicReports.report();
		final TestClassResult testResult = new TestClassResult(this.getClass().getName(), testMethodResultList,
				"General test description", 0L, 1, 1, 0, 0, 0);
		final AbstractPrinterStrategyTemaplate printer = concretePrinter;

		final JasperReportBuilder createdReport = printer.createReport(testResult, report);
		Assert.assertNotNull(createdReport);
		final ByteArrayOutputStream baOs = new ByteArrayOutputStream();
		printer.printReport(report, baOs);
		Assert.assertTrue("Is full", baOs.size() > 0);
	}
}
