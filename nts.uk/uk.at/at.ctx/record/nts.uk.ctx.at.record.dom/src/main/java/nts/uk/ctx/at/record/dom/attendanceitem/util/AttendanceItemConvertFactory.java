package nts.uk.ctx.at.record.dom.attendanceitem.util;

import java.util.Map;

import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordToAttendanceItemConverter;

public interface AttendanceItemConvertFactory {

	DailyRecordToAttendanceItemConverter createDailyConverter();
	
	DailyRecordToAttendanceItemConverter createDailyConverter(Map<Integer, OptionalItem> optionalItems);
	
	MonthlyRecordToAttendanceItemConverter createMonthlyConverter();
}
