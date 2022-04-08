package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import java.util.function.Function;

/**
 * 固定のチェック条件(社員別・スケジュール日次)
 */
@RequiredArgsConstructor
public enum FixedLogicScheduleDailyByEmployee {

	スケジュール未作成(1, c -> checkNotCreated(c)),
	;

	public final int value;

	private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;

	/**
	 * チェックする
	 * @param require
	 * @param employeeId
	 * @param checkingPeriod
	 * @param message
	 * @return
	 */
	public Iterable<AlarmRecordByEmployee> check(
			RequireCheck require,
			String employeeId,
			DatePeriod checkingPeriod,
			String message) {

		val context = new Context(require, employeeId, checkingPeriod, message);

		return logic.apply(context);
	}

	private static Iterable<AlarmRecordByEmployee> checkNotCreated(Context context){
		return () -> context.period.datesBetween().stream()
				.filter(date -> !context.require.isExists(context.employeeId, date))
				.map(date -> context.alarm(date))
				.iterator();
	}


	@Value
	private class Context {
		RequireCheck require;
		String employeeId;
		DatePeriod period;
		String message;

		public AlarmRecordByEmployee alarm(GeneralDate date) {
			return new AlarmRecordByEmployee(
					employeeId,
					new DateInfo(date),
					AlarmListCategoryByEmployee.SCHEDULE_DAILY,
					"",
					"",
					message);
		}
	}

	public interface RequireCheck {
		boolean isExists(String employeeId, GeneralDate date);

	}
}
