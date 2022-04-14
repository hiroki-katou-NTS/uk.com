package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.yearly;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfMonthly;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;

import java.util.function.Function;

/**
 * 条件値チェック（社員別・見込み年次）
 */
@RequiredArgsConstructor
public enum ConditionValueProspectYearlyByEmployee implements ConditionValueLogic<ConditionValueProspectYearlyByEmployee.Context> {
    支給額(1, "（予定時間＋総労働時間）×基準単価", c -> c.aggregate.aggregate(c.require, data -> {

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

    public interface Require extends AggregateIntegrationOfMonthly.AggregationRequire {

    }

    @Value
    public static class Context implements ConditionValueContext {
        Require require;
        AggregateIntegrationOfMonthly aggregate;

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.PROSPECT_YEARLY;
        }

        @Override
        public String getEmployeeId() {
            return aggregate.getEmployeeId();
        }

        @Override
        public DateInfo getDateInfo() {
            // TODO:
            return null;
        }
    }
}
