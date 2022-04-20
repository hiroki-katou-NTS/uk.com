package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.monthly;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.shr.com.time.closure.ClosureMonth;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.AbsenceDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.AttendanceDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.HolidayWorkDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.HolidaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.SpecialVacationDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.WorkDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.WorkTypeCountProspectorBase;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriodProc;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.AggregationPeriod;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.GetRemainingNumberPublicHolidayService;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.AggrResultOfPublicHoliday;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;


/**
 * 条件値チェック(社員別・見込み月次)
 */
@RequiredArgsConstructor
public enum ConditionValueProspectMonthlyByEmployee implements ConditionValueLogic<ConditionValueProspectMonthlyByEmployee.Context> {

    支給額(1, "（予定時間＋総労働時間）×基準単価", c -> c.aggregate.aggregate(c.require, data -> {

        // 就業時間金額＋割増時間金額を求める
        // 就業時間金額
        double withinWorkTimeAmount = data.getAttendanceTimeOfDailyPerformance()
                .get()
                .getActualWorkingTimeOfDaily()
                .getTotalWorkingTime()
                .getWithinStatutoryTimeOfDaily()
                .getWithinWorkTimeAmount()
                .v();
        // 割増時間金額
        double totalAmount = data.getAttendanceTimeOfDailyPerformance()
                .get()
                .getActualWorkingTimeOfDaily()
                .getPremiumTimeOfDailyPerformance()
                .getTotalAmount()
                .v();

        return withinWorkTimeAmount + totalAmount;
    })),
    所定公休日数比(2, "対比：所定公休日数比", c -> {
        Double numerator = getPublichHolidays(c);
        Optional<PublicHolidayManagementUsageUnit> unit = c.require.getPublicHolidayManagementUsageUnit(c.getCompanyId());
        AggregationPeriod period = new AggregationPeriod(c.closureMonth.getYearMonth(), c.closureMonth.defaultPeriod());
        
        //公休管理の設定が無い場合、アラートしない
        if(!unit.isPresent()) {
            return null;
        }
        
        Double denominator = unit.get().GetNumberofPublicHoliday(c.require, c.getCompanyId(), c.getCompanyId(), Arrays.asList(period), period.getPeriod().end())
                .stream().filter(numberOfPublic -> numberOfPublic.createYearMonth().equals(c.closureMonth.getYearMonth()))
                .findFirst().map(numberOfPublic -> numberOfPublic.getInLegalHoliday().v()).orElse(0.0);
        // 所定公休日数の設定が無い場合、0が返ってくるので、その場合はアラームチェックをしない
        return denominator > 0.0 
                ? numerator / denominator * 100
                : null;
    }),
    基準時間比_通常勤務(3, "対比：基準時間比（通常勤務）", c -> {
        // 労働制を取得（月中で労働制変わってた場合は考慮していない（終了日基準））
        val baseDate = CheckingPeriodMonthly.getBaseDate(c.getClosureMonth());
        val workingSystem = c.require.getWorkingConditions(c.getEmployeeId(), baseDate).stream()
                .map(wc -> wc.getWorkingConditionItem().getLaborSystem())
                .collect(Collectors.toList()).get(0);
        if (!workingSystem.isRegularWork()) return null;    // チェック対象外

        // 総労働時間
        Double totalTime = c.aggregate.aggregate(c.require,
            data -> data.getAttendanceTimeOfDailyPerformance()
                    .get()
                    .getActualWorkingTimeOfDaily()
                    .getTotalWorkingTime()
                    .getTotalTime()
                    .v().doubleValue());

        // 法定労働時間
        Double MonStatutoryTime;
        val cacheCarrier = new CacheCarrier();
        // 雇用を取得
        Optional<BsEmploymentHistoryImport> empHist = c.require.employmentHistory(cacheCarrier, c.getCompanyId(), c.getEmployeeId(), baseDate);
        val employmentCd = empHist.get().getEmploymentCode();

        MonthlyStatutoryWorkingHours.RequireM4 requireImpl = c.require.requireService().createRequire();
        val monthlyFlexStatutoryLaborTime = MonthlyStatutoryWorkingHours.monAndWeekStatutoryTime(
                requireImpl, cacheCarrier,
                c.getCompanyId(), employmentCd, c.getEmployeeId(), baseDate, c.closureMonth.getYearMonth(), workingSystem
        );
        MonStatutoryTime = monthlyFlexStatutoryLaborTime.get().getMonthlyEstimateTime().v().doubleValue();

        return totalTime / MonStatutoryTime;
    }),
    基準時間比_変形労働(3, "対比：基準時間比（変形労働）", c -> {
        // 労働制を取得（月中で労働制変わってた場合は考慮していない（終了日基準））
        val baseDate = CheckingPeriodMonthly.getBaseDate(c.getClosureMonth());
        val workingSystem = c.require.getWorkingConditions(c.getEmployeeId(), baseDate).stream()
                .map(wc -> wc.getWorkingConditionItem().getLaborSystem())
                .collect(Collectors.toList()).get(0);
        if (!workingSystem.isVariableWorkingTimeWork()) return null;    // チェック対象外

        // 総労働時間
        Double totalTime = c.aggregate.aggregate(c.require,
                data -> data.getAttendanceTimeOfDailyPerformance()
                        .get()
                        .getActualWorkingTimeOfDaily()
                        .getTotalWorkingTime()
                        .getTotalTime()
                        .v().doubleValue());

        // 法定労働時間
        Double MonStatutoryTime;
        val cacheCarrier = new CacheCarrier();
        // 雇用を取得
        Optional<BsEmploymentHistoryImport> empHist = c.require.employmentHistory(cacheCarrier, c.getCompanyId(), c.getEmployeeId(), baseDate);
        val employmentCd = empHist.get().getEmploymentCode();

        MonthlyStatutoryWorkingHours.RequireM4 requireImpl = c.require.requireService().createRequire();
        val monthlyFlexStatutoryLaborTime = MonthlyStatutoryWorkingHours.monAndWeekStatutoryTime(
                requireImpl, cacheCarrier,
                c.getCompanyId(), employmentCd, c.getEmployeeId(), baseDate, c.closureMonth.getYearMonth(), workingSystem
        );
        MonStatutoryTime = monthlyFlexStatutoryLaborTime.get().getMonthlyEstimateTime().v().doubleValue();

        return totalTime / MonStatutoryTime * 100;
    }),
    基準時間比_フレックス(3, "対比：基準時間比（フレックス）", c -> {
        // 労働制を取得（月中で労働制変わってた場合は考慮していない（終了日基準））
        val baseDate = CheckingPeriodMonthly.getBaseDate(c.getClosureMonth());
        val workingSystem = c.require.getWorkingConditions(c.getEmployeeId(), baseDate).stream()
                .map(wc -> wc.getWorkingConditionItem().getLaborSystem())
                .collect(Collectors.toList()).get(0);
        if (!workingSystem.isFlexTimeWork()) return null;    // チェック対象外

        // 総労働時間
        Double totalTime = c.aggregate.aggregate(c.require,
                data -> data.getAttendanceTimeOfDailyPerformance()
                        .get()
                        .getActualWorkingTimeOfDaily()
                        .getTotalWorkingTime()
                        .getTotalTime()
                        .v().doubleValue());

        // 法定労働時間
        Double MonStatutoryTime;
        val cacheCarrier = new CacheCarrier();
        // 雇用を取得
        Optional<BsEmploymentHistoryImport> empHist = c.require.employmentHistory(cacheCarrier, c.getCompanyId(), c.getEmployeeId(), baseDate);
        val employmentCd = empHist.get().getEmploymentCode();
        MonthlyStatutoryWorkingHours.RequireM1 requireImpl = c.require.requireService().createRequire();
        val monthlyFlexStatutoryLaborTime = MonthlyStatutoryWorkingHours.flexMonAndWeekStatutoryTime(
                requireImpl, cacheCarrier,
                c.getCompanyId(), employmentCd, c.getEmployeeId(), baseDate, c.closureMonth.getYearMonth()
        );
        MonStatutoryTime = monthlyFlexStatutoryLaborTime.getStatutorySetting().v().doubleValue();

        return totalTime / MonStatutoryTime * 100;
    }),
    総労働時間(1, "予定時間＋総労働時間", c -> c.aggregate.aggregate(c.require, data -> {
        // 総労働時間
        return data.getAttendanceTimeOfDailyPerformance()
                .get()
                .getActualWorkingTimeOfDaily()
                .getTotalWorkingTime()
                .getTotalTime()
                .v().doubleValue();
    })),

