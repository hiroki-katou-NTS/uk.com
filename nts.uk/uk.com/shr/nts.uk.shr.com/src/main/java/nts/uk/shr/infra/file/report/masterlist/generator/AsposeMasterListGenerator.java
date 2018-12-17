package nts.uk.shr.infra.file.report.masterlist.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.infra.file.report.masterlist.annotation.NamedAnnotation;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;
import nts.uk.shr.infra.file.report.masterlist.webservice.ReportType;
//import nts.uk.shr.infra.i18n.loading.LanguageMasterRepository;

@Stateless
public class AsposeMasterListGenerator extends AsposeCellsReportGenerator implements MasterListReportGenerator {

	public static final int DEFAULT_FONT_SIZE = 10;
	
	public static final String DEFAULT_FONT_FAMILY = "ＭＳ ゴシック";
	
	private static final String PDF_FILE = ".pdf";

	private static final String EXCEL_FILE = ".xlsx";

	private static final String CSV_FILE = ".csv";

	private static final String COMPANY_ERROR = "Company is not found!!!!";

	private static final String EMPTY_STRING = "";

	private static final String FROM_TO = " ～ ";

	private static final String UNDERLINE = "_";

	private static final String SPACE = " ";

	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";

	private static final String COMPANY = "【会社】";

	private static final String FEATURE_TYPE = "【種類】";

	private static final String DATETIME = "【日時】";

	//private static final String LANGUAGE = "【選択言語】 ";

	private static final String SHEET_NAME = "【sheet名】";

	private static final String YEAR = "年";

	private static final String FISCAL_YEAR = "【対象年度】";

	private static final String BASE_DATE_LABEL = "【基準日】";

	private static final String YYYY_MM_DD = "yyyy/MM/dd";

	private static final String YYYY_M_MDD_H_HMMSS = "yyyyMMddHHmmss";

	private static final String REPORT_ID = "MASTER_LIST";

	// private static final String REPORT_FILE_NAME = "マスタリスト_{TYPE}_{DATE}";

	private static final int HEADER_INFOR_START_ROW = 0;

	private static final int START_COLUMN = 0;

	private static final int MASTERLIST_DATA_START_ROW = 10;

	private static final int TABLE_DISTANCE = 3;

	private static final int STANDARD_WIDTH = 100;

	private static final int STANDARD_HEIGHT = 25;

	//@Inject
	//private LanguageMasterRepository languageRepo;

	@Inject
	private CompanyAdapter company;

	@Override
	public void generate(FileGeneratorContext generatorContext, MasterListExportQuery query) {
		val reportContext = this.createEmptyContext(REPORT_ID);

		val workbook = reportContext.getWorkbook();
		
		MasterListData domainData = CDI.current().select(MasterListData.class, new NamedAnnotation(query.getDomainId())).get();
		
		List<SheetData> subSheets = domainData.extraSheets(query);

		SheetData mainSheet = domainData.mainSheet(query);

		String reportName = processSheet(mainSheet, 0, workbook, query, subSheets == null ? 0 : subSheets.size());

		if (!CollectionUtil.isEmpty(subSheets)) {
			for(int i = 1; i <= subSheets.size(); i++){
				processSheet(subSheets.get(i - 1), i, workbook, query, 0);
			}
		}

		workbook.getWorksheets().setActiveSheetIndex(0);
		
		reportContext.processDesigner();

		switch (query.getReportType()) {
		case CSV:
			reportContext.saveAsCSV(this.createNewFile(generatorContext, reportName + CSV_FILE));
			break;
		case EXCEL:
			reportContext.saveAsExcel(this.createNewFile(generatorContext, reportName + EXCEL_FILE));
			break;
		case PDF:
			reportContext.saveAsPdf(this.createNewFile(generatorContext, reportName + PDF_FILE));
			break;
		default:
			break;
		}

	}

