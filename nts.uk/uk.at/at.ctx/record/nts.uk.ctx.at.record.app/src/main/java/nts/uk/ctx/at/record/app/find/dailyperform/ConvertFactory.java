package nts.uk.ctx.at.record.app.find.dailyperform;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;

@Stateless
public class ConvertFactory implements AttendanceItemConvertFactory {

	@Override
	public DailyRecordToAttendanceItemConverter createDailyConverter() {
		return DailyRecordToAttendanceItemConverterImpl.builder().completed();
	}

}
