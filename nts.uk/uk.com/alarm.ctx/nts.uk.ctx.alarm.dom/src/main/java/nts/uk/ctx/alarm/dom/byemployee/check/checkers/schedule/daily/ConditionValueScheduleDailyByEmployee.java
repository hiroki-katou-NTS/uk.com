package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;

import java.util.Optional;
import java.util.function.Function;

/**
 * 条件値チェック(社員別・スケジュール日次)
 */
@RequiredArgsConstructor
public enum ConditionValueScheduleDailyByEmployee implements ConditionValueLogic<ConditionValueScheduleDailyByEmployee.Context> {

	予定時間(1, "予定時間", c -> {
		return c.require.getWorkSchedule(c.employeeId, c.date)
				.map(ws -> ws.getOptAttendanceTime()
						.map(at -> Double.valueOf(at.getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime().v()))
						.orElse(null))
				.orElse(null);
	});

	public final int value;

	/** 項目名 */
	@Getter
	private final String name;

	private final Function<Context, Double> getValue;

	@Override
	public Double getValue(Context context) {
		return getValue.apply(context);
	}

	@Value
	public static class Context implements ConditionValueContext {
		Require require;
		String employeeId;
		GeneralDate date;

		@Override
		public AlarmListCategoryByEmployee getCategory() {
			return AlarmListCategoryByEmployee.SCHEDULE_DAILY;
		}

		@Override
		public String getEmployeeId() {
			return employeeId;
		}

		@Override
		public DateInfo getDateInfo() {
			return new DateInfo(date);
		}
	}

	public interface Require {
		Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date);
	}
}
