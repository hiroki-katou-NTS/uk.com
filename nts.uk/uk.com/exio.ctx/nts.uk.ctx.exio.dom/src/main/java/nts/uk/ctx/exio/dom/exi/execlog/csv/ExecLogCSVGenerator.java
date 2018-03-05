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

	private final String REPORT_ID = "SCV_GENERATOR";
	
	private final int COND_IMPORT_ROW = 0;
	
	private final int DATE_TIME_ROW = 1;
	
	private final int TOTAL_COUNT_ROW = 2;
	
	private final int NORMAL_COUNT_ROW = 3;
	
	private final int ERROR_COUNT_ROW = 4;
	
	private final int CSV_START_COLUMN = 0;
	
	private final int CSV_HEADER_START_ROW = 5;
	
	private final int CSV_DATA_START_ROW = 6;
	
	private final String FONT_FAMILY = "ＭＳ ゴシック";
	
	private final int FONT_SIZE = 10;
	
	private final int STANDARD_WIDTH = 100;
	
	private final int STANDARD_HEIGHT = 25;
	
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
		this.setCommonStyle(cells);
		this.drawInfoError(cells, condImport, dateTime, totalCount, normalCount, errorCount);
		
		// list error
		if (!headers.isEmpty()){
			this.drawHeader(cells, headers);
			this.drawTableBody(cells, headers, datas);

			try {
				AutoFitterOptions options = new AutoFitterOptions();
				options.setAutoFitMergedCells(true);
				options.setOnlyAuto(true);
				sheet.autoFitColumns(options);
				sheet.autoFitRows(options);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		reportContext.processDesigner();
		
		reportContext.saveAsCSV(this.createNewFile(generatorContext, dataSource.getFileName()));
	}
	
	/**
	 * @param cells
	 */
	private void setCommonStyle(Cells cells){
		
		cells.setStandardWidthPixels(STANDARD_WIDTH);
		cells.setStandardHeightPixels(STANDARD_HEIGHT);
	}

	/**
	 * @param cells
	 * @param headers
	 */
	private void drawHeader (Cells cells, List<String> headers) {
		
		for (int j = 0; j < headers.size(); j++) {
			Cell header = cells.get(CSV_HEADER_START_ROW, CSV_START_COLUMN + j);
			header.setValue(headers.get(j));
			Style style = this.getCellStyleNoBorder(header.getStyle());
			header.setStyle(style);
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
				Style style = this.getCellStyleNoBorder(dataCell.getStyle());
				dataCell.setStyle(style);
			}
		}
	}
		
	/**
	 * @param s
	 * @return
	 */
	private Style getCellStyleNoBorder(Style s){
		Style style = new Style();
		style.copy(s);
		style.setHorizontalAlignment(TextAlignmentType.LEFT);
		style.setVerticalAlignment(TextAlignmentType.CENTER);
		
		this.setFontStyle(style);
		
		return style;
	}
	
	/**
	 * @param style
	 */
	private void setFontStyle (Style style){
		
		Font font = style.getFont();
		font.setSize(FONT_SIZE);
		font.setName(FONT_FAMILY);
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
			Style style = this.getCellStyleNoBorder(conImportCells.getStyle());
			conImportCells.setStyle(style);
		}
		
		// 受入開始日時
		for (int i = 0; i < dateTime.size(); i++) {
			Cell dateTimeCell = cells.get(DATE_TIME_ROW, CSV_START_COLUMN + i);
			dateTimeCell.setValue(dateTime.get(i));
			Style style = this.getCellStyleNoBorder(dateTimeCell.getStyle());
			dateTimeCell.setStyle(style);
		}
		
		// トータル件数
		for (int i = 0; i < totalCount.size(); i++) {
			Cell totalCountCell = cells.get(TOTAL_COUNT_ROW, CSV_START_COLUMN + i);
			totalCountCell.setValue(totalCount.get(i));
			Style style = this.getCellStyleNoBorder(totalCountCell.getStyle());
			totalCountCell.setStyle(style);
		}
		
		// 正常件数
		for (int i = 0; i < normalCount.size(); i++) {
			Cell normalCountCell = cells.get(NORMAL_COUNT_ROW, CSV_START_COLUMN + i);
			normalCountCell.setValue(normalCount.get(i));
			Style style = this.getCellStyleNoBorder(normalCountCell.getStyle());
			normalCountCell.setStyle(style);
		}
		
		// エラー件数
		for (int i = 0; i < errorCount.size(); i++) {
			Cell errorCountCell = cells.get(ERROR_COUNT_ROW, CSV_START_COLUMN + i);
			errorCountCell.setValue(errorCount.get(i));
			Style style = this.getCellStyleNoBorder(errorCountCell.getStyle());
			errorCountCell.setStyle(style);
		}
	}
}
