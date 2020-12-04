package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.averagenumtime;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageNumberOfDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageNumberOfTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.comparison.ComparisonProcessingService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.WorkTimeOfMonthlyVT;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.midnighttime.IllegalMidnightTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.任意抽出条件をチェック."3.平均回数をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class AverageNumTimeCheckService {

    @Inject
    private ComparisonProcessingService comparisonProcessingService;

    /**
     * 3.平均回数をチェック
     *
     * @param workplaceId 職場ID
     * @param condition   アラームリスト（職場）月次の抽出条件
     * @param times       List＜月別実績の勤怠時間＞
     * @param empInfos    List＜社員情報＞
     * @param ym          年月
     * @return 抽出結果
     */
    public ExtractResultDto check(String workplaceId, ExtractionMonthlyCon condition,
                                  List<AttendanceTimeOfMonthly> times,
                                  List<EmployeeInfoImported> empInfos,
                                  YearMonth ym) {
        // 合計値　＝　0
        Double total = 0.0;

        // Input．アラームリスト（職場）月次の抽出条件．平均値をチェック
        Optional<AverageNumberOfTimes> averageNumberOfTimes = condition.getAverageValueItem().getAverageNumberOfTimes();
        if (!averageNumberOfTimes.isPresent()) return null;

        for (AttendanceTimeOfMonthly time : times) {
            WorkTimeOfMonthlyVT workTime = time.getVerticalTotal().getWorkTime();
            switch (averageNumberOfTimes.get()) {
                case NUMBER_LATE_ARRIVALS:
                    // 合計値　+＝　遅刻早退．遅刻．回数
                    total += workTime.getLateLeaveEarly().getLate().getTimes().v();
                    break;
                case NUMBER_EARLY_DEPARTURES:
                    // 遅刻早退．早退．回数
                    total += workTime.getLateLeaveEarly().getLeaveEarly().getTimes().v();
                    break;
                case NUMBER_OUTINGS:
                    // 合計値 +＝外出．回数　+　育児外出．回数
                    Integer goOutTimes = workTime.getGoOut().getGoOuts().entrySet().stream()
                            .map(x -> x.getValue().getTimes().v()).mapToInt(Integer::intValue).sum();
                    Integer goOutForChildTimes = workTime.getGoOut().getGoOutForChildCares().entrySet().stream()
                            .map(x -> x.getValue().getTimes().v()).mapToInt(Integer::intValue).sum();
                    total += goOutTimes + goOutForChildTimes;
                    break;
            }
        }

        // 平均値　＝　合計値/Input．List＜社員情報＞.Size
        Double avg = total / empInfos.size();
        BigDecimal bd = new BigDecimal(Double.toString(avg));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        // 比較処理
        // 「抽出結果」を返す
        return comparisonProcessingService.compare(workplaceId, condition, bd.doubleValue(), averageNumberOfTimes.get(), ym);
    }
}
