package nts.uk.file.at.infra.attendancerecord.generator;

import javax.ejb.Stateless;

import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.attendancerecord.AttendanceRecordReportDatasource;
import nts.uk.file.at.app.export.attendancerecord.AttendanceRecordReportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeAttendanceRecordReportGenerator extends AsposeCellsReportGenerator
		implements AttendanceRecordReportGenerator {

	private static final String TEMPLATE_FILE = "report/KWR002.xlsx";

	private static final String REPORT_FILE_NAME = "サンプル帳票.xlsx";

	private static final String REPORT_ID = "ReportSample";

	private static final String DAILY_RANGE_NAME = "DAILY_RANGE_NAME";

	private static final String WEEKLY_RANGE_NAME = "WEEKLY_RANGE_NAME";

	private static final String START_REPORT_CELL1 = "A14";

	private static final String START_REPORT_CELL2 = "V14";

	@Override
	public void generate(FileGeneratorContext generatorContext, AttendanceRecordReportDatasource dataSource) {

		try (val reportContext = this.createContext(TEMPLATE_FILE, REPORT_ID)) {

			Workbook workbook = reportContext.getWorkbook();

			WorksheetCollection worksheets = workbook.getWorksheets();

			for (int i = 0; i < 5; i++) {
				worksheets.addCopy(0);
				worksheets.get(i + 1).setName("VLLLL" + i);
			}

			Worksheet worksheet = worksheets.get(0);

			// get daily and weekly template
			Range dailyRangeTmpl = worksheet.getCells().createRange("AQ1:BJ2");
			dailyRangeTmpl.setName(DAILY_RANGE_NAME);

			Range weeklyRangeTmpl = worksheet.getCells().createRange("AQ4:BJ5");
			weeklyRangeTmpl.setName(WEEKLY_RANGE_NAME);

			Range dailyReport = worksheet.getCells().createRange(START_REPORT_CELL1);
			dailyReport.copy(dailyRangeTmpl);

			dailyReport = worksheet.getCells().createRange(START_REPORT_CELL2);
			dailyReport.copy(dailyRangeTmpl);

			// set data source
			reportContext.setDataSource("companyName", dataSource.getData().getCompanyName());
			reportContext.setDataSource("reportName", dataSource.getData().getReportName());
			reportContext.setDataSource("exportDateTime", dataSource.getData().getExportDateTime());
			reportContext.setDataSource("reportMonthHead", dataSource.getData().getMonthlyHeader());
			reportContext.setDataSource("reportDayHead", dataSource.getData().getDailyHeader());

			// process data binginds in template
			reportContext.processDesigner();

			// save as PDF file
			reportContext.saveAsExcel(this.createNewFile(generatorContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
