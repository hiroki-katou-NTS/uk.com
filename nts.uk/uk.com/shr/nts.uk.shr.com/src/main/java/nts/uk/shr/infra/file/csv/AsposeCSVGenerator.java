package nts.uk.shr.infra.file.csv;

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
public class AsposeCSVGenerator extends AsposeCellsReportGenerator implements CSVReportGenerator{

	private static final String REPORT_ID = "SCV_GENERATOR";
	
	private static final int CSV_START_COLUMN = 0;
	
	private static final int CSV_HEADER_START_ROW = 0;
	
	private static final int CSV_DATA_START_ROW = 1;
	
	private static final String FONT_FAMILY = "ＭＳ ゴシック";
	
	private static final int FONT_SIZE = 10;
	
	private static final int STANDARD_WIDTH = 100;
	
	private static final int STANDARD_HEIGHT = 25;
	
	@Override
	public void generate(FileGeneratorContext generatorContext, CSVFileData dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);
		
		val workbook = reportContext.getWorkbook();
		val sheet = workbook.getWorksheets().get(0);
		val cells = sheet.getCells();
		
		List<String> headers = dataSource.getHeaders();
		List<Map<String, Object>> datas = dataSource.getDatas();
		
		if (!headers.isEmpty()){
			this.setCommonStyle(cells);
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
	
	private void setCommonStyle(Cells cells){
		
		cells.setStandardWidthPixels(STANDARD_WIDTH);
		cells.setStandardHeightPixels(STANDARD_HEIGHT);
	}

	private void drawHeader (Cells cells, List<String> headers) {
		
		for (int j = 0; j < headers.size(); j++) {
			Cell header = cells.get(CSV_HEADER_START_ROW, CSV_START_COLUMN + j);
			header.setValue(headers.get(j));
			Style style = this.getCellStyleNoBorder(header.getStyle());
			header.setStyle(style);
		}
	}
	
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
		
	private Style getCellStyleNoBorder(Style s){
		Style style = new Style();
		style.copy(s);
		style.setHorizontalAlignment(TextAlignmentType.LEFT);
		style.setVerticalAlignment(TextAlignmentType.CENTER);
		
		this.setFontStyle(style);
		
		return style;
	}
	
	private void setFontStyle (Style style){
		
		Font font = style.getFont();
		font.setSize(FONT_SIZE);
		font.setName(FONT_FAMILY);
	}
}
