package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.anyperiod;

import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;

/**
 * アラームリストのチェック条件(社員別・任意期間) 
 */
@Value
public class AnyPeriodCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee{

	private String companyId;
	
	private AlarmListCheckerCode checkerCode;
	
	private String name;

	private CheckAnyPeriodErrorAlarm errorAlarmChecker; 

	@Override
	public Iterable<AlarmRecordByEmployee> check(
			AlarmListCheckerByEmployee.Require require,
			CheckingContextByEmployee context) {
		val anyPeriodRecord = require.getAttendanceTimeOfAnyPeriod(
				context.getTargetEmployeeId(),
				context.getCheckingPeriod().getAnyPeriod().v()
			);
		return this.errorAlarmChecker.check(require, context, anyPeriodRecord);
	}
	
	public interface RequireCheck extends CheckAnyPeriodErrorAlarm.Require{
		AttendanceTimeOfAnyPeriod getAttendanceTimeOfAnyPeriod(String employeeId, String anyPeriodFrameCode);
		
	}
}
