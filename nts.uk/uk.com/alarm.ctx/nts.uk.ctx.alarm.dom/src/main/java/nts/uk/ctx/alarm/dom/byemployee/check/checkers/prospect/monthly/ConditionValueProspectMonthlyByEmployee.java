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

/**
 * 条件値チェック(社員別・見込み月次)
 */
@RequiredArgsConstructor
public enum ConditionValueProspectMonthlyByEmployee implements ConditionValueLogic<ConditionValueProspectMonthlyByEmployee.Context> {

    支給額(1, "（予定時間＋総労働時間）×基準単価", c -> c.aggregate.aggregate(c.require, data -> {

        int tanka = 0; // TODO: どうにかして取ってくる

        double totalTime = data.getAttendanceTimeOfDailyPerformance()
                .get()
                .getActualWorkingTimeOfDaily()
                .getTotalWorkingTime().getTotalTime().valueAsMinutes();

        return totalTime * tanka;
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

    public interface Require extends AggregateIntegrationOfDaily.AggregationRequire {

    }

    @Value
    public static class Context implements ConditionValueContext {
        Require require;
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
            return new DateInfo(aggregate.getClosureMonth());
        }
    }
}
