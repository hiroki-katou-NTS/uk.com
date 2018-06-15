package nts.uk.ctx.exio.dom.exi.execlog.csv;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import com.aspose.cells.AutoFitterOptions;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class ExecLogCSVGenerator extends AsposeCellsReportGenerator implements ExecLogCSVReportGenerator{

	private static final String REPORT_ID = "CSV_GENERATOR";
	
	private static final int COND_IMPORT_ROW = 0;
	
	private static final int DATE_TIME_ROW = 1;
	
	private static final int TOTAL_COUNT_ROW = 2;
	
	private static final int NORMAL_COUNT_ROW = 3;
	
	private static final int ERROR_COUNT_ROW = 4;
	
	private static final int CSV_START_COLUMN = 0;
	
	private static final int CSV_HEADER_START_ROW = 5;
	
	private static final int CSV_DATA_START_ROW = 6;
	
	@Override
	public void generate(FileGeneratorContext generatorContext, ExecLogCSVFileData dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);

		val workbook = reportContext.getWorkbook();
		val sheet = workbook.getWorksheets().get(0);
		val cells = sheet.getCells();

		// get data
		List<String> condImport = dataSource.getCondImport();
		List<String> dateTime = dataSource.getDateTime();
		List<String> totalCount = dataSource.getTotalCount();
		List<String> normalCount = dataSource.getNormalCount();
		List<String> errorCount = dataSource.getErrorCount();
		List<String> headers = dataSource.getHeaders();
		List<Map<String, Object>> datas = dataSource.getDatas();

		// information error
		this.drawInfoError(cells, condImport, dateTime, totalCount, normalCount, errorCount);

		// list error
		if (!headers.isEmpty()){
			this.drawHeader(cells, headers);
			if (!datas.isEmpty()){
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
	private void drawHeader (Cells cells, List<String> headers) {
		
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
	private void drawTableBody (Cells cells, List<String> headers, List<Map<String, Object>> datas) {
		
		for(int i = 0; i < datas.size(); i++){
			Map<String, Object> data = datas.get(i);
			for (int j = 0; j < headers.size(); j++) {
				Cell dataCell = cells.get(CSV_DATA_START_ROW + i, CSV_START_COLUMN + j);
				dataCell.setValue(data.get(headers.get(j)));
			}
		}
	}
		
	/**
	 * @param cells
	 * @param condImport
	 * @param dateTime
	 * @param totalCount
	 * @param normalCount
	 * @param errorCount
	 */
	private void drawInfoError(Cells cells, List<String> condImport, List<String> dateTime, 
			List<String> totalCount, List<String> normalCount, List<String> errorCount) {
		// 受入する条件
		for (int i = 0; i < condImport.size(); i++) {
			Cell conImportCells = cells.get(COND_IMPORT_ROW, CSV_START_COLUMN + i);
			conImportCells.setValue(condImport.get(i)+"");
		}
		
		// 受入開始日時
		for (int i = 0; i < dateTime.size(); i++) {
			Cell dateTimeCell = cells.get(DATE_TIME_ROW, CSV_START_COLUMN + i);
			dateTimeCell.setValue(dateTime.get(i));
		}
		
		// トータル件数
		for (int i = 0; i < totalCount.size(); i++) {
			Cell totalCountCell = cells.get(TOTAL_COUNT_ROW, CSV_START_COLUMN + i);
			totalCountCell.setValue(totalCount.get(i));
		}
		
		// 正常件数
		for (int i = 0; i < normalCount.size(); i++) {
			Cell normalCountCell = cells.get(NORMAL_COUNT_ROW, CSV_START_COLUMN + i);
			normalCountCell.setValue(normalCount.get(i));
		}
		
		// エラー件数
		for (int i = 0; i < errorCount.size(); i++) {
			Cell errorCountCell = cells.get(ERROR_COUNT_ROW, CSV_START_COLUMN + i);
			errorCountCell.setValue(errorCount.get(i));
		}
	}
}
