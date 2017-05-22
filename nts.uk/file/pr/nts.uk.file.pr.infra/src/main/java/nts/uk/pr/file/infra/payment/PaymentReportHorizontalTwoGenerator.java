package nts.uk.pr.file.infra.payment;

import java.util.ArrayList;
import java.util.List;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

public class PaymentReportHorizontalTwoGenerator extends PaymentReportBaseGenerator implements PaymentGenerator {

	/** The Constant ITEM_WIDTH. */
	public static final int ITEM_WIDTH = 2;

	/** The Constant CATEGORY_START_ROW. */
	public static final int CATEGORY_START_ROW = 4;

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
		printRemark();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * setPageHeaderRange()
	 */
	@Override
	void setPageHeaderRange() {
		pageHeaderRange = cells.createRange("A1", "L3");
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
		list.add(new CellValue(0, 8, "給与明細書"));
		list.add(new CellValue(1, 0, "部門コード"));
		list.add(new CellValue(1, 2, "個人コード"));
		list.add(new CellValue(1, 4, "氏名"));
		list.add(new CellValue(2, 0, employee.getDepartmentInfo().getDepartmentCode()));
		list.add(new CellValue(2, 2, employee.getEmployeeInfo().getEmployeeCode()));
		list.add(new CellValue(2, 4, employee.getEmployeeInfo().getEmployeeName()));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#setTemplateStyle(
	 * )
	 */
	@Override
	void setTemplateStyle() {
		// Get style from template.
		templateStyle.headerStyle = getStyle("A5");
		templateStyle.nameStyle = getStyle("B5");
		templateStyle.valueStyle = getStyle("B6");
		templateStyle.remarkStyle = getStyle("J2");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.pr.file.infra.payment.PaymentReportBaseGenerator#
	 * getCategoryStartRow()
	 */
	@Override
	int getCategoryStartRow() {
		return CATEGORY_START_ROW;
	}

}
