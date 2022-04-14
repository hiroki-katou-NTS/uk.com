package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.AttendRateAtNextHoliday;

import java.util.Optional;
import java.util.function.Function;

/**
 * 条件値チェック(社員別・年休付与)
 */
@RequiredArgsConstructor
public enum ConditionValueAnnualleaveByEmployee implements ConditionValueLogic<ConditionValueAnnualleaveByEmployee.Context> {

	出勤率(1, "年休付与用出勤率", c -> {
		return c.require.getDaysPerYear(c.employeeId)
				.map(ar -> ar.getAttendanceRate().v().doubleValue())
				.orElse(null);
	}),

	;

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
		boolean onlyCurrentGrantTarget;

		@Override
		public AlarmListCategoryByEmployee getCategory() {
			return AlarmListCategoryByEmployee.VACATION;
		}

		@Override
		public String getEmployeeId() {
			return employeeId;
		}

		@Override
		public DateInfo getDateInfo() {
			return new DateInfo(GeneralDate.today());
		}
	}

	public interface Require{
		Optional<AttendRateAtNextHoliday> getDaysPerYear(String employeeId);
	}
}
