/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.ArrayList;
import java.util.List;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

public class PaymentReportZFoldedGenerator extends PaymentReportBaseGenerator
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
	 * nts.uk.pr.file.infra.wagetable.Generator#generate(nts.uk.shr.infra.file.
	 * report.aspose.cells.AsposeCellsReportContext,
	 * nts.uk.ctx.pr.core.app.wagetable.command.WtUpdateCommand)
	 */
	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData data) {
		// Set worksheet name.
		workSheet = context.getWorkbook().getWorksheets().get(0);
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
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#printPageContent(
	 * )
	 */
	@Override
	void printPageContent() {
		printCategoryHeader("支給");
		printCategoryContent(employee.getPaymentItems());
		nextCategory();
		breakLines(2);

		printCategoryHeader("控除");
		printCategoryContent(employee.getDeductionItems());
		nextCategory();
		breakLines(3);

		printCategoryHeader("勤怠");
		printCategoryContent(employee.getAttendanceItems());
		nextCategory();

		printCategoryHeader("記事");
		printCategoryContent(employee.getArticleItems());
		nextCategory();
		breakLines(1);

		// Print remark;
		//printRemark();
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
		list.add(new CellValue(0, 0, "ＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮ"));
		//TODO FAKE DATA
		list.add(new CellValue(3, 0, "給与明細書"));
		list.add(new CellValue(6, 0, employee.getCompanyInfo().getJapaneseYearMonth()));
		list.add(new CellValue(12, 1, "所属"));
		list.add(new CellValue(12, 5, "個人コード"));
		list.add(new CellValue(12, 7, "氏名"));
		list.add(new CellValue(13, 1, employee.getDepartmentInfo().getDepartmentCode()));
		list.add(new CellValue(13, 5, employee.getEmployeeInfo().getEmployeeCode()));
		list.add(new CellValue(13, 7, employee.getEmployeeInfo().getEmployeeName()));
		return list;
	}

	@Override
	int getNumberOfColumnPerItem() {
		return NUMBER_COLUMN_OF_ITEM;
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
		return "J21";
	}

	@Override
	String getCategoryHeaderCell() {
		return "A22";
	}

	@Override
	String getItemNameCell() {
		return "A23";
	}

	@Override
	String getItemValueCell() {
		return "B23";
	}

	@Override
	String getRemarkCell() {
		return "M11";
	}




}
