package nts.uk.screen.at.app.kha003.d;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.*;
import nts.uk.screen.at.app.kha003.*;
import nts.uk.screen.at.app.kha003.exportcsv.ManHourSummaryTableOutputContentDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
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
        if (manHourOutputContent == null)
            throw new BusinessException("Msg_2171");

        // Convert domain ManHourSummaryTableOutputContent to dto
        val itemDetails = getSummaryItemDetails(manHourOutputContent.getItemDetails());
        val outputContent = new ManHourSummaryTableOutputContentDto(
                itemDetails,
                convertValueDaily(manHourOutputContent.getVerticalTotalValues()),
                manHourOutputContent.getTotalPeriod().isPresent() ? manHourOutputContent.getTotalPeriod().get() : 0
        );

        return new ManHourAggregationResultDto(manHourObj, outputContent);
    }

    private List<SummaryItemDto> getSummaryItemList(List<SummaryItem> lstItem) {
        return lstItem.stream().map(item -> new SummaryItemDto(item.getHierarchicalOrder(),
                item.getSummaryItemType().value,
                item.getSummaryItemType().nameId
        )).collect(Collectors.toList());
    }

//    private void getSummaryItemDetailRecursive(List<SummaryItemDetail> parentList, List<SummaryItemDetailDto> targetList) {
//        int countTotalLevel = 0;
//        if (CollectionUtil.isEmpty(parentList)) return;
//        List<SummaryItemDetailDto> data = parentList.stream().map(x -> new SummaryItemDetailDto(
//                x.getCode(),
//                x.getDisplayInfo(),
//                x.getChildHierarchyList().isEmpty() ? Collections.emptyList() : convertHierarchy(x.getChildHierarchyList()),
//                x.getVerticalTotalList(),
//                x.getTotalPeriod().isPresent() ? x.getTotalPeriod().get() : 0
//        )).collect(Collectors.toList());
//        targetList.addAll(data);
//        List<SummaryItemDetail> childHierarchy = parentList.stream().flatMap(x -> x.getChildHierarchyList().stream()).collect(Collectors.toList());
//        countTotalLevel += 1;
//        System.out.println("--------Total level:---------" + countTotalLevel);
//        getSummaryItemDetailRecursive(childHierarchy, targetList);
//    }

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
                x.getDate().toString() != null ? x.getDate().toString() : null
        )).collect(Collectors.toList());
    }
}
