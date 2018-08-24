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

	private static final int CSV_HEADER_START_ROW = 6;

	private static final int CSV_DATA_START_ROW = 7;

	@Override
	public void generate(FileGeneratorContext generatorContext, ExecLogFileDataCSV dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);

		val workbook = reportContext.getWorkbook();
		val sheet = workbook.getWorksheets().get(0);
		val cells = sheet.getCells();

		// get data
		List<String> headers = dataSource.getHeaders();
		List<List<String>> resultLogs = dataSource.getResultLogs();
		List<Map<String, Object>> datas = dataSource.getDataSource();

		// information error
		this.drawInfoError(cells, resultLogs);

		// list error
		if (!headers.isEmpty()) {
			this.drawHeader(cells, headers);
			if (!datas.isEmpty()) {
				this.drawTableBody(cells, headers, datas);
			}
		}

		reportContext.processDesigner();

		reportContext.saveAsCSV(this.createNewFile(generatorContext, dataSource.getFileName()));
	}

	/**
	 * @param cells
	 * @param headers
	 */
	private void drawHeader(Cells cells, List<String> headers) {

		for (int j = 0; j < headers.size(); j++) {
			Cell header = cells.get(CSV_HEADER_START_ROW, CSV_START_COLUMN + j);
			header.setValue(headers.get(j));
		}
	}

	/**
	 * @param cells
	 * @param headers
	 * @param datas
	 */
	private void drawTableBody(Cells cells, List<String> headers, List<Map<String, Object>> datas) {

		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> data = datas.get(i);
			for (int j = 0; j < headers.size(); j++) {
				Cell dataCell = cells.get(CSV_DATA_START_ROW + i, CSV_START_COLUMN + j);
				dataCell.setValue(data.get(headers.get(j)));
			}
		}
	}

	/**
	 * @param cells
	 * @param resultLog
	 */
	private void drawInfoError(Cells cells, List<List<String>> resultLogs) {
		for (int i = 0; i < resultLogs.size(); i++) {
			List<String> resultLog = resultLogs.get(i);
			for (int j = 0; j < resultLog.size(); j++) {
				Cell resultLogCells = cells.get(RESULT_LOG + i, CSV_START_COLUMN + j);
				resultLogCells.setValue(resultLog.get(j));
			}
		}

	}
}
