/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.ArrayList;
import java.util.List;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class PaymentReportVerticalTwoGenerator.
 */
public class PaymentReportVerticalTwoGenerator extends PaymentReportBaseGenerator implements PaymentGenerator {

	/** The Constant NUMBER_OF_COLUMN_PER_ITEM. */
	public static final int NUMBER_OF_COLUMN_PER_ITEM = 2;

	/** The Constant MAX_PERSON_PER_PAGE. */
	public static final int MAX_PERSON_PER_PAGE = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentGenerator#generate(nts.uk.shr.infra.
	 * file.report.aspose.cells.AsposeCellsReportContext,
	 * nts.uk.file.pr.app.export.payment.data.PaymentReportData)
	 */
	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData data) {
		// Set worksheet name.
		workSheet = context.getWorkbook().getWorksheets().get(FIRST_SHEET);
		workSheet.setName("PaymentService");

		// Set data.
		cells = workSheet.getCells();

		super.init();

		data.getReportData().forEach(item -> {
			employee = item;
			super.printData();
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemWidth()
	 */
	@Override
	int getNumberOfColumnPerItem() {
		return NUMBER_OF_COLUMN_PER_ITEM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#printPageContent(
	 * )
	 */
	@Override
	void printPageContent() {
		printCategoryHeader("支給");
		printCategoryContent(employee.getPaymentItems());
		nextCategory();
		breakLines(1);

		printCategoryHeader("控除");
		printCategoryContent(employee.getDeductionItems());
		nextCategory();
		breakLines(1);

		printCategoryHeader("勤怠");
		printCategoryContent(employee.getAttendanceItems());
		nextCategory();

		printCategoryHeader("記事");
		printCategoryContent(employee.getArticleItems());
		nextCategory();
		breakLines(1);

		// Print remark;
		// printRemark();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getHeaderTemplate
	 * ()
	 */
	@Override
	List<CellValue> getHeaderTemplate() {
		List<CellValue> list = new ArrayList<>();
		list.add(new CellValue("I3", employee.getCompanyInfo().getJapaneseYearMonth()));
		list.add(new CellValue("A2", employee.getDepartmentInfo().getDepartmentCode()));
		list.add(new CellValue("C2", employee.getEmployeeInfo().getEmployeeCode()));
		list.add(new CellValue("E2", employee.getEmployeeInfo().getEmployeeName()));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getPageHeaderStartCell()
	 */
	@Override
	String getPageHeaderStartCell() {
		return "A1";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getPageHeaderEndCell()
	 */
	@Override
	String getPageHeaderEndCell() {
		return "S4";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getCategoryHeaderCell()
	 */
	@Override
	String getCategoryHeaderCell() {
		return "A5";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemNameCell()
	 */
	@Override
	String getItemNameCell() {
		return "B5";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemValueCell(
	 * )
	 */
	@Override
	String getItemValueCell() {
		return "B6";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getRemarkCell()
	 */
	@Override
	String getRemarkCell() {
		return "B7";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getPersonPerPage(
	 * )
	 */
	@Override
	int getPersonPerPage() {
		return MAX_PERSON_PER_PAGE;
	}

}
