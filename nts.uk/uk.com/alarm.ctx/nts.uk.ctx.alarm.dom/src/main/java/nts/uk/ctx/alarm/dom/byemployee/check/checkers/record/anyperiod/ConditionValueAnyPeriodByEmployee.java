package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.anyperiod;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;

import java.util.function.Function;

/**
 * 条件値チェック(社員別・任意期間)
 */
@RequiredArgsConstructor
public enum ConditionValueAnyPeriodByEmployee implements ConditionValueLogic<ConditionValueAnyPeriodByEmployee.Context> {

	出勤率(1, "出勤率", c -> {
		val workDays = c.require.getAttendanceTimeOfAnyPeriod(c.employeeId, c.anyPeriodFrameCode.toString()).getVerticalTotal().getWorkDays();
		// 総出勤日数（出勤日数＋休出日数＋振出日数）
		val totalAttendanceDays = workDays.getAttendanceDays().getDays().v()
								+ workDays.getHolidayWorkDays().getDays().v()
								+ workDays.getRecruitmentDays().getDays().v();
		return (totalAttendanceDays / c.require.getAnyAggrPeriod(c.anyPeriodFrameCode).getPeriod().stream().count()) * 100;
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
		AnyAggrFrameCode anyPeriodFrameCode;

		@Override
		public AlarmListCategoryByEmployee getCategory() {
			return AlarmListCategoryByEmployee.RECORD_ANY_PERIOD;
		}

		@Override
		public String getEmployeeId() {
			return employeeId;
		}

		@Override
		public DateInfo getDateInfo() {
			return new DateInfo(require.getAnyAggrPeriod(anyPeriodFrameCode).getPeriod());
		}
	}

	public interface Require {
		AnyAggrPeriod getAnyAggrPeriod(AnyAggrFrameCode anyPeriodFrameCode);

		AttendanceTimeOfAnyPeriod getAttendanceTimeOfAnyPeriod(String employeeId, String anyPeriodFrameCode);
	}
}
