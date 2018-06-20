/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.monthlyschedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.file.at.app.export.dailyschedule.FileOutputType;
import nts.uk.file.at.app.export.monthlyschedule.MonthlyWorkScheduleGenerator;
import nts.uk.file.at.app.export.monthlyschedule.MonthlyWorkScheduleQuery;
import nts.uk.file.at.infra.dailyschedule.WorkScheOutputConstants;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeMonthlyWorkScheduleGenerator.
 */
@Stateless
public class AsposeMonthlyWorkScheduleGenerator extends AsposeCellsReportGenerator
		implements MonthlyWorkScheduleGenerator {

	/** The Constant TEMPLATE. */
	private static final String TEMPLATE = "report/KWR006.xlsx";

	/** The Constant CHUNK_SIZE. */
	private static final int CHUNK_SIZE = 13;

	private static final int COL_MERGED_SIZE = 2;

	/** The Constant SHEET_FILE_NAME. */
	private static final String SHEET_FILE_NAME = "日別勤務表";

	/** The Constant DATA_COLUMN_INDEX. */
	private static final int[] DATA_COLUMN_INDEX = { 3, 8, 10, 14, 16, 39 };

	/** The font family. */
	private final String FONT_FAMILY = "ＭＳ ゴシック";

	/** The font size. */
	private final int FONT_SIZE = 9;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator#
	 * generate(nts.uk.file.at.app.export.dailyschedule.
	 * WorkScheduleOutputCondition,
	 * nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo,
	 * nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery)
	 */
	@Override
	public void generate(FileGeneratorContext generatorContext, MonthlyWorkScheduleQuery query) {
		val reportContext = this.createContext(TEMPLATE);

		Workbook workbook;

		try {
			workbook = new Workbook(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEMPLATE));

			workbook = reportContext.getWorkbook();
			WorksheetCollection sheetCollection = workbook.getWorksheets();
			Worksheet sheet = sheetCollection.get(0);

			//this.setPageHeader(sheet, GeneralDate.today(), GeneralDate.today(), "LOL");
			//this.setItemHeader(sheet.getCells(), sheetCollection);

			//this.printData(sheet.getCells(), sheetCollection);

			// Rename sheet
			sheet.setName(WorkScheOutputConstants.SHEET_FILE_NAME);

			// Move to first position
			sheet.moveTo(0);

			// Delete the rest of workbook
			int sheetCount = sheetCollection.getCount();
			for (int i = sheetCount - 1; i > 0; i--) {
				sheetCollection.removeAt(i);
			}

			WorkbookDesigner designer = new WorkbookDesigner();
			designer.setWorkbook(workbook);

			// Process designer
			reportContext.processDesigner();

			// Get current date and format it
			DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.JAPAN);
			String currentFormattedDate = LocalDateTime.now().format(jpFormatter);
			String fileName = WorkScheOutputConstants.SHEET_FILE_NAME + "_" + currentFormattedDate;

			// Save workbook
			if (query.getFileType() == FileOutputType.FILE_TYPE_EXCEL)
				reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + ".xlsx"));
			else {
				reportContext.saveAsPdf(this.createNewFile(generatorContext, fileName + ".pdf"));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Sets the page header.
	 *
	 * @param sheet the sheet
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param companyName the company name
	 */
	private void setPageHeader(Worksheet sheet, GeneralDate startDate, GeneralDate endDate, String companyName) {
		PageSetup pageSetup = sheet.getPageSetup();
		Cells cells = sheet.getCells();

		// set company name
		pageSetup.setHeader(0, "&8 " + companyName);

		// set title header
		pageSetup.setHeader(1, "&8 " + TextResource.localize("KWR006_1"));

		// Set header date time + page number
		DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm", Locale.JAPAN);
		pageSetup.setHeader(2, "&8 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P ");

		// Set period cell
		Cell periodCell = cells.get(0, 0);
		DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("yyyy/M/d (E)", Locale.JAPAN);
		String periodStr = WorkScheOutputConstants.PERIOD + " " + startDate.toLocalDate().format(jpFormatter) + " ～ "
				+ endDate.toLocalDate().format(jpFormatter);
		periodCell.setValue(periodStr);
	}

	/**
	 * Sets the item header.
	 */
	private void setItemHeader(Cells cells, WorksheetCollection sheetCollection) {
		// Set item header (A2_1/B2_1) 
		Cell itemHeader = cells.get("A3");
		itemHeader.setValue(TextResource.localize("KWR006_65")); // yearmonth
		//itemHeader.setValue(TextResource.localize("KWR006_76")); // personal name

		// remark header
		Range remarkHeader = sheetCollection.getRangeByName("REMARK");
		remarkHeader.setValue(TextResource.localize("KWR006_67"));

		// create mock data.
		List<String> mock = new ArrayList<>();
		int count = 0;
		while (count < 50) {
			mock.add("test-item"+count);
			count++;
		}

		int currentRow = 2;
		int currentCol = 3;
		final int finalCol = currentCol + CHUNK_SIZE * COL_MERGED_SIZE;
		Iterator<String> iterator = mock.iterator();

		while (iterator.hasNext()) {
			if (currentCol == finalCol) {
				currentRow++;
				// reset col
				currentCol = 3;
			}

			String string = iterator.next();
			Cell currentCell = cells.get(currentRow, currentCol);
			currentCell.setValue(string);
			currentCol += COL_MERGED_SIZE;
		}

	}

	private void printData(Cells cells, WorksheetCollection sheetCollection) throws Exception {
		// create mock data.
		List<String> mock = new ArrayList<>();
		int count = 0;
		while (count < 80) {
			mock.add("test-value"+count);
			count++;
		}

		// print header data
		int currentRow = 8;
		// workplace
		Range workplaceRangeTempplate = sheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WORKPLACE_ROW);
		Range workplaceRange = cells.createRange(currentRow, 0, 1, 39);
		workplaceRange.copy(workplaceRangeTempplate);

		// A3_1
		Cell workplaceTagCell = cells.get(currentRow, 0);
		workplaceTagCell.setValue(TextResource.localize("KWR006_68"));
		
		// A3_2
		Cell workplaceInfo = cells.get(currentRow, DATA_COLUMN_INDEX[0]);
		workplaceInfo.setValue("test test");

		// employee
		currentRow++;

		Range employeeRangeTemp = sheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_EMPLOYEE_ROW);
		Range employeeRange = cells.createRange(currentRow, 0, 1, 39);
		employeeRange.copy(employeeRangeTemp);

		// A4_1
		Cell employeeTagCell = cells.get(currentRow, 0);
		employeeTagCell.setValue(TextResource.localize("KWR006_69"));
		
		// A4_2
		Cell employeeCell = cells.get(currentRow, 3);
		employeeCell.setValue("Code + name");
		
		// A4_3
		Cell employmentTagCell = cells.get(currentRow, 9);
		employmentTagCell.setValue(WorkScheOutputConstants.EMPLOYMENT);
		
		// A4_5
		Cell employmentCell = cells.get(currentRow, 11);
		employmentCell.setValue("name");
		
		// A4_6
		Cell jobTitleTagCell = cells.get(currentRow, 15);
		jobTitleTagCell.setValue(WorkScheOutputConstants.POSITION);

		// A4_7
		Cell jobTitleCell = cells.get(currentRow, 17);
		jobTitleCell.setValue("Job");

		// print data
		currentRow++;

		int currentCol = 3;
		int rowCount = 1;
		final int maxRowCount = 3;
		final int finalCol = currentCol + CHUNK_SIZE * COL_MERGED_SIZE;
		Iterator<String> iterator = mock.iterator();

		// range template
		Range whiteRange = sheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WHITE_ROW + "3");
		Range blueRange = sheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_LIGHTBLUE_ROW + "3");
		boolean isWhite = true;
		
		while (iterator.hasNext()) {
			if (rowCount == 1) {
				Range itemRange = cells.createRange(currentRow, 0, maxRowCount, 39);
				if (isWhite) {
					itemRange.copy(whiteRange);
				} else {
					itemRange.copy(blueRange);
				}
			}

			// set value to cell
			String string = iterator.next();
			Cell currentCell = cells.get(currentRow, currentCol);
			currentCell.setValue(string);
			currentCol += COL_MERGED_SIZE;

			if (currentCol == finalCol) {
				// next row
				rowCount++;
				currentRow++;
				// reset col
				currentCol = 3;
			}

			if (rowCount == 4) {
				// reset row count
				rowCount = 1;
				isWhite = !isWhite;
			}
		}
		
	}
}