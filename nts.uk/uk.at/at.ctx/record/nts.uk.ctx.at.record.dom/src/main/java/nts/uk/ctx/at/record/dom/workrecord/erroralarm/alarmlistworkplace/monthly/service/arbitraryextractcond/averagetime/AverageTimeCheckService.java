package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.averagetime;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.WorkTimeOfMonthlyVT;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.midnighttime.IllegalMidnightTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureResultDto;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.任意抽出条件をチェック."1.平均時間をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class AverageTimeCheckService {

    /**
     * 1.平均時間をチェック
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
        // 合計値　＝　０
        Integer total = 0;

        // Input．アラームリスト（職場）月次の抽出条件．平均値をチェック
        Optional<AverageTime> averageTime = condition.getAverageValueItem().getAverageTime();
        if (!averageTime.isPresent()) return null;

        for (AttendanceTimeOfMonthly time : times) {
            WorkTimeOfMonthlyVT workTime = time.getVerticalTotal().getWorkTime();
            MonthlyCalculation monthlyCalculation = time.getMonthlyCalculation();
            switch (averageTime.get()) {
                case LATE_TIME:
                    // 合計値　+＝　遅刻早退．遅刻．時間
                    total += workTime.getLateLeaveEarly().getLate().getTime().getTime().v();
                    break;
                case EARLY_LEAVE_TIME:
                    // 合計値　+＝　遅刻早退．早退．時間
                    total += workTime.getLateLeaveEarly().getLeaveEarly().getTime().getTime().v();
                    break;
                case TOTAL_OUTING_TIME:
                    // 合計値　+＝　外出．外出．時間　+　外出．育児外出．合計時間
                    Integer goOutTime = workTime.getGoOut().getGoOuts().entrySet().stream()
                            .map(x -> x.getValue().getTotalTime().getTime().v()).mapToInt(Integer::intValue).sum();
                    Integer goOutForChildTime = workTime.getGoOut().getGoOutForChildCares().entrySet().stream()
                            .map(x -> x.getValue().getTime().v()).mapToInt(Integer::intValue).sum();
                    total += goOutTime + goOutForChildTime;
                    break;
                case TOTAL_WORKING_HOURS:
                    // 合計値　+＝　総労働時間
                    total += monthlyCalculation.getTotalWorkingTime().v();
                    break;
                case TOTAL_OVERTIME_HOURS:
                    // 合計値　+＝　集計時間．残業時間．残業合計時間
                    total += monthlyCalculation.getAggregateTime().getOverTime().getTotalOverTime().getTime().v();
                    break;
                case TOTAL_VACATION_HOURS:
                    // 合計値　+＝　集計時間．残業時間．休出合計時間
                    total += monthlyCalculation.getAggregateTime().getHolidayWorkTime().getTotalHolidayWorkTime().getTime().v();
                    break;
                case TOTAL_LATE_NIGHT:
                    // 合計値　+＝　深夜時間．所定内深夜時間　+　深夜時間．所定外深夜時間
                    IllegalMidnightTime illegalMidnightTime = workTime.getMidnightTime().getIllegalMidnightTime();
                    total += illegalMidnightTime.getTime().getTime().v() + illegalMidnightTime.getBeforeTime().v();
                    break;
                case FLEX_TIME:
                    // 合計値　+＝　フレックス時間
                    total += monthlyCalculation.getFlexTime().getFlexTime().getFlexTime().getTime().v();
                    break;
                case TOTAL_RESTRAINT_TIME:
                    // 合計値　+＝　総拘束時間
                    total += monthlyCalculation.getTotalTimeSpentAtWork().getTotalTimeSpentAtWork().v();
                    break;
                case TOTAL_RESTRAINT_WORKING_TIME:
                    // 合計値　+＝ 総拘束時間－総労働時間
                    total += monthlyCalculation.getTotalTimeSpentAtWork().getTotalTimeSpentAtWork().v();
                    total -= monthlyCalculation.getTotalWorkingTime().v();
                    break;
                case TIME_ANNUAL_LEAVE_USE_TIME:
                    // 合計値　+＝ 使用時間
                    total += monthlyCalculation.getAggregateTime().getVacationUseTime().getAnnualLeave().getUseTime().v();
                    break;
            }
        }

        // 平均時間　＝　合計値/List<社員情報＞.size
        BigDecimal bd = new BigDecimal(Double.toString(total));
        bd = bd.setScale(1, RoundingMode.HALF_UP);

        return null;
    }
}
