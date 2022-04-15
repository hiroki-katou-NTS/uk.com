package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.yearly;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.monthly.FixedLogicProspectMonthlyByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * アラームリストのチェック条件(見込み年次)
 */
@AllArgsConstructor
@Getter
public class ProspectYearlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    private List<AlarmListConditionValue<
            ConditionValueProspectYearlyByEmployee, ConditionValueProspectYearlyByEmployee.Context>> conditionValues;

    private List<FixedLogicSetting<FixedLogicProspectYearlyByEmployee>> fixedLogics;

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        String companyId = AppContexts.user().companyId();
        String employeeId = context.getTargetEmployeeId();
        val closureMonths = context.getCheckingPeriod().getProspectYearly().calculatePeriod(require, employeeId);
        
        val conditionValueContext = new ConditionValueProspectYearlyByEmployee.Context(require, companyId, employeeId, closureMonths);

        val alarms = IteratorUtil.iterableFilter(conditionValues, cv -> cv.checkIfEnabled(conditionValueContext));

        val checkFixedLogicsContext = new FixedLogicProspectYearlyByEmployee.Context(require, employeeId, context.getCheckingPeriod().getProspectYearly());
        val fixedlogicAlarms = checkFixedLogics(checkFixedLogicsContext);

        return IteratorUtil.merge(alarms, fixedlogicAlarms);
    }

    /**
     * 固定チェック条件
     * @return
     */
    private Iterable<AlarmRecordByEmployee> checkFixedLogics(FixedLogicProspectYearlyByEmployee.Context context) {
        return IteratorUtil.iterableFilter(fixedLogics, fls -> fls.checkIfEnabledOpt(
                        (logic, message) -> logic.check(context, message)
                ));
    }

    public interface RequireChack extends
            ConditionValueProspectYearlyByEmployee.Require,
            FixedLogicProspectYearlyByEmployee.RequireCheck {

    }
}
