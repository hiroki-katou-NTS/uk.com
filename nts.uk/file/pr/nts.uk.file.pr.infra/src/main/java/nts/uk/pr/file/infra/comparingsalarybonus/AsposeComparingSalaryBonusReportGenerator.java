package nts.uk.pr.file.infra.comparingsalarybonus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.TextDirectionType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.comparingsalarybonus.ComparingSalaryBonusGenerator;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DataRowComparingSalaryBonus;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DataRowComparingSalaryBonusDto;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DeparmentInf;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DetailEmployee;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.HeaderTable;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeComparingSalaryBonusReportGenerator extends AsposeCellsReportGenerator
		implements ComparingSalaryBonusGenerator {
	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/QPP008_ComparingSalaryBonusTemp.xlsx";

	/** The Constant REPORT_FILE_NAME. */
	private static final String REPORT_FILE_NAME = "QPP008_";
	/** The Constant REPORT_FILE_NAME. */
	private static final String EXTENSION = ".pdf";
	/** The Constant HEADER. */
	private static final String HEADER = "HEADER";

	private static final int AMOUNT_COLUMN = 8;

	private static final int FIRST_COLUMN = 0;

	/** The Constant FIRST_ROW_INDEX. */
	private static final int FIRST_ROW_INDEX = 9;

	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	private static final int[] COLUMN_INDEX_DEPARTMENT = { 0 };
	private static final int[] COLUMN_INDEX_EMPLOYEE = { 0 };

	private static final int ROW_HEIGHT = 28;
	private static final int AMOUNT_PER_PAGE = 46;
	/** The Constant AMOUNT_ROWS_IN_PAGE. */
	private static final int AMOUNT_ROWS_IN_PAGE = 60;
	// number of Row will start
	private int firtRow = 0;
	private int maxDepartment = 0;

	@Override
	public void generate(FileGeneratorContext fileContext, ComparingSalaryBonusReportData reportData) {
		List<DeparmentInf> lstDeparmentInf = reportData.getDeparmentInf();
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			Cells cells = worksheet.getCells();

			// Set header.
			DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd hh:mm");
			worksheet.getPageSetup().setHeader(2,
					"&\"Times New Roman,Bold\"&12&F" + dateFormat.format(new Date()) + "\r\n" + "&P " + " ページ");

			designer.getDesigner().setDataSource(HEADER, Arrays.asList(reportData.getHeaderData()));
			DateFormat dateFM = new SimpleDateFormat("yyyyMMddhhssmm");
			Date date = new Date();
			String fileName = REPORT_FILE_NAME.concat(dateFM.format(date).toString()).concat(EXTENSION);

			// Fill data
			// List Item Data
			int firstRowIndex = FIRST_ROW_INDEX;

			lstDeparmentInf.stream().forEach(c -> {
				this.maxDepartment = 1 + c.getLstEmployee().size() * 7 + 4;
			});

			createRange(cells, firstRowIndex, 1);
			printTitleRow(worksheets, firstRowIndex, reportData);
			int rangeRows = AMOUNT_PER_PAGE;

			while (maxDepartment > 0) {
				// Create ranges
				if (maxDepartment < AMOUNT_PER_PAGE) {
					rangeRows = Math.min(maxDepartment, AMOUNT_PER_PAGE);
				}
				createRange(cells, firstRowIndex, 1);
				printTitleRow(worksheets, firstRowIndex, reportData);
				firstRowIndex += 1;
				printDepartment(worksheets, firstRowIndex, reportData);
				// print grand total
				createRange(cells, this.firtRow + 1, 1);
				printGrandTotal(worksheets, this.firtRow + 1, reportData);
				maxDepartment -= AMOUNT_PER_PAGE;
			}

			// Caculate number of Page
			this.firtRow = this.firtRow + 1;
			int numberOfPage = this.firtRow / 60;
			int numberOfRow = this.firtRow - numberOfPage * AMOUNT_ROWS_IN_PAGE;
			if (numberOfRow > 0) {
				numberOfPage = numberOfPage + 1;
			}

			// Set print page
			PageSetup pageSetup = worksheet.getPageSetup();
			pageSetup.setPrintArea("A1:H" + numberOfPage * 60);
			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();
			designer.saveAsPdf(this.createNewFile(fileContext, fileName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Prints the title row.
	 *
	 * @param worksheets
	 *            the worksheets
	 * @param rowIndex
	 *            the row index
	 */
	private void printTitleRow(WorksheetCollection worksheets, int rowIndex,
			ComparingSalaryBonusReportData comparingQuery) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		HeaderTable headerTable = comparingQuery.getHeaderTable();
		// Set Row height
		cells.setRowHeightPixel(rowIndex, ROW_HEIGHT);

		// Print itemName Header
		Cell itemName = cells.get(rowIndex, COLUMN_INDEX[0]);
		itemName.setValue(headerTable.getItemName());

		// Print Month1 Header
		Cell month1 = cells.get(rowIndex, COLUMN_INDEX[1]);
		month1.setValue(headerTable.getMonth1());

		// Print Month2 Header
		Cell month2 = cells.get(rowIndex, COLUMN_INDEX[2]);
		month2.setValue(headerTable.getMonth2());

		// Print Diffferent Salary Header
		Cell different = cells.get(rowIndex, COLUMN_INDEX[3]);
		different.setValue(headerTable.getDifferentSalary());

		// Print RegistrationStatus1 Header
		Cell registration1 = cells.get(rowIndex, COLUMN_INDEX[4]);
		registration1.setValue(headerTable.getRegistrationStatus1());

		// Print RegistrationStatus2 Status Header
		Cell registration2 = cells.get(rowIndex, COLUMN_INDEX[5]);
		registration2.setValue(headerTable.getRegistrationStatus2());

		// Print reason Status Header
		Cell reason = cells.get(rowIndex, COLUMN_INDEX[6]);
		reason.setValue(headerTable.getReason());

		// Print confirm Status Header
		Cell confirm = cells.get(rowIndex, COLUMN_INDEX[7]);
		confirm.setValue(headerTable.getConfirmed());

		for (int c : COLUMN_INDEX) {
			Cell cell = cells.get(rowIndex, COLUMN_INDEX[c]);
			setTitleStyle(cell);
		}

	}

	/**
	 * Prints the title row.
	 *
	 * @param worksheets
	 *            the worksheets
	 * @param rowIndex
	 *            the row index
	 */
	private void printDepartment(WorksheetCollection worksheets, int rowIndex,
			ComparingSalaryBonusReportData comparingQuery) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		// Set Row height
		cells.setRowHeightPixel(rowIndex, ROW_HEIGHT);

		for (int i = 0; i < comparingQuery.getDeparmentInf().size(); i++) {
			DeparmentInf departInf = comparingQuery.getDeparmentInf().get(i);
			// Print Department code
			cells.merge(rowIndex, 0, 1, 8);
			Cell departmentCode = cells.get(rowIndex, COLUMN_INDEX_DEPARTMENT[0]);
			departmentCode.setValue("部門：" + " " + departInf.getDepcode() + "総務部　総務課　総務" + departInf.getDepname());
			for (int c : COLUMN_INDEX) {
				Cell cell = cells.get(rowIndex, COLUMN_INDEX[c]);
				Style style = cells.getStyle();
				style.setPattern(BackgroundType.SOLID);
				style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
				style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
				if (c == 0) {
					style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getGray());
				}
				if (c == 7) {
					style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getGray());
				}
				cell.setStyle(style);
			}

			// print employee
			createContent(worksheets, rowIndex + 1, comparingQuery.getDeparmentInf());
			rowIndex = this.firtRow;
			// print total Department
			createRange(cells, this.firtRow, 1);
			printTotalDepartment(worksheets, this.firtRow, comparingQuery, i);

			// print total A
			createRange(cells, this.firtRow + 1, 1);
			printTotalADepartment(worksheets, this.firtRow + 1, comparingQuery, i);

			// print total C
			createRange(cells, this.firtRow + 2, 1);
			printTotalCDepartment(worksheets, this.firtRow + 2, comparingQuery, i);
			this.firtRow = this.firtRow + 3;

			rowIndex = this.firtRow + 1;
		}

	}

	/**
	 * Creates the content.
	 *
	 * @param cells
	 *            the cells
	 * @param firstRowIndex
	 *            the first row index
	 * @param lstDataRow
	 *            the accumulated payment list
	 */
	private void createContent(WorksheetCollection worksheets, int firstRowIndex, List<DeparmentInf> lstDepartment) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();

		// Set Row height
		cells.setRowHeightPixel(firstRowIndex, ROW_HEIGHT);

		DeparmentInf deparmentInf = lstDepartment.get(0);
		for (int i = 0; i < deparmentInf.getLstEmployee().size(); i++) {

			DetailEmployee detailEmployeeInf = deparmentInf.getLstEmployee().get(i);

			// Print employee Code
			cells.merge(firstRowIndex, 0, 1, 8);
			Cell employeeCode = cells.get(firstRowIndex, COLUMN_INDEX_EMPLOYEE[0]);
			employeeCode.setValue(
					"部門コード : " + detailEmployeeInf.getPersonID() + "社員名: " + detailEmployeeInf.getPersonName());
			for (int c : COLUMN_INDEX) {
				Cell cell = cells.get(firstRowIndex, COLUMN_INDEX[c]);
				Style style = cells.getStyle();
				style.setPattern(BackgroundType.SOLID);
				if (c == 0) {
					style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getGray());
				}
				if (c == 7) {
					style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getGray());
				}
				cell.setStyle(style);
			}
			
			// print Data Row
			this.createRange(cells, firstRowIndex + 1, 6);
			for (int j = 0; j < detailEmployeeInf.getLstData().size(); j++) {

				DataRowComparingSalaryBonusDto dataRow = detailEmployeeInf.getLstDataDto().get(j);
				Cell itemName = cells.get(firstRowIndex + 1, COLUMN_INDEX[0]);
				itemName.setValue(dataRow.getItemName());

				// Print month1 amount
				Cell month1 = cells.get(firstRowIndex + 1, COLUMN_INDEX[1]);
				month1.setValue(dataRow.getMonth1());

				// Print month2 amount
				Cell month2 = cells.get(firstRowIndex + 1, COLUMN_INDEX[2]);
				month2.setValue(dataRow.getMonth2());

				// Print Different Salary
				Cell differentAmount = cells.get(firstRowIndex + 1, COLUMN_INDEX[3]);
				differentAmount.setValue(dataRow.getDifferentSalary());

				// Print registration1
				Cell registration1 = cells.get(firstRowIndex + 1, COLUMN_INDEX[4]);
				registration1.setValue(dataRow.getRegistrationStatus1());

				// Print registration2
				Cell registration2 = cells.get(firstRowIndex + 1, COLUMN_INDEX[5]);
				registration2.setValue(dataRow.getRegistrationStatus2());

				// Print reason
				Cell reason = cells.get(firstRowIndex + 1, COLUMN_INDEX[6]);
				reason.setValue(dataRow.getReason());

				// Print confirm Status
				Cell confirm = cells.get(firstRowIndex + 1, COLUMN_INDEX[7]);
				confirm.setValue(dataRow.getConfirmed());

				// Set Background Color for odd rows
				if ((j % 2) == 1) {
					for (int c : COLUMN_INDEX) {
						Cell oddCell = cells.get(firstRowIndex, COLUMN_INDEX[c]);
						setBackgroundcolor(oddCell);
					}
				}

				firstRowIndex++;
			}
			firstRowIndex = firstRowIndex + 1;
		}
		this.firtRow = firstRowIndex + 1;
	}

	/**
	 * Prints the Total Department row.
	 *
	 * @param worksheets
	 *            the worksheets
	 * @param rowIndex
	 *            the row index
	 */
	private void printTotalDepartment(WorksheetCollection worksheets, int rowIndex,
			ComparingSalaryBonusReportData comparingQuery, int i) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		DataRowComparingSalaryBonusDto divisionTotal = comparingQuery.getLstDivisionTotal().get(i);
		// Set Row height
		cells.setRowHeightPixel(rowIndex, ROW_HEIGHT);

		// Print itemName Header
		Cell itemName = cells.get(rowIndex, COLUMN_INDEX[0]);
		itemName.setValue(divisionTotal.getItemName());

		// Print Month1 Header
		Cell month1 = cells.get(rowIndex, COLUMN_INDEX[1]);
		month1.setValue(divisionTotal.getMonth1());

		// Print Month2 Header
		Cell month2 = cells.get(rowIndex, COLUMN_INDEX[2]);
		month2.setValue(divisionTotal.getMonth2());

		// Print Diffferent Salary Header
		Cell different = cells.get(rowIndex, COLUMN_INDEX[3]);
		different.setValue(divisionTotal.getDifferentSalary());

		// Print RegistrationStatus1 Header
		Cell registration1 = cells.get(rowIndex, COLUMN_INDEX[4]);
		registration1.setValue(divisionTotal.getRegistrationStatus1());

		// Print RegistrationStatus2 Status Header
		Cell registration2 = cells.get(rowIndex, COLUMN_INDEX[5]);
		registration2.setValue(divisionTotal.getRegistrationStatus2());

		// Print reason Status Header
		Cell reason = cells.get(rowIndex, COLUMN_INDEX[6]);
		reason.setValue(divisionTotal.getReason());

		// Print confirm Status Header
		Cell confirm = cells.get(rowIndex, COLUMN_INDEX[7]);
		confirm.setValue(divisionTotal.getConfirmed());

		for (int c : COLUMN_INDEX) {
			Cell cell = cells.get(rowIndex, COLUMN_INDEX[c]);
			setTitleStyle(cell);
		}

	}

	/**
	 * Prints the Total A Department row.
	 *
	 * @param worksheets
	 *            the worksheets
	 * @param rowIndex
	 *            the row index
	 */
	private void printTotalADepartment(WorksheetCollection worksheets, int rowIndex,
			ComparingSalaryBonusReportData comparingQuery, int i) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		DataRowComparingSalaryBonusDto totalA = comparingQuery.getLstDivisionTotal().get(i);
		// Set Row height
		cells.setRowHeightPixel(rowIndex, ROW_HEIGHT);

		// Print itemName Header
		Cell itemName = cells.get(rowIndex, COLUMN_INDEX[0]);
		itemName.setValue(totalA.getItemName());

		// Print Month1 Header
		Cell month1 = cells.get(rowIndex, COLUMN_INDEX[1]);
		month1.setValue(totalA.getMonth1());

		// Print Month2 Header
		Cell month2 = cells.get(rowIndex, COLUMN_INDEX[2]);
		month2.setValue("");

		// Print Diffferent Salary Header
		Cell different = cells.get(rowIndex, COLUMN_INDEX[3]);
		different.setValue("");

		// Print RegistrationStatus1 Header
		Cell registration1 = cells.get(rowIndex, COLUMN_INDEX[4]);
		registration1.setValue("");

		// Print RegistrationStatus2 Status Header
		Cell registration2 = cells.get(rowIndex, COLUMN_INDEX[5]);
		registration2.setValue("");

		// Print reason Status Header
		Cell reason = cells.get(rowIndex, COLUMN_INDEX[6]);
		reason.setValue("");

		// Print confirm Status Header
		Cell confirm = cells.get(rowIndex, COLUMN_INDEX[7]);
		confirm.setValue("");

		for (int c : COLUMN_INDEX) {
			Cell cell = cells.get(rowIndex, COLUMN_INDEX[c]);
			setTitleStyle(cell);
		}

	}

	/**
	 * Prints the Total C Department row.
	 *
	 * @param worksheets
	 *            the worksheets
	 * @param rowIndex
	 *            the row index
	 */
	private void printTotalCDepartment(WorksheetCollection worksheets, int rowIndex,
			ComparingSalaryBonusReportData comparingQuery, int i) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		DataRowComparingSalaryBonusDto totalC = comparingQuery.getLstTotalC().get(i);
		// Set Row height
		cells.setRowHeightPixel(rowIndex, ROW_HEIGHT);

		// Print itemName Header
		Cell itemName = cells.get(rowIndex, COLUMN_INDEX[0]);
		itemName.setValue(totalC.getItemName());

		// Print Month1 Header
		Cell month1 = cells.get(rowIndex, COLUMN_INDEX[1]);
		month1.setValue("");

		// Print Month2 Header
		Cell month2 = cells.get(rowIndex, COLUMN_INDEX[2]);
		month2.setValue("");

		// Print Diffferent Salary Header
		Cell different = cells.get(rowIndex, COLUMN_INDEX[3]);
		different.setValue(totalC.getDifferentSalary());

		// Print RegistrationStatus1 Header
		Cell registration1 = cells.get(rowIndex, COLUMN_INDEX[4]);
		registration1.setValue("");

		// Print RegistrationStatus2 Status Header
		Cell registration2 = cells.get(rowIndex, COLUMN_INDEX[5]);
		registration2.setValue("");

		// Print reason Status Header
		Cell reason = cells.get(rowIndex, COLUMN_INDEX[6]);
		reason.setValue("");

		// Print confirm Status Header
		Cell confirm = cells.get(rowIndex, COLUMN_INDEX[7]);
		confirm.setValue("");

		for (int c : COLUMN_INDEX) {
			Cell cell = cells.get(rowIndex, COLUMN_INDEX[c]);
			setTitleStyle(cell);
		}

	}

	/**
	 * Prints the Grand total row.
	 *
	 * @param worksheets
	 *            the worksheets
	 * @param rowIndex
	 *            the row index
	 */
	private void printGrandTotal(WorksheetCollection worksheets, int rowIndex,
			ComparingSalaryBonusReportData comparingQuery) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		DataRowComparingSalaryBonusDto grandTotal = comparingQuery.getGrandTotal();
		// Set Row height
		cells.setRowHeightPixel(rowIndex, ROW_HEIGHT);

		// Print itemName Header
		Cell itemName = cells.get(rowIndex, COLUMN_INDEX[0]);
		itemName.setValue(grandTotal.getItemName());

		// Print Month1 Header
		Cell month1 = cells.get(rowIndex, COLUMN_INDEX[1]);
		month1.setValue(grandTotal.getMonth1());

		// Print Month2 Header
		Cell month2 = cells.get(rowIndex, COLUMN_INDEX[2]);
		month2.setValue(grandTotal.getMonth2());

		// Print Diffferent Salary Header
		Cell different = cells.get(rowIndex, COLUMN_INDEX[3]);
		different.setValue(grandTotal.getDifferentSalary());

		// Print RegistrationStatus1 Header
		Cell registration1 = cells.get(rowIndex, COLUMN_INDEX[4]);
		registration1.setValue(grandTotal.getRegistrationStatus1());

		// Print RegistrationStatus2 Status Header
		Cell registration2 = cells.get(rowIndex, COLUMN_INDEX[5]);
		registration2.setValue(grandTotal.getRegistrationStatus2());

		// Print reason Status Header
		Cell reason = cells.get(rowIndex, COLUMN_INDEX[6]);
		reason.setValue(grandTotal.getReason());

		// Print confirm Status Header
		Cell confirm = cells.get(rowIndex, COLUMN_INDEX[7]);
		confirm.setValue(grandTotal.getConfirmed());

		for (int c : COLUMN_INDEX) {
			Cell cell = cells.get(rowIndex, COLUMN_INDEX[c]);
			setTitleStyle(cell);
		}

	}

	/**
	 * Creates the range.
	 *
	 * @param cells
	 *            the cells
	 * @param firstRow
	 *            the first row
	 * @param totalRows
	 *            the total row
	 */
	private void createRange(Cells cells, int firstRow, int totalRow) {
		for (int i = FIRST_COLUMN; i < AMOUNT_COLUMN; i++) {
			Range range = cells.createRange(firstRow, i, totalRow, 1);
			range.setOutlineBorders(CellBorderType.THIN, Color.getGray());
		}
	}

	/**
	 * Sets the title style.
	 *
	 * @param cell
	 *            the new title style
	 */
	private void setTitleStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setForegroundColor(Color.fromArgb(197, 241, 247));
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getGray());
		cell.setStyle(style);
	}

	/**
	 * Sets the backgroundcolor.
	 *
	 * @param cell
	 *            the new backgroundcolor
	 */
	private void setBackgroundcolor(Cell cell) {
		Style style = cell.getStyle();
		style.setForegroundColor(Color.fromArgb(199, 243, 145));
		style.setPattern(BackgroundType.SOLID);
		style.setTextDirection(TextDirectionType.LEFT_TO_RIGHT);
		style.setVerticalAlignment(TextAlignmentType.TOP);
		style.setHorizontalAlignment(TextAlignmentType.LEFT);
		cell.setStyle(style);
	}

}
