package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.yearly;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.shr.com.context.AppContexts;

/**
 * アラームリストのチェック条件(見込み年次)
 */
@AllArgsConstructor
@Getter
public class ProspectYearlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    private List<AlarmListConditionValue<
            ConditionValueProspectYearlyByEmployee, ConditionValueProspectYearlyByEmployee.Context>> conditionValues;

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        String companyId = AppContexts.user().companyId();
        String employeeId = context.getTargetEmployeeId();
        val closureMonths = context.getCheckingPeriod().getMonthly().calculatePeriod(require, employeeId);
        
        val conditionValueContext = new ConditionValueProspectYearlyByEmployee.Context(require, companyId, employeeId, closureMonths);

        return IteratorUtil.iterableFilter(conditionValues, cv -> cv.checkIfEnabled(conditionValueContext));
    }

    public interface RequireChack extends
            ConditionValueProspectYearlyByEmployee.Require {

    }
}
