package nts.uk.shr.infra.file.report.masterlist.generator;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

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
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.webservice.ReportType;

@Stateless
public class AsposeMasterListGenerator extends AsposeCellsReportGenerator implements MasterListReportGenerator {

	private static final String REPORT_ID = "MASTER_LIST";

	// private static final String REPORT_FILE_NAME = "マスタリスト_{TYPE}_{DATE}";

	private static final int HEADER_INFOR_START_ROW = 0;

	private static final int START_COLUMN = 0;

	private static final int MASTERLIST_DATA_START_ROW = 7;
	
	private static final int TABLE_DISTANCE = 3;

	private static final String FONT_FAMILY = "ＭＳ ゴシック";

	private static final int FONT_SIZE = 10;

	private static final int STANDARD_WIDTH = 100;

	private static final int STANDARD_HEIGHT = 25;

	@Override
	public void generate(FileGeneratorContext generatorContext, MasterListExportSource dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);

		val workbook = reportContext.getWorkbook();
		val sheet = workbook.getWorksheets().get(0);
		val cells = sheet.getCells();

		List<MasterHeaderColumn> columns = getViewColumn(dataSource.getHeaderColumns());

		sheet.setName("マスタリスト");

		String reportName = this.fillHeader(cells, dataSource, columns.size() <= 1 ? 1 : columns.size() - 1);

		if (!columns.isEmpty()) {
			this.setCommonStyle(cells);

			int startNextTable = drawATable(cells, columns, dataSource.getMasterList(), this.MASTERLIST_DATA_START_ROW);

			drawExtraTable(cells, startNextTable, dataSource.getExtraHeaderColumns(), dataSource.getExtraMasterList());

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

		switch (dataSource.getReportType()) {
		case CSV:
			reportContext.saveAsCSV(this.createNewFile(generatorContext, reportName + ".csv"));
			break;
		case EXCEL:
			reportContext.saveAsExcel(this.createNewFile(generatorContext, reportName + ".xlsx"));
			break;
		case PDF:
			reportContext.saveAsPdf(this.createNewFile(generatorContext, reportName + ".pdf"));
			break;
		default:
			break;
		}

	}
	
	private String fillHeader(Cells cells, MasterListExportSource dataSource, int columnSize) {
		Map<String, String> headerData = dataSource.getHeaders();
		int i = this.HEADER_INFOR_START_ROW;
		boolean isCsv = isExportCsvFile(dataSource.getReportType());
		for (Entry<String, String> headerInfor : headerData.entrySet()) {
			Cell labelCell = cells.get(this.HEADER_INFOR_START_ROW + i, 0);
			Range valueCell = cells.createRange(this.HEADER_INFOR_START_ROW + i, 1, 1, isCsv ? 1 : columnSize);
			valueCell.merge();

			Style style = this.getCellStyleNoBorder(labelCell.getStyle());

			labelCell.setValue(headerInfor.getKey());
			valueCell.setValue(headerInfor.getValue());

			labelCell.setStyle(style);
			valueCell.setStyle(style);

			i++;
		}
		return StringUtils.join(headerData.get("【種類】"), "_", getCreateReportTime(headerData.get("【日時】")));
	}

	private boolean isExportCsvFile(ReportType reportType) {
		return reportType == ReportType.CSV;
	}

	private String getCreateReportTime(String createDate) {
		return GeneralDateTime.fromString(createDate, "yyyy/MM/dd HH:mm:ss").localDateTime()
				.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}

	private int drawATable(final Cells cells, List<MasterHeaderColumn> columns,
			List<MasterData> datas, int startRow) {
		this.drawTableHeader(cells, columns, startRow);
		this.drawTableBody(cells, columns, datas, startRow);
		return startRow + datas.size() + this.TABLE_DISTANCE;
	}

	private int drawExtraTable(final Cells cells, int startRow,
			Map<String, List<MasterHeaderColumn>> extraTableColumns, Map<String, List<MasterData>> extraTableDatas) {
		if (!extraTableColumns.isEmpty()) {
			for (Entry<String, List<MasterHeaderColumn>> extraTable : extraTableColumns.entrySet()) {
				List<MasterHeaderColumn> columns = getViewColumn(extraTable.getValue());
				List<MasterData> extraData = extraTableDatas.get(extraTable.getKey());
				startRow = drawATable(cells, columns, extraData, startRow);
			}
		}
		return startRow;
	}

	private List<MasterHeaderColumn> getViewColumn(List<MasterHeaderColumn> original) {
		return original.stream().filter(c -> c.isDisplay()).collect(Collectors.toList());
	}

	private void drawTableHeader(Cells cells, List<MasterHeaderColumn> columns, int startRow) {
		int columnIndex = 0;

		for (MasterHeaderColumn c : columns) {
			Cell cell = cells.get(startRow - 1, columnIndex);
			cell.setStyle(this.getCellStyle(cell.getStyle(), c));
			cell.setValue(c.getColumnText());
			columnIndex++;
		}
	}

	private void drawTableBody(Cells cells, List<MasterHeaderColumn> columns, List<MasterData> datas, int startRow) {
		for (int i = 0; i < datas.size(); i++) {
			MasterData data = datas.get(i);
			int j = this.START_COLUMN;
			for (MasterHeaderColumn column : columns) {
				Cell cell = cells.get(startRow + i, j);
				// TODO: format date with format
				Style style = this.getCellStyle(cell.getStyle(), column);
				cell.setValue(data.getDatas().get(column.getColumnId()));
				cell.setStyle(style);
				j++;
			}
		}
	}

	private Style getCellStyle(Style s, MasterHeaderColumn column) {
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
		if (column != null) {
			style.setHorizontalAlignment(column.getTextAlign().value);
		}

		return style;
	}

	private Style getCellStyleNoBorder(Style s) {
		Style style = new Style();
		style.copy(s);
		style.setHorizontalAlignment(TextAlignmentType.LEFT);
		style.setVerticalAlignment(TextAlignmentType.CENTER);

		this.setFontStyle(style);

		return style;
	}

	private void setFontStyle(Style style) {

		Font font = style.getFont();
		font.setSize(FONT_SIZE);
		font.setName(FONT_FAMILY);
	}

	private void setCommonStyle(Cells cells) {
		cells.setStandardWidthPixels(this.STANDARD_WIDTH);
		cells.setStandardHeightPixels(this.STANDARD_HEIGHT);
	}
}
