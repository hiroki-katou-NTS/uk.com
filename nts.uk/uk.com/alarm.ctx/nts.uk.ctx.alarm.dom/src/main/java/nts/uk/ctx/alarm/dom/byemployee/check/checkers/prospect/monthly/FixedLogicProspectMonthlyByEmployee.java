package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.monthly;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.CriterionAmountChecker;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.shr.com.time.closure.ClosureMonth;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 *固定のチェック条件(社員別・見込み月次)
 */
@RequiredArgsConstructor
public enum FixedLogicProspectMonthlyByEmployee {
    目安金額比(1, CriterionAmountChecker.CHECK_NAME, (c, message) -> {
        Double amountValue = c.getAggregate().aggregate(c.getRequire(), data -> {
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
        return CriterionAmountChecker.check(c.require, c, baseDate, amountValue.intValue(), message);
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
    public static class Context implements ConditionValueContext {
        RequireCheck require;
        String employeeId;
        ClosureMonth closureMonth;
        AggregateIntegrationOfDaily aggregate;

        public ClosureMonth getClosureMonth() {
            return closureMonth;
        }

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.PROSPECT_YEARLY;
        }

        @Override
        public DateInfo getDateInfo() {
            return new DateInfo(closureMonth);
        }
    }

    public interface RequireCheck extends
            AggregateIntegrationOfDaily.AggregationRequire,
            CriterionAmountChecker.Require {
    }
}
