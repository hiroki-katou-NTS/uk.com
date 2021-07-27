package nts.uk.screen.at.app.kha003.d;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.*;
import nts.uk.screen.at.app.kha003.*;
import nts.uk.screen.at.app.kha003.exportcsv.ManHourHierarchyFlatData;
import nts.uk.screen.at.app.kha003.exportcsv.ManHourSummaryTableOutputContentDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ＜＜ScreenQuery＞＞ 集計結果を作成する
 */
@Stateless
public class CreateAggregationManHourResult {
    @Inject
    private ManHourSummaryTableFormatRepository manHourSummaryRepo;

    /**
     * @param code           工数集計表コード
     * @param masterNameInfo マスタ名称情報　（C画面で指定したマスタ）
     * @param workDetailList 工数集計データ．作業詳細データ
     * @param dateList       年月日<List>
     * @param yearMonthList  年月<List>
     */
    public ManHourAggregationResultDto get(String code, MasterNameInformation masterNameInfo, List<WorkDetailData> workDetailList,
                                           List<GeneralDate> dateList, List<YearMonth> yearMonthList) {
        val optManHour = this.manHourSummaryRepo.get(AppContexts.user().companyId(), code);
        if (!optManHour.isPresent()) return null;
        // Convert domain to dto
        val manHourObj =  optManHour.map(opt -> new ManHourSummaryTableFormatDto(
                opt.getCode().v(),
                opt.getName().v(),
                opt.getDetailFormatSetting().getTotalUnit().value,
                opt.getDetailFormatSetting().getDisplayFormat().value,
                opt.getDetailFormatSetting().getDisplayVerticalHorizontalTotal().value,
                getSummaryItemList(opt.getDetailFormatSetting().getSummaryItemList())
        )).orElse(null);

        val manHourOutputContent = optManHour.get().createOutputContent(dateList, yearMonthList, workDetailList, masterNameInfo);
        if (manHourOutputContent == null || workDetailList == null)
            throw new BusinessException("Msg_2171");

        // Convert domain ManHourSummaryTableOutputContent to dto
        val itemDetails = getSummaryItemDetails(manHourOutputContent.getItemDetails());
        val outputContent = new ManHourSummaryTableOutputContentDto(
                itemDetails,
                convertValueDaily(manHourOutputContent.getVerticalTotalValues()),
                manHourOutputContent.getTotalPeriod().isPresent() ? manHourOutputContent.getTotalPeriod().get() : 0
        );

        // Flat data
//        val flatDataList = this.flatDataProcessing(outputContent, manHourObj.getTotalUnit());
        CountTotalLevel totalLevel = new CountTotalLevel(0);
        countHierarchy(outputContent.getItemDetails(), totalLevel);

        return new ManHourAggregationResultDto(manHourObj, outputContent, Collections.emptyList(), totalLevel.getCountTotalLevel());
    }

    private List<SummaryItemDto> getSummaryItemList(List<SummaryItem> lstItem) {
        return lstItem.stream().map(item -> new SummaryItemDto(item.getHierarchicalOrder(),
                item.getSummaryItemType().value,
                item.getSummaryItemType().nameId
        )).sorted(Comparator.comparingInt(SummaryItemDto::getHierarchicalOrder)).collect(Collectors.toList());
    }

    private List<SummaryItemDetailDto> getSummaryItemDetails(List<SummaryItemDetail> childList) {
        return childList.isEmpty() ? Collections.emptyList()
                : childList.stream().map(x -> new SummaryItemDetailDto(
                x.getCode(),
                new DisplayInfoDto(x.getDisplayInfo().getCode(), x.getDisplayInfo().getName()),
                getSummaryItemDetails(x.getChildHierarchyList()),
                convertValueDaily(x.getVerticalTotalList()),
                x.getTotalPeriod().isPresent() ? x.getTotalPeriod().get() : 0
        )).collect(Collectors.toList());
    }

    private List<VerticalValueDailyDto> convertValueDaily(List<VerticalValueDaily> dailyValues) {
        return dailyValues.stream().map(x -> new VerticalValueDailyDto(
                x.getWorkingHours(),
                x.getYearMonth() != null ? x.getYearMonth().toString() : null,
                x.getDate() != null ? x.getDate().toString() : null
        )).collect(Collectors.toList());
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
