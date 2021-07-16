package nts.uk.file.at.app.export.manhoursummarytable;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.TotalUnit;
import nts.uk.screen.at.app.kha003.SummaryItemDetailDto;
import nts.uk.screen.at.app.kha003.VerticalValueDailyDto;
import nts.uk.screen.at.app.kha003.exportcsv.ManHourHierarchyFlatData;
import nts.uk.screen.at.app.kha003.exportcsv.ManHourSummaryTableOutputContentDto;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ManHourSummaryTableExportExcel extends ExportService<ManHourSummaryTableQuery> {
    @Inject
    private ManHourSummaryTableGenerator manHourSummaryTableGenerator;

    @Override
    protected void handle(ExportServiceContext<ManHourSummaryTableQuery> exportServiceContext) {
        val generatorContext = exportServiceContext.getGeneratorContext();
        val query = exportServiceContext.getQuery();
//        ManHourSummaryTableQuery query = new ManHourSummaryTableQuery(
//                Dummy.SummaryTableFormat.create(),
//                Dummy.SummaryTableOutputContent.create(),
//                new ManHourPeriod(
//                        0,
//                        "2021/06/01",
//                        "2021/06/03",
//                        "2021/06",
//                        "2021/06"
//                )
//        );

        CountTotalLevel totalLevelObj = new CountTotalLevel(0);
        countHierarchy(query.getOutputContent().getItemDetails(), totalLevelObj);
        int countTotalLevel = totalLevelObj.getCountTotalLevel();
        val totalUnit = query.getSummaryTableFormat().getTotalUnit();
//        val flatDataList = this.flatDataProcessing(query.getOutputContent(), totalUnit);

        ManHourSummaryExportData data = new ManHourSummaryExportData(
                query.getSummaryTableFormat(),
                query.getOutputContent(),
                query.getPeriod(),
                Collections.emptyList(), //flatDataList,
                countTotalLevel
        );
        this.manHourSummaryTableGenerator.generate(generatorContext, data);
    }

    private Collector<ManHourHierarchyFlatData, ?, Map<String, Map<String, List<ManHourHierarchyFlatData>>>> groupByLv2AndLv3() {
        return groupingBy(ManHourHierarchyFlatData::getCodeLv2, groupingBy(ManHourHierarchyFlatData::getCodeLv3));
    }

    private List<ManHourHierarchyFlatData> flatDataProcessing(ManHourSummaryTableOutputContentDto outputContent, int unit) {
        List<ManHourHierarchyFlatData> lstResult = new ArrayList<>();
        for (SummaryItemDetailDto level1 : outputContent.getItemDetails()) {
            if (level1.getChildHierarchyList().isEmpty()) {
                val workingTimeMap1 = this.getWorkingTime(unit, level1.getVerticalTotalList());
                lstResult.add(new ManHourHierarchyFlatData(
                        level1.getCode(),
                        null,
                        null,
                        null,
                        level1.getDisplayInfo(),
                        workingTimeMap1,
                        level1.getTotalPeriod()
                ));
            } else {
                for (SummaryItemDetailDto level2 : level1.getChildHierarchyList()) {
                    if (level2.getChildHierarchyList().isEmpty()) {
                        val workingTimeMap2 = this.getWorkingTime(unit, level2.getVerticalTotalList());
                        lstResult.add(new ManHourHierarchyFlatData(
                                level1.getCode(),
                                level2.getCode(),
                                null,
                                null,
                                level2.getDisplayInfo(),
                                workingTimeMap2,
                                level2.getTotalPeriod()
                        ));
                    } else {
                        for (SummaryItemDetailDto level3 : level2.getChildHierarchyList()) {
                            if (level3.getChildHierarchyList().isEmpty()) {
                                val workingTimeMap3 = this.getWorkingTime(unit, level3.getVerticalTotalList());
                                lstResult.add(new ManHourHierarchyFlatData(
                                        level1.getCode(),
                                        level2.getCode(),
                                        level3.getCode(),
                                        null,
                                        level3.getDisplayInfo(),
                                        workingTimeMap3,
                                        level3.getTotalPeriod()
                                ));
                            } else {
                                for (SummaryItemDetailDto level4 : level3.getChildHierarchyList()) {
                                    val workingTimeMap4 = this.getWorkingTime(unit, level4.getVerticalTotalList());
                                    lstResult.add(new ManHourHierarchyFlatData(
                                            level1.getCode(),
                                            level2.getCode(),
                                            level3.getCode(),
                                            level4.getCode(),
                                            level4.getDisplayInfo(),
                                            workingTimeMap4,
                                            level4.getTotalPeriod()
                                    ));
                                }
                            }
                        }
                    }
                }
            }
        }

        return lstResult;
    }

    private Map<String, Integer> getWorkingTime(int unit, List<VerticalValueDailyDto> lstValueDaily) {
        Map<String, Integer> map = new HashMap<>();
        if (unit == TotalUnit.DATE.value)
            lstValueDaily.forEach(d -> map.put(d.getDate(), d.getWorkingHours()));
        else
            lstValueDaily.forEach(d -> map.put(d.getYearMonth(), d.getWorkingHours()));

        return map;
    }

    private void countHierarchy(List<SummaryItemDetailDto> parentList, CountTotalLevel result) {
        int totalLevel = result.getCountTotalLevel();
        if (CollectionUtil.isEmpty(parentList)) return;
        List<SummaryItemDetailDto> childHierarchy = parentList.stream().flatMap(x -> x.getChildHierarchyList().stream()).collect(Collectors.toList());
        totalLevel += 1;
        result.setCountTotalLevel(totalLevel);
        countHierarchy(childHierarchy, result);
    }
}
