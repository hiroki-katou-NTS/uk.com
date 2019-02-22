package nts.uk.file.at.infra.yearholidaymanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.worktime.WorkTimeReportGenerator;
import nts.uk.file.at.app.export.yearholidaymanagement.OutputYearHolidayManagementGenerator;
import nts.uk.file.at.app.export.yearholidaymanagement.OutputYearHolidayManagementQuery;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeOutputYearHolidayManagementGenerator extends AsposeCellsReportGenerator
		implements OutputYearHolidayManagementGenerator {

	private static final String COMPANY_ERROR = "Company is not found!!!!";

	private static final String TEMPLATE_FILE = "report/KDR002.xlsx";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";
	private static final int WORK_TIME_NORMAL_START_INDEX = 10;
	private static final int WORK_TIME_NORMAL_NUM_ROW = 10;

	@Inject
	private CompanyAdapter company;

	@Override
	public void generate(FileGeneratorContext generatorContext, OutputYearHolidayManagementQuery query) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook workbook = reportContext.getWorkbook();

			WorksheetCollection worksheets = workbook.getWorksheets();
			String programName = query.getProgramName();

			String companyName = company.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
					.getCompanyName();
			reportContext.setHeader(0, "&8&\"MS ゴシック\"" + companyName);
			reportContext.setHeader(1, "&16&\"MS ゴシック\"" + TextResource.localize("KDR002_10"));
			String exportTime = query.getExportTime().toString();
			Worksheet normalSheet = worksheets.get(0);
			String normalSheetName = TextResource.localize("KMK003_309");
			List<Object[]> normalData = new ArrayList<Object[]>();

			printData(normalSheet, programName, companyName, exportTime, normalData, normalSheetName,
					WORK_TIME_NORMAL_START_INDEX, WORK_TIME_NORMAL_NUM_ROW);
			worksheets.setActiveSheetIndex(0);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(generatorContext,
					programName + "_" + query.getExportTime().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printData(Worksheet worksheet, String programId, String companyName, String exportTime,
			List<Object[]> data, String sheetName, int startIndex, int numRow) {
		try {
			worksheet.setName(sheetName);
			Cells cells = worksheet.getCells();

			// Header Data
			// cells.get(0, 1).setValue(companyName);
			// cells.get(1, 1).setValue(programId);
			// cells.get(2, 1).setValue(exportTime);
			// cells.get(3, 1).setValue(sheetName);

			// Main Data
			for (int i = 0; i < data.size(); i++) {
				Object[] dataRow = data.get(i);
				if (i % numRow == 0 && i + numRow < data.size()) {
					cells.copyRows(cells, startIndex + i, startIndex + i + numRow, numRow);
				}
				for (int j = 0; j < dataRow.length; j++) {
					cells.get(startIndex + i, j).setValue(Objects.toString(dataRow[j], ""));
				}
			}
			cells.deleteRows(startIndex + data.size(), numRow);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
