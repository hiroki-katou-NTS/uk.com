/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.ArrayList;
import java.util.List;

import com.aspose.cells.Workbook;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class PaymentReportVerticalGenerator.
 */
public class PaymentReportVerticalGenerator extends BasePaymentReportGenerator implements PaymentGenerator {

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
		// Get workSheet.
		workSheet = workbook.getWorksheets().get(FIRST_SHEET);
		workSheet.setName(SHEET_NAME);

		// Get cells.
		cells = workSheet.getCells();

		// Initialize setting.
		super.init();

		// Print report data.
		reportData.getReportData().forEach(emp -> {
			employee = emp;
			printData();
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.BasePaymentReportGenerator#printPageContent(
	 * )
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.BasePaymentReportGenerator#getHeaderTemplate
	 * ()
	 */
	@Override
	List<CellValue> getHeaderTemplate() {
		List<CellValue> list = new ArrayList<>();
		list.add(new CellValue(0, 3, "給与明細書"));
		list.add(new CellValue(5, 2, "部門コード"));
		list.add(new CellValue(5, 5, "個人コード"));
		list.add(new CellValue(5, 7, "氏名"));
		list.add(new CellValue(2, 3, employee.getProcessingYm()));
		list.add(new CellValue(6, 2, employee.getDepartmentInfo().getDepartmentCode()));
		list.add(new CellValue(6, 5, employee.getEmployeeInfo().getEmployeeCode()));
		list.add(new CellValue(6, 7, employee.getEmployeeInfo().getEmployeeName()));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.BasePaymentReportGenerator#setTemplateStyle(
	 * )
	 */
	@Override
	void setTemplateStyle() {
		// Get style from template.
		templateStyle.headerStyle = getStyle("A10");
		templateStyle.nameStyle = getStyle("B10");
		templateStyle.remarkStyle = getStyle("A8");
		templateStyle.valueStyle = getStyle("B11");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.BasePaymentReportGenerator#
	 * setPageHeaderRange()
	 */
	@Override
	void setPageHeaderRange() {
		pageHeaderRange = cells.createRange("A1", "I7");
	}

}
