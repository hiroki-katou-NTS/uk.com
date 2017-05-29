/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class PaymentReportVerticalThreeGenerator.
 */
public class PaymentReportVerticalThreeGenerator extends PaymentReportBaseGenerator implements PaymentGenerator {

	/** The Constant NUMBER_OF_COLUMN_PER_ITEM. */
	public static final int NUMBER_OF_COLUMN_PER_ITEM = 3;

	/** The Constant MAX_PERSON_PER_PAGE. */
	public static final int MAX_PERSON_PER_PAGE = 3;

	/** The Constant REMARK_TOTAL_ROW. */
	public static final int REMARK_TOTAL_ROW = 1;

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
		// Get work sheet.
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
		printDeductionItems();
		printAttendanceItems();
		printArticleItems();
		printOtherItems();
		printSumary();

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
		list.add(new CellValue("S2", employee.getCompanyInfo().getJapaneseYearMonth()));
		list.add(new CellValue("B3", employee.getDepartmentInfo().getDepartmentCode()));
		list.add(new CellValue("E3", employee.getEmployeeInfo().getEmployeeCode()));
		list.add(new CellValue("H3", employee.getEmployeeInfo().getEmployeeName()));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getPageHeaderEndCell()
	 */
	@Override
	String getPageHeaderEndCell() {
		return "AC4";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getCategoryHeaderCell()
	 */
	@Override
	String getCategoryHeaderCell() {
		return "B5";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemNameCell()
	 */
	@Override
	String getItemNameCell() {
		return "C5";
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
		return "C6";
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
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getRemarkTotalRow
	 * ()
	 */
	@Override
	int getRemarkTotalRow() {
		return REMARK_TOTAL_ROW;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getPageTopMargin(
	 * )
	 */
	@Override
	BigDecimal getPageTopMargin() {
		return config.getRefundPaddingThreeDto().getUpperAreaPaddingTop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getPageLeftMargin
	 * ()
	 */
	@Override
	BigDecimal getPageLeftMargin() {
		return config.getRefundPaddingThreeDto().getPaddingLeft();
	}

}