	@SneakyThrows
	private String processSheet(SheetData sheetData, int idx, Workbook workbook, MasterListExportQuery query, int max) {

		List<MasterHeaderColumn> columns = this.getViewColumn(sheetData.getMainDataColumns());
		String reportName = null;
		Worksheet sheet;
		Cells cells;

		if (idx == 0) {
			sheet = workbook.getWorksheets().get(0);
			cells = sheet.getCells();
			
			reportName = this.fillHeader(cells, query, columns.size() <= 1 ? 1 : columns.size() - 1, true);
			
			this.cloneSheets(max, workbook);
		} else {
			sheet = workbook.getWorksheets().get(idx);
			cells = sheet.getCells();
		}

		this.setCommonStyle(cells);

		int startNextTable = this.drawATable(cells, columns, sheetData.getMainData(), MASTERLIST_DATA_START_ROW);

		this.drawExtraTable(cells, startNextTable, sheetData.getSubDatas(), sheetData.getSubDataColumns());

		this.processSheetName(sheetData.getSheetName(), sheet, cells, workbook, idx);
		
		this.setDefaultSheetOption(sheet);

		return reportName;
	}

	private void processSheetName(String sheetName, Worksheet sheet, Cells cells, Workbook workbook, int idx) {
		boolean isExistName = false;
		
		for(Object x : workbook.getWorksheets()){
			if(((Worksheet) x).getName().equals(sheetName)){
				isExistName = true;
				break;
			}
		}
		
		sheet.setName(getSheetName(sheetName, idx, isExistName));
		
		cells.get(HEADER_INFOR_START_ROW + 4, 1).setValue(sheetName);
	}
	
	@SneakyThrows
	private void cloneSheets(int max, final com.aspose.cells.Workbook workbook) {
		for(int i = 0; i < max; i++){
			workbook.getWorksheets().addCopy(0);
		}
	}

	private String getSheetName(String sheetName, int idx, boolean isExistName) {
		return isExistName ? sheetName + "(" + idx + ")" : sheetName;
	}

