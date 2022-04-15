package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.monthly;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployeeGettingService;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.shr.com.time.closure.ClosureMonth;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *固定のチェック条件(社員別・見込み月次)
 */
@RequiredArgsConstructor
public enum FixedLogicProspectMonthlyByEmployee {
    目安金額比(1, "（予定時間＋総労働時間）×基準単価と目安金額の比較", (c, message) -> {
        Double paidAmount = c.getAggregate().aggregate(c.getRequire(), data -> {
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
        });
        val baseDate = CheckingPeriodMonthly.getBaseDate(c.getClosureMonth());
        CriterionAmount criterionAmount = CriterionAmountForEmployeeGettingService.get(
                c.getRequire(), new EmployeeId(c.employeeId), baseDate);
        // どの枠の目安金額使うか分からん
        boolean over = criterionAmount.getMonthly().getList().stream()
                .allMatch(ca -> paidAmount > ca.getAmount().v());

        return over
                ? Optional.of(c.alarm("（予定時間＋総労働時間）×基準単価と目安金額の比較", c.closureMonth, message))
                : Optional.empty();
    }),
    ;

    public final int value;

    /** 項目名 */
    @Getter
    private final String name;
    private final BiFunction<Context, AlarmListAlarmMessage, Optional<AlarmRecordByEmployee>> logic;

    public Optional<AlarmRecordByEmployee> check(Context context, AlarmListAlarmMessage message) {
        return logic.apply(context, message);
    }


    @Value
    public static class Context {
        RequireCheck require;
        String employeeId;
        ClosureMonth closureMonth;
        AggregateIntegrationOfDaily aggregate;

        public AlarmRecordByEmployee alarm(String name, ClosureMonth closureMonth, AlarmListAlarmMessage message) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    new DateInfo(CheckingPeriodMonthly.getDatePeriod(closureMonth)),
                    AlarmListCategoryByEmployee.PROSPECT_MONTHLY,
                    name,
                    "",
                    null,
                    message);
        }

        public ClosureMonth getClosureMonth() {
            return closureMonth;
        }
    }

    public interface RequireCheck extends
            AggregateIntegrationOfDaily.AggregationRequire,
            CriterionAmountForEmployeeGettingService.Require {
    }
}
