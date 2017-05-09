package nts.uk.pr.file.infra.comparingsalarybonus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DeparmentInf;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DetailEmployee;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.EmployeeInf;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.HeaderTable;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeComparingSalaryBonusReportGenerator extends AsposeCellsReportGenerator
		implements ComparingSalaryBonusGenerator {
	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/ComparingSalaryBonusTemplate.xlsx";

	/** The Constant REPORT_FILE_NAME. */
	private static final String REPORT_FILE_NAME = "QPP008_";
	/** The Constant REPORT_FILE_NAME. */
	private static final String EXTENSION = ".pdf";
	/** The Constant DATE_TIME_FORMAT. */
	private static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
	/** The Constant HEADER. */
	private static final String HEADER = "HEADER";

	private static final int AMOUNT_COLUMN = 8;

	private static final int FIRST_COLUMN = 0;

	/** The Constant FIRST_ROW_INDEX. */
	private static final int FIRST_ROW_INDEX = 9;

	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7 };

	private static final int[] COLUMN_INDEX_DEPARTMENT = { 0, 1 };
	private static final int[] COLUMN_INDEX_EMPLOYEE = { 0, 1 };

	private static final int ROW_HEIGHT = 28;
	@Override
	public void generate(FileGeneratorContext fileContext, ComparingSalaryBonusReportData reportData) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			Cells cells = worksheet.getCells();

			// Set header.
			DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd hh:mm");
			
			worksheet.getPageSetup().setHeader(0, dateFormat.format(new Date()));

			designer.getDesigner().setDataSource(HEADER, Arrays.asList(reportData.getHeaderData()));
			DateFormat dateFM = new SimpleDateFormat("yyyyMMddhhssmm");
			Date date = new Date();
			String fileName = REPORT_FILE_NAME.concat(dateFM.format(date).toString()).concat(EXTENSION);

			// Fill data
			// List Item Data
			int firstRowIndex = FIRST_ROW_INDEX;
			createRange(cells, firstRowIndex, 1);
			printTitleRow(worksheets, firstRowIndex, reportData);
			printDepartment(worksheets, 11, reportData);
			// Set Print Area				
			PageSetup pageSetup = worksheet.getPageSetup();
			pageSetup.setPrintArea("A1:G" + 100);	
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

		DeparmentInf departInf = comparingQuery.getDeparmentInf().get(0);
		// Print Department code
		Cell departmentCode = cells.get(rowIndex, COLUMN_INDEX_DEPARTMENT[0]);
		departmentCode.setValue("部門：" + " " + departInf.getDepcode());
		
		// Print Department name
		Cell departmentName = cells.get(rowIndex, COLUMN_INDEX_DEPARTMENT[1]);
		departmentName.setValue(" " + departInf.getDepname());
		
		Style style = cells.getStyle();
		style.setForegroundColor(Color.fromArgb(197, 241, 247));
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
		cells.setStyle(style);

		// print employee
		 createContent(worksheets, rowIndex + 1, comparingQuery.getDeparmentInf());

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
	private void createContent(WorksheetCollection worksheets,  int firstRowIndex, List<DeparmentInf> lstDepartment) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		
		// Set Row height
		cells.setRowHeightPixel(firstRowIndex, ROW_HEIGHT);

		DeparmentInf deparmentInf = lstDepartment.get(0);
		for (int i = 0; i < deparmentInf.getLstEmployee().size(); i++) {
			DetailEmployee detailEmployeeInf  =deparmentInf.getLstEmployee().get(0);
			
			// Print employee Code
			cells.setColumnWidth(0,200.0);
			Cell employeeCode = cells.get(firstRowIndex, COLUMN_INDEX_EMPLOYEE[0]);
			employeeCode.setValue("部門コード : " + detailEmployeeInf.getPersonID());

			
			//print employee Name
			Cell employeeName = cells.get(firstRowIndex, COLUMN_INDEX_EMPLOYEE[1]);
		    employeeName.setValue(" 社員名: " + detailEmployeeInf.getPersonName());
		    cells.setColumnWidth(0, 50.0);
			this.createRange(cells, firstRowIndex + 1, 6);
			for (int j = 0; j < detailEmployeeInf.getLstData().size(); j++) {

				DataRowComparingSalaryBonus dataRow = detailEmployeeInf.getLstData().get(j);
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
			firstRowIndex = firstRowIndex + 2;
		}
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
