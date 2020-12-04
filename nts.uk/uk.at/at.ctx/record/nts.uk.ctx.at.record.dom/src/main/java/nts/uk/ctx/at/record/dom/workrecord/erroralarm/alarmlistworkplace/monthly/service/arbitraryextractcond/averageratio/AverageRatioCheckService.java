package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.averageratio;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageNumberOfDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.comparison.ComparisonProcessingService;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.WorkDaysOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.任意抽出条件をチェック."4.平均比率をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class AverageRatioCheckService {

    @Inject
    private RecordDomRequireService requireService;
    @Inject
    private ComparisonProcessingService comparisonProcessingService;

    /**
     * 4.平均比率をチェック
     *
     * @param cid         会社ID
     * @param workplaceId 職場ID
     * @param condition   アラームリスト（職場）月次の抽出条件
     * @param times       List＜月別実績の勤怠時間＞
     * @param empInfos    List＜社員情報＞
     * @param ym          年月
     * @return 抽出結果
     */
    public ExtractResultDto check(String cid, String workplaceId, ExtractionMonthlyCon condition,
                                  List<AttendanceTimeOfMonthly> times,
                                  List<EmployeeInfoImported> empInfos,
                                  YearMonth ym) {
        // 総日数　＝　０、総集計日数　＝　０
        Double total = 0.0;
        Double aggregateTotal = 0.0;
        Optional<AverageRatio> averageRatio = condition.getAverageValueItem().getAverageRatio();
        if (!averageRatio.isPresent()) return null;
        DatePeriod period = new DatePeriod(GeneralDate.ymd(ym.year(), ym.month(), 1), ym.lastGeneralDate());
        GeneralDate criteriaDate = GeneralDate.today();
        val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();

        for (EmployeeInfoImported empInfo : empInfos) {
            List<AttendanceTimeOfMonthly> timesByEmp = times.stream().filter(x -> x.getEmployeeId().equals(empInfo.getSid()))
                    .collect(Collectors.toList());
            for (AttendanceTimeOfMonthly time : timesByEmp) {
                // Input．アラームリスト（職場）月次の抽出条件．平均値をチェック
                switch (averageRatio.get()) {
                    case ATTENDANCE:
                        // 総集計日数　+＝　集計日数
                        aggregateTotal += time.getAggregateDays().v();
                        // 総日数　+＝　出勤日数．日数+休出日数．日数+振出日数．日数
                        total += time.getVerticalTotal().getWorkDays().getAttendanceDays().getDays().v();
                        total += time.getVerticalTotal().getWorkDays().getHolidayWorkDays().getDays().v();
                        break;
                    default:
                        WorkDaysOfMonthly workDays = time.getVerticalTotal().getWorkDays();
                        // 社員に対応する締め開始日を取得する
                        Optional<GeneralDate> closureStartDate = GetClosureStartForEmployee.algorithm(require, cacheCarrier, empInfo.getSid());
                        // 期間中の年休積休残数を取得
                        AggrResultOfAnnAndRsvLeave aggResult = GetAnnAndRsvRemNumWithinPeriod.algorithm(require, cacheCarrier,
                                cid, empInfo.getSid(), period, InterimRemainMngMode.OTHER, criteriaDate,
                                false, false, Optional.of(false),
                                Optional.empty(), Optional.empty(), Optional.empty(),
                                Optional.empty(), Optional.empty(), Optional.empty());
                        switch (averageRatio.get()) {
                            case HOLIDAY_VACATION_RATE:
                                // 総集計日数　+＝　集計日数
                                aggregateTotal += time.getAggregateDays().v();
                                // 総集計日数を合計
                                total += getTotalHolidayVacationRate(require, cacheCarrier, cid, empInfo.getSid(),
                                        period, closureStartDate.get(), time, aggResult);
                                break;
                            case ANNUAL_LEAVE_DIGESTION_RATE:
                                // 総集計日数を合計

                                // 総日数を合計する

                                break;
                            case TIME_ANNUAL_BREAK_DIG:
                                // 総日数を合計する

                                // 総集計日数を合計

                                break;
                            case ANNUAL_HOLIDAY_DIGESTIBILITY:
                                // 総日数を合計する

                                // 総集計日数を合計

                                break;
                            case TIME_ABD_NOT_INC:
                                // 総日数を合計する

                                // 総集計日数を合計

                                break;
                        }
                        break;
                }
            }
        }

        // 平均値　＝　合計値/Input．List＜社員情報＞．size
        Double avg = total / empInfos.size();
        BigDecimal bd = new BigDecimal(Double.toString(avg));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        // 比較処理
        // 取得した「抽出結果」を返す
        return comparisonProcessingService.compare(workplaceId, condition, bd.doubleValue(), averageRatio.get(), ym);
    }

    private Double getTotalHolidayVacationRate(RecordDomRequireService.Require require, CacheCarrier cacheCarrier,
                                               String cid, String employeeId, DatePeriod period, GeneralDate closureStartDate,
                                               AttendanceTimeOfMonthly time, AggrResultOfAnnAndRsvLeave aggResult) {
        Double total = 0.0;
        WorkDaysOfMonthly workDays = time.getVerticalTotal().getWorkDays();
        // 期間内の振出振休残数を取得する
        AbsRecRemainMngOfInPeriod absRecRemain = AbsenceReruitmentMngInPeriodQuery.getAbsRecMngInPeriod(
                require, cacheCarrier, new AbsRecMngInPeriodParamInput(
                        cid, employeeId, period, closureStartDate, true, false,
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                        Optional.empty(), Optional.empty(), Optional.empty()
                ));
        // 期間内の休出代休残数を取得する
        BreakDayOffRemainMngOfInPeriod breakDayOffRemain = BreakDayOffMngInPeriodQuery.getBreakDayOffMngInPeriod(
                require, cacheCarrier, new BreakDayOffRemainMngParam(
                        cid, employeeId, period, true, closureStartDate, false,
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                        Optional.empty(), Optional.empty(), Optional.empty()
                ));

        // 総日数を合計する
        // 休日日数．日数
        total += workDays.getHolidayDays().getDays().v();

        // 特別休暇日数．特別休暇合計日数
        total += workDays.getSpecialVacationDays().getTotalSpcVacationDays().v();

        // 欠勤．欠勤合計日数
        total += workDays.getAbsenceDays().getTotalAbsenceDays().v();

        // 合計（休業．固定休業日数．日数+休業．任意休業日数．日数）
        total += workDays.getLeave().getFixLeaveDays().entrySet().stream()
                .map(x -> x.getValue().getDays().v()).mapToDouble(Double::doubleValue).sum();
        total += workDays.getLeave().getAnyLeaveDays().entrySet().stream()
                .map(x -> x.getValue().getDays().v()).mapToDouble(Double::doubleValue).sum();

        // 年休情報(期間終了日時点)．使用日数
        Optional<AggrResultOfAnnualLeave> annualLeave = aggResult.getAnnualLeave();
        if (annualLeave.isPresent()) {
            total += annualLeave.get().getAsOfPeriodEnd().getUsedDays().v();
        }

        // 積立年休情報(期間終了日時点)．使用日数
        Optional<AggrResultOfReserveLeave> reserveLeave = aggResult.getReserveLeave();
        if (reserveLeave.isPresent()) {
            total += reserveLeave.get().getAsOfPeriodEnd().getUsedDays().v();
        }

        // 代休使用数（※１）
        if (breakDayOffRemain.getLstDetailData().stream().anyMatch(x -> OccurrenceDigClass.OCCURRENCE.equals(x.getOccurrentClass()))) {
            // 代休使用数　＝　発生数．日数
            total += breakDayOffRemain.getOccurrenceDays();
        }

        // 振休使用数（※2）
        if (absRecRemain.getLstAbsRecMng().stream().anyMatch(x -> OccurrenceDigClass.OCCURRENCE.equals(x.getOccurrentClass()))) {
            // 振休使用数　＝　発生数．日数
            total += absRecRemain.getOccurrenceDays();
        }

        return total;
    }
}
