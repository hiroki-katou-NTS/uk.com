package nts.uk.ctx.at.record.dom.dailyresult.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別実績.実績データから在席状態を判断する.実績データから在席状態を判断する
 */
public class JudgingStatusDomainService {
	
	private JudgingStatusDomainService() {}

	//出勤
	private static final String WORK = "出勤";
	
	//休み
	private static final String NOT_WORK = "休み";
	
	private static Optional<Integer> defineAttendanceTime(Optional<TimeLeavingWork> time) {
		if(time.isPresent()) {
			Optional<TimeActualStamp> attendance = time.get().getAttendanceStamp();
			if(attendance.isPresent()) {
				Optional<WorkStamp> stamp = attendance.get().getStamp();
				if(stamp.isPresent()) {
					Optional<TimeWithDayAttr> timeWithDay = stamp.get().getTimeDay().getTimeWithDay();
					if(timeWithDay.isPresent()) {
						return Optional.ofNullable(timeWithDay.get().v());
					}
				}
			}
		}
		return Optional.empty();
	}
	
	private static Optional<Integer> defineLeaveTime(Optional<TimeLeavingWork> time) {
		if(time.isPresent()) {
			Optional<TimeActualStamp> attendance = time.get().getLeaveStamp();
			if(attendance.isPresent()) {
				Optional<WorkStamp> stamp = attendance.get().getStamp();
				if(stamp.isPresent()) {
					Optional<TimeWithDayAttr> timeWithDay = stamp.get().getTimeDay().getTimeWithDay();
					if(timeWithDay.isPresent()) {
						return Optional.ofNullable(timeWithDay.get().v());
					}
				}
			}
		}
		return Optional.empty();
	}
	
	private static Optional<String> defineWorkDivision(Optional<Boolean> workingNow) {
		if(workingNow.isPresent()) {
			return workingNow.get() ? Optional.ofNullable(WORK) : Optional.ofNullable(NOT_WORK);
		}
		return Optional.empty();
	}
	
	private static Optional<Boolean> defineDirectDivision(Optional<WorkInfoOfDailyPerformance> dailyActualWorkInfo) {
		if(dailyActualWorkInfo.isPresent()) {
			return Optional.ofNullable(dailyActualWorkInfo.get().getWorkInformation().getGoStraightAtr().value == 1);
		}
		return Optional.empty();
	}
	
	public static AttendanceAccordActualData JudgingStatus(Require rq, String sid) {

		GeneralDate baseDate = GeneralDate.today();

		// 日別実績の勤務情報を取得する
		Optional<WorkInfoOfDailyPerformance> dailyActualWorkInfo = rq.getDailyActualWorkInfo(sid, baseDate);

		// 日別実績の出退勤を取得する
		Optional<TimeLeavingOfDailyPerformance> dailyAttendanceAndDeparture = rq.getDailyAttendanceAndDeparture(sid,
				baseDate);
		Optional<String> workTypeCode = Optional.empty();

		// 日別勤務予定を取得する
		if (dailyActualWorkInfo.isPresent()) {
			workTypeCode = Optional
					.ofNullable(dailyActualWorkInfo.get().getWorkInformation().getRecordInfo().getWorkTypeCode().v());
		} else {
			workTypeCode = rq.getDailyWorkScheduleWorkTypeCode(sid, baseDate);
		}

		// 勤務種類を取得する
		Optional<Boolean> workingNow = Optional.empty();
		if (workTypeCode.isPresent()) {
			Optional<WorkType> workType = rq.getWorkType(workTypeCode.get());
			if (workType.isPresent()) {
				workingNow = Optional.ofNullable(judgePresenceStatus(workType.get().getDailyWork()));
			}
		}
		
		// 作成する
		// $出勤時刻
		Optional<Integer> attendanceTime = Optional.empty();
		
		// $退勤時刻
		Optional<Integer> leaveTime = Optional.empty();

		// $勤務区分
		Optional<String> workDivision = Optional.empty();

		// $直行区分
		Optional<Boolean> directDivision = Optional.empty();
		
		if (dailyAttendanceAndDeparture.isPresent()) {
			Optional<TimeLeavingWork> time = dailyAttendanceAndDeparture.get().getAttendance().getTimeLeavingWorks()
					.stream().filter(fil -> fil.getWorkNo().v() == 1).findFirst();
			attendanceTime = defineAttendanceTime(time);
			leaveTime = defineLeaveTime(time);
		}
		workDivision = defineWorkDivision(workingNow);
		directDivision = defineDirectDivision(dailyActualWorkInfo);
		
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
		
		// case 1
		if (leaveTime.isPresent() && leaveTime.get() <= now) {
			return buildData(StatusClassfication.GO_HOME, workingNow);
		}
		
		if (leaveTime.isPresent()) {
			if (attendanceTime.isPresent() && attendanceTime.get() <= now) {
				// case 2
				if (directDivision.isPresent() && directDivision.get().booleanValue()) {
					return buildData(StatusClassfication.GO_OUT, workingNow);
				}
				
				// case 3
				return buildData(StatusClassfication.PRESENT, workingNow);
			}
			
			// case 4
			if (attendanceTime.isPresent()) {
				return buildData(StatusClassfication.NOT_PRESENT, workingNow);
			}
			
			// case 5
			return buildData(StatusClassfication.NOT_PRESENT, workingNow);
		}
		
		if (attendanceTime.isPresent() && attendanceTime.get() <= now) {
			// case 6
			if (directDivision.isPresent() && directDivision.get().booleanValue()) {
				return buildData(StatusClassfication.GO_OUT, workingNow);
			}
			// case 7
			return buildData(StatusClassfication.PRESENT, workingNow);
		}
		
		// case 8
		if (attendanceTime.isPresent()) {
			return buildData(StatusClassfication.NOT_PRESENT, workingNow);
		} 
		
		// case 9
		if (!workDivision.isPresent()) {
			return buildData(StatusClassfication.NOT_PRESENT, workingNow);
		}
		
		// case 10
		if (workDivision.get().equals(WORK)) {
			return buildData(StatusClassfication.NOT_PRESENT, workingNow);
		}
		
		// case 11
		return buildData(StatusClassfication.HOLIDAY, workingNow);
	}
	
