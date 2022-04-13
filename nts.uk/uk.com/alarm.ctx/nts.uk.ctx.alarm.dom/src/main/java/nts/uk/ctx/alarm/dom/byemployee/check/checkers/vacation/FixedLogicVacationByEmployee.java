package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;

import java.util.function.Function;

/**
 * 固定のチェック条件(社員別・休暇)
 */
@RequiredArgsConstructor
public enum FixedLogicVacationByEmployee {

	子の看護休暇上限(1,c -> {
		return null;
	}),

	;

	public final int value;

	private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;

	/**
	 * チェックする
	 * @param require
	 * @param employeeId
	 * @param message
	 * @return
	 */
	public Iterable<AlarmRecordByEmployee> check(
			RequireCheck require,
			String employeeId,
			AlarmListAlarmMessage message) {

		val context = new Context(require, employeeId, message);

		return logic.apply(context);
	}



	@Value
	private class Context {
		RequireCheck require;
		String employeeId;
		AlarmListAlarmMessage message;

		public AlarmRecordByEmployee alarm() {
			return new AlarmRecordByEmployee(
					employeeId,
					DateInfo.none(),
					AlarmListCategoryByEmployee.VACATION,
					"",
					"",
					"",
					message);
		}
	}

	public interface RequireCheck{

	}
}
