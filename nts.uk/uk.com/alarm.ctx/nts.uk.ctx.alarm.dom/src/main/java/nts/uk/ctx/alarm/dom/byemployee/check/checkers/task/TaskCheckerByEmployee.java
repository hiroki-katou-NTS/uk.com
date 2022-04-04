package nts.uk.ctx.alarm.dom.byemployee.check.checkers.task;

import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;

/**
 * アラームリストのチェック条件(社員別・工数)
 */
public class TaskCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee{

	@Override
	public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
		// TODO Auto-generated method stub
		return null;
	}

}
