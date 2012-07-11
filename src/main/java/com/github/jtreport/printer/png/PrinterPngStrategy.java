package com.github.jtreport.printer.png;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.constant.ImageType;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.component.ImageBuilder;
import net.sf.dynamicreports.report.constant.ImageScale;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jtreport.printer.core.AbstractPrinterStrategyTemaplate;
import com.github.jtreport.printer.core.Templates;

/**
 * 
 * @author Fabio Rubino.
 * 
 */
public class PrinterPngStrategy extends AbstractPrinterStrategyTemaplate {

	private static final Logger L = LoggerFactory.getLogger(PrinterPngStrategy.class);

	private class ImageExpression extends AbstractSimpleExpression<InputStream> {

		private static final long serialVersionUID = 1L;

		@Override
		public InputStream evaluate(final ReportParameters reportParameters) {
			final String value = reportParameters.getValue("state");
			final InputStream resourceAsStream = PrinterPngStrategy.class.getClassLoader().getResourceAsStream(
					"img/" + value + ".png");
			return resourceAsStream;
		}
	}

	public static void main(final String[] args) {
		new PrinterPngStrategy();
	}

	@Override
	protected void addColumn(final JasperReportBuilder report) {
		final ImageBuilder image = Components.image(new ImageExpression()).setImageScale(ImageScale.NO_RESIZE);
		report.addColumn(Columns.componentColumn("State", image).setStyle(Templates.columnStyle));
	}

	@Override
	protected void addTitle(final JasperReportBuilder report, final String title) {
		report.addTitle(Templates.createTitleComponent(title, true));
	}

	@Override
	public void printReport(final JasperReportBuilder report, final OutputStream os) throws DRException {
		report.toImage(os, ImageType.PNG);
	}

	@Override
	protected void setBackground(final JasperReportBuilder report) {
	}

	@Override
	public String setExtension() {
		return ".png";
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
