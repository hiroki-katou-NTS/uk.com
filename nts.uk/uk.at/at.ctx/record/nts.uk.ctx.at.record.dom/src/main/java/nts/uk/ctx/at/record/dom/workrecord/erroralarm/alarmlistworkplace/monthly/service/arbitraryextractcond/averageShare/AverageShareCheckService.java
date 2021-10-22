package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.averageShare;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.CheckMonthlyItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.comparison.ComparisonProcessingService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.DataCheckAlarmListService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.algorithm.MonthlyRecordValuesDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次.アルゴリズム.月次の集計処理.任意抽出条件をチェック.0.平均比率以外をチェック
 * @author hai.tt
 */
@Stateless
public class AverageShareCheckService {

    @Inject
    DataCheckAlarmListService dataCheckAlarmListService;

    @Inject
    private ComparisonProcessingService comparisonProcessingService;

    /**
     *
     * @param workplaceId 職場ID
     * @param condition アラームリスト（職場）月次の抽出条件
     * @param times List＜月別実績＞
     * @param empInfos List＜社員情報＞
     * @param ym 年月
     */
    public ExtractResultDto check(String workplaceId,
                                  ExtractionMonthlyCon condition,
                                  List<AttendanceTimeOfMonthly> times,
                                  List<EmployeeInfoImported> empInfos,
                                  YearMonth ym) {
        Double total = 0.0;

        List<Integer> addItems = ((CountableTarget) condition.getCheckedTarget().get())
                .getAddSubAttendanceItems()
                .getAdditionAttendanceItems();
        List<Integer> subItems = ((CountableTarget) condition.getCheckedTarget().get())
                .getAddSubAttendanceItems()
                .getSubstractionAttendanceItems();
        ArrayList<Integer> itemIds = new ArrayList<>();
        itemIds.addAll(addItems);
        itemIds.addAll(subItems);

        Map<String, List<MonthlyRecordValuesDto>> monData = dataCheckAlarmListService.getMonthData(
                empInfos.stream().map(EmployeeInfoImported::getSid).collect(Collectors.toList()),
                new YearMonthPeriod(ym, ym),
                itemIds
        );

//        for (AttendanceTimeOfMonthly time : times) {
            for (EmployeeInfoImported empInfo : empInfos) {
                List<MonthlyRecordValuesDto> lstRecordValue = monData.get(empInfo.getSid());
                if (lstRecordValue != null && !lstRecordValue.isEmpty()) {
                    List<ItemValue> totalItemValue = new ArrayList<>();
                    lstRecordValue.forEach(x -> {
                        if (!x.getItemValues().isEmpty()) {
                            totalItemValue.addAll(x.getItemValues());
                        }
                    });

                    for (ItemValue iValue : totalItemValue) {
                        total += Double.valueOf(iValue.getValue());
                    }

                }
            }
//        }

        Double avg = empInfos.size() == 0 ? 0: total / (double) empInfos.size();
        BigDecimal bd = BigDecimal.valueOf(avg);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return comparisonProcessingService.compare(workplaceId, condition, bd.doubleValue(), condition.getCheckMonthlyItemsType().nameId, ym);

    }

}
