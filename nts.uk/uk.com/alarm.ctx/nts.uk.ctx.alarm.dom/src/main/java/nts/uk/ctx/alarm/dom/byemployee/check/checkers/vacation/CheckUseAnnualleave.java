package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.val;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 年休付与のチェック条件
 */
public class CheckUseAnnualleave {

	/** 次回の年休付与日が指定した月以内にある人のみチェックする */
	private boolean onlyWithinNextGrantDate;

	/** 次回の年休付与日までの月数 */
	private Optional<Integer> months;

	/** 前回の年休付与日数が指定した日数以上の人のみチェックする */
	private boolean onlyPreviousGrantDaysOver;

	/** 前回の年休付与日数 */
	private Optional<Integer> previousGrantDays;

	/** 前回付与までの期間が１年未満の場合、期間按分する */
	private boolean proportionalDistributionLessThanOneYear;

	/** 条件値のチェック条件*/
	private List<AlarmListConditionValue<
			ConditionValueUseAnnualleaveByEmployee,
			ConditionValueUseAnnualleaveByEmployee.Context>> conditionValues;

	/**
	 * 条件に該当するか
	 * @param employeeId
	 * @return
	 */
	public Iterable<AlarmRecordByEmployee> checkIfEnabled(RequireCheck require, String employeeId) {
		// 次回の年休付与日が指定した月以内にある人のみチェックする
		if(onlyWithinNextGrantDate){
			return Collections.emptyList();
		}
		// 前回の年休付与日数が指定した日数以上の人のみチェックする
		if(onlyPreviousGrantDaysOver){
			return Collections.emptyList();
		}
		return IteratorUtil.iterableFilter(conditionValues, cv -> {
			return cv.checkIfEnabled(new ConditionValueUseAnnualleaveByEmployee.Context(require, employeeId, proportionalDistributionLessThanOneYear));
		});
	}

	public interface RequireCheck extends ConditionValueUseAnnualleaveByEmployee.Require{

	}
}
