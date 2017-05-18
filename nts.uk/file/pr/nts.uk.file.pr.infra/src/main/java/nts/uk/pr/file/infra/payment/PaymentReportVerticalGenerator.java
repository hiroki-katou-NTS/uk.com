/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class PaymentReportVerticalGenerator.
 */
public class PaymentReportVerticalGenerator extends BasePaymentReportGenerator implements PaymentGenerator {

	/** The Constant FIRST_ITEM. */
	public static final int FIRST_ITEM = 0;

	/** The Constant SHEET_NAME. */
	public static final String SHEET_NAME = "SHEET 1";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentGenerator#generate(nts.uk.shr.infra.
	 * file.report.aspose.cells.AsposeCellsReportContext,
	 * nts.uk.file.pr.app.export.payment.data.PaymentReportData)
	 */
	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData reportData) {
		Workbook workbook = context.getWorkbook();
		Worksheet worksheet = workbook.getWorksheets().get(FIRST_ITEM);
		worksheet.setName(SHEET_NAME);

		// Get cells.
		cells = worksheet.getCells();

		// Get style from template.
		templateStyle = new TemplateStyle();
		templateStyle.headerStyle = (cells.get("A10").getStyle());
		templateStyle.nameStyle = cells.get("B10").getStyle();
		templateStyle.remarkStyle = cells.get("A8").getStyle();
		templateStyle.valueStyle = cells.get("B11").getStyle();

		hPageBreaks = worksheet.getHorizontalPageBreaks();
		pageHeaderRange = cells.createRange("A1", "I7");

		reportData.getReportData().forEach(emp -> {
			employee = emp;
			printData();
		});

	}

	@Override
	void printPageContent() {

		printSectionTitle("支給額");
		printCategoryHeader("支給");
		printCategoryContent(employee.getPaymentItems());
		nextCategory();
		breakLines(2);

		printSectionTitle("控除");
		printCategoryHeader("控除");
		printCategoryContent(employee.getDeductionItems());
		nextCategory();
		breakLines(3);

		printSectionTitle("勤怠/記事");
		printCategoryHeader("勤怠");
		printCategoryContent(employee.getAttendanceItems());
		nextCategory();

		printCategoryHeader("記事");
		printCategoryContent(employee.getArticleItems());
		nextCategory();
		breakLines(1);

		// Print remark;
		printRemark();
	}

}
