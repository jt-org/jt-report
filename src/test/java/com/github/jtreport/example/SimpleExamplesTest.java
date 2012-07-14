package com.github.jtreport.example;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.jtreport.annotation.TestClassReport;
import com.github.jtreport.annotation.TestSingleReport;
import com.github.jtreport.core.JtreportRunner;
import com.github.jtreport.core.ReportSummary;
import com.github.jtreport.core.ReportTestRunner;
import com.github.jtreport.core.ReportTypePrinterEnum;

@RunWith(ReportTestRunner.class)
@TestClassReport(description = "Put here the description for this test Class.")
public class SimpleExamplesTest extends JtreportRunner {

	@Override
	public ReportSummary initalizeReport() {
		return new ReportSummary("/tmp/jtreportDir",
				new ArrayList<ReportTypePrinterEnum>() {

					private static final long serialVersionUID = 1L;

					{
						this.add(ReportTypePrinterEnum.PDF);
						this.add(ReportTypePrinterEnum.ODT);
						this.add(ReportTypePrinterEnum.ODS);
						this.add(ReportTypePrinterEnum.XML);
						this.add(ReportTypePrinterEnum.PNG);
						this.add(ReportTypePrinterEnum.XHTML);

					}
				});
	}

	@Ignore
	@Test
	@TestSingleReport(keyCustomReport = "Primary key for report", description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest1() {
	}

	@Ignore("Put here why this test is skipped")
	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest2() {
	}

	@Ignore("This comment was override in the report")
	@Test
	@TestSingleReport(description = "Put here the description for this test...", ignored = "Put here why this test is skipped. This comment go in the test report", expectations = "Put here the expectations test of the test...")
	public void simpleTest3() {
	}

	@Ignore
	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest4() {
		Assert.assertEquals("Are the same", 0, 1);
	}

	@Ignore
	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...", failed = "This ovverride the failed massage.")
	public void simpleTest5() {
		Assert.assertEquals("Are the same", 0, 1);
	}

	@Ignore
	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...")
	public void simpleTest6() {
		final Integer x = null;
		x.intValue();
	}

	@Ignore
	@Test
	@TestSingleReport(description = "Put here the description for this test...", expectations = "Put here the expectations test of the test...", error = "This ovverride the error massage.")
	public void simpleTest7() {
		final Integer x = null;
		x.intValue();
	}
}
