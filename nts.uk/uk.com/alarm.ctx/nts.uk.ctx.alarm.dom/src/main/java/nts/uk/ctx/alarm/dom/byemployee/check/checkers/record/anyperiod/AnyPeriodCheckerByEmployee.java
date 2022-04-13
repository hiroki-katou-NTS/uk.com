package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.anyperiod;

import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;

import java.util.ArrayList;
import java.util.List;

/**
 * アラームリストのチェック条件(社員別・任意期間) 
 */
@Value
public class AnyPeriodCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee{

	private String companyId;
	
	private AlarmListCheckerCode checkerCode;
	
	private String name;

	private CheckAnyPeriodErrorAlarm errorAlarmChecker;

	/** 条件値のチェック条件*/
	private List<AlarmListConditionValue<
			ConditionValueAnyPeriodByEmployee,
			ConditionValueAnyPeriodByEmployee.Context>> conditionValues;

	@Override
	public Iterable<AlarmRecordByEmployee> check(
			AlarmListCheckerByEmployee.Require require,
			CheckingContextByEmployee context) {
		val anyPeriodRecord = require.getAttendanceTimeOfAnyPeriod(
				context.getTargetEmployeeId(),
				context.getCheckingPeriod().getAnyPeriod().v()
			);

		val alarmRecords = new ArrayList<Iterable<AlarmRecordByEmployee>>();

		// 条件値のチェック
		alarmRecords.add(checkConditionValues(require, anyPeriodRecord.getEmployeeId(), anyPeriodRecord.getAnyAggrFrameCode()));


		alarmRecords.add(this.errorAlarmChecker.check(require, context, anyPeriodRecord));

		return IteratorUtil.flatten(alarmRecords);
	}

	/**
	 * 条件値のチェック
	 * @param require
	 * @param employeeId
	 * @param anyAggrFrameCode
	 * @return
	 */
	private Iterable<AlarmRecordByEmployee> checkConditionValues(Require require, String employeeId, AnyAggrFrameCode anyAggrFrameCode) {
		return IteratorUtil.iterableFilter(conditionValues, cv -> {
			return cv.checkIfEnabled(new ConditionValueAnyPeriodByEmployee.Context(require, employeeId, anyAggrFrameCode));
		});
	}
	
	public interface RequireCheck extends
			CheckAnyPeriodErrorAlarm.Require,
			ConditionValueAnyPeriodByEmployee.Require{
		AttendanceTimeOfAnyPeriod getAttendanceTimeOfAnyPeriod(String employeeId, String anyPeriodFrameCode);
		
	}
}
