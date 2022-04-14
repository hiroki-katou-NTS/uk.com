package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.yearly;

import java.util.List;
import java.util.function.DoubleFunction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfMonthly;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;

import java.util.function.Function;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.AttendanceDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.WorkDaysProspectorBase;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;

/**
 * 条件値チェック（社員別・見込み年次）
 */
@RequiredArgsConstructor
public enum ConditionValueProspectYearlyByEmployee implements ConditionValueLogic<ConditionValueProspectYearlyByEmployee.Context> {
    支給額(1, "（予定時間＋総労働時間）×基準単価", c -> c.closedAggregator.aggregate(c.require, data -> {

        // 就業時間金額＋割増時間金額を求める
        // 就業時間金額
        double withinWorkTimeAmount = data.getAttendanceTime()
                .get()
                .getVerticalTotal()
                .getWorkAmount()
                .getWorkTimeAmount()
                .v();
        // 割増時間金額
        double totalAmount = data.getAttendanceTime()
                .get()
                .getVerticalTotal()
                .getWorkTime()
                .getPremiumTime()
                .getPremiumAmountTotal()
                .v();

        return withinWorkTimeAmount + totalAmount;
    })),
    出勤日数(2, "日数：出勤日数", c -> {
        return aggregate(c,
                (iom) -> {
                    return iom.getAttendanceTime()
                            .map(attendanceTime -> attendanceTime.getVerticalTotal().getWorkDays().getAttendanceDays().getDays().v())
                            .orElse(0.0);
                },
                (aggregator) -> {
                    AttendanceDaysProspector prospector = new AttendanceDaysProspector(c.require, "仮", aggregator);
                    return prospector.prospect(c.require, "仮", c.getEmployeeId());
                });

    }),;

    public final int value;

    /**
     * 項目名
     */
    @Getter
    private final String name;
    private final Function<Context, Double> getValue;

    private static double aggregate(
            Context context,
            Function<IntegrationOfMonthly, Double> closedFunction,
            Function<AggregateIntegrationOfDaily, Double> prospectFunction) {

        double closedRecord = context.closedAggregator.aggregate(context.require, iom -> closedFunction.apply(iom));
        double prospect = context.prospectAggregator.stream().mapToDouble(aggregator -> {
            return prospectFunction.apply(aggregator);
        }).sum();

        return closedRecord + prospect;
    }

    @Override
    public Double getValue(Context context) {
        return getValue.apply(context);
    }

    public interface Require extends
            AggregateIntegrationOfMonthly.AggregationRequire,
            AggregateIntegrationOfDaily.AggregationRequire,
            WorkDaysProspectorBase.RequireOfCreate,
            AttendanceDaysProspector.Require {
    }

    @Value
    public static class Context implements ConditionValueContext {

        Require require;
        // 当月より前
        AggregateIntegrationOfMonthly closedAggregator;
        // 当月以降
        List<AggregateIntegrationOfDaily> prospectAggregator;

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.PROSPECT_YEARLY;
        }

        @Override
        public String getEmployeeId() {
            return closedAggregator.getEmployeeId();
        }

        @Override
        public DateInfo getDateInfo() {
            // TODO:
            return null;
        }
    }
}
