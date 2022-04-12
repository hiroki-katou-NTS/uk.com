package nts.uk.ctx.alarm.dom.byemployee.check.checkers.master;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.service.CheckNotExistAnnualLeaveTable;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workingcondition.service.GetNotExistWorkTime;
import nts.uk.ctx.at.shared.dom.workingcondition.service.GetNotExistWorkType;

/**
 *固定のチェック条件(社員別・マスタ)
 */
@RequiredArgsConstructor
public enum FixLogicMasterByEmployee {

	社員のカードNO確認(1, c -> checkCardNumberByEmployee(c)),

	年休付与テーブル確認(2, c -> checkAnnualLeaveBasicInfo(c)),

	平日時勤務種類確認(3, c -> checkWorkInfo(
			c, (con) -> GetNotExistWorkType.getByWeekDay(con.require, con.employeeId))),

	平日時就業時間帯確認(4, c -> checkWorkInfo(
			c, (con) -> GetNotExistWorkTime.getByWeekDay(con.require, con.employeeId))),

	休出時勤務種類確認(5, c -> checkWorkInfo(
			c, (con) -> GetNotExistWorkType.getByHolidayWork(con.require, con.employeeId))),

	休出時就業時間帯確認(6, c -> checkWorkInfo(
			c, (con) -> GetNotExistWorkTime.getByHolidayWork(con.require, con.employeeId))),

	休日時勤務種類確認(7, c -> checkWorkInfo(
			c, (con) -> GetNotExistWorkType.getByHoliday(con.require, con.employeeId))),

	作業１確認(9, c -> checkTaskAssign(c, new TaskFrameNo(1))),

	作業２確認(10, c -> checkTaskAssign(c, new TaskFrameNo(2))),

	作業３確認(11, c -> checkTaskAssign(c, new TaskFrameNo(3))),

	作業４確認(12, c -> checkTaskAssign(c, new TaskFrameNo(4))),

	作業５確認(13, c -> checkTaskAssign(c, new TaskFrameNo(5))),

	;

	public final int value;

	private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;

	public Iterable<AlarmRecordByEmployee> check(
			RequireCheck require,
			String employeeId,
			AlarmListAlarmMessage message) {

		val contex = new Context(require, employeeId, message);
		return logic.apply(contex);
	}

	private static Iterable<AlarmRecordByEmployee> checkCardNumberByEmployee(Context context) {
		val result = new ArrayList<AlarmRecordByEmployee>();
		if(context.require.getStampCard(context.employeeId).isEmpty()){
			result.add(context.alarm());
		}
		return result;
	}

	private static Iterable<AlarmRecordByEmployee> checkAnnualLeaveBasicInfo(Context context){
		val result = new ArrayList<AlarmRecordByEmployee>();
		if(CheckNotExistAnnualLeaveTable.check(context.require, context.employeeId)){
			result.add(context.alarm());
		}
		return result;
	}

	private static Iterable<AlarmRecordByEmployee> checkWorkInfo(
			Context context,
			Function<Context, Map<DatePeriod, String>> checker) {
		val checkResults = checker.apply(context);
		return () -> checkResults
				.entrySet()
				.stream()
				.map(pWithw -> context.alarm(pWithw.getKey()))
				.iterator();
	}

	private static Iterable<AlarmRecordByEmployee> checkTaskAssign(Context context, TaskFrameNo frameNo){
		val result = new ArrayList<AlarmRecordByEmployee>();
		val optTaskAssign = context.require.getTaskAssign(context.employeeId, frameNo);
		if(optTaskAssign.isPresent() && !context.require.existsTask(frameNo, optTaskAssign.get().getTaskCode())){
			result.add(context.alarm());
		}
		return result;

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
		AlarmListAlarmMessage message;

		public AlarmRecordByEmployee alarm(DatePeriod period) {
			return new AlarmRecordByEmployee(
					employeeId,
					new DateInfo(period),
					AlarmListCategoryByEmployee.MASTER,
					getName(),
					getAlarmCondition(),
					"",
					message);
		}

		public AlarmRecordByEmployee alarm(GeneralDate date) {
			return new AlarmRecordByEmployee(
					employeeId,
					new DateInfo(date),
					AlarmListCategoryByEmployee.MASTER,
					getName(),
					getAlarmCondition(),
					"",
					message);
		}

		public AlarmRecordByEmployee alarm() {
			return new AlarmRecordByEmployee(
					employeeId,
					DateInfo.none(),
					AlarmListCategoryByEmployee.MASTER,
					getName(),
					getAlarmCondition(),
					"",
					message);
		}
	}

	public interface RequireCheck extends
			CheckNotExistAnnualLeaveTable.Require,
			GetNotExistWorkType.Require,
			GetNotExistWorkTime.Require {
		List<StampCard> getStampCard(String employeeId);

		Optional<TaskAssignEmployee> getTaskAssign(String employeeId, TaskFrameNo frameNo);

		boolean existsTask(TaskFrameNo taskFrameNo, TaskCode code);

		List<WorkLocation> getWorkLocation(String workLocationCode);
	}
}
