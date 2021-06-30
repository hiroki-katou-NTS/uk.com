package nts.uk.file.at.infra.manhoursummarytable;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.*;
import nts.uk.file.at.app.export.manhoursummarytable.ManHourSummaryExportData;
import nts.uk.file.at.app.export.manhoursummarytable.ManHourSummaryTableGenerator;
import nts.uk.screen.at.app.kha003.b.ManHourPeriod;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeManHourSummaryTableGenerator extends AsposeCellsReportGenerator implements ManHourSummaryTableGenerator {
    private static final String FILE_TITLE = "職場別作業集計表";
    private static final String TEMPLATE_FILE = "report/KHA003.xlsx";
    private static final String EXCEL_EXTENSION = ".xlsx";
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String VERTICAL_TOTAL = "KHA003_99";
    private static final String HORIZONTAL_TOTAL = "KHA003_98";
    private static final String TOTAL = "KHA003_100";
    private static final int MAX_COLUMN_TEMPLATE = 36;

    @Override
    public void generate(FileGeneratorContext generatorContext, ManHourSummaryExportData data) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            String title = FILE_TITLE;

            val isDisplayTotal = data.getSummaryTableFormat().getDetailFormatSetting().getDisplayVerticalHorizontalTotal().value == 1;
            Worksheet worksheetTemplate = isDisplayTotal ? worksheets.get(0) : worksheets.get(1);
            Worksheet worksheet = worksheets.get(3);
            worksheet.setName(title);

            pageSetting(worksheet, title);
            printContents(worksheetTemplate, worksheet, data, title);
            worksheets.removeAt(0);
            worksheets.removeAt(1);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            val fileName = title + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss");
            reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + EXCEL_EXTENSION));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void pageSetting(Worksheet worksheet, String title) {
        String companyName = "3Si";
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);

        pageSetup.setHeader(0, "&9&\"ＭＳ フォントサイズ\"" + companyName);
        pageSetup.setHeader(1, "&16&\"ＭＳ フォントサイズ,Bold\"" + title);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        pageSetup.setHeader(2,
                "&9&\"MS フォントサイズ\"" + LocalDateTime.now().format(dateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
        pageSetup.setFitToPagesTall(0);
        pageSetup.setFitToPagesWide(0);
        pageSetup.setCenterHorizontally(true);
        pageSetup.setBottomMarginInch(1.5);
        pageSetup.setTopMarginInch(1.5);
        pageSetup.setLeftMarginInch(1.0);
        pageSetup.setRightMarginInch(1.0);
        pageSetup.setHeaderMarginInch(0.8);
        pageSetup.setZoom(100);
    }

    private void printContents(Worksheet worksheetTemplate, Worksheet worksheet, ManHourSummaryExportData data, String title) throws Exception {
        val detailFormatSetting = data.getSummaryTableFormat().getDetailFormatSetting();
        val dispFormat = detailFormatSetting.getDisplayFormat();
        val totalUnit = detailFormatSetting.getTotalUnit();
        val isDisplayTotal = detailFormatSetting.getDisplayVerticalHorizontalTotal().value == 1;
        val outputContent = data.getOutputContent();

        String dateRange = totalUnit == TotalUnit.DATE
                ? data.getPeriod().getDatePeriod().start().toString(DATE_FORMAT) + "　～　" + data.getPeriod().getDatePeriod().end().toString(DATE_FORMAT)
                : data.getPeriod().getYearMonthPeriod().start().toString() + "　～　" + data.getPeriod().getYearMonthPeriod().end().toString();
        int maxDateRange = totalUnit == TotalUnit.DATE ? data.getPeriod().getDateList().size() : data.getPeriod().getYearMonthList().size();
        HierarchyData hierarchyData = new HierarchyData(0);
        countHierarchy(outputContent.getItemDetails(), hierarchyData);
        // Max level: 4
        int totalLevel = hierarchyData.getTotalLevel();
        if (totalLevel == 0) return;
        val headerList = getHeaderList(data.getPeriod(), detailFormatSetting, isDisplayTotal);
        int countColumn = headerList.size();

        Cells cellsTemplate = worksheetTemplate.getCells();
        Cells cells = worksheet.getCells();
        cells.copyRows(cellsTemplate, 0, 0, 2);  // Copy 3 row

        if (totalLevel == 1) {
            cells.deleteColumns(1, 3, true);
        }
        if (totalLevel == 2) {
            cells.deleteColumns(2, 2, true);
        }
        if (totalLevel == 3) {
            cells.deleteColumns(3, 1, true);
        }

        // Check total column
        int maxColumnTemplate = isDisplayTotal ? MAX_COLUMN_TEMPLATE : (MAX_COLUMN_TEMPLATE - 1);
        int columnHandle = checkTotalColumn(maxColumnTemplate, countColumn);
        if (columnHandle < 0) {
//            if (Math.abs(columnHandle) > maxColumnTemplate) {  //TODO : case columnHandle > maxColumnTemplate
//                int columnCopyAvai = Math.abs(columnHandle);
//                do {
//                    if (columnCopyAvai < 35){
//                        cells.copyColumns(cellsTemplate, 5, headerList.size(), Math.abs(columnHandle));
//                    }
//                    columnCopyAvai = Math.abs(columnHandle);
//                }
//                while (columnCopyAvai <= Math.abs(columnHandle));
//            }
            cells.copyColumns(cellsTemplate, 5, headerList.size(), Math.abs(columnHandle));
        }
        if (columnHandle > 0) {
            if (totalLevel == 1) {
                cells.deleteColumns(1, columnHandle - 3, true);
            }
            if (totalLevel == 2) {
                cells.deleteColumns(2, columnHandle - 2, true);
            }
            if (totalLevel == 3) {
                cells.deleteColumns(3, columnHandle - 1, true);
            }
        }

//        double columnWith = 0.41;
//        cells.setColumnWidthInch(0,(columnWith) *2);
//        for (int i = 1; i <= 20 ; i++) {
//            cells.setColumnWidthInch(i,columnWith);
//        }
        cells.clearContents(0, 0, cells.getMaxRow(), cells.getMaxColumn());
        //A1_1
        cells.get(0, 0).setValue(title);
        //A1_2
        cells.get(1, 0).setValue(dateRange);
        //D2_1 -> D5_1 , D6_1... D7_1 : Print Header
        for (int i = 0; i <= headerList.size() - 1; i++) {
            String item = headerList.get(i);
            cells.get(2, i).setValue(item);
        }

        // Print data
        switch (totalLevel) {
            case 1:
                printData1Level(cellsTemplate, cells, outputContent, isDisplayTotal, maxDateRange, headerList, dispFormat, totalUnit);
                break;
            case 2:
                printData2Level(cellsTemplate, cells, outputContent, isDisplayTotal, maxDateRange, headerList, dispFormat, totalUnit);
                break;
            case 3:
                printData3Level(cellsTemplate, cells, outputContent, isDisplayTotal, maxDateRange, headerList, dispFormat, totalUnit);
                break;
            case 4:
                printData4Level(cellsTemplate, cells, outputContent, isDisplayTotal, maxDateRange, headerList, dispFormat, totalUnit);
                break;
        }
    }

    private int checkTotalColumn(int maxColumnTemplate, int countColumn) {
        int countColumnNeedHandle;
        if (countColumn < maxColumnTemplate) { // Thua: > 0
            countColumnNeedHandle = maxColumnTemplate - countColumn;
        } else if (countColumn > maxColumnTemplate) { // Thieu: < 0
            countColumnNeedHandle = maxColumnTemplate - countColumn;
        } else {
            countColumnNeedHandle = 0;
        }

        return countColumnNeedHandle;
    }

    private void printData1Level(Cells cellsTemplate, Cells cells, ManHourSummaryTableOutputContent outputContent, boolean isDispTotal, int maxDateRange, List<String> headerList, DisplayFormat dispFormat, TotalUnit unit) throws Exception {
        List<SummaryItemDetail> itemDetails = outputContent.getItemDetails();
        cells.copyRows(cellsTemplate, 3, 3, itemDetails.size());
        for (int i = 0; i < itemDetails.size(); i++) {
            SummaryItemDetail level1 = itemDetails.get(i);
            cells.get(i + 3, 0).setValue(level1.getDisplayInfo().getName());
            val workingTimeMap1 = this.getWorkingTimeByDate(unit, level1.getVerticalTotalList());
            // Fill data by date/yearMonth
            for (int r = 1; r <= maxDateRange; r++) {
                cells.get(i + 3, r).setValue(formatValue(Double.valueOf(workingTimeMap1.getOrDefault(headerList.get(r), 0)), dispFormat));
            }
            // Total of each row by horizontal
            if (isDispTotal) {
                cells.get(i + 3, headerList.size() - 1).setValue(formatValue(level1.getTotalPeriod().isPresent() ? Double.valueOf(level1.getTotalPeriod().get()) : 0, dispFormat));
            }
        }
        if (isDispTotal) { // Total of each row by vertical
            cells.copyRows(cellsTemplate, 3, 3, 1);
            printAllTotalByVertical(cells, outputContent, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1);
        }
    }

    private void printData2Level(Cells cellsTemplate, Cells cells, ManHourSummaryTableOutputContent outputContent, boolean isDispTotal, int maxDateRange, List<String> headerList, DisplayFormat dispFormat, TotalUnit unit) {
        List<SummaryItemDetail> itemDetails = outputContent.getItemDetails();
        for (SummaryItemDetail level1 : itemDetails) {
            List<SummaryItemDetail> childHierarchyList = level1.getChildHierarchyList();
            for (int j = 0; j < childHierarchyList.size(); j++) {
                SummaryItemDetail level2 = childHierarchyList.get(j);
                cells.get(j + 2, 0).setValue(level1.getDisplayInfo().getName());
                cells.get(j + 2, 1).setValue(level2.getDisplayInfo().getName());
                val workingTimeMap2 = this.getWorkingTimeByDate(unit, level2.getVerticalTotalList());
                for (int r = 1; r <= maxDateRange; r++) {
                    cells.get(j + 2, r + 1).setValue(formatValue(Double.valueOf(workingTimeMap2.getOrDefault(headerList.get(r), 0)), dispFormat));
                }
                if (isDispTotal) {  // Tong level2 theo chieu ngang
                    cells.get(j + 2, headerList.size() - 1).setValue(formatValue(level2.getTotalPeriod().isPresent() ? Double.valueOf(level2.getTotalPeriod().get()) : 0, dispFormat));
                }
            }
            if (isDispTotal) { // Tong level 2 theo chieu doc
                printTotalByVerticalOfEachLevel(cells, level1, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1, 1, 0, level1.getDisplayInfo().getName());
            }
        }
        if (isDispTotal) { // Tong tat ca cac level theo chieu doc
            printAllTotalByVertical(cells, outputContent, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1);
        }
    }

    private void printData3Level(Cells cellsTemplate, Cells cells, ManHourSummaryTableOutputContent outputContent, boolean isDispTotal, int maxDateRange, List<String> headerList, DisplayFormat dispFormat, TotalUnit unit) {
        List<SummaryItemDetail> itemDetails = outputContent.getItemDetails();
        for (SummaryItemDetail level1 : itemDetails) {
            for (SummaryItemDetail level2 : level1.getChildHierarchyList()) {
                List<SummaryItemDetail> childHierarchyList = level2.getChildHierarchyList();
                for (int i = 0; i < childHierarchyList.size(); i++) {
                    SummaryItemDetail level3 = childHierarchyList.get(i);
                    cells.get(i + 2, 0).setValue(level1.getDisplayInfo().getName());
                    cells.get(i + 2, 1).setValue(level2.getDisplayInfo().getName());
                    cells.get(i + 2, 2).setValue(level3.getDisplayInfo().getName());
                    val workingTimeMap3 = this.getWorkingTimeByDate(unit, level3.getVerticalTotalList());
                    for (int r = 1; r <= maxDateRange; r++) {
                        cells.get(i + 2, r + 2).setValue(formatValue(Double.valueOf(workingTimeMap3.getOrDefault(headerList.get(r), 0)), dispFormat));
                    }
                    if (isDispTotal) {  // Tong chieu ngang level 3
                        cells.get(i + 2, headerList.size() - 1).setValue(formatValue(level3.getTotalPeriod().isPresent() ? Double.valueOf(level3.getTotalPeriod().get()) : 0, dispFormat));
                    }
                }
                if (isDispTotal) { // Tong chieu doc level 3
                    printTotalByVerticalOfEachLevel(cells, level2, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1, 2, 1, level2.getDisplayInfo().getName());
                }
            }
            if (isDispTotal) { // Tong chieu doc level 2
                printTotalByVerticalOfEachLevel(cells, level1, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1, 1, 0, level1.getDisplayInfo().getName());
            }
        }
        if (isDispTotal) { // Tong chieu doc cua tat ca
            printAllTotalByVertical(cells, outputContent, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1);
        }
    }

    private void printData4Level(Cells cellsTemplate, Cells cells, ManHourSummaryTableOutputContent outputContent, boolean isDispTotal, int maxDateRange, List<String> headerList, DisplayFormat dispFormat, TotalUnit unit) throws Exception {
        cells.copyRows(cellsTemplate, 3, 3, 20);
        List<SummaryItemDetail> itemDetails = outputContent.getItemDetails();
        for (SummaryItemDetail level1 : itemDetails) {
            for (SummaryItemDetail level2 : level1.getChildHierarchyList()) {
                for (SummaryItemDetail level3 : level2.getChildHierarchyList()) {
                    List<SummaryItemDetail> childHierarchyList = level3.getChildHierarchyList();
                    for (int i = 0; i < childHierarchyList.size(); i++) {
                        SummaryItemDetail level4 = childHierarchyList.get(i);
                        cells.get(i + 2, 0).setValue(level1.getDisplayInfo().getName());
                        cells.get(i + 2, 1).setValue(level2.getDisplayInfo().getName());
                        cells.get(i + 2, 2).setValue(level3.getDisplayInfo().getName());
                        cells.get(i + 2, 3).setValue(level4.getDisplayInfo().getName());
                        val workingTimeMap4 = this.getWorkingTimeByDate(unit, level4.getVerticalTotalList());
                        for (int r = 1; r <= maxDateRange; r++) {
                            cells.get(i + 2, r + 3).setValue(formatValue(Double.valueOf(workingTimeMap4.getOrDefault(headerList.get(r), 0)), dispFormat));
                        }
                        if (isDispTotal) {  // Tong chieu ngang level 4
                            cells.get(i + 2, headerList.size() - 1).setValue(formatValue(level4.getTotalPeriod().isPresent() ? Double.valueOf(level4.getTotalPeriod().get()) : 0, dispFormat));
                        }
                    }
                    if (isDispTotal) { // Tong chieu doc level 4
                        printTotalByVerticalOfEachLevel(cells, level3, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1, 3, 2, level3.getDisplayInfo().getName());
                    }
                }
                if (isDispTotal) { // Tong chieu doc level 3
                    printTotalByVerticalOfEachLevel(cells, level2, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1, 2, 1, level2.getDisplayInfo().getName());
                }
            }
            if (isDispTotal) { // Tong chieu doc level 2
                printTotalByVerticalOfEachLevel(cells, level1, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1, 1, 0, level1.getDisplayInfo().getName());
            }
        }
        if (isDispTotal) { // Tong chieu doc cua tat ca
            printAllTotalByVertical(cells, outputContent, maxDateRange, headerList, dispFormat, unit, cells.getMaxRow() + 1);
        }
    }

    // Total of each column of each level by vertical
    private void printTotalByVerticalOfEachLevel(Cells cells, SummaryItemDetail summaryItemDetail, int maxDateRange, List<String> headerList, DisplayFormat dispFormat, TotalUnit unit,
                                                 int row, int index, int columnNameIndex, String columnName) {
        cells.get(row, columnNameIndex).setValue(columnName + TextResource.localize(TOTAL));
        for (int t = 1; t <= maxDateRange; t++) {
            val mapTotal = this.getWorkingTimeByDate(unit, summaryItemDetail.getVerticalTotalList());
            cells.get(cells.getMaxRow() + 1, t + index).setValue(formatValue(Double.valueOf(mapTotal.getOrDefault(headerList.get(t), 0)), dispFormat));
        }
        cells.get(row, headerList.size() - 1).setValue(formatValue(summaryItemDetail.getTotalPeriod().isPresent() ? Double.valueOf(summaryItemDetail.getTotalPeriod().get()) : 0, dispFormat));
    }

    // All total by vertical
    private void printAllTotalByVertical(Cells cells, ManHourSummaryTableOutputContent outputContent, int maxDateRange, List<String> headerList, DisplayFormat dispFormat, TotalUnit unit, int row) {
        cells.get(row, 0).setValue(TextResource.localize(VERTICAL_TOTAL));
        for (int t = 1; t <= maxDateRange; t++) {
            val mapTotal = this.getWorkingTimeByDate(unit, outputContent.getVerticalTotalValues());
            cells.get(cells.getMaxRow() + 1, t).setValue(formatValue(Double.valueOf(mapTotal.getOrDefault(headerList.get(t), 0)), dispFormat));
        }
        cells.get(row, headerList.size() - 1).setValue(formatValue(outputContent.getTotalPeriod().isPresent() ? Double.valueOf(outputContent.getTotalPeriod().get()) : 0, dispFormat));
    }

    private Map<String, Integer> getWorkingTimeByDate(TotalUnit unit, List<VerticalValueDaily> lstValueDaily) {
        Map<String, Integer> map = new HashMap<>();
        if (unit == TotalUnit.DATE)
            lstValueDaily.forEach(d -> map.put(d.getDate().toString(), d.getWorkingHours()));
        else
            lstValueDaily.forEach(d -> map.put(d.getDate().toString(), d.getWorkingHours()));

        return map;
    }

    private List<String> getHeaderList(ManHourPeriod period, DetailFormatSetting detailSetting, boolean isDispTotal) {
        List<String> lstHeader = new ArrayList<>();
        // Sort before adding
        val sortedList = detailSetting.getSummaryItemList().stream().sorted(Comparator.comparing(SummaryItem::getHierarchicalOrder)).collect(Collectors.toList());
        // Add code & name to header
        for (int i = 0; i < sortedList.size(); i++) {
            SummaryItem item = sortedList.get(i);
            lstHeader.add(item.getSummaryItemType().nameId);
        }

        // Add date/yearMonth list to header
        if (detailSetting.getTotalUnit() == TotalUnit.DATE)
            period.getDateList().forEach(date -> lstHeader.add(date.toString()));
        else
            period.getYearMonthList().forEach(ym -> lstHeader.add(toYearMonthString(ym)));

        // Add horizontal total to header
        if (isDispTotal) lstHeader.add(TextResource.localize(HORIZONTAL_TOTAL));

        return lstHeader;
    }

    private Integer getSourceRowIndex(Integer maxLevel) {
        return null;
    }

    private Integer getTargetRowIndex() {
        return null;
    }

    private Integer checkMaxRowNumber(int maxColumn) {
        return null;
    }

    private void countHierarchy(List<SummaryItemDetail> parentList, HierarchyData result) {
        int totalLevel = result.getTotalLevel();
        if (CollectionUtil.isEmpty(parentList)) return;
        List<SummaryItemDetail> childHierarchy = parentList.stream().flatMap(x -> x.getChildHierarchyList().stream()).collect(Collectors.toList());
        totalLevel += 1;
        result.setTotalLevel(totalLevel);
        countHierarchy(childHierarchy, result);
    }

    /**
     * Format value by display format
     *
     * @param value
     * @param displayFormat
     * @return String
     */
    private String formatValue(Double value, DisplayFormat displayFormat) {
        String targetValue = null;
        switch (displayFormat) {
            case DECIMAL:
                if (value != null && value != 0) {
                    DecimalFormat formatter = new DecimalFormat("#.#");
                    targetValue = formatter.format(value);
                }
                break;
            case HEXA_DECIMAL:
                BigDecimal decimalValue = new BigDecimal(value);
                BigDecimal intValue = decimalValue.divideToIntegralValue(BigDecimal.valueOf(60.00));
                BigDecimal remainValue = decimalValue.subtract(intValue.multiply(BigDecimal.valueOf(60.00)));
                decimalValue = intValue.add(remainValue.divide(BigDecimal.valueOf(100.00), 2, RoundingMode.HALF_UP));
                targetValue = decimalValue.toString();
                break;
            case MINUTE:
                if (value != null) {
                    val intItemValue = value.intValue();
                    if (intItemValue != 0) {
                        targetValue = toMinute(intItemValue);
                    }
                }
                break;
        }

        return targetValue;
    }

    /**
     * convert YearMonth to String with format: yyyy/MM
     *
     * @param yearMonth
     * @return
     */
    private String toYearMonthString(YearMonth yearMonth) {
        return String.format("%04d/%02d", yearMonth.year(), yearMonth.month());
    }

    /**
     * Convert to minute (HH:mm)
     *
     * @param value
     * @return
     */
    private String toMinute(int value) {
        val minuteAbs = Math.abs(value);
        int hours = minuteAbs / 60;
        int minutes = minuteAbs % 60;
        return (value < 0 ? "-" : "") + String.format("%d:%02d", hours, minutes);
    }
}
