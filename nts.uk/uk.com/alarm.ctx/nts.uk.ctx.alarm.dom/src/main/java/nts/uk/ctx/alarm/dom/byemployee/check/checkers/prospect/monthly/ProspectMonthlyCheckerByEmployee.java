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
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;

import java.util.Arrays;
import java.util.List;

import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.closure.ClosureMonth;
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

    private List<FixedLogicSetting<FixedLogicProspectMonthlyByEmployee>> fixedLogics;

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {

        String employeeId = context.getTargetEmployeeId();
        val closureMonths = context.getCheckingPeriod().getMonthly().calculatePeriod(require, employeeId);

        // 締め月ごとにチェック
        return IteratorUtil.iterableFlatten(closureMonths, closureMonth -> {

            AggregateIntegrationOfDaily aggregate = new AggregateIntegrationOfDaily(employeeId, closureMonth);
            String companyId = AppContexts.user().companyId();

            val conditionValueContext = new ConditionValueProspectMonthlyByEmployee.Context(require, companyId, aggregate, closureMonth);

            List<Iterable<AlarmRecordByEmployee>> alarms = Arrays.asList(
                    IteratorUtil.iterableFilter(conditionValues, cv -> cv.checkIfEnabled(conditionValueContext))
            );

            val checkFixedLogicsContext = new FixedLogicProspectMonthlyByEmployee.Context(require, employeeId, closureMonth, aggregate);
            List<Iterable<AlarmRecordByEmployee>> fixedlogicAlarms = checkFixedLogics(checkFixedLogicsContext);
            alarms.addAll(fixedlogicAlarms);

            return IteratorUtil.flatten(alarms);
        });
    }


    /**
     * 固定チェック条件
     * @return
     */
    private List<Iterable<AlarmRecordByEmployee>> checkFixedLogics(FixedLogicProspectMonthlyByEmployee.Context context) {
        return Arrays.asList(
                IteratorUtil.iterableFilter(fixedLogics, fls -> fls.checkIfEnabledOpt(
                        (logic, message) -> logic.check(context, message)
                ))
        );
    }

    public interface RequireCheck extends
            CheckingPeriodMonthly.Require,
            AggregateIntegrationOfDaily.AggregationRequire,
            ConditionValueProspectMonthlyByEmployee.Require,
            FixedLogicProspectMonthlyByEmployee.RequireCheck {

    }
}
