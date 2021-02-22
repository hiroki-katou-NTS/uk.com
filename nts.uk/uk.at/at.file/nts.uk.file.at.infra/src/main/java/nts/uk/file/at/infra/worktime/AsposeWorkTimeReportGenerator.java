package nts.uk.file.at.infra.worktime;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeSettingInfoDto;
import nts.uk.file.at.app.export.worktime.WorkTimeReportDatasource;
import nts.uk.file.at.app.export.worktime.WorkTimeReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeWorkTimeReportGenerator extends AsposeCellsReportGenerator implements WorkTimeReportGenerator {

	private static final String TEMPLATE_FILE = "report/KMK003.xlsx";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";
	private static final int WORK_TIME_NORMAL_START_INDEX = 10;
	private static final int WORK_TIME_FLOW_START_INDEX = 10;
	private static final int WORK_TIME_FLEX_START_INDEX = 10;
	private static final int WORK_TIME_NORMAL_NUM_ROW = 10;
	private static final int WORK_TIME_FLOW_NUM_ROW = 10;
	private static final int WORK_TIME_FLEX_NUM_ROW = 10;
	
	@Inject
	private WorkTimeReportService_New reportService;

	@Override
	public void generate(FileGeneratorContext generatorContext, WorkTimeReportDatasource dataSource) {

		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			
			String programName =  dataSource.getProgramName();
			String companyName = dataSource.getCompanyName();
			String exportTime = dataSource.getExportTime().toString();
			Worksheet normalSheet = worksheets.get(0);
			Worksheet flowSheet = worksheets.get(1);
			Worksheet flexSheet = worksheets.get(2);
			String normalSheetName = TextResource.localize("KMK003_309");
			String flowSheetName = TextResource.localize("KMK003_310");
			String flexSheetName = TextResource.localize("KMK003_311");
			List<WorkTimeSettingInfoDto> normalData = dataSource.getWorkTimeNormal();
			List<WorkTimeSettingInfoDto> flowData = dataSource.getWorkTimeFlow();
			List<WorkTimeSettingInfoDto> flexData = dataSource.getWorkTimeFlex();

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

	private void printData(Worksheet worksheet, String programId, String companyName, String exportTime, List<WorkTimeSettingInfoDto> data,
			String sheetName, int startIndex, int numRow) {
	    String normalSheetName = TextResource.localize("KMK003_309");
        String flowSheetName = TextResource.localize("KMK003_310");
        String flexSheetName = TextResource.localize("KMK003_311");
        
		try {
			worksheet.setName(sheetName);
			Cells cells = worksheet.getCells();
			// Header Data
			cells.get(0, 1).setValue(companyName);
			cells.get(1, 1).setValue(programId);
			cells.get(2, 1).setValue(exportTime);
			cells.get(3, 1).setValue(sheetName);

			// Main Data
			for (int i = 0; i < data.size(); i++) {
				if (i < data.size() - 1) {
					cells.copyRows(cells, startIndex + i*numRow, startIndex + (i + 1)*numRow, numRow);
				}
				
				// bind data to cell by type
				if (sheetName.equals(normalSheetName)) {
				    reportService.insertDataOneLineNormal(data.get(i), cells, startIndex + i*numRow);
				}
				
				if (sheetName.equals(flowSheetName)) {
				    reportService.insertDataOneLineFlow(data.get(i), cells, startIndex + i*numRow);
				}
				
				if (sheetName.equals(flexSheetName)) {
				    reportService.insertDataOneLineFlex(data.get(i), cells, startIndex + i*numRow);
				}
			}
//			cells.deleteRows(startIndex + data.size(), numRow);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
