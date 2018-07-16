package nts.uk.ctx.exio.dom.exo.execlog.csv;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class ExecLogGeneratorCSV extends AsposeCellsReportGenerator implements ExecLogReportCSVGenerator {

	private static final String REPORT_ID = "CSV_GENERATOR";

	private static final int RESULT_LOG = 0;

	private static final int ERROR_LOG = 1;

	private static final int CSV_START_COLUMN = 0;

	@Override
	public void generate(FileGeneratorContext generatorContext, ExecLogFileDataCSV dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);

		val workbook = reportContext.getWorkbook();
		val sheet = workbook.getWorksheets().get(0);
		val cells = sheet.getCells();

		// get data
		List<String> resultLog = dataSource.getResultLog();
		List<Map<String, Object>> datas = dataSource.getDataSource();

		// information error
		this.drawInfoError(cells, resultLog, datas);

		reportContext.processDesigner();

		reportContext.saveAsCSV(this.createNewFile(generatorContext, dataSource.getFileName()));
	}

	/**
	 * @param cells
	 * @param resultLog
	 * @param errorLog
	 */
	private void drawInfoError(Cells cells, List<String> resultLog, List<Map<String, Object>> datas) {

		for (int i = 0; i < resultLog.size(); i++) {
			Cell resultLogCells = cells.get(RESULT_LOG, CSV_START_COLUMN + i);
			resultLogCells.setValue(resultLog.get(i) + "");
		}

		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> data = datas.get(i);
			Cell resultLogCells = cells.get(ERROR_LOG, CSV_START_COLUMN + i);
			resultLogCells.setValue(data);
		}
	}
}
