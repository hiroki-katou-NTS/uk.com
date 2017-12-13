package nts.uk.ctx.at.shared.app.find.attendanceitem.daily;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto.AttendanceTimeDailyPerformDto;

/** 日別実績の勤怠時間 finder */
@Stateless
public class DailyAttendanceItemFinder {

	public AttendanceTimeDailyPerformDto find(){
		//TODO: get 日別実績の勤怠時間 and convert to dto
		return new AttendanceTimeDailyPerformDto();
	}
}
