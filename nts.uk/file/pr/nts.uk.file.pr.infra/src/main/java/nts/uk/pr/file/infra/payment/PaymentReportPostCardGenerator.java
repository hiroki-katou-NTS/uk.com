/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.math.BigDecimal;
import java.util.List;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Range;
import com.aspose.cells.Worksheet;

import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingOnceDto;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentSalaryItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class PaymentReportPostCardGenerator.
 */
public class PaymentReportPostCardGenerator extends AsposeCellsReportGenerator
	implements PaymentGenerator {

	/** The work sheet. */
	protected Worksheet workSheet;

	/** The cells. */
	protected Cells cells;

	/** The page range. */
	protected Range pageRange;

	/** The Constant FIRST_COLUMN. */
	private static final int FIRST_COLUMN = 0;

	/** The Constant START_PAGE_CELLS. */
	private static final String START_PAGE_CELLS = "A1";

	/** The Constant END_PAGE_CELLS. */
	private static final String END_PAGE_CELLS = "Q35";

	/** The Constant REMARK_CELLS. */
	public static final String REMARK_CELLS = "L2";

	/** The Constant PROCESS_YEARMONTH_CELLS. */
	public static final String PROCESS_YEARMONTH_CELLS = "E3";

	/** The Constant DEPARTMENT_CELLS. */
	public static final String DEPARTMENT_CELLS = "H2";

	/** The Constant EMPLOYEE_NAME_CELLS. */
	public static final String EMPLOYEE_NAME_CELLS = "H3";

	/** The Constant EMPLOYEE_INFOR_NAME_CELLS. */
	public static final String EMPLOYEE_INFOR_NAME_CELLS = "B22";

	/** The Constant EMPLOYEE_POSTAL_CODE. */
	public static final String EMPLOYEE_POSTAL_CODE_CELLS = "B14";

	/** The Constant EMPLOYEE_ADDRESS_ONE_CELLS. */
	public static final String EMPLOYEE_ADDRESS_ONE_CELLS = "B15";

	/** The Constant EMPLOYEE_ADDRESS_TWO_CELLS. */
	public static final String EMPLOYEE_ADDRESS_TWO_CELLS = "B17";

	/** The Constant EMPLOYEE_TOTAL_TAX_CELLS. */
	public static final String EMPLOYEE_TAX_TOTAL_CELLS = "P6";

	/** The Constant EMPLOYEE_TAX_EXEMPTION_TOTAL_CELLS. */
	public static final String EMPLOYEE_TAX_EXEMPTION_TOTAL_CELLS = "P7";

	/** The Constant EMPLOYEE_TAX_EXEMPTION_TOTAL_CELLS. */
	public static final String EMPLOYEE_PAYMENT_TOTAL_CELLS = "P8";

	/** The Constant EMPLOYEE_SOCIAL_INSURANCE_TOTAL_CELLS. */
	public static final String EMPLOYEE_SOCIAL_INSURANCE_TOTAL_CELLS = "P9";

	/** The Constant EMPLOYEE_TAXABLE_AMOUNT_CELLS. */
	public static final String EMPLOYEE_TAXABLE_AMOUNT_CELLS = "P10";

	/** The Constant EMPLOYEE_DEDUCTION_TOTAL_CELLS. */
	public static final String EMPLOYEE_DEDUCTION_TOTAL_CELLS = "P11";

	/** The Constant EMPLOYEE_SUBSCRIPTION_AMOUNT_CELLS. */
	public static final String EMPLOYEE_SUBSCRIPTION_AMOUNT_CELLS = "P12";

	/** The Constant EMPLOYEE_TAXABLE_TOTAL_CELLS. */
	public static final String EMPLOYEE_TAXABLE_TOTAL_CELLS = "P22";

	/** The Constant COMPANY_NAME_CELLS. */
	public static final String COMPANY_NAME_CELLS = "C35";

	/** The Constant COMPANY_POST_CODE_CELLS. */
	public static final String COMPANY_POST_CODE_CELLS = "B31";

	/** The Constant COMPANY_ADDRESS_ONE_CELLS. */
	public static final String COMPANY_ADDRESS_ONE_CELLS = "B32";

	/** The Constant COMPANY_ADDRESS_TWO_CELLS. */
	public static final String COMPANY_ADDRESS_TWO_CELLS = "B34";

	/** The Constant START_ROW_CATEGORY_ATTENDANCE. */
	public static final int START_ROW_CATEGORY_ATTENDANCE = 5;

	/** The Constant START_COL_CATEGORY_ATTENDANCE. */
	public static final int START_COL_CATEGORY_ATTENDANCE = 4;

	/** The Constant START_ROW_CATEGORY_PAYMENT. */
	public static final int START_ROW_CATEGORY_PAYMENT = 5;

	/** The Constant START_COL_CATEGORY_PAYMENT. */
	public static final int START_COL_CATEGORY_PAYMENT = 7;

	/** The Constant START_ROW_CATEGORY_DEDUCTION. */
	public static final int START_ROW_CATEGORY_DEDUCTION = 5;

	/** The Constant START_COL_CATEGORY_DEDUCTION. */
	public static final int START_COL_CATEGORY_DEDUCTION = 10;

	/** The Constant START_ROW_CATEGORY_ARTICLE. */
	public static final int START_ROW_CATEGORY_ARTICLE = 14;

	/** The Constant START_COL_CATEGORY_ARTICLE. */
	public static final int START_COL_CATEGORY_ARTICLE = 14;

	/** The Constant START_ROW_CATEGORY_OTHER. */
	public static final int START_ROW_CATEGORY_OTHER = 24;

	/** The Constant START_COL_CATEGORY_OTHER. */
	public static final int START_COL_CATEGORY_OTHER = 14;

	/** The Constant FIRST_SHEET. */
	public static final int FIRST_SHEET = 0;

	/** The current row. */
	public int currentRow = 0;

	/** The current cell row. */
	public int currentCellRow = 0;
	
	/** The Constant EXCEL_ROW_UNIT_IN_MM. */
	public static final double EXCEL_ROW_UNIT_IN_MM = 0.35;

	/** The Constant EXCEL_COL_UNIT_IN_MM. */
	public static final double EXCEL_COL_UNIT_IN_MM = 3.15;

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

		// Set data.
		cells = workSheet.getCells();

		pageRange = cells.createRange(START_PAGE_CELLS, END_PAGE_CELLS);

		List<PaymentReportDto> reportData = data.getReportData();

		// copy blank data
		reportData.forEach(employee -> {
			copyBlankEmployee(data.getConfig().getRefundPaddingOnceDto());
		});

		// reset cell row 
		currentCellRow = 0;
		
		// print value data
		reportData.forEach(employee -> {
			printEmployeeInfo(employee);
		});

	}

	/**
	 * Sets the data category.
	 *
	 * @param startRow the start row
	 * @param startCol the start col
	 * @param merge the merge
	 * @param data the data
	 */
	public void setDataCategory(int startRow, int startCol, int merge,
		List<PaymentSalaryItemDto> data) {
		currentRow = 0;
		data.forEach(item -> {
			if (item.isView()) {
				cells.get(currentRow + startRow + currentCellRow, startCol)
					.setValue(item.getItemName());
				cells.get(currentRow + startRow + currentCellRow, startCol + merge)
					.setValue(item.getItemVal());
				currentRow++;
			}
		});
	}

	/**
	 * Prints the employee info.
	 *
	 * @param dataFirst
	 *            the data first
	 */
	private void printEmployeeInfo(PaymentReportDto dataFirst) {
		int pageRowCount = pageRange.getRowCount();
		printCellValue(dataFirst);
		currentCellRow += pageRowCount;
	}

	/**
	 * Copy blank employee.
	 */
	private void copyBlankEmployee(RefundPaddingOnceDto setting) {
		int pageRowCount = pageRange.getRowCount();
		this.configPageBySetting(setting);
		Range newPageHeaderRange = cells.createRange(currentCellRow, FIRST_COLUMN,
			pageRange.getRowCount(), pageRange.getColumnCount());
		try {
			newPageHeaderRange.copy(pageRange);
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentCellRow += pageRowCount;
		workSheet.getHorizontalPageBreaks().add(currentCellRow);
	}

	/**
	 * Prints the cell value.
	 *
	 * @param dataFirst the data first
	 */
	private void printCellValue(PaymentReportDto dataFirst) {
		setDataCategory(START_ROW_CATEGORY_ATTENDANCE, START_COL_CATEGORY_ATTENDANCE, 1,
			dataFirst.getAttendanceItems());

		setDataCategory(START_ROW_CATEGORY_PAYMENT, START_COL_CATEGORY_PAYMENT, 1,
			dataFirst.getPaymentItems());

		setDataCategory(START_ROW_CATEGORY_DEDUCTION, START_COL_CATEGORY_DEDUCTION, 2,
			dataFirst.getDeductionItems());

		setDataCategory(START_ROW_CATEGORY_ARTICLE, START_COL_CATEGORY_ARTICLE, 1,
			dataFirst.getArticleItems());

		setDataCategory(START_ROW_CATEGORY_OTHER, START_COL_CATEGORY_OTHER, 1,
			dataFirst.getOtherItems());

		// set remark
		getCell(REMARK_CELLS).setValue(dataFirst.getRemark());

		// set process year month
		getCell(PROCESS_YEARMONTH_CELLS)
			.setValue(dataFirst.getCompanyInfo().getJapaneseYearMonth());

		// set department info
		getCell(DEPARTMENT_CELLS).setValue(dataFirst.getDepartmentInfo().getDepartmentName());

		// set employee info
		getCell(EMPLOYEE_NAME_CELLS).setValue(dataFirst.getEmployeeInfo().getEmployeeName());
		getCell(EMPLOYEE_INFOR_NAME_CELLS)
			.setValue(dataFirst.getEmployeeInfo().getEmployeeName() + " 様");
		getCell(EMPLOYEE_POSTAL_CODE_CELLS)
			.setValue("〒　" + dataFirst.getEmployeeInfo().getPostalCode());
		getCell(EMPLOYEE_ADDRESS_ONE_CELLS)
			.setValue("　　　　" + dataFirst.getEmployeeInfo().getAddressOne());
		getCell(EMPLOYEE_ADDRESS_TWO_CELLS)
			.setValue("　　　　" + dataFirst.getEmployeeInfo().getAddressTwo());

		// total
		getCell(EMPLOYEE_TAX_TOTAL_CELLS).setValue(dataFirst.getEmployeeInfo().getTaxTotal());
		getCell(EMPLOYEE_TAX_EXEMPTION_TOTAL_CELLS)
			.setValue(dataFirst.getEmployeeInfo().getTaxExemptionTotal());
		getCell(EMPLOYEE_PAYMENT_TOTAL_CELLS)
			.setValue(dataFirst.getEmployeeInfo().getPaymentTotal());
		getCell(EMPLOYEE_SOCIAL_INSURANCE_TOTAL_CELLS)
			.setValue(dataFirst.getEmployeeInfo().getSocialInsuranceTotal());
		getCell(EMPLOYEE_TAXABLE_AMOUNT_CELLS)
			.setValue(dataFirst.getEmployeeInfo().getTaxableAmount());
		getCell(EMPLOYEE_DEDUCTION_TOTAL_CELLS)
			.setValue(dataFirst.getEmployeeInfo().getDeductionTotal());
		getCell(EMPLOYEE_SUBSCRIPTION_AMOUNT_CELLS)
			.setValue(dataFirst.getEmployeeInfo().getSubscriptionAmount());
		getCell(EMPLOYEE_TAXABLE_TOTAL_CELLS)
			.setValue(dataFirst.getEmployeeInfo().getTaxableTotal());

		// set company info
		getCell(COMPANY_NAME_CELLS).setValue(dataFirst.getCompanyInfo().getName());
		getCell(COMPANY_POST_CODE_CELLS)
			.setValue("〒　" + dataFirst.getCompanyInfo().getPostalCode());
		getCell(COMPANY_ADDRESS_ONE_CELLS)
			.setValue("　　　　" + dataFirst.getCompanyInfo().getAddressOne());
		getCell(COMPANY_ADDRESS_TWO_CELLS)
			.setValue("　　　　" + dataFirst.getCompanyInfo().getAddressTwo());
	}

	/**
	 * Gets the cell.
	 *
	 * @param cell the cell
	 * @return the cell
	 */
	private Cell getCell(String cell) {
		return cells.get(cells.get(cell).getRow() + currentCellRow, cells.get(cell).getColumn());
	}
	
	/**
	 * To excel row unit.
	 *
	 * @param value the value
	 * @return the double
	 */
	private double toExcelRowUnit(BigDecimal value) {
		return value.doubleValue() / EXCEL_ROW_UNIT_IN_MM;
	}

	/**
	 * To excel col unit.
	 *
	 * @param value the value
	 * @return the double
	 */
	private double toExcelColUnit(BigDecimal value) {
		return value.doubleValue() / EXCEL_COL_UNIT_IN_MM;
	}

	/**
	 * Config page by setting.
	 *
	 * @param setting the setting
	 */
	private void configPageBySetting(RefundPaddingOnceDto setting){
		cells.setRowHeight(currentCellRow, toExcelRowUnit(setting.getPaddingTop()));
		cells.setColumnWidth(FIRST_COLUMN, toExcelColUnit(setting.getPaddingLeft()));
	}
}
