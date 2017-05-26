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
 * The Class PaymentReportVerticalOneGenerator.
 */
public class PaymentReportVerticalOneGenerator extends PaymentReportBaseGenerator
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
	 * nts.uk.pr.file.infra.payment.PaymentGenerator#generate(nts.uk.shr.infra.
	 * file.report.aspose.cells.AsposeCellsReportContext,
	 * nts.uk.file.pr.app.export.payment.data.PaymentReportData)
	 */
	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData reportData) {
		super.workSheet = context.getWorkbook().getWorksheets().get(FIRST_SHEET);
		super.paymentReportData = reportData;

		// Print data
		super.printData();
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
		printPaymentItems();
		printDeductionItems();

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
	 * nts.uk.pr.file.infra.payment.BasePaymentReportGenerator#getHeaderTemplate
	 * ()
	 */
	@Override
	List<CellValue> getHeaderTemplate() {
		List<CellValue> list = new ArrayList<>();
		return list;
	}

	/* (non-Javadoc)
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getNumberOfColumnPerItem()
	 */
	@Override
	int getNumberOfColumnPerItem() {
		return NUMBER_COLUMN_OF_ITEM;
	}

	/* (non-Javadoc)
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getPersonPerPage()
	 */
	@Override
	int getPersonPerPage() {
		return PERSON_OF_PAGE;
	}

	/* (non-Javadoc)
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getPageHeaderEndCell()
	 */
	@Override
	String getPageHeaderEndCell() {
		return "J5";
	}

	/* (non-Javadoc)
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getCategoryHeaderCell()
	 */
	@Override
	String getCategoryHeaderCell() {
		return "B6";
	}

	/* (non-Javadoc)
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemNameCell()
	 */
	@Override
	String getItemNameCell() {
		return "C6";
	}

	/* (non-Javadoc)
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemValueCell()
	 */
	@Override
	String getItemValueCell() {
		return "C7";
	}

	/* (non-Javadoc)
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getRemarkCell()
	 */
	@Override
	String getRemarkCell() {
		return "C8";
	}

	/* (non-Javadoc)
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getRemarkTotalRow()
	 */
	@Override
	int getRemarkTotalRow() {
		return REMARK_TOTAL_ROW ;
	}

}
