package nts.uk.ctx.alarm.dom.byemployee.check.checkers.master;

import java.util.ArrayList;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.service.CheckNotExistAnnualLeaveTable;
import nts.uk.ctx.alarm.dom.byemployee.check.result.DateInfo;
import nts.uk.ctx.at.shared.dom.workingcondition.service.GetNotExistWorkType;

/**
 *固定のチェック条件(社員別・マスタ)
 */
@RequiredArgsConstructor
public enum FixLogicMasterByEmployee {

	年休付与テーブル確認(2, c -> checkAnnualLeaveBasicInfo(c)),

	平日時勤務種類確認(3, c -> checkWeekdayWorkType(c)),

	休出時勤務種類確認(5, c -> checkHolidayWorkWorkType(c)),

	;

	public final int value;

	private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;

	public Iterable<AlarmRecordByEmployee> check(
			RequireCheck require,
			String employeeId,
			String message) {

		val contex = new Context(require, employeeId, message);
		return logic.apply(contex);
	}

	private static Iterable<AlarmRecordByEmployee> checkAnnualLeaveBasicInfo(Context context){
		val result = new ArrayList<AlarmRecordByEmployee>();
		if(CheckNotExistAnnualLeaveTable.check(context.require, context.employeeId)){
			result.add(context.alarm());
		}
		return result;
	}

	private static Iterable<AlarmRecordByEmployee> checkWeekdayWorkType(Context context){
		val checkResults = GetNotExistWorkType.getByWeekDay(context.require, context.employeeId);
		return () -> checkResults
				.entrySet()
				.stream()
				.map(pWithw -> context.alarm(pWithw.getKey()))
				.iterator();
	}

	private static Iterable<AlarmRecordByEmployee> checkHolidayWorkWorkType(Context context){
		val checkResults = GetNotExistWorkType.getByHolidayWork(context.require, context.employeeId);
		return () -> checkResults
				.entrySet()
				.stream()
				.map(pWithw -> context.alarm(pWithw.getKey()))
				.iterator();
	}

	private String getName() {
		return "チェック項目名";
	}

	private String getAlarmCondition() {
		return "アラーム条件";
	}

	@Value
	private class Context {
		RequireCheck require;
		String employeeId;
		String message;

		public AlarmRecordByEmployee alarm(DatePeriod period) {
			return new AlarmRecordByEmployee(
					employeeId,
					new DateInfo(period),
					AlarmListCategoryByEmployee.MASTER,
					getName(),
					getAlarmCondition(),
					message);
		}

		public AlarmRecordByEmployee alarm(GeneralDate date) {
			return new AlarmRecordByEmployee(
					employeeId,
					new DateInfo(date),
					AlarmListCategoryByEmployee.MASTER,
					getName(),
					getAlarmCondition(),
					message);
		}

		public AlarmRecordByEmployee alarm() {
			return new AlarmRecordByEmployee(
					employeeId,
					DateInfo.none(),
					AlarmListCategoryByEmployee.MASTER,
					getName(),
					getAlarmCondition(),
					message);
		}
	}

	public interface RequireCheck extends
			GetNotExistWorkType.Require,
			CheckNotExistAnnualLeaveTable.Require {
	}
}
