package nts.uk.file.com.infra.equipment.data;

import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.office.dom.equipment.achievement.EquipmentUsageRecordItemSetting;
import nts.uk.ctx.office.dom.equipment.achievement.ItemClassification;
import nts.uk.ctx.office.dom.equipment.achievement.ItemDisplay;
import nts.uk.ctx.office.dom.equipment.classificationmaster.EquipmentClassification;
import nts.uk.ctx.office.dom.equipment.data.ActualItemUsageValue;
import nts.uk.ctx.office.dom.equipment.data.EquipmentData;
import nts.uk.ctx.office.dom.equipment.data.ItemData;
import nts.uk.ctx.office.dom.equipment.information.EquipmentInformation;
import nts.uk.file.com.app.equipment.data.EquipmentDataExportDataSource;
import nts.uk.file.com.app.equipment.data.EquipmentDataExportGenerator;
import nts.uk.file.com.app.equipment.data.EquipmentDataReportType;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeEquipmentDataReportGenerator extends AsposeCellsReportGenerator
		implements EquipmentDataExportGenerator {

	private static final String TEMPLATE_FILE = "report/OEW001.xlsx";
	// B1_1 年月見出し
	private static final String EXPORT_YM_HEADER = "A1";
	// B2_1 設備分類コードヘッダー
	private static final String EQUIPMENT_CLS_CODE_HEADER = "A2";
	// B2_2 設備分類名称ヘッダー
	private static final String EQUIPMENT_CLS_NAME_HEADER = "B2";
	// B2_3 設備コードヘッダー
	private static final String EQUIPMENT_CODE_HEADER = "C2";
	// B2_4 設備名称ヘッダー
	private static final String EQUIPMENT_NAME_HEADER = "D2";
	// B2_5 利用日付ヘッダー
	private static final String USE_DATE_HEADER = "E2";
	// B2_6 氏名ヘッダー
	private static final String BUSINESS_NAME_HEADER = "F2";
	// D3_6 社員コード見出し
	private static final String CSV_SCD_HEADER = "F2";
	// D3_7 社員名称見出し
	private static final String CSV_BUSINESS_NAME_HEADER = "G2";
	// The column where dynamic header or dynamic data begins (G~)
	private static final int DYNAMIC_COL_START = 6;
	private static final int CSV_DYNAMIC_COL_START = 7;
	// Header row index
	private static final int HEADER_ROW = 1;
	// First data row index
	private static final int DATA_ROW = 2;
	// First data col index
	private static final int DATA_COL = 0;
	// C1_1 設備分類コード
	private static final int EQUIPMENT_CLS_CODE_INDEX = 0;
	// C1_2 設備分類名称
	private static final int EQUIPMENT_CLS_NAME_INDEX = 1;
	// C1_3 設備コード
	private static final int EQUIPMENT_CODE_INDEX = 2;
	// C1_4 設備名称
	private static final int EQUIPMENT_NAME_INDEX = 3;
	// C1_5 利用日付
	private static final int USE_DATE_INDEX = 4;
	// C1_6 氏名
	private static final int BUSINESS_NAME_INDEX = 5;
	// D5_7 社員名称
	private static final int CSV_BUSINESS_NAME_INDEX = 6;
	// Start of lines in CSV
	private static final int CSV_LINE_START_COL_INDEX = 0;
	// 1行目 ヘッダ
	private static final int CSV_TITLE_INDEX = 0;
	// 2行目 条件
	private static final int CSV_CONDITIONS_INDEX = 1;
	// 固定値Ｃ
	private static final int FIXED_VALUE_C = 8;

	@Override
	public void generate(FileGeneratorContext generatorContext, EquipmentDataExportDataSource dataSource) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			WorksheetCollection collection = reportContext.getWorkbook().getWorksheets();
			Worksheet sheet = collection.get(0);
			dataSource.getFormSetting().ifPresent(data -> sheet.setName(data.getTitle().v()));
			if (dataSource.getReportType().equals(EquipmentDataReportType.EXCEL)) {
				this.printHeaderExcel(sheet, reportContext, dataSource);
			} else {
				this.printHeaderCsv(sheet, reportContext, dataSource);
			}
			this.printData(sheet, reportContext, dataSource);

			String extension = dataSource.getReportType().equals(EquipmentDataReportType.EXCEL) ? ".xlsx" : ".csv";
			String fileName = dataSource.getFormSetting().map(data -> data.getTitle().v()).orElse("") + extension;
			OutputStream outputStream = this.createNewFile(generatorContext, fileName);
			reportContext.processDesigner();
			if (dataSource.getReportType().equals(EquipmentDataReportType.EXCEL)) {
				reportContext.saveAsExcel(outputStream);
			} else {
				reportContext.saveAsCSV(outputStream);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printHeaderExcel(Worksheet sheet, AsposeCellsReportContext reportContext,
			EquipmentDataExportDataSource dataSource) {
		String companyName = dataSource.getCompanyInfo().map(data -> data.getCompanyName().v()).orElse("");
		String titleName = dataSource.getFormSetting().map(data -> data.getTitle().v()).orElse("");
		String yearMonth = dataSource.getYearMonth().firstGeneralDate().toString("yyyy/MM");
		reportContext.setDataSource("titleName", titleName);
		reportContext.setDataSource("companyName", companyName);
		reportContext.setDataSource("yearMonth", yearMonth);
		sheet.getPageSetup().setHeader(0, "&\"ＭＳ ゴシック\"" + companyName);
		sheet.getPageSetup().setHeader(1, "&\"ＭＳ ゴシック\"" + titleName);
		this.printCell(sheet.getCells().get(EXPORT_YM_HEADER), TextResource.localize("OEW001_101"),
				TextAlignmentType.LEFT, null);
	}

	private void printHeaderCsv(Worksheet sheet, AsposeCellsReportContext reportContext,
			EquipmentDataExportDataSource dataSource) {
		Cells cells = sheet.getCells();
		String equipmentName = "すべての設備分類";
		if (dataSource.getOptEquipmentClsCode().isPresent()) {
			if (dataSource.getOptEquipmentInfoCode().isPresent()) {
				if (!dataSource.getEquipmentInfos().isEmpty()) {
					equipmentName = dataSource.getEquipmentInfos().get(0).getEquipmentName().v();
				}
			} else if (!dataSource.getEquipmentClassifications().isEmpty()) {
				equipmentName = dataSource.getEquipmentClassifications().get(0).getName().v();
			}
		}

		String yearMonth = dataSource.getYearMonth().firstGeneralDate().toString("yyyy/MM");
		this.printCell(cells.get(CSV_TITLE_INDEX, CSV_LINE_START_COL_INDEX),
				dataSource.getFormSetting().map(data -> data.getTitle().v()).orElse(null), TextAlignmentType.LEFT,
				null);
		this.printCell(cells.get(CSV_CONDITIONS_INDEX, CSV_LINE_START_COL_INDEX),
				TextResource.localize("OEW001_100") + equipmentName + "、" + yearMonth, TextAlignmentType.LEFT, null);
	}

	private void printData(Worksheet sheet, AsposeCellsReportContext reportContext,
			EquipmentDataExportDataSource dataSource) {
		Cells cells = sheet.getCells();
		boolean isPrintExcel = dataSource.getReportType().equals(EquipmentDataReportType.EXCEL);

		// Print headers
		// Print fixed headers
		this.printFixedColumnsHeader(cells, isPrintExcel);

		// Print dynamic headers
		List<ItemDisplay> itemDisplays = dataSource.getFormatSetting().getItemDisplaySettings().stream()
				.sorted(Comparator.comparing(ItemDisplay::getDisplayOrder)).collect(Collectors.toList());
		this.printDynamicColumnsHeader(cells, itemDisplays, dataSource.getItemSettings());

		// Print data
		List<EquipmentData> equipmentDatas = dataSource.getEquipmentDatas();
		AtomicInteger currentRow = new AtomicInteger(DATA_ROW);
		AtomicBoolean isBlueBackground = new AtomicBoolean(false);
		equipmentDatas.forEach(equipmentData -> {
			AtomicInteger currentColumn = new AtomicInteger(DATA_COL);
			int col = currentColumn.get();
			Cell cell = cells.get(currentRow.get(), currentColumn.getAndIncrement());

			Optional<EquipmentClassification> optEquipmentCls = dataSource.getEquipmentClassifications().stream()
					.filter(data -> data.getCode().equals(equipmentData.getEquipmentClassificationCode())).findFirst();
			Optional<EquipmentInformation> optEquipmentInfo = dataSource.getEquipmentInfos().stream()
					.filter(data -> data.getEquipmentCode().equals(equipmentData.getEquipmentCode())).findFirst();
			Optional<EmployeeInformation> optEmployee = dataSource.getEmployees().stream()
					.filter(data -> data.getEmployeeId().equals(equipmentData.getSid())).findFirst();

			// Print fixed data
			int colStart = isPrintExcel ? DYNAMIC_COL_START : CSV_DYNAMIC_COL_START;
			if (col < colStart) {
				switch (col) {
				case EQUIPMENT_CLS_CODE_INDEX:
					this.printCell(cell, equipmentData.getEquipmentClassificationCode().v(), TextAlignmentType.CENTER,
							isBlueBackground.get());
					break;
				case EQUIPMENT_CLS_NAME_INDEX:
					this.printCell(cell, optEquipmentCls.map(data -> data.getName().v()).orElse(null),
							TextAlignmentType.LEFT, isBlueBackground.get());
					break;
				case EQUIPMENT_CODE_INDEX:
					this.printCell(cell, equipmentData.getEquipmentCode().v(), TextAlignmentType.CENTER,
							isBlueBackground.get());
					break;
				case EQUIPMENT_NAME_INDEX:
					this.printCell(cell, optEquipmentInfo.map(data -> data.getEquipmentName().v()).orElse(null),
							TextAlignmentType.LEFT, isBlueBackground.get());
					break;
				case USE_DATE_INDEX:
					this.printCell(cell, equipmentData.getUseDate().toString("yyyy/MM/dd"), TextAlignmentType.CENTER,
							isBlueBackground.get());
					break;
				case BUSINESS_NAME_INDEX:
					if (isPrintExcel) {
						this.printCell(cell, optEmployee.map(EmployeeInformation::getBusinessName).orElse(null),
								TextAlignmentType.LEFT, isBlueBackground.get());
					} else {
						// case CSV_SCD_INDEX
						this.printCell(cell, optEmployee.map(EmployeeInformation::getEmployeeCode).orElse(null),
								TextAlignmentType.LEFT, isBlueBackground.get());
					}
					break;
				case CSV_BUSINESS_NAME_INDEX:
					this.printCell(cell, optEmployee.map(EmployeeInformation::getBusinessName).orElse(null),
							TextAlignmentType.LEFT, isBlueBackground.get());
					break;
				}
			} else {
				// Print dynamic data
				// Get display number
				int displayNo = col - DYNAMIC_COL_START + 1;
				Optional<ItemDisplay> optItemDisplay = itemDisplays.stream()
						.filter(data -> data.getDisplayOrder() == displayNo).findFirst();
				optItemDisplay.ifPresent(itemDisplay -> {
					Optional<ItemData> optItemData = equipmentData.getItemDatas().stream()
							.filter(data -> data.getItemNo().equals(itemDisplay.getItemNo())).findFirst();
					optItemData.ifPresent(itemData -> {
						String value = itemData.getActualValue().map(ActualItemUsageValue::v).orElse(null);
						int alignType = TextAlignmentType.LEFT;

						if (itemData.getItemClassification().equals(ItemClassification.NUMBER)) {
							value = String.format("%,f", Float.valueOf(value));
							alignType = TextAlignmentType.RIGHT;
						} else if (itemData.getItemClassification().equals(ItemClassification.TIME)) {
							value = LocalTime.MIN.plus(Duration.ofMinutes(Integer.valueOf(value))).toString();
							alignType = TextAlignmentType.RIGHT;
						}
						this.printCell(cell, value, alignType, isBlueBackground.get());
					});
				});
			}
			// Row + 1 and change background color
			currentRow.incrementAndGet();
			isBlueBackground.set(!isBlueBackground.get());
		});
	}

	/**
	 * Print cells A2~F2
	 * 
	 * @param cells
	 */
	private void printFixedColumnsHeader(Cells cells, boolean isPrintExcel) {
		int alignType = TextAlignmentType.CENTER;
		this.printCell(cells.get(EQUIPMENT_CLS_CODE_HEADER), TextResource.localize("OEW001_102"), alignType, null);
		this.printCell(cells.get(EQUIPMENT_CLS_NAME_HEADER), TextResource.localize("OEW001_103"), alignType, null);
		this.printCell(cells.get(EQUIPMENT_CODE_HEADER), TextResource.localize("OEW001_104"), alignType, null);
		this.printCell(cells.get(EQUIPMENT_NAME_HEADER), TextResource.localize("OEW001_105"), alignType, null);
		this.printCell(cells.get(USE_DATE_HEADER), TextResource.localize("OEW001_106"), alignType, null);
		if (isPrintExcel) {
			this.printCell(cells.get(BUSINESS_NAME_HEADER), TextResource.localize("OEW001_107"), alignType, null);
		} else {
			this.printCell(cells.get(CSV_SCD_HEADER), null, alignType, null);
			this.printCell(cells.get(CSV_BUSINESS_NAME_HEADER), TextResource.localize("OEW001_107"), alignType, null);
		}
	}

	/**
	 * Print cells G2~
	 * 
	 * @param cells
	 * @param col
	 * @param itemName
	 * @param width
	 */
	private void printDynamicColumnsHeader(Cells cells, List<ItemDisplay> itemDisplays,
			List<EquipmentUsageRecordItemSetting> itemSettings) {
		AtomicInteger currentCol = new AtomicInteger(DYNAMIC_COL_START);

		itemDisplays.forEach(itemDisplay -> {
			EquipmentUsageRecordItemSetting itemSetting = itemSettings.stream()
					.filter(data -> data.getItemNo().equals(itemDisplay.getItemNo())).findFirst().orElse(null);
			if (itemSetting != null) {
				int col = currentCol.getAndIncrement();
				int alignType = TextAlignmentType.CENTER;
				Cell cell = cells.get(HEADER_ROW, col);

				// Set column value
				this.printCell(cell, itemSetting.getItems().getItemName().v(), alignType, null);

				// Set column width
				cells.setColumnWidth(col, itemDisplay.getDisplayWidth().v() * FIXED_VALUE_C);
			}
		});
	}

	/**
	 * Print a cell
	 * 
	 * @param cell
	 * @param value
	 * @param alignType        {@link TextAlignmentType}
	 * @param isBlueBackground
	 *                         <li>null: for header</li>
	 *                         <li>true: for even row</li>
	 *                         <li>false: for odd row</li>
	 */
	private void printCell(Cell cell, String value, int alignType, Boolean isBlueBackground) {
		cell.setValue(value);
		Style style = cell.getStyle();
		style.setHorizontalAlignment(alignType);
		style.setVerticalAlignment(TextAlignmentType.CENTER);
		if (isBlueBackground != null) {
			if (isBlueBackground) {
				// Even row background color
				style.setBackgroundColor(Color.fromArgb(221, 235, 247));
			} else {
				// Odd row background color
				style.setBackgroundColor(Color.getWhite());
			}
		}
	}
}
