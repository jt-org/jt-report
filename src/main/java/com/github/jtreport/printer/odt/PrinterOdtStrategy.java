package com.github.jtreport.printer.odt;

import java.io.OutputStream;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.exception.DRException;

import com.github.jtreport.printer.core.AbstractPrinterStrategyTemaplate;
import com.github.jtreport.printer.core.Templates;

/**
 * 
 * @author Fabio Rubino.
 * 
 */
public class PrinterOdtStrategy extends AbstractPrinterStrategyTemaplate {

	public static void main(final String[] args) {
		new PrinterOdtStrategy();
	}

	@Override
	protected void addColumn(final JasperReportBuilder report) {
	}

	@Override
	protected void addTitle(final JasperReportBuilder report, final String title) {
		report.addTitle(Templates.createTitleComponent(title, false));
	}

	@Override
	public void printReport(final JasperReportBuilder report, final OutputStream os) throws DRException {
		report.toOdt(os);
	}

	@Override
	protected void setBackground(final JasperReportBuilder report) {
	}

	@Override
	public String setExtension() {
		return ".odt";
	}

	@Override
	protected ReportTemplateBuilder setReportTemplate() {
		return Templates.reportTemplate;
	}

	@Override
	protected void printConcatenateReport(JasperConcatenatedReportBuilder report, OutputStream os) throws DRException {
		report.toOdt(os);
	}
}
