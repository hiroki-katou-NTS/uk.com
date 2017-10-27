package nts.uk.shr.infra.file.report.masterlist.generator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.AutoFitterOptions;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;

@Stateless
public class AsposeMasterListGenerator extends AsposeCellsReportGenerator implements MasterListReportGenerator{

	private final String REPORT_ID = "MASTER_LIST";
	
	private final String REPORT_FILE_NAME = "マスターリスト_{TYPE}.xlsx";
	
	private final int HEADER_INFOR_START_ROW = 0;
	
	private final int START_COLUMN = 0;
	
	private final int MASTERLIST_DATA_START_ROW = 9;
	
	private final String FONT_FAMILY = "ＭＳ ゴシック";
	
	private final int FONT_SIZE = 10;
	
	private final int STANDARD_WIDTH = 100;
	
	private final int STANDARD_HEIGHT = 25;
	
	@Override
	public void generate(FileGeneratorContext generatorContext, MasterListExportSource dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);
		
		val workbook = reportContext.getWorkbook();
		val sheet = workbook.getWorksheets().get(0);
		val cells = sheet.getCells();
		
		List<MasterHeaderColumn> columns = dataSource.getHeaderColumns().stream()
				.filter(c -> c.isDisplay()).collect(Collectors.toList());
		
		sheet.setName("マスターリスト");
		
		if (!columns.isEmpty()){
			this.setCommonStyle(cells);
			this.fillHeader(cells, dataSource.getHeaders(), columns);
			this.drawTableHeader(cells, columns);
			this.drawTableBody(cells, columns, dataSource.getMasterList());

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
		
		String reportName = REPORT_FILE_NAME.replace("{TYPE}", dataSource.getHeaders().get("【種類】"));
		
		switch (dataSource.getReportType()) {
		case CSV:
			reportContext.saveAsCSV(this.createNewFile(generatorContext, reportName));
			break;
		case EXCEL:
			reportContext.saveAsExcel(this.createNewFile(generatorContext, reportName));
			break;
		case PDF:
			reportContext.saveAsPdf(this.createNewFile(generatorContext, reportName));
			break;
		default:
			break;
		}
		
	}
	
	private void setCommonStyle(Cells cells){
		
		cells.setStandardWidthPixels(STANDARD_WIDTH);
		cells.setStandardHeightPixels(STANDARD_HEIGHT);
	}

	private void fillHeader (Cells cells, Map<String, String> headerData, List<MasterHeaderColumn> columns) {
		
		int i = START_COLUMN;
		for (Entry<String, String> headerInfor : headerData.entrySet()) {
			Cell labelCell = cells.get(HEADER_INFOR_START_ROW + i, 0);
			Range valueCell = cells.createRange(HEADER_INFOR_START_ROW + i, 1, 1, columns.size() - 1);
			valueCell.merge();
			
			labelCell.setValue(headerInfor.getKey());
			valueCell.setValue(headerInfor.getValue());
			Style style = this.getCellStyleNoBorder(labelCell.getStyle());
			labelCell.setStyle(style);
			valueCell.setStyle(style);
			i++;
		}
	}
	
	private void drawTableHeader(Cells cells, List<MasterHeaderColumn> columns) {
		int columnIndex = 0;
		
		for (MasterHeaderColumn c : columns) {
			Cell cell = cells.get(MASTERLIST_DATA_START_ROW - 1, columnIndex);
			cell.setStyle(this.getCellStyle(cell.getStyle(), c));
			cell.setValue(c.getColumnText());
			columnIndex++;
		}
	}
	
	private void drawTableBody (Cells cells, List<MasterHeaderColumn> columns, List<MasterData> datas) {
		
		for(int i = 0; i < datas.size(); i++){
			MasterData data = datas.get(i);
			int j = START_COLUMN;
			for(MasterHeaderColumn column : columns){
				Cell cell = cells.get(MASTERLIST_DATA_START_ROW + i, j);
				// TODO: format date with format
				Style style = this.getCellStyle(cell.getStyle(), column);
				cell.setValue(data.getDatas().get(column.getColumnId()));
				cell.setStyle(style);
				j++;
			}
		}
	}
	
	private Style getCellStyle(Style s, MasterHeaderColumn column){
		Style style = new Style();
		style.copy(s);
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.HORIZONTAL, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.VERTICAL, CellBorderType.THIN, Color.getBlack());
		style.setVerticalAlignment(TextAlignmentType.CENTER);
		this.setFontStyle(style);
		if(column != null){
			style.setHorizontalAlignment(column.getTextAlign().value);
		}
		
		return style;
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
