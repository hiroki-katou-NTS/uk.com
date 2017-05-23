/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.ArrayList;
import java.util.List;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

public class PaymentReportHorizontalTwoGenerator extends PaymentReportBaseGenerator implements PaymentGenerator {

	/** The Constant ITEM_WIDTH. */
	public static final int NUMBER_OF_COLUMN_PER_ITEM = 2;
	public static final int MAX_PERSON_PER_PAGE = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.wagetable.Generator#generate(nts.uk.shr.infra.file.
	 * report.aspose.cells.AsposeCellsReportContext,
	 * nts.uk.ctx.pr.core.app.wagetable.command.WtUpdateCommand)
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

		// PageSetup pageSetup = workSheet.getPageSetup();
		// pageSetup.setTopMargin(0);

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
		printRemark();
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
		list.add(new CellValue("J5", employee.getCompanyInfo().getJapaneseYearMonth()));
		list.add(new CellValue("A4", employee.getDepartmentInfo().getDepartmentCode()));
		list.add(new CellValue("C4", employee.getEmployeeInfo().getEmployeeCode()));
		list.add(new CellValue("E4", employee.getEmployeeInfo().getEmployeeName()));
		return list;
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

	@Override
	int getPersonPerPage() {
		return MAX_PERSON_PER_PAGE;
	}

	@Override
	String getPageHeaderStartCell() {
		return "A1";
	}

	@Override
	String getPageHeaderEndCell() {
		return "J3";
	}

	@Override
	String getCategoryHeaderCell() {
		return "A4";
	}

	@Override
	String getItemNameCell() {
		return "B4";
	}

	@Override
	String getItemValueCell() {
		return "B5";
	}

	@Override
	String getRemarkCell() {
		return "B8";
	}

}
