package com.github.jtreport.printer.xhtml;

import java.io.OutputStream;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.exception.DRException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.printer.core.AbstractPrinterStrategyTemaplate;
import com.github.jtreport.printer.core.Templates;
import com.github.jtreport.printer.png.PrinterPngStrategy;

/**
 * 
 * @author Fabio Rubino.
 * 
 */
public class PrinterXHtmlStrategy extends AbstractPrinterStrategyTemaplate {

	private static final Logger L = LoggerFactory.getLogger(PrinterPngStrategy.class);

	@Override
	protected void addColumn(final JasperReportBuilder report) {
	}

	@Override
	protected void addTitle(final JasperReportBuilder report, final String title) {
		report.addTitle(Templates.createTitleComponent(title, false));
	}

	@Override
	public void printReport(final JasperReportBuilder report, final OutputStream os) throws DRException {
		report.toXhtml(os);
	}

	@Override
	protected void setBackground(final JasperReportBuilder report) {
	}

	@Override
	public String setExtension() {
		return ".html";
	}

	@Override
	protected ReportTemplateBuilder setReportTemplate() {
		return Templates.reportTemplate;
	}

	@Override
	protected void printConcatenateReport(JasperConcatenatedReportBuilder report, OutputStream os) throws DRException {
		String msgError = "Not implemented yet!";
		L.error(msgError);
		throw new UnsupportedOperationException(msgError);
	}
}
