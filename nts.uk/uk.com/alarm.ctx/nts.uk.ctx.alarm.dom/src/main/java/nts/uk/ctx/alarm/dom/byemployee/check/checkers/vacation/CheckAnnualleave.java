package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.shr.com.context.AppContexts;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class CheckAnnualleave {

	/** 当月の付与対象者のみチェックする */
	private boolean onlyCurrentGrantTarget;

	/** 条件値のチェック条件*/
	private List<AlarmListConditionValue<
			ConditionValueAnnualleaveByEmployee,
			ConditionValueAnnualleaveByEmployee.Context>> conditionValues;

	/**
	 * 条件に該当するか
	 * @param employeeId
	 * @return
	 */
	public Iterable<AlarmRecordByEmployee> checkIfEnabled(RequireCheck require, String employeeId) {
		// 次回年休付与対象者のみチェックする場合
		if(onlyCurrentGrantTarget){
			// 次回年休付与を計算する
			val GrantInfo = CalcNextAnnualLeaveGrantDate.algorithmContainPeriodStartDate(
					require, new CacheCarrier(),
					AppContexts.user().companyId(),
					employeeId, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
			if(GrantInfo.isEmpty()){
				return Collections.emptyList();
			}
		}
		return IteratorUtil.iterableFilter(conditionValues, cv -> {
			return cv.checkIfEnabled(new ConditionValueAnnualleaveByEmployee.Context(require, employeeId, onlyCurrentGrantTarget));
		});
	}

	public interface RequireCheck extends
			ConditionValueAnnualleaveByEmployee.Require,
			CalcNextAnnualLeaveGrantDate.RequireM2 {

	}

}
