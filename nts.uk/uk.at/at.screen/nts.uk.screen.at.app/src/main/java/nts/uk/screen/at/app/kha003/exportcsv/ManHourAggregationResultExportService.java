package nts.uk.screen.at.app.kha003.exportcsv;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.*;
import nts.uk.screen.at.app.kdl053.RegistrationErrorListDto;
import nts.uk.screen.at.app.kha003.d.AggregationResultDto;
import nts.uk.screen.at.app.kha003.d.AggregationResultQuery;
import nts.uk.screen.at.app.kha003.d.CreateAggregationManHourResult;
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
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
//public class ManHourAggregationResultExportService extends ExportService<AggregationResultQuery> {
public class ManHourAggregationResultExportService extends ExportService<List<RegistrationErrorListDto>> {
    @Inject
    private CSVReportGenerator generator;

    @Inject
    private CreateAggregationManHourResult createAggregationManHourResult;

    private static final String CODE_HEADER = "KHA003_103";

    private static final String TOTAL_HEADER = "KHA003_98";

    private static final String PGID = "KHA003";

    private static final String FILE_EXTENSION = ".csv";

    private static final String DATE_FORMAT = "yyyy/MM/dd";

    @Override
//    protected void handle(ExportServiceContext<AggregationResultQuery> exportServiceContext) {
    protected void handle(ExportServiceContext<List<RegistrationErrorListDto>> exportServiceContext) {
//        val query = exportServiceContext.getQuery();
//        if (query == null) return;
        AggregationResultQuery query = new AggregationResultQuery(
               "01", null, Collections.emptyList(),
                Arrays.asList(
                        GeneralDate.fromString("2021/06/01", "yyyy/MM/dd"),
                        GeneralDate.fromString("2021/06/02", "yyyy/MM/dd"),
                        GeneralDate.fromString("2021/06/03", "yyyy/MM/dd")),
                Collections.singletonList(YearMonth.of(2021, 6))
                );

        String executionTime = GeneralDateTime.now().toString().replaceAll("[/:\\s]", "");

        // Get data result
//        AggregationResultDto data = this.createAggregationManHourResult.get(query.getCode(), query.getMasterNameInfo(), query.getWorkDetailList(),
//                query.getDateList(), query.getYearMonthList());
        AggregationResultDto data = new AggregationResultDto(
                Dummy.SummaryTableFormat.create(),
                Dummy.SummaryTableOutputContent.create());
        val detailFormatSetting = data.getSummaryTableFormat().getDetailFormatSetting();
        val displayFormat = detailFormatSetting.getDisplayFormat();
        val outputContent = data.getOutputContent();
        val totalUnit = detailFormatSetting.getTotalUnit();
        int maxRangeDate = totalUnit == TotalUnit.DATE ? query.getDateList().size() : query.getYearMonthList().size();

        // Flag display total
        val isDisplayTotal = detailFormatSetting.getDisplayVerticalHorizontalTotal().value == 1;

        // create Header list
        List<String> headerList = this.createTextHeader(query, detailFormatSetting, isDisplayTotal);

//        // Handle data on needed data to export
//        List<SummaryItemDetail> lstSummaryItemDetail = new ArrayList<>();
//        // using Recursive to flat list
//        convertTreeToFlatList(outputContent.getItemDetails(), lstSummaryItemDetail);
//        // get hierarchical number available : Convert Tree using Recursive to flat list
//        val hierarchyLength = lstSummaryItemDetail.size();

        // Add data source
        List<Map<String, Object>> dataSource = new ArrayList<>();
        // data processing
        this.outputDataProcessing(outputContent, isDisplayTotal, totalUnit, dataSource, headerList, maxRangeDate);

        // Execute export
        CSVFileData fileData = new CSVFileData(
                PGID + "_" + executionTime + "_" + AppContexts.user().companyCode() + FILE_EXTENSION, headerList, dataSource);
        generator.generate(exportServiceContext.getGeneratorContext(), fileData);
    }

