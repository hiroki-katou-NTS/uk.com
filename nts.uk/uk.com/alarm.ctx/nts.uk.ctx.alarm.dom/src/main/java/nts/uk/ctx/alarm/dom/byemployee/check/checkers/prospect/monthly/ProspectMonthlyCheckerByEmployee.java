package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.TargetEmployeesFilter;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;

import java.util.Arrays;
import java.util.List;
import sun.awt.AppContext;

/**
 * アラームリストのチェック条件(見込み月次)
 */
@AllArgsConstructor
@Getter
public class ProspectMonthlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    private TargetEmployeesFilter employeesFilter;

    private List<AlarmListConditionValue<
            ConditionValueProspectMonthlyByEmployee,
            ConditionValueProspectMonthlyByEmployee.Context>> conditionValues;

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {

        String employeeId = context.getTargetEmployeeId();
        val closureMonths = context.getCheckingPeriod().getMonthly().calculatePeriod(require, employeeId);

        // 締め月ごとにチェック
        return IteratorUtil.iterableFlatten(closureMonths, closureMonth -> {

            AggregateIntegrationOfDaily aggregate = new AggregateIntegrationOfDaily(employeeId, closureMonth);

            val conditionValueContext = new ConditionValueProspectMonthlyByEmployee.Context(require, "companyId_仮",aggregate);

            List<Iterable<AlarmRecordByEmployee>> alarms = Arrays.asList(
                    IteratorUtil.iterableFilter(conditionValues, cv -> cv.checkIfEnabled(conditionValueContext))
            );

            return IteratorUtil.flatten(alarms);
        });


        // ↓サンプルコード
//        // 時間：予定時間＋総労働時間
//        Double check01Value = aggregateService.aggregate(require, data -> {
//            return (double) data.getAttendanceTimeOfDailyPerformance()
//                    .get()
//                    .getActualWorkingTimeOfDaily()
//                    .getTotalWorkingTime().getTotalTime().valueAsMinutes();
//        });
//
//        // （予定時間＋総労働時間）×基準単価
//        Double check02Value = aggregateService.aggregate(require, data -> {
//            val totalTime = (double) data.getAttendanceTimeOfDailyPerformance()
//                    .get()
//                    .getActualWorkingTimeOfDaily()
//                    .getTotalWorkingTime().getTotalTime().valueAsMinutes();
//            val baseUnitPrice = require.getBaseUnitPrice(data.getYmd(), data.getEmployeeId());
//            return totalTime * baseUnitPrice;
//        });
//
//        // 対比：基準時間比（通常勤務）
//        val workingConditionItem = require.getWorkingConditions(employeeId, period).stream()
//                .filter(wc -> wc.getWorkingConditionItem().getLaborSystem() == WorkingSystem.REGULAR_WORK)
//                .collect(Collectors.toList());
//        for (WorkingConditionItemWithPeriod wc : workingConditionItem) {
//            AggregateIntegrationOfDaily aggregateService2 = new AggregateIntegrationOfDaily(wc.getDatePeriod(), employeeId);
//            Double check03Value = aggregateService2.calcRatioValue(require,
//                    data -> {
//                        // TODO:どこから値を持ってくるのかまだ未定
//                        return (double) data.getAttendanceTimeOfDailyPerformance()
//                                .get()
//                                .getActualWorkingTimeOfDaily()
//                                .getTotalWorkingTime().getTotalTime().valueAsMinutes();
//                    },
//                    data -> (double) wc.getWorkingConditionItem().getContractTime().valueAsMinutes());
//        }
//
//        throw new RuntimeException("not implemented");
    }

    public interface RequireCheck extends
            CheckingPeriodMonthly.Require,
            AggregateIntegrationOfDaily.AggregationRequire,
            ConditionValueProspectMonthlyByEmployee.Require {

    }
}
