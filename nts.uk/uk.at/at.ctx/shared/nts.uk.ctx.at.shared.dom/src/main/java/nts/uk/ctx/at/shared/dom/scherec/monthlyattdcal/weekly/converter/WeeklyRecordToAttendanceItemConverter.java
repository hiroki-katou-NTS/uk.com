package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.converter;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;

/**
 * 週別実績の勤怠時間Converter
 */
public interface WeeklyRecordToAttendanceItemConverter extends AttendanceItemConverter {
	
	WeeklyRecordToAttendanceItemConverter withAttendanceTime(AttendanceTimeOfWeekly domain);
	
	Optional<AttendanceTimeOfWeekly> toAttendanceTime();
}
