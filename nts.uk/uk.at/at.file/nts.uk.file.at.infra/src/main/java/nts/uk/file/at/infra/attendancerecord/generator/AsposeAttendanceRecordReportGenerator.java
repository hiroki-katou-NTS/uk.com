package nts.uk.file.at.infra.attendancerecord.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.attendancerecord.AttendanceRecordReportDatasource;
import nts.uk.file.at.app.export.attendancerecord.AttendanceRecordReportGenerator;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportColumnData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportEmployeeData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeAttendanceRecordReportGenerator extends AsposeCellsReportGenerator
		implements AttendanceRecordReportGenerator {

	private static final String TEMPLATE_FILE = "report/KWR002.xlsx";

	private static final String REPORT_FILE_NAME = "サンプル帳票.xlsx";

	private static final String REPORT_ID = "ReportSample";

	private static final String REPORT_PAGE_ADDR = "A1:AO";

	private static final String PAGE_NUMBER_START_COL = "AD";

	private static final String PAGE_NUMBER_END_COL = "AO";

	private static final String MONTHLY_DATA_START_COL = "C";

	private static final String MONTHLY_DATA_END_COL = "Z";

	private static final String DAILY_W_RANGE_TMPL_ADDR = "AQ1:BJ2";

	private static final String DAILY_B_RANGE_TMPL_ADDR = "AQ4:BJ5";

	private static final String WEEKLY_RANGE_TMPL_ADDR = "AQ11:BJ12";

	private static final String SEAL_RANGE_TMPL_ADDR = "AQ14:AR17";

	private static final List<String> SEAL_COL_ADDR = Arrays
			.asList(new String[] { "AN6", "AL6", "AJ6", "AH6", "AF6", "AD6" });

	private static final String START_REPORT_COL1 = "A";

	private static final String START_REPORT_COL2 = "V";

	private static final String END_REPORT_COL2 = "AO";
	
	private static final String REPORT_LEFT_COL_ADDR = "A%d:T%d";
	
	private static final String REPORT_RIGHT_COL_ADDR = "V%d:AO%d";

	private static final int START_EMPLOYEE_DATA_ROW = 11;

	private static final int START_REPORT_DATA_ROW = 14;

	private static final int START_REPORT_ROW = 0;

	private static final int MAX_ROW_PER_EMPL = 51;

	private static final int EMPL_INVIDUAL_INDEX = 0;

	private static final int EMPL_WORKPLACE_INDEX = 7;

	private static final int EMPL_EMPLOYMENT_INDEX = 0;

	private static final int EMPL_TITLE_INDEX = 5;

	private static final int EMPL_WORKTYPE_INDEX = 10;

	private static final int EMPL_YEARMONTH_INDEX = 17;

	private static final int PAGE_NUMBER_START_ROW = 3;

	private static final int MONTHLY_DATA_START_ROW = 8;

	@Override
	public void generate(FileGeneratorContext generatorContext, AttendanceRecordReportDatasource dataSource) {

		AttendanceRecordReportData data = dataSource.getData();

		try (val reportContext = this.createContext(TEMPLATE_FILE, REPORT_ID)) {

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
			Range reportPageTmpl = templateSheet.getCells().createRange(REPORT_PAGE_ADDR + MAX_ROW_PER_EMPL);
			Range dailyWTmpl = templateSheet.getCells().createRange(DAILY_W_RANGE_TMPL_ADDR);
			Range dailyBTmpl = templateSheet.getCells().createRange(DAILY_B_RANGE_TMPL_ADDR);
			Range weeklyRangeTmpl = templateSheet.getCells().createRange(WEEKLY_RANGE_TMPL_ADDR);

			// Create seal column
			this.createSealCol(templateSheet, data.getSealColName());

			// Init report page
			int page = 1;
			// Start loop to create report for earch month
			Map<String, List<AttendanceRecordReportEmployeeData>> reportDatas = data.getReportData();
			for (String sheetName : reportDatas.keySet()) {
				int sheetPage = 0;

				// create new sheet from template sheet
				worksheetCollection.get(worksheetCollection.addCopy(0)).setName(sheetName);
				// get new sheet
				Worksheet worksheet = worksheetCollection.get(sheetName);

				// get list employee data
				List<AttendanceRecordReportEmployeeData> reportEmployeeDatas = reportDatas.get(sheetName);
				// create employee report page
				for (AttendanceRecordReportEmployeeData employeeData : reportEmployeeDatas) {
					
					this.createEmployeeReportPage(worksheet, employeeData, page, sheetPage, reportPageTmpl,
							dailyWTmpl, dailyBTmpl, weeklyRangeTmpl);
					sheetPage++;
					page++;
				}

			}

			// createTestFile(workbook);

			// save as PDF file
			reportContext.saveAsExcel(this.createNewFile(generatorContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates the seal col.
	 *
	 * @param templateSheet
	 *            the template sheet
	 * @param sealColNames
	 *            the seal col names
	 * @throws Exception
	 *             the exception
	 */
	private void createSealCol(Worksheet templateSheet, List<String> sealColNames) throws Exception {
		Range sealColTmpl = templateSheet.getCells().createRange(SEAL_RANGE_TMPL_ADDR);

		for (int i = 0, j = sealColNames.size(); i < j; i++) {
			String sealColAddr = SEAL_COL_ADDR.get(j - i - 1);
			Range sealCol = templateSheet.getCells().createRange(sealColAddr);
			sealCol.copy(sealColTmpl);
			sealCol.get(0, 0).setValue(sealColNames.get(i));
		}
	}

	/**
	 * Creates the employee report page.
	 *
	 * @param worksheet the worksheet
	 * @param employeeData the employee data
	 * @param page the page
	 * @param sheetPage the sheet page
	 * @param pageTmpl the page tmpl
	 * @param dailyWTmpl the daily W tmpl
	 * @param dailyBTmpl the daily B tmpl
	 * @param weeklyRangeTmpl the weekly range tmpl
	 * @throws Exception the exception
	 */
	private void createEmployeeReportPage(Worksheet worksheet, AttendanceRecordReportEmployeeData employeeData,
			int page, int sheetPage, Range pageTmpl, Range dailyWTmpl, Range dailyBTmpl, Range weeklyRangeTmpl)
			throws Exception {
		int startNewPage = MAX_ROW_PER_EMPL * sheetPage;
		if (sheetPage > 0) {

			// copy new report place
			Range newReportPage = worksheet.getCells().createRange(START_REPORT_COL1 + (startNewPage + 1));
			newReportPage.copy(pageTmpl);
			HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
			hPageBreaks.add(END_REPORT_COL2 + (startNewPage + 1));
		}

		// Add page number
		Range pageNumberRange = worksheet.getCells().createRange(
				PAGE_NUMBER_START_COL + (startNewPage + PAGE_NUMBER_START_ROW),
				PAGE_NUMBER_END_COL + (startNewPage + PAGE_NUMBER_START_ROW + 1));
		pageNumberRange.get(0, 0).setValue(page + "" + pageNumberRange.get(0, 0).getValue());
		
		// Add monthly data
		Range monththDataRange = worksheet.getCells().createRange(
				MONTHLY_DATA_START_COL + (startNewPage + MONTHLY_DATA_START_ROW),
				MONTHLY_DATA_END_COL + (startNewPage + MONTHLY_DATA_START_ROW + 1));
		List<AttendanceRecordReportColumnData> monthLyData = employeeData.getEmployeeMonthlyData();
		for (int i = 0; i < monthLyData.size(); i++) {
			monththDataRange.get(0, i*2).setValue(monthLyData.get(i).getUper());
			monththDataRange.get(1, i*2).setValue(monthLyData.get(i).getLower());
		}

		// Add employee info
		Range employeeInfoL = worksheet.getCells().createRange(String.format(REPORT_LEFT_COL_ADDR,
				(startNewPage + START_EMPLOYEE_DATA_ROW), (startNewPage + START_EMPLOYEE_DATA_ROW)));
		Range employeeInfoR = worksheet.getCells().createRange(String.format(REPORT_RIGHT_COL_ADDR,
				(startNewPage + START_EMPLOYEE_DATA_ROW), (startNewPage + START_EMPLOYEE_DATA_ROW)));

		employeeInfoL.get(0, EMPL_INVIDUAL_INDEX)
				.setValue(employeeInfoL.get(0, EMPL_INVIDUAL_INDEX).getValue() + " " + employeeData.getInvidual());
		employeeInfoL.get(0, EMPL_WORKPLACE_INDEX)
				.setValue(employeeInfoL.get(0, EMPL_WORKPLACE_INDEX).getValue() + " " + employeeData.getWorkplace());
		employeeInfoR.get(0, EMPL_EMPLOYMENT_INDEX)
				.setValue(employeeInfoR.get(0, EMPL_EMPLOYMENT_INDEX).getValue() + " " + employeeData.getEmployment());
		employeeInfoR.get(0, EMPL_TITLE_INDEX)
				.setValue(employeeInfoR.get(0, EMPL_TITLE_INDEX).getValue() + " " + employeeData.getTitle());
		employeeInfoR.get(0, EMPL_WORKTYPE_INDEX)
				.setValue(employeeInfoR.get(0, EMPL_WORKTYPE_INDEX).getValue() + " " + employeeData.getWorkType());
		employeeInfoR.get(0, EMPL_YEARMONTH_INDEX)
				.setValue(employeeInfoR.get(0, EMPL_YEARMONTH_INDEX).getValue() + " " + employeeData.getYearMonth());

		// create print area
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setPrintArea(REPORT_PAGE_ADDR + (startNewPage + MAX_ROW_PER_EMPL));
	}
/*
	private void createTestFile(Workbook workbook) throws Exception {

		WorksheetCollection worksheets = workbook.getWorksheets();

		Worksheet worksheet = worksheets.get(0);

		Range reportPage = worksheet.getCells().createRange(REPORT_PAGE_ADDR + MAX_ROW_PER_EMPL);
		int startNewPage = MAX_ROW_PER_EMPL + 1;
		for (int i = 0; i < 3; i++) {
			Range newReportPage = worksheet.getCells().createRange(START_REPORT_COL1 + startNewPage);
			newReportPage.copy(reportPage);
			startNewPage += MAX_ROW_PER_EMPL;
			HorizontalPageBreakCollection hPageBreaks = worksheets.get(0).getHorizontalPageBreaks();
			hPageBreaks.add("AO" + startNewPage);
		}

		// get daily and weekly template
		Range dailyRangeTmpl = worksheet.getCells().createRange(DAILY_W_RANGE_TMPL_ADDR);

		Range weeklyRangeTmpl = worksheet.getCells().createRange(WEEKLY_RANGE_TMPL_ADDR);

		Range dailyReport = worksheet.getCells().createRange(START_REPORT_COL1 + START_REPORT_DATA_ROW);
		dailyReport.copy(dailyRangeTmpl);

		dailyReport = worksheet.getCells().createRange(START_REPORT_COL2 + START_REPORT_DATA_ROW);
		dailyReport.copy(dailyRangeTmpl);

		for (int i = 0; i < 5; i++) {
			worksheets.addCopy(0);
			worksheets.get(i + 1).setName("VLLLL" + i);
		}
	}*/
}
