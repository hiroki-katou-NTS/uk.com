package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily.single;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.CheckDuplicateTimeSpan;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupGettingService;
import nts.uk.shr.com.context.AppContexts;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 固定のチェック条件(社員別・スケジュール日次)
 */
@RequiredArgsConstructor
public enum FixedLogicScheduleDailyByEmployee {

	スケジュール未作成(1, c -> checkNotCreated(c)),

	勤務種類未登録(2, c -> alarmToWorkSchedule(
			c, ws -> c.require.existsWorkType(ws.getWorkInfo().getRecordInfo().getWorkTypeCode().toString()))),

	就業時間帯未登録(3, c -> alarmToWorkSchedule(
			c, ws -> c.require.existsWorkTime(ws.getWorkInfo().getRecordInfo().getWorkTimeCode().toString()))),

	時間帯の重複(4, c -> alarmToWorkSchedule(
			c, ws -> CheckDuplicateTimeSpan.checkDuplicatePreviousAndNext(c.require, ws.getEmployeeID(), ws.getYmd()))),

	複数回勤務(5, c -> alarmToWorkSchedule(
			c, ws -> checkMultipleWork(ws))),

	特定日勤務(6, c -> alarmToWorkSchedule(
			c, ws -> checkSpecificDay(c, ws))),

	スケジュール未確定(7, c -> alarmToWorkSchedule(
			c, ws -> ws.getConfirmedATR().equals(ConfirmedATR.UNSETTLED))),
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
			AlarmListAlarmMessage message) {

		val context = new Context(require, employeeId, checkingPeriod, message);

		return logic.apply(context);
	}

	/**
	 * スケジュール未作成かチェックする
	 * @param context
	 * @return
	 */
	private static Iterable<AlarmRecordByEmployee> checkNotCreated(Context context){
		return () -> context.period.datesBetween().stream()
				.filter(date -> !context.require.existsWorkSchedule(context.employeeId, date))
				.map(date -> context.alarm(date))
				.iterator();
	}

	/**
	 * 複数回勤務か
	 * @param schedule
	 * @return
	 */
	private static boolean checkMultipleWork(WorkSchedule schedule){
		return schedule.getOptTimeLeaving().get().getTimeLeavingWorks().size() > 1;
	}

	/**
	 * 特定日勤務か
	 * @param context
	 * @param schedule
	 * @return
	 */
	private static boolean checkSpecificDay(Context context, WorkSchedule schedule){
		// 出勤系かチェック
		if(schedule.getWorkInfo().getRecordInfo().isAttendanceRate(context.require, AppContexts.user().companyId())){
			// 所属を取得
			val orgIdenInfo = GetTargetIdentifiInforService.get(context.require, schedule.getYmd(), schedule.getEmployeeID());
			// 特定日かチェック
			return DateInformation.create(context.require, schedule.getYmd(), orgIdenInfo).isSpecificDay();
		}
		return false;
	}

	/**
	 * 勤務予定(WorkSchedule）からアラームを発生させるメソッド
	 * @param <T>
	 * @param context
	 * @param checker
	 * @return
	 */
	private static <T> Iterable<AlarmRecordByEmployee> alarmToWorkSchedule(
			Context context,
			Function<WorkSchedule, Boolean> checker) {
		return alarm(context, (date) -> {
			return context.require.getWorkSchedule(context.employeeId, date)
					.map(bs -> checker.apply(bs)).orElse(false);
		});
	}

	/**
	 * 日付からアラームを発生させるメソッド
	 * @param <T>
	 * @param context
	 * @param checker
	 * @return
	 */
	private static <T> Iterable<AlarmRecordByEmployee> alarm(
			Context context,
			Predicate<GeneralDate> checker) {
		return () -> context.period.stream()
				.filter(checker)
				.map(date -> context.alarm(date))
				.iterator();
	}

	private String getName() {
		return "チェック項目名";
	}

	@Value
	private class Context {
		RequireCheck require;
		String employeeId;
		DatePeriod period;
		AlarmListAlarmMessage message;

		public AlarmRecordByEmployee alarm(GeneralDate date) {
			return new AlarmRecordByEmployee(
					employeeId,
					new DateInfo(date),
					AlarmListCategoryByEmployee.SCHEDULE_DAILY,
					getName(),
					"",
					"",
					message);
		}
	}

	public interface RequireCheck extends
			CheckDuplicateTimeSpan.Require,
			GetTargetIdentifiInforService.Require,
			DateInformation.Require,
			WorkInformation.Require{
		boolean existsWorkSchedule(String employeeId, GeneralDate date);

		Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date);

		boolean existsWorkType(String workTypeCode);

		boolean existsWorkTime(String workTimeCode);
	}
}
