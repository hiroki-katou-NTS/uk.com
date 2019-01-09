package nts.uk.file.at.infra.worktime;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.worktime.WorkTimeReportDatasource;
import nts.uk.file.at.app.export.worktime.WorkTimeReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeWorkTimeReportGenerator extends AsposeCellsReportGenerator implements WorkTimeReportGenerator {

	private static final String TEMPLATE_FILE = "report/KMK003.xlsx";
	private static final String REPORT_NAME = "KMK003就業時間帯の登録";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";
	private static final int WORK_TIME_NORMAL_START_INDEX = 10;
	private static final int WORK_TIME_FLOW_START_INDEX = 10;
	private static final int WORK_TIME_FLEX_START_INDEX = 10;
	private static final int WORK_TIME_NORMAL_NUM_ROW = 10;
	private static final int WORK_TIME_FLOW_NUM_ROW = 10;
	private static final int WORK_TIME_FLEX_NUM_ROW = 10;

	@Override
	public void generate(FileGeneratorContext generatorContext, WorkTimeReportDatasource dataSource) {

		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();

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

			printData(normalSheet, companyName, exportTime, normalData, normalSheetName,
					WORK_TIME_NORMAL_START_INDEX, WORK_TIME_NORMAL_NUM_ROW);
			printData(flowSheet, companyName, exportTime, flowData, flowSheetName, 
					WORK_TIME_FLOW_START_INDEX, WORK_TIME_FLOW_NUM_ROW);
			printData(flexSheet, companyName, exportTime, flexData, flexSheetName, 
					WORK_TIME_FLEX_START_INDEX, WORK_TIME_FLEX_NUM_ROW);
			worksheets.setActiveSheetIndex(0);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(generatorContext,
					REPORT_NAME + "_" + dataSource.getExportTime().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void printData(Worksheet worksheet, String companyName, String exportTime, List<Object[]> data,
			String sheetName, int startIndex, int numRow) {
		try {
			worksheet.setName(sheetName);
			Cells cells = worksheet.getCells();
			// Header Data
			cells.get(0, 1).setValue(companyName);
			cells.get(1, 1).setValue(REPORT_NAME);
			cells.get(2, 1).setValue(exportTime);
			cells.get(3, 1).setValue(sheetName);

			// Main Data
			if (data.size() == 0) {
				cells.deleteRows(startIndex, numRow);
				return;
			}
			for (int i = 0; i < data.size(); i++) {
				Object[] dataRow = data.get(i);
				if (i % numRow == 0 && i + numRow < data.size()) {
					cells.copyRows(cells, startIndex + i, startIndex + i + numRow, numRow);
				}
				for (int j = 0; j < dataRow.length; j++) {
					cells.get(startIndex + i, j).setValue(Objects.toString(dataRow[j], ""));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
