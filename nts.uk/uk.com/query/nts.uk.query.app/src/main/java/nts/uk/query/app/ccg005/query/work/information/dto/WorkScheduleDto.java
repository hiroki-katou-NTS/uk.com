package nts.uk.query.app.ccg005.query.work.information.dto;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@Builder
public class WorkScheduleDto {
	// 社員ID
	private String sid;

	// 年月日
	private GeneralDate ymd;

	// 直帰区分
	private Integer backStraightAtr;

	// 直行区分
	private Integer goStraightAtr;

	// 出勤時刻
	private Integer attendanceTime;

	// 退勤時刻
	private Integer leaveTime;

	public static WorkScheduleDto toDto(WorkSchedule domain) {
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

	private static Integer getAttendanceTime(List<TimeLeavingWork> list) {
		Optional<TimeLeavingWork> timeLeaveWork = list.stream().filter(item -> item.getWorkNo().v() == 1).findAny();
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
		Optional<TimeLeavingWork> timeLeaveWork = list.stream().filter(item -> item.getWorkNo().v() == 1).findAny();
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