	private void setDefaultSheetOption(final Worksheet sheet) {
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

	private String fillHeader(Cells cells, MasterListExportQuery query, int columnSize, boolean createName) {

		LoginUserContext context = AppContexts.user();
		String companyname = this.company.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
															.getCompanyName();
		
		GeneralDateTime now = GeneralDateTime.now();
		boolean isCsv = isExportCsvFile(query.getReportType());
		
		processHeaderInfo(cells, columnSize, HEADER_INFOR_START_ROW, isCsv, COMPANY, context.companyCode() + SPACE + companyname);
		processHeaderInfo(cells, columnSize, HEADER_INFOR_START_ROW + 1, isCsv, FEATURE_TYPE, query.getDomainType());
		processHeaderInfo(cells, columnSize, HEADER_INFOR_START_ROW + 2, isCsv, DATETIME, now.toString(YYYY_MM_DD_HH_MM_SS));
		//processHeaderInfo(cells, columnSize, HEADER_INFOR_START_ROW + 3, isCsv, LANGUAGE, 
		//		languageRepo.getSystemLanguage(query.getLanguageId()).get().getLanguageName());
		processHeaderInfo(cells, columnSize, HEADER_INFOR_START_ROW + 4, isCsv, SHEET_NAME, EMPTY_STRING);
		
		if(query.getMode() == MasterListMode.BASE_DATE){
			processHeaderInfo(cells, columnSize, HEADER_INFOR_START_ROW + 5, isCsv, BASE_DATE_LABEL, 
					query.getStartDate() == null ? EMPTY_STRING : query.getStartDate().toString(YYYY_MM_DD));
		} else if (query.getMode() == MasterListMode.FISCAL_YEAR_RANGE) {
			String range = (query.getStartDate() == null ? EMPTY_STRING : query.getStartDate().year() + YEAR)
								+ FROM_TO
								+ (query.getEndDate() == null ? EMPTY_STRING : query.getEndDate().year() + YEAR);
			processHeaderInfo(cells, columnSize, HEADER_INFOR_START_ROW + 5, isCsv, FISCAL_YEAR, range);
		}
		
		
		if (!createName)
			return EMPTY_STRING;

		return StringUtils.join(query.getDomainType(), UNDERLINE, now.toString(YYYY_M_MDD_H_HMMSS));
	}

	private void processHeaderInfo(Cells cells, int columnSize, int row, boolean isCsv,
			String label, String info) {
		Cell labelCell = cells.get(row, 0);
		Range valueCell = cells.createRange(row, 1, 1, isCsv ? 1 : columnSize);
		valueCell.merge();

		Style style = this.getCellStyleNoBorder(labelCell.getStyle());

		labelCell.setValue(label);
		valueCell.setValue(info);

		labelCell.setStyle(style);
		valueCell.setStyle(style);
	}

	private boolean isExportCsvFile(ReportType reportType) {
		return reportType == ReportType.CSV;
	}

	private int drawATable(final Cells cells, List<MasterHeaderColumn> columns, List<MasterData> datas, int startRow) {
		this.drawTableHeader(cells, columns, startRow);
		this.drawTableBody(cells, columns, datas, startRow);

		return startRow + datas.size() + TABLE_DISTANCE;
	}

	private int drawExtraTable(final Cells cells, int startRow, Map<String, List<MasterData>> dataSource,
			Map<String, List<MasterHeaderColumn>> extraColumnMaps) {
		if (!extraColumnMaps.isEmpty()) {
			val colMap = new ArrayList<>(extraColumnMaps.entrySet());

			for (int i = 0; i < colMap.size(); i++) {
				Entry<String, List<MasterHeaderColumn>> extraCol = colMap.get(i);
				List<MasterHeaderColumn> columns = this.getViewColumn(extraCol.getValue());
				List<MasterData> extraData = dataSource.get(extraCol.getKey());

				startRow = this.drawATable(cells, columns, extraData, startRow);
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
			cell.setStyle(this.getCellStyle(cell.getStyle(), c.getStyle()));
			cell.setValue(c.getColumnText());
			columnIndex++;
		}
	}

	private void drawTableBody(Cells cells, List<MasterHeaderColumn> columns, List<MasterData> datas, int startRow) {
		for (int i = 0; i < datas.size(); i++) {
			MasterData data = datas.get(i);
			int j = START_COLUMN;
			for (MasterHeaderColumn column : columns) {
				Cell cell = cells.get(startRow + i, j);
				// TODO: format date with format
				MasterCellData cellData = data.cellAt(column.getColumnId());
				Style style = this.getCellStyle(cell.getStyle(), cellData.getStyle());
				cell.setValue(cellData.getValue());
				cell.setStyle(style);
				if(!StringUtil.isNullOrEmpty(cellData.getStyle().columnFormat(), true)) {
					cell.setFormula(cellData.getStyle().columnFormat());
				}
				j++;
			}
		}
	}

	private Style getCellStyle(Style s, MasterCellStyle cellStyle) {
		Style style = new Style();
		style.copy(s);
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.HORIZONTAL, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.VERTICAL, CellBorderType.THIN, Color.getBlack());

		Font font = style.getFont();
		
		font.setSize(cellStyle.fontSize());
		font.setName(cellStyle.fontFamily());
		style.setHorizontalAlignment(cellStyle.horizontalAlign().value);
		style.setVerticalAlignment(cellStyle.verticalAlign().value);
		style.setBackgroundColor(toColor(cellStyle.backgroundColor()));
		style.setForegroundColor(toColor(cellStyle.textColor()));

		return style;
	}
	
	private Color toColor(java.awt.Color base){
		return Color.fromArgb(base.getAlpha(), base.getRed(), base.getGreen(), base.getBlue());
	}

	private Style getCellStyleNoBorder(Style s) {
		Style style = new Style();
		style.copy(s);
		style.setHorizontalAlignment(TextAlignmentType.LEFT);
		style.setVerticalAlignment(TextAlignmentType.CENTER);
		Font font = style.getFont();
		font.setSize(DEFAULT_FONT_SIZE);
		font.setName(DEFAULT_FONT_FAMILY);

		this.setFontStyle(style);

		return style;
	}

	private void setFontStyle(Style style) {

	}

	private void setCommonStyle(Cells cells) {
		cells.setStandardWidthPixels(STANDARD_WIDTH);
		cells.setStandardHeightPixels(STANDARD_HEIGHT);
	}
}
