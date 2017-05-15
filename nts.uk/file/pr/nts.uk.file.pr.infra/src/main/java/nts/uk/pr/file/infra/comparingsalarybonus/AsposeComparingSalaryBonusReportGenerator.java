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
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Range;
import com.aspose.cells.SaveFormat;
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
	private int FIRST_ROW_INDEX = 9;

	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	private static final int[] COLUMN_INDEX_DEPARTMENT = { 0 };
	private static final int[] COLUMN_INDEX_EMPLOYEE = { 0 };

	private static final int ROW_HEIGHT = 28;
	// number of Row will start
	private int startRow = 0;
	private int maxRowOfDepartments = 0;

	@Override
	public void generate(FileGeneratorContext fileContext, ComparingSalaryBonusReportData reportData) {
		List<DeparmentInf> lstDeparmentInf = reportData.getDeparmentInf();
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			Cells cells = worksheet.getCells();
			
			// Set print page
            this.printPage(worksheet, lstDeparmentInf);
            
            // set title of table
		    this.setHeaderPage(worksheets, cells, reportData);
		    
		    // set header of page
			designer.getDesigner().setDataSource(HEADER, Arrays.asList(reportData.getHeaderData()));
			DateFormat dateFM = new SimpleDateFormat("yyyyMMddhhssmm");
			Date date = new Date();
			String fileName = REPORT_FILE_NAME.concat(dateFM.format(date).toString()).concat(EXTENSION);

			// Fill data
			lstDeparmentInf.stream().forEach(c -> {
				this.maxRowOfDepartments = 1 + c.getLstEmployee().size() * c.getLstEmployee().get(0).getLstData().size() + 4;
			});
			while (maxRowOfDepartments > 0) {
				FIRST_ROW_INDEX = FIRST_ROW_INDEX + 1;
				printDepartment(worksheets, FIRST_ROW_INDEX, reportData);
				// print grand total
				createRange(cells, this.startRow - 1, 1);
				printGrandTotal(worksheets, this.startRow - 1, reportData);
				maxRowOfDepartments -= 46;
			}
			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();
			PdfSaveOptions saveOptions = new PdfSaveOptions(SaveFormat.PDF);
			saveOptions.setAllColumnsInOnePagePerSheet(true);
			designer.getWorkbook().save(this.createNewFile(fileContext, fileName), saveOptions);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * PRINT PAGE
	 * @param worksheet
	 * @param lstDeparmentInf
	 */
	private void printPage(	Worksheet worksheet , List<DeparmentInf> lstDeparmentInf){
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		int total = FIRST_ROW_INDEX + lstDeparmentInf.size() * (7+ lstDeparmentInf.get(0).getLstEmployee().size()
				* (lstDeparmentInf.get(0).getLstEmployee().get(0).getLstData().size() + 1)) +1;
		System.out.println(total);
		int numberOfPage = this.caclulatePage(total);
		System.out.println(numberOfPage);
		pageSetup.setPrintArea("A1:H" + numberOfPage * 55);
	}
	
	private void setHeaderPage(WorksheetCollection worksheets,Cells cells, ComparingSalaryBonusReportData reportData){
		Worksheet worksheet = worksheets.get(0);
		createRange(cells, FIRST_ROW_INDEX, 1);
		this.printTitleRow(worksheets, FIRST_ROW_INDEX, reportData);
		
		// Set header.
		DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd hh:mm");
		worksheet.getPageSetup().setHeader(2,
				"&\"Times New Roman,Bold\"&12&F" + dateFormat.format(new Date()) + "\r\n" + "&P" + " ページ");
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
			this.setBorderDataRow(cells, rowIndex);
			// print employee
			createContent(worksheets, rowIndex + 1, comparingQuery.getDeparmentInf());
			rowIndex = this.startRow;
			// print total Department
			createRange(cells, this.startRow - 1, 1);
			printTotalDepartment(worksheets, this.startRow - 1, comparingQuery, i);

			// print total A
			createRange(cells, this.startRow, 1);
			printTotalADepartment(worksheets, this.startRow, comparingQuery, i);
			this.startRow = this.startRow + 1;
			rowIndex = this.startRow;
		}

	}

	/**
	 * Creates the content.
	 *
	 * @param cells
	 *            the cells
	 * @param startRowIndex
	 *            the first row index
	 * @param lstDataRow
	 *            the accumulated payment list
	 */
	private void createContent(WorksheetCollection worksheets, int startRowIndex, List<DeparmentInf> lstDepartment) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();

		// Set Row height
		cells.setRowHeightPixel(startRowIndex, ROW_HEIGHT);

		DeparmentInf deparmentInf = lstDepartment.get(0);
		for (int i = 0; i < deparmentInf.getLstEmployee().size(); i++) {

			DetailEmployee detailEmployeeInf = deparmentInf.getLstEmployee().get(i);

			// Print employee Code
			cells.merge(startRowIndex, 0, 1, 8);
			Cell employeeCode = cells.get(startRowIndex, COLUMN_INDEX_EMPLOYEE[0]);
			employeeCode.setValue(
					"部門コード : " + detailEmployeeInf.getPersonID() + "社員名: " + detailEmployeeInf.getPersonName());
			this.setBorderDataRow(cells, startRowIndex);
			// print Data Row
			int numberOfItemCode = detailEmployeeInf.getLstData().size();
			this.createRange(cells, startRowIndex + 1, numberOfItemCode);
			for (int j = 0; j < numberOfItemCode; j++) {
				DataRowComparingSalaryBonusDto dataRow = detailEmployeeInf.getLstDataDto().get(j);
				this.setDataRow(cells, dataRow, startRowIndex + 1);
				// Set Background Color for odd rows
				if ((j % 2) == 1) {
					for (int c : COLUMN_INDEX) {
						Cell oddCell = cells.get(startRowIndex, COLUMN_INDEX[c]);
						setBackgroundcolor(oddCell);
					}
				}

				startRowIndex++;
			}
			startRowIndex = startRowIndex + 1;
		}
		this.startRow = startRowIndex + 1;
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
		this.setDataRow(cells, divisionTotal, rowIndex);
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
		DataRowComparingSalaryBonusDto totalA = comparingQuery.getLstTotalA().get(i);
		this.setDataRow(cells, totalA, rowIndex);
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
		this.setDataRow(cells, grandTotal, rowIndex);
		for (int c : COLUMN_INDEX) {
			Cell cell = cells.get(rowIndex, COLUMN_INDEX[c]);
			Style style = cell.getStyle();
			style.setForegroundColor(Color.fromArgb(197, 241, 247));
			style.setPattern(BackgroundType.SOLID);
			style.setBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getGray());
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
			style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getGray());
			style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getGray());
			cell.setStyle(style);
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
	 *  the new title style
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

	/**
	 * caculate number of Page
	 * 
	 * @param total
	 * @return
	 */
	private int caclulatePage(int total) {
		int numberOfPage = total / 55;
		int numberOfRow = total - numberOfPage * 55;
		if (numberOfRow > 0) {
			numberOfPage = numberOfPage + 1;
		}
		return numberOfPage;
	}
	/**
	 * set Border Data Row of deparment and employee 
	 * @param cells
	 * @param startRow
	 */
	
	private void setBorderDataRow(Cells cells, int startRow){
		for (int c : COLUMN_INDEX) {
			Cell cell = cells.get(startRow, COLUMN_INDEX[c]);
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
	}
	
	/**
	 * set Data Row of item code
	 * @param cells
	 * @param dataRow
	 * @param rowIndex
	 */
	
	private void setDataRow(Cells cells,DataRowComparingSalaryBonusDto  dataRow, int rowIndex){
		// Print itemName Header
		Cell itemName = cells.get(rowIndex, COLUMN_INDEX[0]);
		itemName.setValue(dataRow.getItemName());

		// Print Month1 Header
		Cell month1 = cells.get(rowIndex, COLUMN_INDEX[1]);
		month1.setValue(dataRow.getMonth1());

		// Print Month2 Header
		Cell month2 = cells.get(rowIndex, COLUMN_INDEX[2]);
		month2.setValue(dataRow.getMonth2());

		// Print Diffferent Salary Header
		Cell different = cells.get(rowIndex, COLUMN_INDEX[3]);
		different.setValue(dataRow.getDifferentSalary());

		// Print RegistrationStatus1 Header
		Cell registration1 = cells.get(rowIndex, COLUMN_INDEX[4]);
		registration1.setValue(dataRow.getRegistrationStatus1());

		// Print RegistrationStatus2 Status Header
		Cell registration2 = cells.get(rowIndex, COLUMN_INDEX[5]);
		registration2.setValue(dataRow.getRegistrationStatus2());

		// Print reason Status Header
		Cell reason = cells.get(rowIndex, COLUMN_INDEX[6]);
		reason.setValue(dataRow.getReason());

		// Print confirm Status Header
		Cell confirm = cells.get(rowIndex, COLUMN_INDEX[7]);
		confirm.setValue(dataRow.getConfirmed());

		
	}

}
