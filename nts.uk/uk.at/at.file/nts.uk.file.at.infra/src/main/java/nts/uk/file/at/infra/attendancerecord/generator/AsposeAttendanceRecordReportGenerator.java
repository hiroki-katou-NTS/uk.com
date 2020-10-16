package nts.uk.file.at.infra.attendancerecord.generator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Color;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.PageOrientationType;
import com.aspose.cells.PageSetup;
import com.aspose.cells.PaperSizeType;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.VerticalPageBreakCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportFontSize;
import nts.uk.file.at.app.export.attendancerecord.AttendanceRecordReportDatasource;
import nts.uk.file.at.app.export.attendancerecord.AttendanceRecordReportGenerator;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportColumnData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportDailyData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportEmployeeData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportWeeklyData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportWeeklySumaryData;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeAttendanceRecordReportGenerator.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeAttendanceRecordReportGenerator extends AsposeCellsReportGenerator
		implements AttendanceRecordReportGenerator {

	/** The Constant TEMPLATE_FILE. */
	private String TEMPLATE_FILE = "report/KWR002.xlsx";

	/** The Constant PDF_EXT. */
	private static final String PDF_EXT = ".pdf";

	/** The Constant EXCEL_EXT. */
	private static final String EXCEL_EXT = ".xlsx";

	private static final String REPORT_START_PAGE_ROW = "REPORT_START_PAGE_ROW";

	/** The Constant REPORT_LEFT_ROW. */
	private static final String REPORT_LEFT_ROW = "REPORT_LEFT_ROW";

	/** The Constant REPORT_RIGHT_ROW. */
	private static final String REPORT_RIGHT_ROW = "REPORT_RIGHT_ROW";

	/** The Constant REPORT_ROW_BG. */
	private static final String REPORT_ROW_BG = "REPORT_ROW_BG";

	/** The Constant REPORT_ROW_START_RIGHT. */
	private static final String REPORT_ROW_START_RIGHT = "REPORT_ROW_START_RIGHT";

	/** The Constant REPORT_PAGE_ADDR. */
	private String REPORT_PAGE_ADDR = "A1:AO";

	/** The Constant MONTHLY_DATA_ADDR. */
	private String MONTHLY_DATA_ADDR = "C%d:Z%d";

	/** The Constant DAILY_W_RANGE_TMPL_ADDR. */
	private String DAILY_W_RANGE_TMPL_ADDR = "AQ1:BJ2";

	/** The Constant DAILY_B_RANGE_TMPL_ADDR. */
	private String DAILY_B_RANGE_TMPL_ADDR = "AQ4:BJ5";

	/** The Constant WEEKLY_RANGE_TMPL_ADDR. */
	private String WEEKLY_RANGE_TMPL_ADDR = "AQ11:BJ12";

	/** The Constant SEAL_RANGE_TMPL_ADDR. */
	private String SEAL_RANGE_TMPL_ADDR = "AQ14:AR17";

	/** The Constant SEAL_COL_ADDR. */
	private List<String> SEAL_COL_ADDR = Arrays
			.asList(new String[] { "AN1", "AL1", "AJ1", "AH1", "AF1", "AD1" });
	
	/** The Constant END_REPORT_COL2. */
	private String END_REPORT_PAGE_BREAK= "AP";

	/** The Constant REPORT_LEFT_COL_ADDR. font size large */
	private String REPORT_LEFT_COL_ADDR = "A%d:T%d";
	
	/** The Constant REPORT_LEFT_COL_ADDR. font size medium*/
//	private static final String REPORT_LEFT_COL_ADDR = "A%d:V%d";

	/** The Constant REPORT_RIGHT_COL_ADDR. */
	private String REPORT_RIGHT_COL_ADDR = "V%d:AO%d";
	
	/** The Constant PRINT_TITLE_ROW. */
	private static final String PRINT_TITLE_ROW = "$6:$7";

	/** The Constant START_EMPLOYEE_DATA_ROW. */
	private static final int START_EMPLOYEE_DATA_ROW = 1;
	
	/** The Constant START_EMPLOYEE_CENTER_DATA_ROW. */
	private static final int START_EMPLOYEE_CENTER_DATA_ROW = 2;
	
	/** The Constant START_EMPLOYEE_BOTTOM_DATA_ROW. */
	private static final int START_EMPLOYEE_BOTTOM_DATA_ROW = 3;

	/** The Constant START_REPORT_DATA_ROW. in ra data .... */ 
	private static final int START_REPORT_DATA_ROW = 11;

	/** The Constant MAX_ROW_PER_EMPL. */
	private static final int MAX_ROW_PER_EMPL = 50;

	/** The Constant EMPL_INVIDUAL_INDEX. */
	private static final int EMPL_INVIDUAL_INDEX = 0;

	/** The Constant EMPL_WORKPLACE_INDEX. */
	private static final int EMPL_WORKPLACE_INDEX = 7;

	/** The Constant EMPL_EMPLOYMENT_INDEX. */
	private static final int EMPL_EMPLOYMENT_INDEX = 0;

	/** The Constant EMPL_TITLE_INDEX. */
	private static final int EMPL_TITLE_INDEX = 7;

	/** The Constant EMPL_WORKTYPE_INDEX. */
	private static final int EMPL_WORKTYPE_INDEX = 13;

	/** The Constant EMPL_YEARMONTH_INDEX. */
	private static final int EMPL_YEARMONTH_INDEX = 0;

	/** The Constant MONTHLY_DATA_START_ROW. */
	private int MONTHLY_DATA_START_ROW = 6;
	
	/** The Constant REPORT_ROW_BG_WHITE. */
	private static final int REPORT_ROW_BG_WHITE = 1;

	/** The Constant REPORT_ROW_BG_BLUE. */
	private static final int REPORT_ROW_BG_BLUE = 2;

	/** The Constant REPORT_ROW_START_RIGHT_COUNT. */
	private static final int REPORT_ROW_START_RIGHT_COUNT = 1;

	/** The Constant EXPORT_EXCEL. */
	private static final int EXPORT_EXCEL = 2;

	/** The Constant EXPORT_PDF. */
	private static final int EXPORT_PDF = 1;
	
	/** The font size. */
	private int FONT_SIZE = 16;
	
	/** The report approval */
	private String REPORT_APPROVAL = "AB7:AC7";
	
	private static final String APPROVAL = "確認済";
		
	private static final int MONTHLY_TITLE_START_ROW = 4;
	
	private String MONTHLY_TITLE_FIX = "C4:Z5";

	private String MONTHLY_CUMULATIVE_TOTAL_FIX = "A4:B7";
	
	private String REPORT_CUMULATIVE_FIX = "A%d:B%d";
	
	private String DAILY_TITLE_FIX_LEFT = "A9:T10";
	
	private String DAILY_TITLE_FIX_RIGHT = "V9:AO10";
	
	private static final int DAILY_TITLE_START_ROW = 9;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.at.app.export.attendancerecord.AttendanceRecordReportGenerator#
	 * generate(nts.arc.layer.infra.file.export.FileGeneratorContext,
	 * nts.uk.file.at.app.export.attendancerecord.AttendanceRecordReportDatasource)
	 */
	@Override
	public void generate(FileGeneratorContext generatorContext, AttendanceRecordReportDatasource dataSource) {

		AttendanceRecordReportData data = dataSource.getData();
		
		if (dataSource.getData().getFontSize() == ExportFontSize.CHAR_SIZE_MEDIUM.value) {
			TEMPLATE_FILE = "report/KWR002_FM.xlsx";
			
			MONTHLY_DATA_ADDR = "C%d:AD%d";
			
			REPORT_PAGE_ADDR = "A1:AW";
			
			REPORT_LEFT_COL_ADDR = "A%d:X%d";
			
			REPORT_RIGHT_COL_ADDR = "Z%d:AW%d";
			
			END_REPORT_PAGE_BREAK = "AX";
			
			DAILY_W_RANGE_TMPL_ADDR = "AY1:BV2";
			
			DAILY_B_RANGE_TMPL_ADDR = "AY4:BV5";
			
			WEEKLY_RANGE_TMPL_ADDR = "AY11:BV12";
			
			
			SEAL_RANGE_TMPL_ADDR = "AY14:AZ17";
			
			FONT_SIZE = 14;
			
			SEAL_COL_ADDR = Arrays
					.asList(new String[] { "AV1", "AT1", "AR1", "AP1", "AN1", "AL1" });
			
			REPORT_APPROVAL = "AF7:AG7";
			
		} else if( dataSource.getData().getFontSize() == ExportFontSize.CHARS_SIZE_SMALL.value) {
			TEMPLATE_FILE = "report/KWR002_FS.xlsx";
			
			MONTHLY_DATA_ADDR = "C%d:AH%d";
			
			REPORT_PAGE_ADDR = "A1:BE";
			
			REPORT_LEFT_COL_ADDR = "A%d:AB%d";
			
			REPORT_RIGHT_COL_ADDR = "AD%d:BE%d";
			
			END_REPORT_PAGE_BREAK = "BF";
			
			DAILY_W_RANGE_TMPL_ADDR = "BG1:CH2";
			
			DAILY_B_RANGE_TMPL_ADDR = "BG4:CH5";
			
			WEEKLY_RANGE_TMPL_ADDR = "BG11:CH12";
			
			
			SEAL_RANGE_TMPL_ADDR = "BG14:BH17";
			
			FONT_SIZE = 12;
			
			SEAL_COL_ADDR = Arrays
					.asList(new String[] { "BD1", "BB1", "AZ1", "AX1", "AV1", "AT1" });
			
			REPORT_APPROVAL = "AJ7:AK7";
		}

		try (val reportContext = this.createContext(TEMPLATE_FILE, data.getExportDateTime())) {

			// Get workbook
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheetCollection = workbook.getWorksheets();

			// set data source
			reportContext.setDataSource("companyName", dataSource.getData().getCompanyName());
			reportContext.setDataSource("reportName", dataSource.getData().getReportName());
			reportContext.setDataSource("exportDateTime", dataSource.getData().getExportDateTime());
			reportContext.setDataSource("reportMonthHead", dataSource.getData().getMonthlyHeader());
			reportContext.setDataSource("reportDayHead", dataSource.getData().getDailyHeader());
			// process data binginds in template
			reportContext.processDesigner();

			// Prepare template ranges
			Worksheet templateSheet = worksheetCollection.get(0);
			Range dailyWTmpl = templateSheet.getCells().createRange(DAILY_W_RANGE_TMPL_ADDR);
			Range dailyBTmpl = templateSheet.getCells().createRange(DAILY_B_RANGE_TMPL_ADDR);
			Range weeklyRangeTmpl = templateSheet.getCells().createRange(WEEKLY_RANGE_TMPL_ADDR);

			// Generate seal column
			// TODO : sửa lại phần này trong vòng for
			this.generateSealColumn(templateSheet, data.getSealColName());

			// Init report page
			int page = 1;

			// Start loop to create report for earch month
			Map<String, List<AttendanceRecordReportEmployeeData>> reportDatas = data.getReportData();
			// set sheet name report
			String sheetName = data.getReportName();
			// create new sheet from template sheet
			worksheetCollection.get(worksheetCollection.addCopy(0)).setName(sheetName);
			Worksheet worksheet = worksheetCollection.get(sheetName);
			int startNewPage = 0;
			for (String employeeCd : reportDatas.keySet()) {
				
				// get list employee data
				List<AttendanceRecordReportEmployeeData> reportEmployeeDatas = reportDatas.get(employeeCd);
				Range reportPageTmpl = templateSheet.getCells().createRange(REPORT_PAGE_ADDR + (MAX_ROW_PER_EMPL * reportDatas.keySet().size() * reportEmployeeDatas.size()));
				// Generate employee report page
				for (AttendanceRecordReportEmployeeData employeeData : reportEmployeeDatas) {
					startNewPage = this.generateEmployeeReportPage(startNewPage, worksheet, employeeData, page,
							reportPageTmpl, dailyWTmpl, dailyBTmpl, weeklyRangeTmpl);
					page++;

					// create print area
					PageSetup pageSetup = worksheet.getPageSetup();
					pageSetup.setPrintArea(REPORT_PAGE_ADDR + startNewPage);
					pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
					pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
					
					// Set header value
					pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&9 " + dataSource.getData().getCompanyName());
					pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&"+ FONT_SIZE + " " + dataSource.getData().getReportName());
					// Get current date and format it
					DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm", Locale.JAPAN);
					String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
					pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&9 " + currentFormattedDate+"\npage&P");
					
					pageSetup.setPrintTitleRows(PRINT_TITLE_ROW);
					if (dataSource.getMode() == EXPORT_EXCEL) {
						pageSetup.setZoom(100);
					} else if (dataSource.getMode() == EXPORT_PDF) {
						pageSetup.setFitToPagesTall(1);
						pageSetup.setFitToPagesWide(1);
					}
					startNewPage += MAX_ROW_PER_EMPL;
					
				}
				
			}
			// Delete template column
			if (dataSource.getData().getFontSize() == ExportFontSize.CHAR_SIZE_LARGE.value) {
				worksheet.getCells().deleteColumns(42, 20, true);
			} else if (dataSource.getData().getFontSize() == ExportFontSize.CHAR_SIZE_MEDIUM.value) {
				worksheet.getCells().deleteColumns(50, 24, true);
			} else {
				worksheet.getCells().deleteColumns(58, 28, true);
			}
			
			// delete template sheet
			worksheetCollection.removeAt(0);
			worksheetCollection.setActiveSheetIndex(0);
			// Create file name
			String fileName = data.getReportName() + "＿"
					+ data.getExportDateTime().replaceAll(" ", "_").replaceAll(":", "").replaceAll("/", "");

			if (dataSource.getMode() == EXPORT_EXCEL) {
				// save as excel file
				reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + EXCEL_EXT));
			} else if (dataSource.getMode() == EXPORT_PDF) {
				// save as PDF file
				reportContext.saveAsPdf(this.createNewFile(generatorContext, fileName + PDF_EXT));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Generate seal column.
	 *
	 * @param templateSheet
	 *            the template sheet
	 * @param sealColNames
	 *            the seal col names
	 * @throws Exception
	 *             the exception
	 */
	private void generateSealColumn(Worksheet templateSheet, List<String> sealColNames) throws Exception {
		Range sealColTmpl = templateSheet.getCells().createRange(SEAL_RANGE_TMPL_ADDR);

		for (int i = 0, j = sealColNames.size(); i < j; i++) {
			String sealColAddr = SEAL_COL_ADDR.get(j - i - 1);
			Range sealCol = templateSheet.getCells().createRange(sealColAddr);
			sealCol.copy(sealColTmpl);
			sealCol.get(0, 0).setValue(sealColNames.get(i));
		}
	}

	/**
	 * Generate employee report page.
	 *
	 * @param worksheet
	 *            the worksheet
	 * @param employeeData
	 *            the employee data
	 * @param page
	 *            the page
	 * @param pageTmpl
	 *            the page tmpl
	 * @param dailyWTmpl
	 *            the daily W tmpl
	 * @param dailyBTmpl
	 *            the daily B tmpl
	 * @param weeklyRangeTmpl
	 *            the weekly range tmpl
	 * @throws Exception
	 *             the exception
	 */
	private int generateEmployeeReportPage(int startNewPage, Worksheet worksheet,
			AttendanceRecordReportEmployeeData employeeData, int page, Range pageTmpl, Range dailyWTmpl,
			Range dailyBTmpl, Range weeklyRangeTmpl) throws Exception {

		// Add monthly data
		Range monththDataRange = worksheet.getCells().createRange(String.format(MONTHLY_DATA_ADDR,
				(startNewPage + MONTHLY_DATA_START_ROW), (startNewPage + MONTHLY_DATA_START_ROW + 1)));
		Range dataMonthRange = worksheet.getCells().createRange(String.format(MONTHLY_DATA_ADDR,
				(startNewPage + MONTHLY_DATA_START_ROW), (startNewPage + MONTHLY_DATA_START_ROW + 1)));
		
		// voi TH next page thi copy lai phan monthly header 
		if (startNewPage > 0) {
			// copy monthly header 
			Range fixMonthyHeader = worksheet.getCells().createRange(MONTHLY_TITLE_FIX);
			Range monthTitleRange = worksheet.getCells().createRange(String.format(MONTHLY_DATA_ADDR,
					(startNewPage + MONTHLY_TITLE_START_ROW), (startNewPage + MONTHLY_TITLE_START_ROW + 1)));
			monthTitleRange.copyData(fixMonthyHeader);
			monthTitleRange.copy(fixMonthyHeader);
			
			// copy lai KWR002_221 月間累計
			Range fixCumulativeTotal = worksheet.getCells().createRange(MONTHLY_CUMULATIVE_TOTAL_FIX);
			Range monthCumulativeRange = worksheet.getCells().createRange(String.format(REPORT_CUMULATIVE_FIX,
					(startNewPage + MONTHLY_TITLE_START_ROW), (startNewPage + MONTHLY_TITLE_START_ROW + 3)));
			monthCumulativeRange.copy(fixCumulativeTotal);
			monthCumulativeRange.copyData(fixCumulativeTotal);
			// copy daily header left
			Range fixLeftDailyHeader = worksheet.getCells().createRange(DAILY_TITLE_FIX_LEFT);
			Range leftDailyTitleRange = worksheet.getCells().createRange(String.format(REPORT_LEFT_COL_ADDR,
					(startNewPage + DAILY_TITLE_START_ROW), (startNewPage + DAILY_TITLE_START_ROW + 1)));
			leftDailyTitleRange.copy(fixLeftDailyHeader);
			leftDailyTitleRange.copyData(fixLeftDailyHeader);
			
			// copy daily header right 
			Range fixRightDailyHeader = worksheet.getCells().createRange(DAILY_TITLE_FIX_RIGHT);
			Range rightDailyTitleRange = worksheet.getCells().createRange(String.format(REPORT_RIGHT_COL_ADDR,
					(startNewPage + DAILY_TITLE_START_ROW), (startNewPage + DAILY_TITLE_START_ROW + 1)));
			rightDailyTitleRange.copy(fixRightDailyHeader);
			rightDailyTitleRange.copyData(fixRightDailyHeader);
		} 
		List<AttendanceRecordReportColumnData> monthLyData = employeeData.getEmployeeMonthlyData();
		for (int i = 0, j = monthLyData.size(); i < j; i++) {
			monththDataRange.get(0, i * 2).setValue(monthLyData.get(i).getUper());
			monththDataRange.get(1, i * 2).setValue(monthLyData.get(i).getLower());
		}

		// Add employee info
		Range employeeInfoL = worksheet.getCells().createRange(String.format(REPORT_LEFT_COL_ADDR,
				(startNewPage + START_EMPLOYEE_DATA_ROW), (startNewPage + START_EMPLOYEE_DATA_ROW)));
		Range employeeInfoR = worksheet.getCells().createRange(String.format(REPORT_LEFT_COL_ADDR,
				(startNewPage + START_EMPLOYEE_CENTER_DATA_ROW), (startNewPage + START_EMPLOYEE_CENTER_DATA_ROW)));
		Range employeeYearInfo = worksheet.getCells().createRange(String.format(REPORT_LEFT_COL_ADDR,
				(startNewPage + START_EMPLOYEE_BOTTOM_DATA_ROW), (startNewPage + START_EMPLOYEE_BOTTOM_DATA_ROW)));

		employeeInfoL.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		employeeInfoL.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		employeeInfoR.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		employeeYearInfo.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		this.setFontBold(employeeInfoL.get(0, EMPL_INVIDUAL_INDEX));
		this.setFontBold(employeeInfoL.get(0, EMPL_WORKPLACE_INDEX));
		this.setFontBold(employeeInfoR.get(0, EMPL_EMPLOYMENT_INDEX));
		this.setFontBold(employeeInfoL.get(0, EMPL_TITLE_INDEX));
		this.setFontBold(employeeInfoL.get(0, EMPL_WORKTYPE_INDEX));
		this.setFontBold(employeeInfoL.get(0, EMPL_YEARMONTH_INDEX));
		
		employeeInfoL.get(0, EMPL_INVIDUAL_INDEX)
				.setValue(TextResource.localize("KWR002_212") + employeeData.getInvidual());
		employeeInfoL.get(0, EMPL_WORKPLACE_INDEX)
				.setValue(TextResource.localize("KWR002_213", "#Com_Workplace") + employeeData.getWorkplace());
		employeeInfoR.get(0, EMPL_EMPLOYMENT_INDEX)
				.setValue(TextResource.localize("KWR002_214", "#Com_Employment") + employeeData.getEmployment());
		employeeInfoR.get(0, EMPL_TITLE_INDEX)
				.setValue(TextResource.localize("KWR002_215", "#Com_Jobtitle") + employeeData.getTitle());
		employeeInfoR.get(0, EMPL_WORKTYPE_INDEX)
				.setValue(TextResource.localize("KWR002_216") + employeeData.getWorkType());
		employeeYearInfo.get(0, EMPL_YEARMONTH_INDEX)
				.setValue(TextResource.localize("KWR002_217") + employeeData.getYearMonth());
		// Create weekly data
		List<AttendanceRecordReportWeeklyData> weeklyDatas = employeeData.getWeeklyDatas();
		Map<String, Integer> dataRow = new HashMap<>();
		dataRow.put(REPORT_START_PAGE_ROW, startNewPage);
		dataRow.put(REPORT_LEFT_ROW, startNewPage + START_REPORT_DATA_ROW);
		dataRow.put(REPORT_RIGHT_ROW, startNewPage + START_REPORT_DATA_ROW);
		dataRow.put(REPORT_ROW_BG, REPORT_ROW_BG_WHITE);
		dataRow.put(REPORT_ROW_START_RIGHT, REPORT_ROW_START_RIGHT_COUNT);
		for (AttendanceRecordReportWeeklyData weeklyData : weeklyDatas) {
			generateWeeklyData(worksheet, weeklyData, dataRow, dailyWTmpl, dailyBTmpl, weeklyRangeTmpl);
		}

		// trường này cũng cần sửa nếu in tất cả thành 1 sheet
		if (employeeData.isApprovalStatus()) {
			Range approvalRange =  worksheet.getCells().createRange(REPORT_APPROVAL);
			approvalRange.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.THICK, Color.getRed());
			approvalRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THICK, Color.getRed());
			approvalRange.setOutlineBorder(BorderType.LEFT_BORDER, CellBorderType.THICK, Color.getRed());
			approvalRange.setOutlineBorder(BorderType.RIGHT_BORDER, CellBorderType.THICK, Color.getRed());
			approvalRange.get(0, 0).setValue(APPROVAL);
		}
		// update start page row value
//		startNewPage = dataRow.get(REPORT_START_PAGE_ROW) - 1;

		VerticalPageBreakCollection vPageBreaks = worksheet.getVerticalPageBreaks();
		vPageBreaks.add(END_REPORT_PAGE_BREAK + (startNewPage + 1 ));
		HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
		hPageBreaks.add(END_REPORT_PAGE_BREAK + (startNewPage + 1 ));

		return startNewPage;
	}

	/**
	 * Generate weekly data.
	 *
	 * @param worksheet
	 *            the worksheet
	 * @param weeklyData
	 *            the weekly data
	 * @param dataRow
	 *            the data row
	 * @param dailyDataW
	 *            the daily data W
	 * @param dailyDataB
	 *            the daily data B
	 * @param weekSumaryTmpl
	 *            the week sumary
	 * @throws Exception
	 *             the exception
	 */
	private void generateWeeklyData(Worksheet worksheet, AttendanceRecordReportWeeklyData weeklyData,
			Map<String, Integer> dataRow, Range dailyDataW, Range dailyDataB, Range weekSumaryTmpl) throws Exception {
		List<AttendanceRecordReportDailyData> dailyDatas = weeklyData.getDailyDatas();
		boolean isWhiteBackground = dataRow.get(REPORT_ROW_BG) == REPORT_ROW_BG_WHITE;
		for (int i = 1, j = dailyDatas.size(); i <= j; i++) {
			Range dailyRange;
			AttendanceRecordReportDailyData data = dailyDatas.get(i - 1);
			if (!data.isSecondCol()) {
				int row = dataRow.get(REPORT_LEFT_ROW);

				// Get range
				dailyRange = worksheet.getCells().createRange(String.format(REPORT_LEFT_COL_ADDR, row, (row + 1)));
				dailyRange.copy(isWhiteBackground ? dailyDataW : dailyDataB);

				dataRow.put(REPORT_LEFT_ROW, row + 2);
			} else {
				if (dataRow.get(REPORT_ROW_START_RIGHT) == REPORT_ROW_START_RIGHT_COUNT) {
					dataRow.put(REPORT_ROW_START_RIGHT, REPORT_ROW_START_RIGHT_COUNT + 1);
					isWhiteBackground = true;
				}
				int row = dataRow.get(REPORT_RIGHT_ROW);

				// Get range
				dailyRange = worksheet.getCells().createRange(String.format(REPORT_RIGHT_COL_ADDR, row, row + 1));
				dailyRange.copy(isWhiteBackground ? dailyDataW : dailyDataB);

				dataRow.put(REPORT_RIGHT_ROW, row + 2);
			}

			// fill data data
			dailyRange.get(0, 0).setValue(data.getDate());
			dailyRange.get(0, 1).setValue(data.getDayOfWeek());
			List<AttendanceRecordReportColumnData> reportColumnDatas = data.getColumnDatas();
			for (int k = 0, l = reportColumnDatas.size(); k < l; k++) {
				// set left or right
//				if()
				dailyRange.get(0, 2 * (k + 1)).setValue(reportColumnDatas.get(k).getUper());
				dailyRange.get(1, 2 * (k + 1)).setValue(reportColumnDatas.get(k).getLower());
			}

			isWhiteBackground = !isWhiteBackground;
		}

		// fill weekly summary data
		AttendanceRecordReportWeeklySumaryData sumaryData = weeklyData.getWeeklySumaryData();
		Range weeklySumaryRange;
		if (!sumaryData.isSecondCol()) {
			int row = dataRow.get(REPORT_LEFT_ROW);
			// Get range
			weeklySumaryRange = worksheet.getCells().createRange(String.format(REPORT_LEFT_COL_ADDR, row, row + 1));
			dataRow.put(REPORT_LEFT_ROW, row + 2);
		} else {
			int row = dataRow.get(REPORT_RIGHT_ROW);
			// Get range
			weeklySumaryRange = worksheet.getCells().createRange(String.format(REPORT_RIGHT_COL_ADDR, row, row + 1));
			dataRow.put(REPORT_RIGHT_ROW, row + 2);
		}
		weeklySumaryRange.copy(weekSumaryTmpl);
		weeklySumaryRange.get(1, 0).setValue(sumaryData.getDateRange());

		List<AttendanceRecordReportColumnData> summaryColDatas = sumaryData.getColumnDatas();
		for (int i = 0, j = summaryColDatas.size(); i < j; i++) {
			weeklySumaryRange.get(0, 14 + (i * 2)).setValue(summaryColDatas.get(i).getUper());
			weeklySumaryRange.get(1, 14 + (i * 2)).setValue(summaryColDatas.get(i).getLower());
		}

		// update last next row color
		dataRow.put(REPORT_ROW_BG, isWhiteBackground ? REPORT_ROW_BG_WHITE : REPORT_ROW_BG_BLUE);
		dataRow.put(REPORT_START_PAGE_ROW,
				dataRow.get(REPORT_LEFT_ROW) < dataRow.get(REPORT_RIGHT_ROW) ? dataRow.get(REPORT_RIGHT_ROW)
						: dataRow.get(REPORT_LEFT_ROW));
	}
	
	private void setFontBold(Cell cell) {
		Style style = cell.getStyle();
		style.getFont().setBold(true);
		cell.setStyle(style);
	}
	
//	private void setBorderThin(Range range, ) {
//		
//	}
}