    private void outputDataProcessing(ManHourSummaryTableOutputContent outputContent, boolean isDispTotal, TotalUnit unit, List<Map<String, Object>> dataSource, List<String> headerList, int maxRangeDate) {
        Map<String, Object> row = new HashMap<>();
        for (SummaryItemDetail layer1 : outputContent.getItemDetails()) {
            if (layer1.getChildHierarchyList().isEmpty()) {
                Map<String, Object> row1 = new HashMap<>();
                row1.put(headerList.get(0), layer1.getDisplayInfo().getCode());
                row1.put(headerList.get(1), layer1.getDisplayInfo().getName());

                for (int i = 2; i < maxRangeDate + 2; i++) {
                    int index = i;
                    Integer workingTime = unit == TotalUnit.DATE
                            ? layer1.getVerticalTotalList().stream().filter(x -> x.getDate().toString().equals(headerList.get(index)))
                            .map(VerticalValueDaily::getWorkingHours).findFirst().orElse(null)
                            : layer1.getVerticalTotalList().stream().filter(x -> yearMonthToString(x.getYearMonth()).equals(headerList.get(index)))
                            .map(VerticalValueDaily::getWorkingHours).findFirst().orElse(null);
                    row1.put(headerList.get(index), workingTime);
                }
                dataSource.add(row1);
            } else {
                for (SummaryItemDetail layer2 : layer1.getChildHierarchyList()) {
                    if (layer2.getChildHierarchyList().isEmpty()) {
                        Map<String, Object> row2 = new HashMap<>();
                        row2.put(headerList.get(0), layer1.getDisplayInfo().getCode());
                        row2.put(headerList.get(1), layer1.getDisplayInfo().getName());
                        row2.put(headerList.get(2), layer2.getDisplayInfo().getCode());
                        row2.put(headerList.get(3), layer2.getDisplayInfo().getName());

                        for (int i = 4; i < maxRangeDate + 4; i++) {
                            int index = i;
                            Integer workingTime = unit == TotalUnit.DATE
                                    ? layer2.getVerticalTotalList().stream().filter(x -> x.getDate().toString().equals(headerList.get(index)))
                                    .map(VerticalValueDaily::getWorkingHours).findFirst().orElse(null)
                                    : layer2.getVerticalTotalList().stream().filter(x -> yearMonthToString(x.getYearMonth()).equals(headerList.get(index)))
                                    .map(VerticalValueDaily::getWorkingHours).findFirst().orElse(null);
                            row2.put(headerList.get(index), workingTime);
                        }
                        dataSource.add(row2);
                    } else {
                        for (SummaryItemDetail layer3 : layer2.getChildHierarchyList()) {
                            if (layer3.getChildHierarchyList().isEmpty()) {
                                Map<String, Object> row3 = new HashMap<>();
                                row3.put(headerList.get(0), layer1.getDisplayInfo().getCode());
                                row3.put(headerList.get(1), layer1.getDisplayInfo().getName());
                                row3.put(headerList.get(2), layer2.getDisplayInfo().getCode());
                                row3.put(headerList.get(3), layer2.getDisplayInfo().getName());
                                row3.put(headerList.get(4), layer3.getDisplayInfo().getCode());
                                row3.put(headerList.get(5), layer3.getDisplayInfo().getName());

                                for (int i = 6; i < maxRangeDate + 6; i++) {
                                    int index = i;
                                    Integer workingTime = unit == TotalUnit.DATE
                                            ? layer3.getVerticalTotalList().stream().filter(x -> x.getDate().toString().equals(headerList.get(index)))
                                            .map(VerticalValueDaily::getWorkingHours).findFirst().orElse(null)
                                            : layer3.getVerticalTotalList().stream().filter(x -> yearMonthToString(x.getYearMonth()).equals(headerList.get(index)))
                                            .map(VerticalValueDaily::getWorkingHours).findFirst().orElse(null);
                                    row3.put(headerList.get(index), workingTime);
                                }
                                dataSource.add(row3);
                            } else {
                                for (SummaryItemDetail layer4 : layer3.getChildHierarchyList()) {
                                    Map<String, Object> row4 = new HashMap<>();
                                    row4.put(headerList.get(0), layer1.getDisplayInfo().getCode());
                                    row4.put(headerList.get(1), layer1.getDisplayInfo().getName());
                                    row4.put(headerList.get(2), layer2.getDisplayInfo().getCode());
                                    row4.put(headerList.get(3), layer2.getDisplayInfo().getName());
                                    row4.put(headerList.get(4), layer3.getDisplayInfo().getCode());
                                    row4.put(headerList.get(5), layer3.getDisplayInfo().getName());
                                    row4.put(headerList.get(6), layer4.getDisplayInfo().getCode());
                                    row4.put(headerList.get(7), layer4.getDisplayInfo().getName());

                                    for (int i = 8; i < maxRangeDate + 8; i++) {
                                        int index = i;
                                        Integer workingTime = unit == TotalUnit.DATE
                                                ? layer4.getVerticalTotalList().stream().filter(x -> x.getDate().toString().equals(headerList.get(index)))
                                                .map(VerticalValueDaily::getWorkingHours).findFirst().orElse(null)
                                                : layer4.getVerticalTotalList().stream().filter(x -> yearMonthToString(x.getYearMonth()).equals(headerList.get(index)))
                                                .map(VerticalValueDaily::getWorkingHours).findFirst().orElse(null);
                                        row4.put(headerList.get(index), workingTime);
                                    }
                                    dataSource.add(row4);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void convertTreeToFlatList(ManHourSummaryTableOutputContent outputContent) {

//        if (!CollectionUtil.isEmpty(itemDetails)) {
//            return;
//        }
//        lstResult.addAll(itemDetails);
//        List<SummaryItemDetail> childHierarchy = itemDetails.stream().flatMap(x -> x.getChildHierarchyList().stream()).collect(Collectors.toList());
//        // Recursive
//        convertTreeToFlatList(childHierarchy, lstResult);
    }

    /**
     * create text Header
     *
     * @param query
     * @param detailSetting
     * @param isDispTotal
     * @return
     */
    private List<String> createTextHeader(AggregationResultQuery query, DetailFormatSetting detailSetting, boolean isDispTotal) {
        List<String> lstHeader = new ArrayList<>();
        // Sort before adding
        val sortedList = detailSetting.getSummaryItemList().stream().sorted(Comparator.comparing(SummaryItem::getHierarchicalOrder)).collect(Collectors.toList());
        // Add code & name to header
        for (int i = 0; i < sortedList.size(); i++) {
            SummaryItem item = sortedList.get(i);
            lstHeader.add(TextResource.localize(CODE_HEADER));
            lstHeader.add(item.getSummaryItemType().nameId);
        }

        // Add date/yearMonth to header
        if (detailSetting.getTotalUnit() == TotalUnit.DATE) {
            query.getDateList().forEach(date -> lstHeader.add(date.toString()));
        } else {
            query.getYearMonthList().forEach(ym -> lstHeader.add(yearMonthToString(ym)));
        }

        // Add horizontal total to header
        if (isDispTotal) {
            lstHeader.add(TextResource.localize(TOTAL_HEADER));
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