    出勤日数(3, "日数：出勤日数", c ->  {
        val prospector = new AttendanceDaysProspector(c.require, c.getCompanyId(), c.aggregate);
        return prospector.prospect(c.require, c.getCompanyId(), c.getEmployeeId());
    }),
    
    休日日数(3, "日数：休日日数", c-> {
        val prospector = new HolidaysProspector(c.require, c.getCompanyId(), c.aggregate);
        return prospector.prospect(c.require, c.getCompanyId(), c.getEmployeeId());
    }),
    
    休出日数(3, "日数：休出日数", c-> {
        val prospector = new HolidayWorkDaysProspector(c.require, c.getCompanyId(), c.aggregate);
        return prospector.prospect(c.require, c.getCompanyId(), c.getEmployeeId());
    }),
    
    公休日数(3, "日数：公休日数", c-> getPublichHolidays(c)),
    
    特休日数合計(3, "日数：特休日数合計", c-> {
        val prospector = new SpecialVacationDaysProspector(c.require, c.getCompanyId(), c.aggregate);
        return prospector.prospect(c.require, c.getCompanyId(), c.getEmployeeId());
    }),
    
    欠勤日数合計(3, "日数：欠勤日数合計", c-> {
        val prospector = new AbsenceDaysProspector(c.require, c.getCompanyId(), c.aggregate);
        return prospector.prospect(c.require, c.getCompanyId(), c.getEmployeeId());
    }),
    
