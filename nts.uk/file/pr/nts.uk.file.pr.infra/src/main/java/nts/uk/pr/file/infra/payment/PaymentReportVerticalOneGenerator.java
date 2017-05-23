/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.ArrayList;
import java.util.List;

import com.aspose.cells.Workbook;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator.CellValue;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class PaymentReportVerticalOneGenerator.
 */
public class PaymentReportVerticalOneGenerator extends PaymentReportBaseGenerator
	implements PaymentGenerator {

	/** The Constant ITEM_WIDTH. */
	public static final int ITEM_WIDTH = 1;

	/** The Constant CATEGORY_START_ROW. */
	public static final int CATEGORY_START_ROW = 4;
	
	/** The Constant PERSON_OF_PAGE. */
	public static final int PERSON_OF_PAGE = 1;
	
	/** The Constant NUMBER_COLUMN_OF_ITEM. */
	public static final int NUMBER_COLUMN_OF_ITEM =1 ;

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

		printCategoryHeader("支給");
		printCategoryContent(employee.getPaymentItems());
		nextCategory();

		printCategoryHeader("控除");
		printCategoryContent(employee.getDeductionItems());
		nextCategory();

		printCategoryHeader("勤怠");
		printCategoryContent(employee.getAttendanceItems());
		nextCategory();

		printCategoryHeader("記事");
		printCategoryContent(employee.getArticleItems());
		nextCategory();
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
		list.add(new CellValue(0, 0, "ＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮ"));
		return list;
	}

	@Override
	int getNumberOfColumnPerItem() {
		return ITEMS_PER_ROW;
	}

	@Override
	int getPersonPerPage() {
		return PERSON_OF_PAGE;
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
		return "A5";
	}

	@Override
	String getItemNameCell() {
		return "B5";
	}

	@Override
	String getItemValueCell() {
		return "B6";
	}

	@Override
	String getRemarkCell() {
		return "B6";
	}

}
