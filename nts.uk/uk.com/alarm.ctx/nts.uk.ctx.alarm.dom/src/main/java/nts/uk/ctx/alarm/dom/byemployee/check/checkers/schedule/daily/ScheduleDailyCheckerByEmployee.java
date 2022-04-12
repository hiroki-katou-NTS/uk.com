package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * アラームリストのチェック条件(社員別・スケジュール日次)
 */
@AllArgsConstructor
@Getter
public class ScheduleDailyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

	/** 会社ID */
	private final String companyId;

	/** コード */
	private final AlarmListCheckerCode code;

	/** 条件値のチェック条件*/
	private List<AlarmListConditionValue<
			ConditionValueScheduleDailyByEmployee,
			ConditionValueScheduleDailyByEmployee.Context>> conditionValues;

	/** 固定ロジックのチェック条件 */
	private List<FixedLogicSetting<FixedLogicScheduleDailyByEmployee>> fixedLogics;

	/**
	 * チェックする
	 * @param require
	 * @param context
	 * @return
	 */
	@Override
	public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {

		String employeeId = context.getTargetEmployeeId();
		val period = context.getCheckingPeriod().getDaily().calculatePeriod(require, employeeId);

		val alarmRecords = new ArrayList<Iterable<AlarmRecordByEmployee>>();

		// 条件値のチェック
		alarmRecords.add(checkConditionValues(require, employeeId, period));

		// 固定ロジックのチェック
		alarmRecords.add(checkFixedLogics(require, employeeId, period));

		return IteratorUtil.flatten(alarmRecords);
	}

	/**
	 * 条件値のチェック
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	private Iterable<AlarmRecordByEmployee> checkConditionValues(Require require, String employeeId, DatePeriod period) {

		return IteratorUtil.iterableFlatten(period.iterate(), date -> {
			return IteratorUtil.iterableFilter(conditionValues, cv -> {
				return cv.checkIfEnabled(new ConditionValueScheduleDailyByEmployee.Context(require, employeeId, date));
			});
		});
	}

	/**
	 * 固定ロジックのチェック
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	private Iterable<AlarmRecordByEmployee> checkFixedLogics(Require require, String employeeId, DatePeriod period) {
		return IteratorUtil.iterableFlatten(
				fixedLogics,
				f -> f.checkIfEnabled((logic, message) -> logic.check(require, employeeId, period, message)));
	}

	public interface RequireCheck extends
			CheckingPeriodDaily.Require,
			FixedLogicScheduleDailyByEmployee.RequireCheck,
			ConditionValueScheduleDailyByEmployee.Require{

	}
}