    年休使用数(3, "日数：年休使用数", c-> {
        DatePeriod period = c.closureMonth.defaultPeriod();
        val aggrResult = GetAnnLeaRemNumWithinPeriodProc.algorithm(
                c.require, new CacheCarrier(), c.getCompanyId(), c.getEmployeeId(), period/*期間*/, InterimRemainMngMode.OTHER,
                period.end()/*基準日*/, false/*未使用*/, Optional.empty()/*上書きフラグ*/, Optional.empty()/*上書き用の暫定年休管理データ*/, Optional.empty()/*前回の年休の集計結果*/,
                Optional.empty()/*過去月集計モード*/, Optional.empty()/*年月※過去月集計モード  = true の場合は必須*/, Optional.empty()/*上書き対象期間*/);
        return aggrResult.flatMap(r -> r.getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo().getUsedNumber().getUsedDays().map(day -> day.v())).orElse(null);
    }),
    
    積立年休使用数(3, "日数：積立年休使用数", c-> {
        DatePeriod period = c.closureMonth.defaultPeriod();
        val aggrResult = GetAnnAndRsvRemNumWithinPeriod.algorithm(
                c.require, new CacheCarrier(), c.getCompanyId(), c.getEmployeeId(), period/*期間*/, InterimRemainMngMode.OTHER, 
                period.end()/*基準日*/, true/*未使用*/, true/*未使用*/, Optional.empty()/*上書きフラグ*/, Optional.empty()/*上書き用の暫定年休管理データ*/, Optional.empty()/*上書き用の暫定積休管理データ*/, 
                Optional.empty()/*不足分付与残数データ出力区分*/, Optional.of(true)/*集計開始日を締め開始日とする　（締め開始日を確認しない）*/, Optional.empty()/*前回の年休の集計結果*/, Optional.empty()/*前回の積立年休の集計結果*/, Optional.empty()/*上書き対象期間*/);
        return aggrResult.getReserveLeave().map(r -> r.getAsOfPeriodEnd().getRemainingNumber().getReserveLeaveWithMinus().getUsedNumber().getUsedDays().v()).orElse(null);
    }),
    
    勤務日数(3, "日数：予定勤務日数＋勤務日数", c-> {
        val prospector = new WorkDaysProspector(c.require, c.getCompanyId(), c.aggregate);
        return prospector.prospect(c.require, c.getCompanyId(), c.getEmployeeId());
    }),
    ;

    public final int value;

    /** 項目名 */
    @Getter
    private final String name;

    private final Function<Context, Double> getValue;

    @Override
    public Double getValue(Context context) {
        return getValue.apply(context);
    }

    /**
     * 対比：所定公休日数比
     * 日数：公休日数
     * で使う　公休日数を取得する処
     */
    private static double getPublichHolidays(Context context) {
        AggrResultOfPublicHoliday aggrResult = GetRemainingNumberPublicHolidayService.getPublicHolidayRemNumWithinPeriod(
                context.getCompanyId(), context.getEmployeeId(), Arrays.asList(context.closureMonth.getYearMonth()), context.closureMonth.defaultPeriod().end(), InterimRemainMngMode.OTHER, 
                Optional.empty(), new ArrayList<>(), Optional.empty(), Optional.empty(), new CacheCarrier(), context.require);
        return aggrResult.createPublicHolidayRemainData(context.getEmployeeId(), context.closureMonth.getYearMonth(), ClosureId.valueOf(context.closureMonth.closureId()), context.closureMonth.closureDate())
                .getPublicHolidayday().v();
    }

    public interface Require extends
            AggregateIntegrationOfDaily.AggregationRequire,
            WorkTypeCountProspectorBase.RequireOfCreate,
            AttendanceDaysProspector.Require,
            HolidayWorkDaysProspector.Require, 
            HolidaysProspector.Require,
            SpecialVacationDaysProspector.Require, 
            AbsenceDaysProspector.Require,
            WorkDaysProspector.Require,
            GetRemainingNumberPublicHolidayService.RequireM1,
            GetAnnLeaRemNumWithinPeriodProc.RequireM3,
            GetAnnAndRsvRemNumWithinPeriod.RequireM2,
            PublicHolidayManagementUsageUnit.RequireM1{

        List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, GeneralDate baseDate);
        Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyID, String employeeId, GeneralDate baseDate);
        RecordDomRequireService requireService();
        String getCompanyId();
        Optional<PublicHolidayManagementUsageUnit> getPublicHolidayManagementUsageUnit(String companyId);
    }

    @Value
    public static class Context implements ConditionValueContext {
        Require require;
        AggregateIntegrationOfDaily aggregate;
        ClosureMonth closureMonth;

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.PROSPECT_MONTHLY;
        }

        @Override
        public String getEmployeeId() {
            return aggregate.getEmployeeId();
        }

        public String getCompanyId(){
            return require.getCompanyId();
        }

        @Override
        public DateInfo getDateInfo() {
            return new DateInfo(aggregate.getAggregationPeriod());
        }
    }
}
