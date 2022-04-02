package nts.uk.ctx.alarm.dom.byemployee.check.checkers.appapproval;

import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;

/**
 * アラームリストのチェック条件(社員別・申請承認) 
 */
public class AppApprovalByEmployee implements DomainAggregate, AlarmListCheckerByEmployee{

	@Override
	public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
		return null;
	}

}
