package nts.uk.ctx.at.record.dom.attendanceitem.util;

import java.util.Map;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;

public interface AttendanceItemConvertFactory {

	DailyRecordToAttendanceItemConverter createDailyConverter();
	
	DailyRecordToAttendanceItemConverter createDailyConverter(Map<Integer, OptionalItem> optionalItems);
	
	MonthlyRecordToAttendanceItemConverter createMonthlyConverter();
}
