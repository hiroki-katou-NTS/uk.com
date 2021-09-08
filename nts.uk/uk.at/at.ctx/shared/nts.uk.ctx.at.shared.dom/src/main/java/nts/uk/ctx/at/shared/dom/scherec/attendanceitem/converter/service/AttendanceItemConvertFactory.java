package nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service;

import java.util.Map;

import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.converter.WeeklyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;

public interface AttendanceItemConvertFactory {

	DailyRecordToAttendanceItemConverter createDailyConverter();
	
	DailyRecordToAttendanceItemConverter createDailyConverter(Map<Integer, OptionalItem> optionalItems);
	
	MonthlyRecordToAttendanceItemConverter createMonthlyConverter();
	
	WeeklyRecordToAttendanceItemConverter createWeeklyConverter();

	AnyPeriodRecordToAttendanceItemConverter createOptionalItemConverter();
	
	OptionalItemRepository getOptionalItemRepo();
}
