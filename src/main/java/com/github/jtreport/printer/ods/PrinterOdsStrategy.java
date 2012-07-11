package com.github.jtreport.printer.ods;

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
public class PrinterOdsStrategy extends AbstractPrinterStrategyTemaplate {

	public static void main(final String[] args) {
		new PrinterOdsStrategy();
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
		report.toOds(os);
	}

	@Override
	protected void setBackground(final JasperReportBuilder report) {
	}

	@Override
	public String setExtension() {
		return ".ods";
	}

	@Override
	protected ReportTemplateBuilder setReportTemplate() {
		return Templates.reportTemplate;
	}

	@Override
	protected void printConcatenateReport(JasperConcatenatedReportBuilder report, OutputStream os) throws DRException {
		report.toOds(os);
	}
}
