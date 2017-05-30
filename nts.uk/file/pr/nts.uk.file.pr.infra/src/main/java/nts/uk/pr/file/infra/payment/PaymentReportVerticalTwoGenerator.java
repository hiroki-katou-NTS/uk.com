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
 * The Class PaymentReportVerticalTwoGenerator.
 */
public class PaymentReportVerticalTwoGenerator extends PaymentReportBaseGenerator implements PaymentGenerator {

	/** The Constant NUMBER_OF_COLUMN_PER_ITEM. */
	public static final int NUMBER_OF_COLUMN_PER_ITEM = 2;

	/** The Constant MAX_PERSON_PER_PAGE. */
	public static final int MAX_PERSON_PER_PAGE = 2;

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
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemWidth()
	 */
	@Override
	protected int getNumberOfColumnPerItem() {
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
	protected void printPageContent() {
		printPaymentItems();
		breakLines(1);
		printDeductionItems();
		breakLines(1);

		printAttendanceItems();
		printArticleItems();
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
	protected List<CellValue> getHeaderTemplate() {
		List<CellValue> list = new ArrayList<>();
		list.add(new CellValue("J4", employee.getCompanyInfo().getJapaneseYearMonth()));
		list.add(new CellValue("B3", employee.getDepartmentInfo().getDepartmentCode()));
		list.add(new CellValue("D3", employee.getEmployeeInfo().getEmployeeCode()));
		list.add(new CellValue("F3", employee.getEmployeeInfo().getEmployeeName()));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getPageHeaderEndCell()
	 */
	@Override
	protected String getPageHeaderEndCell() {
		return "N5";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getCategoryHeaderCell()
	 */
	@Override
	protected String getCategoryHeaderCell() {
		return "B6";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemNameCell()
	 */
	@Override
	protected String getItemNameCell() {
		return "C6";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemValueCell(
	 * )
	 */
	@Override
	protected String getItemValueCell() {
		return "C7";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getRemarkCell()
	 */
	@Override
	protected String getRemarkCell() {
		return "B8";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getPersonPerPage(
	 * )
	 */
	@Override
	protected int getPersonPerPage() {
		return MAX_PERSON_PER_PAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getRemarkTotalRow
	 * ()
	 */
	@Override
	protected int getRemarkTotalRow() {
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
	protected BigDecimal getPageTopMargin() {
		return config.getRefundPaddingTwoDto().getUpperAreaPaddingTop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getPageLeftMargin
	 * ()
	 */
	@Override
	protected BigDecimal getPageLeftMargin() {
		return config.getRefundPaddingTwoDto().getPaddingLeft();
	}

}
