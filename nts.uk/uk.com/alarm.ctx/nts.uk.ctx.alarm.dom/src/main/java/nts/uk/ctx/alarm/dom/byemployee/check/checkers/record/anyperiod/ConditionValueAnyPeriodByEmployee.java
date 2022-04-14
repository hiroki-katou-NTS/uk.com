package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.anyperiod;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
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
		return (totalAttendanceDays / getPeriod(c).stream().count()) * 100;
	}),

	休日休暇率(2, "休日・休暇率", c -> {
		// 現状、休暇の集計を行っていないため、渋々日次データを参照する。
		double totalRestDays = 0.0;
		val dailyRecords = c.require.getIntegrationOfDailyRecords(c.employeeId, getPeriod(c));
		for (IntegrationOfDaily rec : dailyRecords) {
			val workStyle = rec.getWorkInformation().getWorkStyle(c.require, AppContexts.user().companyId());
			switch (workStyle.get()){
				case ONE_DAY_REST:
					totalRestDays = totalRestDays + 1.0;
				case MORNING_WORK:
				case AFTERNOON_WORK:
					totalRestDays = totalRestDays + 0.5;
				default:
			}
		}
		return (totalRestDays / getPeriod(c).stream().count()) * 100;
	})

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

	/**
	 * 任意期間の期間を取得する
	 * @param context
	 * @return
	 */
	private static DatePeriod getPeriod(Context context){
		return context.require.getAnyAggrPeriod(context.anyPeriodFrameCode).getPeriod();
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

	public interface Require extends WorkInfoOfDailyAttendance.Require {
		AnyAggrPeriod getAnyAggrPeriod(AnyAggrFrameCode anyPeriodFrameCode);

		AttendanceTimeOfAnyPeriod getAttendanceTimeOfAnyPeriod(String employeeId, String anyPeriodFrameCode);

		List<IntegrationOfDaily> getIntegrationOfDailyRecords(String employeeId, DatePeriod period);
	}
}