	private static AttendanceAccordActualData buildData(StatusClassfication statusClassfication, Optional<Boolean> workingNow) {
		return AttendanceAccordActualData.builder()
				.attendanceState(statusClassfication)
				.workingNow(workingNow)
				.build();
	}

	private static boolean judgePresenceStatus(DailyWork daily) {
		// 勤務区分の判断方法
		List<WorkTypeClassification> notIn = new ArrayList<>();
		notIn.add(WorkTypeClassification.Attendance);
		notIn.add(WorkTypeClassification.HolidayWork);
		notIn.add(WorkTypeClassification.Shooting);
		notIn.add(WorkTypeClassification.ContinuousWork);
		
		// 1日場合、「出勤、休日出勤、振出、連続勤務」 → 出勤 || その他 → 休み
		if (daily.getWorkTypeUnit() == WorkTypeUnit.OneDay && !notIn.stream().anyMatch(item -> item == daily.getOneDay())) {
			return false;
		}
		// 午前と午後の場合、午前が休み AND 午後が休み → 休み || その他 → 出勤
		if (daily.getWorkTypeUnit() == WorkTypeUnit.MonringAndAfternoon && !notIn.stream().anyMatch(item -> item == daily.getMorning())
				&& !notIn.stream().anyMatch(item -> item == daily.getAfternoon())) {
			return false;
		}
		return true;
	}

	public interface Require {

		/**
		 * [R-1]日別実績の勤務情報を取得する
		 * 
		 * 日別実績の勤務情報Repository.取得する(社員ID,年月日)
		 * 
		 * @param sid      社員ID
		 * @param baseDate 年月日
		 */
		public Optional<WorkInfoOfDailyPerformance> getDailyActualWorkInfo(String sid, GeneralDate baseDate);

		/**
		 * [R-2] 日別実績の出退勤を取得する
		 * 
		 * 日別実績の出退勤Repository.取得する(社員ID,年月日)
		 * 
		 * @param sid      社員ID
		 * @param baseDate 年月日
		 */
		public Optional<TimeLeavingOfDailyPerformance> getDailyAttendanceAndDeparture(String sid, GeneralDate baseDate);

		/**
		 * [R-3] 日別勤務予定を取得する
		 * 
		 * 日別勤務予定を取得するAdapter.取得する(社員ID,年月日)
		 * 
		 * @param sid      社員ID
		 * @param baseDate 年月日
		 */
		public Optional<String> getDailyWorkScheduleWorkTypeCode(String sid, GeneralDate baseDate);

		/**
		 * [R-4] 勤務種類を取得する
		 * 
		 * 勤務種類Repository.取得する(ログイン会社ID、コード)
		 * 
		 * @param loginCid ログイン会社ID
		 * @param code     コード
		 */
		public Optional<WorkType> getWorkType(String code);
	}
}
