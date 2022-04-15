package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.yearly;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfMonthly;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.CriterionAmountChecker;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodYearly;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.shr.com.time.closure.ClosureMonth;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 *固定のチェック条件(社員別・見込み年次)
 */
@RequiredArgsConstructor
public enum FixedLogicProspectYearlyByEmployee {
    目安金額比(1, CriterionAmountChecker.CHECK_NAME, (c, message) -> {
        Double amountValue = c.getClosedAggregator().aggregate(c.getRequire(), data -> {
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
        });
        val baseDate = c.period.getBaseDate();
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
        CheckingPeriodYearly period;

        public AggregateIntegrationOfMonthly getClosedAggregator() {
            // TODO: 当月より前のものだけにする
            List<ClosureMonth> closedMonths = null;
            return new AggregateIntegrationOfMonthly(this.employeeId, closedMonths);
        }

        public List<AggregateIntegrationOfDaily> getProspectAggregator() {
            // TODO: 当月以降のものだけにする
            List<ClosureMonth> prospectMonths = null;
            return prospectMonths.stream().map(month -> new AggregateIntegrationOfDaily(this.employeeId, month))
                    .collect(Collectors.toList());
        }

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.PROSPECT_YEARLY;
        }

        @Override
        public DateInfo getDateInfo() {
            return period.getDateInfo();
        }
    }

    public interface RequireCheck extends
            AggregateIntegrationOfMonthly.AggregationRequire,
            CriterionAmountChecker.Require {
    }
}
