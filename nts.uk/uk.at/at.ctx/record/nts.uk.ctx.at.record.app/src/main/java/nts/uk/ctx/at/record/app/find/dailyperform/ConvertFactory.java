package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.monthly.MonthlyRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;

@Stateless
public class ConvertFactory implements AttendanceItemConvertFactory {
	
	@Inject
	private OptionalItemRepository optionalItem;

	@Override
	public DailyRecordToAttendanceItemConverter createDailyConverter() {
		return DailyRecordToAttendanceItemConverterImpl.builder(optionalItem).completed();
	}

	@Override
	public DailyRecordToAttendanceItemConverter createDailyConverter(Map<Integer, OptionalItem> optionalItems) {
		return DailyRecordToAttendanceItemConverterImpl.builder(optionalItems).completed();
	}

	@Override
	public MonthlyRecordToAttendanceItemConverter createMonthlyConverter() {
		return MonthlyRecordToAttendanceItemConverterImpl.builder(optionalItem).completed();
	}
}
