package nts.uk.file.com.infra.generate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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
import nts.uk.file.com.app.EmployeeUnregisterOutputDataSoure;
import nts.uk.file.com.app.EmployeeUnregisterOutputGenerator;
import nts.uk.file.com.app.HeaderEmployeeUnregisterOutput;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeEmployeeUnregisterOutputReportGenerator extends AsposeCellsReportGenerator
		implements EmployeeUnregisterOutputGenerator {

	private static final String TEMPLATE_FILE = "report/1.xlsx";

	private static final String REPORT_FILE_NAME = "CMM018.xlsx";
	
	/** The Constant REPORT_FILE_NAME. */
	private static final String EXTENSION = ".xlsx";

	private static final String REPORT_ID = "ReportSample";

	/** The Constant HEADER. */
	private static final String HEADER = "HEADER";

	private static final int AMOUNT_COLUMN = 5;

	private static final int FIRST_COLUMN = 0;

	/** The Constant FIRST_ROW_INDEX. */
	private int FIRST_ROW_INDEX = 5;

	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4 };

	private static final int ROW_HEIGHT = 28;

	@Override
	public void generate(FileGeneratorContext generatorContext, EmployeeUnregisterOutputDataSoure dataSource) {
		Object x = this.getClass().getClassLoader().getResourceAsStream(TEMPLATE_FILE);
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {
			
			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			Cells cells = worksheet.getCells();

			// set up page prepare print
			this.printPage(worksheet);

			// set title of table
			this.setHeaderPage(worksheets, cells, dataSource, 0);

			// set header of page
			designer.getDesigner().setDataSource(HEADER, Arrays.asList(dataSource.getHeaderEmployee()));
			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();
			
			designer.saveAsExcel(this.createNewFile(generatorContext,
					this.getReportName(REPORT_FILE_NAME)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * PRINT PAGE
	 * 
	 * @param worksheet
	 * @param lstDeparmentInf
	 */
	private void printPage(Worksheet worksheet) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setPrintArea("A1:G");
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
			EmployeeUnregisterOutputDataSoure employee) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		HeaderEmployeeUnregisterOutput headerTable = employee.getHeaderEmployee();
		// Set Row height
		cells.setRowHeightPixel(rowIndex, ROW_HEIGHT);

		// Print target Header
		// merge start rowIndex, start columIndex, totalRow, totalCol, conflict
		cells.merge(rowIndex, 0, 1, 2, true);
		Cell target = cells.get(rowIndex, COLUMN_INDEX[0]);
		target.setValue(headerTable.getEmployee());

		// Print workplace code Header
		Cell workplaceCode = cells.get(rowIndex, COLUMN_INDEX[2]);
		workplaceCode.setValue(headerTable.getWorkplaceCode());

		// Print workplace name Header
		Cell workplaceName = cells.get(rowIndex, COLUMN_INDEX[3]);
		workplaceName.setValue(headerTable.getWorkplaceName());

		// Print application name Header
		Cell appName = cells.get(rowIndex, COLUMN_INDEX[4]);
		appName.setValue(headerTable.getAppName());

		for (int c : COLUMN_INDEX) {
			Cell cell = cells.get(rowIndex, COLUMN_INDEX[c]);
			setTitleStyle(cell);
		}

	}

	/**
	 * SET HEADER
	 * 
	 * @param worksheets
	 * @param cells
	 * @param reportData
	 * @param sheetIndex
	 */
	private void setHeaderPage(WorksheetCollection worksheets, Cells cells,
			EmployeeUnregisterOutputDataSoure reportData, int sheetIndex) {
		Worksheet worksheet = worksheets.get(sheetIndex);
		createRange(cells, FIRST_ROW_INDEX, 1);
		this.printTitleRow(worksheets, FIRST_ROW_INDEX, reportData);
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
