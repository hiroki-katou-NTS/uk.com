package nts.uk.pr.file.infra.payment;

import java.util.ArrayList;
import java.util.List;

import com.aspose.cells.Workbook;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

public class PaymentReportVerticalOneGenerator extends PaymentReportBaseGenerator implements PaymentGenerator{

	/** The Constant SHEET_NAME. */
	public static final String SHEET_NAME = "SHEET 1";

	/** The Constant ITEM_WIDTH. */
	public static final int ITEM_WIDTH = 1;

	/** The Constant CATEGORY_START_ROW. */
	public static final int CATEGORY_START_ROW = 9;

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
		templateStyle.headerStyle = getStyle("A5");
		templateStyle.nameStyle = getStyle("B5");
		templateStyle.remarkStyle = getStyle("B11");
		templateStyle.valueStyle = getStyle("B6");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.BasePaymentReportGenerator#
	 * setPageHeaderRange()
	 */
	@Override
	void setPageHeaderRange() {
		pageHeaderRange = cells.createRange("A1", "J4");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#getItemWidth()
	 */
	@Override
	int getItemWidth() {
		return ITEM_WIDTH;
	}

}
