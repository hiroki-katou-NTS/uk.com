package nts.uk.ctx.at.record.app.find.dailyperform;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.monthly.MonthlyRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter.MonthlyRecordToAttendanceItemConverter;

@Stateless
public class ConvertFactory implements AttendanceItemConvertFactory {

	@Override
	public DailyRecordToAttendanceItemConverter createDailyConverter() {
		return DailyRecordToAttendanceItemConverterImpl.builder().completed();
	}

	@Override
	public MonthlyRecordToAttendanceItemConverter createMonthlyConverter() {
		return MonthlyRecordToAttendanceItemConverterImpl.builder().completed();
	}
}
