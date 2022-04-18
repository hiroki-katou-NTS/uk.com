package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily;

import java.util.Optional;
import java.util.function.Function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.GetFirstLeaveStampService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.GetLastLeaveStampService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 条件値チェック(社員別・日次)
 */
@RequiredArgsConstructor
public enum ConditionValueDailyByEmployee implements ConditionValueLogic<ConditionValueDailyByEmployee.Context>{

	インターバル時間(1,"インターバル時間", c ->{
		val yesterDayLastLeave = GetLastLeaveStampService.get(c.require, c.employeeId, c.date)
				//前日勤務をしてないということなので、差分を取ってもアラームに引っかからなさそうな時刻にしておく。
				.orElse(new TimeWithDayAttr(0))
				.backByMinutes(1440);
		val toDayFirstAttendance = GetFirstLeaveStampService.get(c.require, c.employeeId, c.date)
				//当日の勤務をしてないってことなので、差分を取ってもアラームに引っかからなさそうな時刻にしておく。
				.orElse(new TimeWithDayAttr(1440));
		return (double) (toDayFirstAttendance.valueAsMinutes() - yesterDayLastLeave.valueAsMinutes());
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
		GeneralDate date;
		
		@Override
		public AlarmListCategoryByEmployee getCategory() {
			return AlarmListCategoryByEmployee.RECORD_DAILY;
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
	
	public interface Require extends GetLastLeaveStampService.Require,
															GetFirstLeaveStampService.Require{
		Optional<IntegrationOfDaily> getIntegrationOfDailyRecord(String employeeId, GeneralDate date);
	}
}
