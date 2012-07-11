package com.github.jtreport.printer.core;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.FillerBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.LineStyle;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;

import com.github.jtreport.core.StateTestEnum;

/**
 * Default template of report.
 * 
 * @author Fabio Rubino
 * 
 */
public class Templates {

	public static class CurrencyType extends BigDecimalType {

		private static final long serialVersionUID = 1L;

		@Override
		public String getPattern() {
			return "$ #,###.00";
		}
	}

	private static class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {

		private static final long serialVersionUID = 1L;

		private final String label;

		public CurrencyValueFormatter(final String label) {
			this.label = label;
		}

		@Override
		public String format(final Number value, final ReportParameters reportParameters) {
			return label + currencyType.valueToString(value, reportParameters.getLocale());
		}
	}

	public static final StyleBuilder rootStyle;
	public static final StyleBuilder boldStyle;
	public static final StyleBuilder italicStyle;
	public static final StyleBuilder boldCenteredStyle;
	public static final StyleBuilder bold12CenteredStyle;
	public static final StyleBuilder bold18CenteredStyle;
	public static final StyleBuilder bold22CenteredStyle;
	public static final StyleBuilder columnStyle;
	public static final StyleBuilder columnTitleStyle;

	public static final StyleBuilder groupStyle;
	public static final StyleBuilder subtotalStyle;
	public static final ReportTemplateBuilder reportTemplate;
	public static final CurrencyType currencyType;

	public static final ComponentBuilder<?, ?> dynamicReportsComponent;

	public static final ComponentBuilder<?, ?> footerComponent;

	static {
		rootStyle = stl.style().setPadding(2);
		boldStyle = stl.style(rootStyle).bold();
		italicStyle = stl.style(rootStyle).italic();
		boldCenteredStyle = stl.style(boldStyle).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
		bold12CenteredStyle = stl.style(boldCenteredStyle).setFontSize(12);
		bold18CenteredStyle = stl.style(boldCenteredStyle).setFontSize(18);
		bold22CenteredStyle = stl.style(boldCenteredStyle).setFontSize(22);
		columnStyle = stl.style(rootStyle).setHorizontalAlignment(HorizontalAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.MIDDLE)
				.setBorder(stl.pen1Point().setLineStyle(LineStyle.SOLID));
		columnTitleStyle = stl.style(columnStyle).setBorder(stl.pen1Point())
				.setHorizontalAlignment(HorizontalAlignment.CENTER).setBackgroundColor(Color.CYAN).bold();
		groupStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.LEFT);
		subtotalStyle = stl.style(boldStyle).setTopBorder(stl.pen1Point());

		reportTemplate = template().setLocale(Locale.ENGLISH).setColumnStyle(columnStyle)
				.setColumnTitleStyle(columnTitleStyle).setGroupStyle(groupStyle).setGroupTitleStyle(groupStyle)
				.setSubtotalStyle(subtotalStyle);
		currencyType = new CurrencyType();

		// final HyperLinkBuilder link = hyperLink("http://jtreport.sourceforge.net");
		dynamicReportsComponent = cmp.horizontalList(cmp.verticalList(cmp.image(
				Templates.class.getClassLoader().getResource("img/logo.png")).setHorizontalAlignment(
				HorizontalAlignment.RIGHT)/*
										 * , cmp.text("http://jtreport.sourceforge.net").setStyle(italicStyle)
										 * .setHyperLink(link).setHorizontalAlignment(HorizontalAlignment.RIGHT)
										 */));

		footerComponent = cmp.pageXofY().setStyle(stl.style(boldCenteredStyle).setTopBorder(stl.pen1Point()));
	}

	public static CurrencyValueFormatter createCurrencyValueFormatter(final String label) {
		return new CurrencyValueFormatter(label);
	}

	public static ComponentBuilder<?, ?> createSubTitleComponent(final String description) {
		return cmp.verticalList().add(
				cmp.text("General description").setStyle(bold18CenteredStyle)
						.setHorizontalAlignment(HorizontalAlignment.LEFT),
				cmp.text(description).setStyle(rootStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
				cmp.verticalGap(20));
	}

	public static ComponentBuilder<?, ?> createSubTitleTestResult(final Map<StateTestEnum, String> testResult,
			final int totalRun, final String duration) {
		final String passed = testResult.get(StateTestEnum.PASSED);
		final String error = testResult.get(StateTestEnum.ERROR);
		final String failed = testResult.get(StateTestEnum.FAILED);
		final String ignored = testResult.get(StateTestEnum.IGNORED);
		return cmp.horizontalList(
				cmp.text("Test run: " + totalRun + ", passed: " + passed + ", error: " + error + ", failed: " + failed
						+ ", ignored: " + ignored) /* ,cmp.text("Duration: " + duration + "ms") */,
				cmp.verticalGap(10));
	}

	public static ComponentBuilder<?, ?> createTitleComponent(final String label, final boolean addImage) {
		final HorizontalListBuilder horizontalList = cmp.horizontalList();

		final TextFieldBuilder<String> setHorizontalAlignment = cmp.text(label).setStyle(bold18CenteredStyle)
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
		final FillerBuilder setFixedHeight = cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point()))
				.setFixedHeight(10);
		HorizontalListBuilder title = null;
		if (addImage) {
			title = horizontalList.add(setHorizontalAlignment, dynamicReportsComponent);
		} else {
			title = horizontalList.add(setHorizontalAlignment).newRow().add(setFixedHeight);
		}
		return title;
	}
}
