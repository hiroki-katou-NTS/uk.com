package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;

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

	/**
	 * チェックする
	 * @param require
	 * @param context
	 * @return
	 */
	@Override
	public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
		return null;
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
				f -> f.checkIfEnabled((logic, message) -> logic.check(require, employeeId, message)));
	}

	public interface RequireCheck extends FixedLogicVacationByEmployee.RequireCheck {

	}
}
