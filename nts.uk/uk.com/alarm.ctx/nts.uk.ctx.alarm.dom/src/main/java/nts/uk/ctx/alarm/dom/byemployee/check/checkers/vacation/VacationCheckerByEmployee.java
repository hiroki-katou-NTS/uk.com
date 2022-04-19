package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodVacation;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * アラームリストのチェック条件(社員別・休暇)
 */
@Value
public class VacationCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

	/** 会社ID */
	private final String companyId;

	/** コード */
	private final AlarmListCheckerCode code;

	/** 固定ロジックのチェック条件 */
	private List<FixedLogicSetting<FixedLogicVacationByEmployee>> fixedLogics;

	/** 条件値のチェック条件*/
	private List<AlarmListConditionValue<
			ConditionValueVacationByEmployee,
			ConditionValueVacationByEmployee.Context>> conditionValueLogics;

	/** 年休付与のチェック条件*/
	private List<CheckAnnualleave> annualleaveLogics;

	/** 年休使用のチェック条件*/
	private CheckUseAnnualleave useAnnualleaveLogic;

	/** 年休優先使用のチェック条件*/
	private CheckPriorityUseAnnualleave priorityUseAnnualleaveLogic;



	/**
	 * チェックする
	 * @param require
	 * @param context
	 * @return
	 */
	@Override
	public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {

		String employeeId = context.getTargetEmployeeId();
		val period = context.getCheckingPeriod().getVacation().calculatePeriod(require, employeeId);

		val alarmRecords = new ArrayList<Iterable<AlarmRecordByEmployee>>();

		// 固定ロジックのチェック
		alarmRecords.add(checkFixedLogics(require, employeeId, period));

		// 条件値のチェック
		alarmRecords.add(checkConditionValueLogics(require, employeeId, period));

		// 年休付与のチェック
		alarmRecords.add(checkAnnualleaveLogics(require, employeeId));

		// 年休取得のチェック
		useAnnualleaveLogic.checkIfEnabled(require, employeeId)
				.ifPresent(r -> alarmRecords.add(Collections.singletonList(r)));

		// 年休優先使用のチェック
		alarmRecords.add(priorityUseAnnualleaveLogic.checkIfEnabled(require, employeeId, period));

		return IteratorUtil.flatten(alarmRecords);
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
				f -> f.checkIfEnabled((logic, message) -> logic.check(
						require,
						employeeId,
						period,
						message)));
	}

	/**
	 * 条件値のチェック
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	private Iterable<AlarmRecordByEmployee> checkConditionValueLogics(Require require, String employeeId, DatePeriod period) {
		return IteratorUtil.iterableFilter(conditionValueLogics, cv -> {
			return cv.checkIfEnabled(new ConditionValueVacationByEmployee.Context(require, employeeId, period));
		});
	}

	/**
	 * 年休付与のチェック
	 * @param require
	 * @param employeeId
	 * @return
	 */
	private Iterable<AlarmRecordByEmployee> checkAnnualleaveLogics(Require require, String employeeId) {
		return IteratorUtil.iterableFlatten(annualleaveLogics, a -> a.checkIfEnabled(require, employeeId));

	}

	public interface RequireCheck extends
			CheckingPeriodVacation.Require,
			FixedLogicVacationByEmployee.RequireCheck,
			ConditionValueVacationByEmployee.Require,
			CheckAnnualleave.RequireCheck,
			CheckUseAnnualleave.RequireCheck,
			CheckPriorityUseAnnualleave.RequireCheck{
	}
}
