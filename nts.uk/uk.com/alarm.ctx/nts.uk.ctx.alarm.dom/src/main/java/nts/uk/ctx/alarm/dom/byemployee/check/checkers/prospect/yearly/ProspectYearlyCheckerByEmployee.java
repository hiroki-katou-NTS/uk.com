package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.yearly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;

/**
 * アラームリストのチェック条件(見込み年次)
 */
@AllArgsConstructor
@Getter
public class ProspectYearlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        // TODO:
        return null;
    }

    public interface RequireChack extends
            ConditionValueProspectYearlyByEmployee.Require{

    }
}
