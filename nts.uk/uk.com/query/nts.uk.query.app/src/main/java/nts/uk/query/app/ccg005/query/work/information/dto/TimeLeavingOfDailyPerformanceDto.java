package nts.uk.query.app.ccg005.query.work.information.dto;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.shr.com.time.TimeWithDayAttr;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.日別実績勤務
 */

@Builder
@Data
public class TimeLeavingOfDailyPerformanceDto {
	
	//年月日
	private GeneralDate ymd;
	
	//社員ID
	private String sid;
	
	// 出勤時刻
	private Integer attendanceTime;

	// 退勤時刻
	private Integer leaveTime;
	
	public static TimeLeavingOfDailyPerformanceDto toDto (TimeLeavingOfDailyPerformance domain) {
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
