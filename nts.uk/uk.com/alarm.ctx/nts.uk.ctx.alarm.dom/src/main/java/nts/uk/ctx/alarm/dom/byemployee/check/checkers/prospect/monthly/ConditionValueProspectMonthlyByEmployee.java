package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.monthly;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import java.util.function.Function;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.AbsenceDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.AttendanceDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.HolidayWorkDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.HolidaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.SpecialVacationDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.WorkDaysProspectorBase;


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
    所定公休日数比(2, "対比：所定公休日数比", c -> c.aggregate.calcRatioValue(c.require,
    data -> {
        // TODO: 保留
        // 実際の公休日数
        //return data.getPublicHolidayLeaveRemain().get().getPublicHolidayday().v();
        return null;
    },
    date -> {
        // 所定公休日数
        // TODO: 保留
        return null;
    })),
//    基準時間比_通常勤務(3, "対比：基準時間比（通常勤務）", c -> c.aggregate.calcRatioValueDenominatorFromOther(c.require,
//    data -> {
//        // 総労働時間
//        return data.getAttendanceTimeOfDailyPerformance()
//                .get()
//                .getActualWorkingTimeOfDaily()
//                .getTotalWorkingTime()
//                .getTotalTime()
//                .v().doubleValue();
//    },
//    (employeeId, period) -> {
//        // MonthlyStatutoryWorkingHours で労働制ごとに取得
//        val workingSystems = c.require.workingCondition(employeeId, period).stream()
//                .map(wc -> wc.getWorkingConditionItem().getLaborSystem())
//                .collect(Collectors.toList());
//        val workingSystem = workingSystems.get(0);
//        if (workingSystems.size() > 1){
//            // 月中で労働制変わってた場合基準時間ってどうなんの？？？
//        }
//
//        if (workingSystem.isFlexTimeWork()){
//            return c.require.flexMonAndWeekStatutoryTime(workingSystem);
//        }
//        else {
//            return c.require.monAndWeekStatutoryTime(workingSystem);
//        }
//    })),

    出勤日数(3, "日数：出勤日数", c ->  {
        AttendanceDaysProspector prospector = new AttendanceDaysProspector(c.require, c.companyId, c.aggregate);
        return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
    }),
    
    休日日数(3, "日数：休日日数", c-> {
        HolidaysProspector prospector = new HolidaysProspector(c.require, c.companyId, c.aggregate);
        return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
    }),
    
    休出日数(3, "日数：休出日数", c-> {
        HolidayWorkDaysProspector prospector = new HolidayWorkDaysProspector(c.require, c.companyId, c.aggregate);
        return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
    }),
    
    特休日数合計(3, "日数：特休日数合計", c-> {
        SpecialVacationDaysProspector prospector = new SpecialVacationDaysProspector(c.require, c.companyId, c.aggregate);
        return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
    }),
    
    欠勤日数合計(3, "日数：欠勤日数合計", c-> {
        AbsenceDaysProspector prospector = new AbsenceDaysProspector(c.require, c.companyId, c.aggregate);
        return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
    })
    
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

    public interface Require extends AggregateIntegrationOfDaily.AggregationRequire, WorkDaysProspectorBase.RequireOfCreate, AttendanceDaysProspector.Require, HolidayWorkDaysProspector.Require, HolidaysProspector.Require, SpecialVacationDaysProspector.Require, AbsenceDaysProspector.Require {
        //List<WorkingConditionItemWithPeriod> workingCondition(String employeeId, DatePeriod datePeriod);
    }

    @Value
    public static class Context implements ConditionValueContext {
        Require require;
        String companyId;
        AggregateIntegrationOfDaily aggregate;

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.PROSPECT_MONTHLY;
        }

        @Override
        public String getEmployeeId() {
            return aggregate.getEmployeeId();
        }

        @Override
        public DateInfo getDateInfo() {
            return new DateInfo(aggregate.getAggregationPeriod());
        }
    }
}
