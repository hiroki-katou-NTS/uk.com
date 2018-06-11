package nts.uk.ctx.at.record.dom.attendanceitem.util;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;

public interface AttendanceItemConvertFactory {

	DailyRecordToAttendanceItemConverter createDailyConverter();
}
