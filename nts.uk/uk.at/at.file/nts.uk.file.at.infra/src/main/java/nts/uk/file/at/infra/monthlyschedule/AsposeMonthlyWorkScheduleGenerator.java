/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.monthlyschedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.PageSetup;
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
	private static final int CHUNK_SIZE = 16;

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
			Worksheet sheet = workbook.getWorksheets().get(0);
			WorksheetCollection sheetCollection = workbook.getWorksheets();

			this.setPageHeader(sheet, GeneralDate.today(), GeneralDate.today(), "LOL");

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

			// Save workbook
			if (query.getFileType() == FileOutputType.FILE_TYPE_EXCEL)
				reportContext.saveAsExcel(this.createNewFile(generatorContext,
						WorkScheOutputConstants.SHEET_FILE_NAME + "_" + currentFormattedDate + ".xlsx"));
			else {
				reportContext.saveAsPdf(this.createNewFile(generatorContext,
						WorkScheOutputConstants.SHEET_FILE_NAME + "_" + currentFormattedDate + ".pdf"));
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
		pageSetup.setHeader(0, "&8 " + companyName);

		// Set header date
		DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm", Locale.JAPAN);
		pageSetup.setHeader(2, "&8 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P ");

		Cells cells = sheet.getCells();
		Cell periodCell = cells.get(0, 0);

		DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("yyyy/M/d (E)", Locale.JAPAN);
		String periodStr = WorkScheOutputConstants.PERIOD + " " + startDate.toLocalDate().format(jpFormatter) + " ～ "
				+ endDate.toLocalDate().format(jpFormatter);
		periodCell.setValue(periodStr);
	}

	/**
	 * Sets the item header.
	 */
	private void setItemHeader() {

	}
}