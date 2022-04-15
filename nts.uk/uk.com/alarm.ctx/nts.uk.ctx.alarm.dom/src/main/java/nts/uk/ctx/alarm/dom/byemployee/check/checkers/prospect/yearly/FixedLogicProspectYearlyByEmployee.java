package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.yearly;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfMonthly;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodYearly;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployeeGettingService;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
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
    目安金額比(1, "（予定時間＋総労働時間）×基準単価と目安金額の比較", (c, message) -> {
        Double paidAmount = c.getClosedAggregator().aggregate(c.getRequire(), data -> {
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
        CriterionAmount criterionAmount = CriterionAmountForEmployeeGettingService.get(
                c.getRequire(), new EmployeeId(c.employeeId), baseDate);
        // どの枠の目安金額使うか分からん
        boolean over = criterionAmount.getYearly().getList().stream()
                .allMatch(ca -> paidAmount > ca.getAmount().v());

        return over
                ? Optional.of(c.alarm("（予定時間＋総労働時間）×基準単価と目安金額の比較", c.period.getDateInfo(), message))
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
        CheckingPeriodYearly period;

        public AlarmRecordByEmployee alarm(String name, DateInfo dateInfo, AlarmListAlarmMessage message) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    dateInfo,
                    AlarmListCategoryByEmployee.PROSPECT_YEARLY,
                    name,
                    "",
                    null,
                    message);
        }

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
    }

    public interface RequireCheck extends
            AggregateIntegrationOfMonthly.AggregationRequire,
            CriterionAmountForEmployeeGettingService.Require {
    }
}
