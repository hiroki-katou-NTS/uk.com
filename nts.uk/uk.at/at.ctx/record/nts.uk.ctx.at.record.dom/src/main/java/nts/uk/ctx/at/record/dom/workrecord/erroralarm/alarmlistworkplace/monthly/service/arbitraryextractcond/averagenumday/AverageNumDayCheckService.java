package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.averagenumday;

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
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.comparison.ComparisonProcessingService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.WorkDaysOfMonthly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.任意抽出条件をチェック."2.平均日数をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class AverageNumDayCheckService {

    @Inject
    private RecordDomRequireService requireService;
    @Inject
    private ComparisonProcessingService comparisonProcessingService;

    /**
     * 2.平均日数をチェック
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
        // 合計値　＝　0
        Double total = 0.0;
        Optional<AverageNumberOfDays> averageNumDay = condition.getAverageValueItem().getAverageNumberOfDays();
        if (!averageNumDay.isPresent()) return null;
        DatePeriod period = new DatePeriod(GeneralDate.ymd(ym.year(), ym.month(), 1), ym.lastGeneralDate());
        GeneralDate criteriaDate = GeneralDate.today();
        val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();

        for (EmployeeInfoImported empInfo : empInfos) {
            // 月別実績の勤務日数　＝　ループ中の社員IDの月別実績の勤務日数
            List<AttendanceTimeOfMonthly> timesByEmp = times.stream().filter(x -> x.getEmployeeId().equals(empInfo.getSid()))
                    .collect(Collectors.toList());
            // Input．アラームリスト（職場）月次の抽出条件．平均値をチェック
            for (AttendanceTimeOfMonthly time : timesByEmp) {
                WorkDaysOfMonthly workDays = time.getVerticalTotal().getWorkDays();
                switch (averageNumDay.get()) {
                    case NUMBER_WORK_DAYS:
                        // 合計値　+＝　出勤日数．日数
                        total += workDays.getAttendanceDays().getDays().v();
                        break;
                    case HOLIDAY_DAYS:
                        // 合計値　+＝休日日数．日数
                        total += workDays.getHolidayDays().getDays().v();
                        break;
                    case NUMBER_DAYS_OFF:
                        // 合計値　+＝　休出日数．日数
                        total += workDays.getHolidayWorkDays().getDays().v();
                        break;
                    case NUMBER_HOLIDAYS:
                        // TODO Q&A 37476
                        // 期間内の公休残数を集計する

                        // 合計値　+＝　取得数
                        break;
                    case TOTAL_SPECIAL_HOLIDAYS:
                        // 合計値　+＝　合計（特定日数．特別休暇日数．日数）
                        total += workDays.getSpecialVacationDays().getSpcVacationDaysList().entrySet().stream()
                                .map(x -> x.getValue().getDays().v()).mapToDouble(Double::doubleValue).sum();
                        break;
                    case TOTAL_ABSENTEE_DAYS:
                        // 合計値　+＝　合計（欠勤．欠勤日数．日数
                        total += workDays.getAbsenceDays().getAbsenceDaysList().entrySet().stream()
                                .map(x -> x.getValue().getDays().v()).mapToDouble(Double::doubleValue).sum();
                        break;
                    case ANNUAL_REST_USE:
                    case ACCUMULATED_ANNUAL_LEAVE_USED:
                        // 期間中の年休積休残数を取得
                        AggrResultOfAnnAndRsvLeave aggResult = GetAnnAndRsvRemNumWithinPeriod.algorithm(require, cacheCarrier,
                                cid, empInfo.getSid(), period, InterimRemainMngMode.OTHER, criteriaDate,
                                false, false, Optional.of(false),
                                Optional.empty(), Optional.empty(), Optional.empty(),
                                Optional.empty(), Optional.empty(), Optional.empty());
                        switch (averageNumDay.get()) {
                            case ANNUAL_REST_USE:
                                Optional<AggrResultOfAnnualLeave> annualLeave = aggResult.getAnnualLeave();
                                // 合計値　を計算
                                if (annualLeave.isPresent()) {
                                    total += annualLeave.get().getAsOfPeriodEnd().getUsedDays().v(); // TODO
                                }
                                break;
                            case ACCUMULATED_ANNUAL_LEAVE_USED:
                                Optional<AggrResultOfReserveLeave> reserveLeave = aggResult.getReserveLeave();
                                // 合計値　を計算
                                if (reserveLeave.isPresent()) {
                                    total += reserveLeave.get().getAsOfPeriodEnd().getUsedDays().v(); // TODO
                                }
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
        return comparisonProcessingService.compare(workplaceId, condition, bd.doubleValue(), averageNumDay.get(), ym);
    }

    /**
     * 期間中の年休積休残数を取得
     */
    private AggrResultOfAnnAndRsvLeave getAggrResult(String cid, String employeeId, DatePeriod period, GeneralDate criteriaDate) {
        val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();

        return GetAnnAndRsvRemNumWithinPeriod.algorithm(require, cacheCarrier,
                cid, employeeId, period, InterimRemainMngMode.OTHER, criteriaDate,
                false, false, Optional.of(false),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
    }
}
