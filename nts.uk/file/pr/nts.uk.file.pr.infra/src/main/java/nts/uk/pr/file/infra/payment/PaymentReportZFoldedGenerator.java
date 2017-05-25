/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.ArrayList;
import java.util.List;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class PaymentReportZFoldedGenerator.
 */
public class PaymentReportZFoldedGenerator extends PaymentReportBaseGenerator
	implements PaymentGenerator {
	
	/** The Constant PERSON_OF_PAGE. */
	public static final int PERSON_OF_PAGE = 1;

	/** The Constant NUMBER_COLUMN_OF_ITEM. */
	public static final int NUMBER_COLUMN_OF_ITEM = 1;

	/** The Constant REMARK_TOTAL_ROW. */
	public static final int REMARK_TOTAL_ROW = 3;

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
		super.workSheet = context.getWorkbook().getWorksheets().get(FIRST_SHEET);
		super.paymentReportData = data;

		// Print data
		super.printData();

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
		printPaymentItems();
		breakLines(1);
		printDeductionItems();
		breakLines(1);
		printAttendanceItems();
		printArticleItems();
		printOtherItems();

		breakLines(1);
		// Print remark;
		printRemark();
		breakLines(1);
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
		list.add(new CellValue("B2", "ＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮ"));
		// TODO FAKE DATA
		list.add(new CellValue("B5", "給与明細書"));
		list.add(new CellValue("B8", employee.getCompanyInfo().getJapaneseYearMonth()));
		list.add(new CellValue("C14", "所属"));
		list.add(new CellValue("G14", "個人コード"));
		list.add(new CellValue("I14", "氏名"));
		list.add(new CellValue("C15", employee.getDepartmentInfo().getDepartmentCode()));
		list.add(new CellValue("G15", employee.getEmployeeInfo().getEmployeeCode()));
		list.add(new CellValue("I15", employee.getEmployeeInfo().getEmployeeName()));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getNumberOfColumnPerItem()
	 */
	@Override
	int getNumberOfColumnPerItem() {
		return NUMBER_COLUMN_OF_ITEM;
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
		return PERSON_OF_PAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getPageHeaderEndCell()
	 */
	@Override
	String getPageHeaderEndCell() {
		return "L22";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getCategoryHeaderCell()
	 */
	@Override
	String getCategoryHeaderCell() {
		return "B23";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemNameCell()
	 */
	@Override
	String getItemNameCell() {
		return "C23";
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
		return "C24";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getRemarkCell()
	 */
	@Override
	String getRemarkCell() {
		return "C25";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getRemarkTotalRow
	 * ()
	 */
	@Override
	int getRemarkTotalRow() {
		return REMARK_TOTAL_ROW;
	}

}
