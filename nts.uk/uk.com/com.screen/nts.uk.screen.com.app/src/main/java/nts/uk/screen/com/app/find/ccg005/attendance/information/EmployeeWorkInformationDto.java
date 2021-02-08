package nts.uk.screen.com.app.find.ccg005.attendance.information;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.office.dom.dto.DailyWorkDto;
import nts.uk.ctx.office.dom.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.office.dom.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.office.dom.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.ctx.office.dom.dto.WorkScheduleDto;
import nts.uk.ctx.office.dom.dto.WorkTypeDto;
import nts.uk.shr.com.time.TimeWithDayAttr;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.社員の勤務情報
 */
@Data
@Builder
public class EmployeeWorkInformationDto {

	//社員ID
	private String sid;
	
	//勤務予定
	private WorkScheduleDto workScheduleDto;
	
	//勤務種類
	private WorkTypeDto workTypeDto;
	
	//日別実績の出退勤
	private TimeLeavingOfDailyPerformanceDto timeLeavingOfDailyPerformanceDto;
	
	//日別実績の勤務情報
	private WorkInfoOfDailyPerformanceDto workPerformanceDto;
	
	//社員の日別実績エラー一覧
	private List<EmployeeDailyPerErrorDto> employeeDailyPerErrorDtos;
	
	public static DailyWorkDto dailyWorkToDto (DailyWork domain) {
		if(domain == null) {
			return DailyWorkDto.builder().build();
		}
		return DailyWorkDto.builder()
				.workTypeUnit(domain.getWorkTypeUnit().value)
				.oneDay(domain.getOneDay().value)
				.morning(domain.getMorning().value)
				.afternoon(domain.getAfternoon().value)
				.build();
	}
	
	public static EmployeeDailyPerErrorDto employeeDailyPerErrorToDto(EmployeeDailyPerError domain) {
		if (domain == null) {
			return EmployeeDailyPerErrorDto.builder().build();
		}
		return EmployeeDailyPerErrorDto.builder()
				.sid(domain.getEmployeeID())
				.ymd(domain.getDate())
				.errorAlarmWorkRecordCode(domain.getErrorAlarmWorkRecordCode().v())
				.build();
	}
	
	public static TimeLeavingOfDailyPerformanceDto timeLeavingOfDailyPerformanceToDto (TimeLeavingOfDailyPerformance domain) {
		if (domain == null) {
			return TimeLeavingOfDailyPerformanceDto.builder().build();
		}
		return TimeLeavingOfDailyPerformanceDto.builder()
				.ymd(domain.getYmd())
				.sid(domain.getEmployeeId())
				.attendanceTime(getAttendanceTime(domain.getAttendance().getTimeLeavingWorks()))
				.leaveTime(getLeaveTime(domain.getAttendance().getTimeLeavingWorks()))
				.build();
	}
	
	public static WorkInfoOfDailyPerformanceDto workInfoOfDailyPerformanceToDto (WorkInfoOfDailyPerformance domain) {
		if (domain == null) {
			return WorkInfoOfDailyPerformanceDto.builder().build();
		}
		return WorkInfoOfDailyPerformanceDto.builder()
				.sid(domain.getEmployeeId())
				.ymd(domain.getYmd())
				.goStraightAtr(domain.getWorkInformation().getGoStraightAtr().value)
				.backStraightAtr(domain.getWorkInformation().getBackStraightAtr().value)
				.build();
	}
	
	public static WorkScheduleDto workScheduleToDto(WorkSchedule domain) {
		if (domain == null) {
			return WorkScheduleDto.builder().build();
		}
		return WorkScheduleDto.builder().sid(domain.getEmployeeID()).ymd(domain.getYmd())
				.goStraightAtr(domain.getWorkInfo().getGoStraightAtr().value)
				.backStraightAtr(domain.getWorkInfo().getBackStraightAtr().value)
				.attendanceTime(domain.getOptTimeLeaving()
						.map(mapper -> getAttendanceTime(mapper.getTimeLeavingWorks())).orElse(null))
				.leaveTime(domain.getOptTimeLeaving().map(mapper -> getLeaveTime(mapper.getTimeLeavingWorks()))
						.orElse(null))
				.build();
	}
	
	
	public static WorkTypeDto workTypeToDto (WorkType domain) {
		if (domain == null) {
			return WorkTypeDto.builder().build();
		}
		return WorkTypeDto.builder()
				.dailyWork(dailyWorkToDto(domain.getDailyWork()))
				.code(domain.getWorkTypeCode().v())
				.displayName(domain.getName().v())
				.build();
	}
	
	private static Integer getAttendanceTime(List<TimeLeavingWork> list) {
		Optional<TimeLeavingWork> timeLeaveWork = list.stream().filter(item -> item.getWorkNo().v() == 1).findFirst();
		if (timeLeaveWork.isPresent()) {
			Optional<TimeActualStamp> attendanceStamp = timeLeaveWork.get().getAttendanceStamp();
			if (attendanceStamp.isPresent()) {
				Optional<WorkStamp> workStamp = attendanceStamp.get().getStamp();
				if (workStamp.isPresent()) {
					Optional<TimeWithDayAttr> timeWithDay = workStamp.get().getTimeDay().getTimeWithDay();
					return timeWithDay.map(q -> q.v()).orElse(null);
				}
			}
		}
		return null;
	}
	
	private static Integer getLeaveTime(List<TimeLeavingWork> list) {
		Optional<TimeLeavingWork> timeLeaveWork = list.stream().filter(item -> item.getWorkNo().v() == 1).findFirst();
		if (timeLeaveWork.isPresent()) {
			Optional<TimeActualStamp> leaveStamp = timeLeaveWork.get().getLeaveStamp();
			if (leaveStamp.isPresent()) {
				Optional<WorkStamp> workStamp = leaveStamp.get().getStamp();
				if (workStamp.isPresent()) {
					Optional<TimeWithDayAttr> timeWithDay = workStamp.get().getTimeDay().getTimeWithDay();
					return timeWithDay.map(q -> q.v()).orElse(null);
				}
			}
		}
		return null;
	}
}
