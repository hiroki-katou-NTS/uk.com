package nts.uk.file.at.infra.yearholidaymanagement;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.worktime.WorkTimeReportGenerator;
import nts.uk.file.at.app.export.yearholidaymanagement.OutputYearHolidayManagementGenerator;
import nts.uk.file.at.app.export.yearholidaymanagement.OutputYearHolidayManagementQuery;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeOutputYearHolidayManagementGenerator extends AsposeCellsReportGenerator
		implements OutputYearHolidayManagementGenerator {

	private static final String TEMPLATE_FILE = "report/KDR002.xlsx";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";
	private static final int WORK_TIME_NORMAL_START_INDEX = 10;
	private static final int WORK_TIME_FLOW_START_INDEX = 10;
	private static final int WORK_TIME_FLEX_START_INDEX = 10;
	private static final int WORK_TIME_NORMAL_NUM_ROW = 10;
	private static final int WORK_TIME_FLOW_NUM_ROW = 10;
	private static final int WORK_TIME_FLEX_NUM_ROW = 10;

	@Override
	public void generate(FileGeneratorContext fileGeneratorContext, OutputYearHolidayManagementQuery query) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();

			String programName = dataSource.getProgramName();
			String companyName = dataSource.getCompanyName();
			String exportTime = dataSource.getExportTime().toString();
			Worksheet normalSheet = worksheets.get(0);
			Worksheet flowSheet = worksheets.get(1);
			Worksheet flexSheet = worksheets.get(2);
			String normalSheetName = TextResource.localize("KMK003_309");
			String flowSheetName = TextResource.localize("KMK003_310");
			String flexSheetName = TextResource.localize("KMK003_311");
			List<Object[]> normalData = dataSource.getWorkTimeNormal();
			List<Object[]> flowData = dataSource.getWorkTimeFlow();
			List<Object[]> flexData = dataSource.getWorkTimeFlex();

			printData(normalSheet, programName, companyName, exportTime, normalData, normalSheetName,
					WORK_TIME_NORMAL_START_INDEX, WORK_TIME_NORMAL_NUM_ROW);
			printData(flowSheet, programName, companyName, exportTime, flowData, flowSheetName,
					WORK_TIME_FLOW_START_INDEX, WORK_TIME_FLOW_NUM_ROW);
			printData(flexSheet, programName, companyName, exportTime, flexData, flexSheetName,
					WORK_TIME_FLEX_START_INDEX, WORK_TIME_FLEX_NUM_ROW);
			worksheets.setActiveSheetIndex(0);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(generatorContext,
					programName + "_" + dataSource.getExportTime().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
