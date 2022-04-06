package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.monthly;

import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;

/**
 * アラームリストのチェック条件(社員別・月次)
 */
public class MonthlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        return null;
    }

    public interface RequireCheck {

    }
}
