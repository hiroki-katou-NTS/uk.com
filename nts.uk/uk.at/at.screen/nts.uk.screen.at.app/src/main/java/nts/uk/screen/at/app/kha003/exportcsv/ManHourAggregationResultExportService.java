package nts.uk.screen.at.app.kha003.exportcsv;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.*;
import nts.uk.screen.at.app.kdl053.RegistrationErrorListDto;
import nts.uk.screen.at.app.kha003.b.ManHourPeriod;
import nts.uk.screen.at.app.kha003.d.AggregationResultQuery;
import nts.uk.screen.at.app.kha003.d.CreateAggregationManHourResult;
import nts.uk.screen.at.app.kha003.d.ManHourAggregationResultDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ManHourAggregationResultExportService extends ExportService<ManHourDataSummaryQuery> {
//public class ManHourAggregationResultExportService extends ExportService<List<RegistrationErrorListDto>> {
    @Inject
    private CSVReportGenerator generator;

    private static final String CODE_HEADER = "KHA003_103";

    private static final String VERTICAL_TOTAL = "KHA003_99";

    private static final String HORIZONTAL_TOTAL = "KHA003_98";

    private static final String TOTAL = "KHA003_100";

    private static final String PGID = "KHA003";

    private static final String FILE_EXTENSION = ".csv";

    private static final String DATE_FORMAT = "yyyy/MM/dd";

    @Override
    protected void handle(ExportServiceContext<ManHourDataSummaryQuery> exportServiceContext) {
//    protected void handle(ExportServiceContext<List<RegistrationErrorListDto>> exportServiceContext) {
        val query = exportServiceContext.getQuery();
        if (query == null) return;
//        AggregationResultQuery query = new AggregationResultQuery(
//                "01", null, Collections.emptyList(),
//                new ManHourPeriod(
//                        0,
//                        "2021/06/01",
//                        "2021/06/03",
//                        "2021/06",
//                        "2021/06"
//                )
//        );

        String executionTime = GeneralDateTime.now().toString().replaceAll("[/:\\s]", "");

//        ManHourAggregationResultDto data = new ManHourAggregationResultDto(
//                Dummy.SummaryTableFormat.create(),
//                Dummy.SummaryTableOutputContent.create());
        val detailFormatSetting = query.getSummaryTableFormat().getDetailFormatSetting();
        val displayFormat = detailFormatSetting.getDisplayFormat();
        val outputContent = query.getOutputContent();
        val totalUnit = detailFormatSetting.getTotalUnit();
        int maxRangeDate = totalUnit == TotalUnit.DATE ? query.getPeriod().getDateList().size() : query.getPeriod().getYearMonthList().size();

        // Flag display total
        val isDisplayTotal = detailFormatSetting.getDisplayVerticalHorizontalTotal().value == 1;

        // create Header list
        List<String> headerList = this.createTextHeader(query, detailFormatSetting, isDisplayTotal);

        // Add data source
        List<Map<String, Object>> dataSource = new ArrayList<>();
        // Handle data on needed data to export
        this.dataOutputProcessing(outputContent, isDisplayTotal, totalUnit, dataSource, headerList, maxRangeDate, displayFormat);

        // Execute export
        CSVFileData fileData = new CSVFileData(
                PGID + "_" + executionTime + "_" + AppContexts.user().companyCode() + FILE_EXTENSION, headerList, dataSource);
        generator.generate(exportServiceContext.getGeneratorContext(), fileData);
    }

    private void dataOutputProcessing(ManHourSummaryTableOutputContent outputContent, boolean isDispTotal, TotalUnit unit, List<Map<String, Object>> dataSource,
                                      List<String> headerList, int maxRangeDate, DisplayFormat dispFormat) {
        for (SummaryItemDetail level1 : outputContent.getItemDetails()) {
            if (level1.getChildHierarchyList().isEmpty()) {
                Map<String, Object> row1 = new HashMap<>();
                row1.put(headerList.get(0), level1.getCode());
                row1.put(headerList.get(1), level1.getDisplayInfo().getName());

                val workingTimeMap1 = this.getWorkingTimeByDate(unit, level1.getVerticalTotalList());
                for (int i = 2; i < maxRangeDate + 2; i++) {
                    row1.put(headerList.get(i), formatValue(Double.valueOf(workingTimeMap1.getOrDefault(headerList.get(i), 0)), dispFormat));
                }
                if (isDispTotal) {  // Tong chieu ngang
                    row1.put(headerList.get(headerList.size() - 1), formatValue(level1.getTotalPeriod().isPresent() ? Double.valueOf(level1.getTotalPeriod().get()) : 0, dispFormat));
                }
                dataSource.add(row1);
            } else {
                for (SummaryItemDetail level2 : level1.getChildHierarchyList()) {
                    if (level2.getChildHierarchyList().isEmpty()) {
                        Map<String, Object> row2 = new HashMap<>();
                        row2.put(headerList.get(0), level1.getCode());
                        row2.put(headerList.get(1), level1.getDisplayInfo().getName());
                        row2.put(headerList.get(2), level2.getCode());
                        row2.put(headerList.get(3), level2.getDisplayInfo().getName());

                        for (int i = 4; i < maxRangeDate + 4; i++) {
                            val workingTimeMap2 = this.getWorkingTimeByDate(unit, level2.getVerticalTotalList());
                            row2.put(headerList.get(i), formatValue(Double.valueOf(workingTimeMap2.getOrDefault(headerList.get(i), 0)), dispFormat));
                        }
                        if (isDispTotal) {  // Tong chieu ngang
                            row2.put(headerList.get(headerList.size() - 1), formatValue(level2.getTotalPeriod().isPresent() ? Double.valueOf(level2.getTotalPeriod().get()) : 0, dispFormat));
                        }
                        dataSource.add(row2);
                    } else {
                        for (SummaryItemDetail level3 : level2.getChildHierarchyList()) {
                            if (level3.getChildHierarchyList().isEmpty()) {
                                Map<String, Object> row3 = new HashMap<>();
                                row3.put(headerList.get(0), level1.getCode());
                                row3.put(headerList.get(1), level1.getDisplayInfo().getName());
                                row3.put(headerList.get(2), level2.getCode());
                                row3.put(headerList.get(3), level2.getDisplayInfo().getName());
                                row3.put(headerList.get(4), level3.getCode());
                                row3.put(headerList.get(5), level3.getDisplayInfo().getName());

                                for (int i = 6; i < maxRangeDate + 6; i++) {
                                    val workingTimeMap3 = this.getWorkingTimeByDate(unit, level3.getVerticalTotalList());
                                    row3.put(headerList.get(i), formatValue(Double.valueOf(workingTimeMap3.getOrDefault(headerList.get(i), 0)), dispFormat));
                                }
                                if (isDispTotal) { // Tong chieu ngang
                                    row3.put(headerList.get(headerList.size() - 1), formatValue(level3.getTotalPeriod().isPresent() ? Double.valueOf(level3.getTotalPeriod().get()) : 0, dispFormat));
                                }
                                dataSource.add(row3);
                            } else {
                                for (SummaryItemDetail level4 : level3.getChildHierarchyList()) {
                                    Map<String, Object> row4 = new HashMap<>();
                                    row4.put(headerList.get(0), level1.getCode());
                                    row4.put(headerList.get(1), level1.getDisplayInfo().getName());
                                    row4.put(headerList.get(2), level2.getCode());
                                    row4.put(headerList.get(3), level2.getDisplayInfo().getName());
                                    row4.put(headerList.get(4), level3.getCode());
                                    row4.put(headerList.get(5), level3.getDisplayInfo().getName());
                                    row4.put(headerList.get(6), level4.getCode());
                                    row4.put(headerList.get(7), level4.getDisplayInfo().getName());

                                    for (int i = 8; i < maxRangeDate + 8; i++) {
                                        val workingTimeMap4 = this.getWorkingTimeByDate(unit, level4.getVerticalTotalList());
                                        row4.put(headerList.get(i), formatValue(Double.valueOf(workingTimeMap4.getOrDefault(headerList.get(i), 0)), dispFormat));
                                    }
                                    if (isDispTotal) { // Tong chieu ngang
                                        row4.put(headerList.get(headerList.size() - 1), formatValue(level4.getTotalPeriod().isPresent() ? Double.valueOf(level4.getTotalPeriod().get()) : 0, dispFormat));
                                    }
                                    dataSource.add(row4);
                                }
                                if (isDispTotal) {  // Tong level 4 theo chieu doc
                                    Map<String, Object> rowTotalLv4 = new HashMap<>();
                                    rowTotalLv4.put(headerList.get(5), headerList.get(5) + TextResource.localize(TOTAL));
                                    for (int i = 8; i < headerList.size(); i++) {
                                        val mapTotal4 = this.getWorkingTimeByDate(unit, level3.getVerticalTotalList());
                                        rowTotalLv4.put(headerList.get(i), formatValue(Double.valueOf(mapTotal4.getOrDefault(headerList.get(i), 0)), dispFormat));
                                    }
                                    rowTotalLv4.put(headerList.get(headerList.size() - 1), formatValue(level3.getTotalPeriod().isPresent() ? Double.valueOf(level3.getTotalPeriod().get()) : 0, dispFormat));
                                    dataSource.add(rowTotalLv4);
                                }
                            }
                        }
                        if (isDispTotal) {  // Tong level 3 theo chieu doc
                            Map<String, Object> rowTotalLv3 = new HashMap<>();
                            rowTotalLv3.put(headerList.get(3), headerList.get(3) + TextResource.localize(TOTAL));
                            for (int i = 6; i < headerList.size(); i++) {
                                val mapTotal2 = this.getWorkingTimeByDate(unit, level2.getVerticalTotalList());
                                rowTotalLv3.put(headerList.get(i), formatValue(Double.valueOf(mapTotal2.getOrDefault(headerList.get(i), 0)), dispFormat));
                            }
                            rowTotalLv3.put(headerList.get(headerList.size() - 1), formatValue(level2.getTotalPeriod().isPresent() ? Double.valueOf(level2.getTotalPeriod().get()) : 0, dispFormat));
                            dataSource.add(rowTotalLv3);
                        }
                    }
                }
                if (isDispTotal) { // Tong level 2 theo chieu doc
                    Map<String, Object> rowTotalLv2 = new HashMap<>();
                    rowTotalLv2.put(headerList.get(1), headerList.get(1) + TextResource.localize(TOTAL));
                    for (int i = 4; i < headerList.size(); i++) {
                        val mapTotal2 = this.getWorkingTimeByDate(unit, level1.getVerticalTotalList());
                        rowTotalLv2.put(headerList.get(i), formatValue(Double.valueOf(mapTotal2.getOrDefault(headerList.get(i), 0)), dispFormat));
                    }
                    rowTotalLv2.put(headerList.get(headerList.size() - 1), formatValue(level1.getTotalPeriod().isPresent() ? Double.valueOf(level1.getTotalPeriod().get()) : 0, dispFormat));
                    dataSource.add(rowTotalLv2);
                }
            }
        }
        if (isDispTotal) {  // Tong tat ca cac level theo chieu doc
            Map<String, Object> rowTotal = new HashMap<>();
            rowTotal.put(headerList.get(1), TextResource.localize(VERTICAL_TOTAL));
            for (int i = 2; i < headerList.size(); i++) {
                val mapTotal = this.getWorkingTimeByDate(unit, outputContent.getVerticalTotalValues());
                rowTotal.put(headerList.get(i), formatValue(Double.valueOf(mapTotal.getOrDefault(headerList.get(i), 0)), dispFormat));
            }
            rowTotal.put(headerList.get(headerList.size() - 1), formatValue(outputContent.getTotalPeriod().isPresent() ? Double.valueOf(outputContent.getTotalPeriod().get()) : 0, dispFormat));
            dataSource.add(rowTotal);
        }
    }

    private Map<String, Integer> getWorkingTimeByDate(TotalUnit unit, List<VerticalValueDaily> lstValueDaily) {
        Map<String, Integer> map = new HashMap<>();
        if (unit == TotalUnit.DATE)
            lstValueDaily.forEach(d -> map.put(d.getDate().toString(), d.getWorkingHours()));
        else
            lstValueDaily.forEach(d -> map.put(d.getDate().toString(), d.getWorkingHours()));

        return map;
    }

    /**
     * create text Header
     *
     * @param query
     * @param detailSetting
     * @param isDispTotal
     * @return
     */
    private List<String> createTextHeader(ManHourDataSummaryQuery query, DetailFormatSetting detailSetting, boolean isDispTotal) {
        List<String> lstHeader = new ArrayList<>();
        // Sort before adding
        val sortedList = detailSetting.getSummaryItemList().stream().sorted(Comparator.comparing(SummaryItem::getHierarchicalOrder)).collect(Collectors.toList());
        // Add code & name to header
        for (int i = 0; i < sortedList.size(); i++) {
            SummaryItem item = sortedList.get(i);
            lstHeader.add(TextResource.localize(CODE_HEADER) + i);
            lstHeader.add(item.getSummaryItemType().nameId);
        }

        // Add date/yearMonth list to header
        if (detailSetting.getTotalUnit() == TotalUnit.DATE)
            query.getPeriod().getDateList().forEach(date -> lstHeader.add(date.toString()));
        else
            query.getPeriod().getYearMonthList().forEach(ym -> lstHeader.add(yearMonthToString(ym)));

        // Add horizontal total to header
        if (isDispTotal) {
            lstHeader.add(TextResource.localize(HORIZONTAL_TOTAL));
        }
        return lstHeader;
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
                BigDecimal decimaValue = new BigDecimal(value);
                decimaValue = decimaValue.divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
                targetValue = String.valueOf(decimaValue.doubleValue());
                break;
            case HEXA_DECIMAL:
                BigDecimal decimalValue = new BigDecimal(value);
                BigDecimal intValue = decimalValue.divideToIntegralValue(BigDecimal.valueOf(60));
                BigDecimal remainValue = decimalValue.subtract(intValue.multiply(BigDecimal.valueOf(60)));
                StringBuilder sb = new StringBuilder();
                targetValue = sb.append(intValue).append(":").append(remainValue).toString();
                break;
            case MINUTE:
                DecimalFormat df = new DecimalFormat("#,###");
                targetValue = df.format(value);
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
    private String yearMonthToString(YearMonth yearMonth) {
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
