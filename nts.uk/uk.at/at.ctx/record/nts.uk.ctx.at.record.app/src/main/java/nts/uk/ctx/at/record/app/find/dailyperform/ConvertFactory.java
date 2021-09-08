package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.monthly.MonthlyRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.app.find.monthly.week.WeeklyRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.app.find.anyperiod.AnyPeriodRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.converter.WeeklyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;

@Stateless
public class ConvertFactory implements AttendanceItemConvertFactory {
	
	@Inject
	@Getter
	private OptionalItemRepository optionalItemRepo;

	@Override
	public DailyRecordToAttendanceItemConverter createDailyConverter() {
		return DailyRecordToAttendanceItemConverterImpl.builder(optionalItemRepo).completed();
	}

	@Override
	public DailyRecordToAttendanceItemConverter createDailyConverter(Map<Integer, OptionalItem> optionalItems) {
		return DailyRecordToAttendanceItemConverterImpl.builder(optionalItems, optionalItemRepo).completed();
	}

	@Override
	public MonthlyRecordToAttendanceItemConverter createMonthlyConverter() {
		return MonthlyRecordToAttendanceItemConverterImpl.builder(optionalItemRepo).completed();
	}

	@Override
	public AnyPeriodRecordToAttendanceItemConverter createOptionalItemConverter() {
		return AnyPeriodRecordToAttendanceItemConverterImpl.builder(optionalItemRepo).completed();
	}

	@Override
	public WeeklyRecordToAttendanceItemConverter createWeeklyConverter() {
		return new WeeklyRecordToAttendanceItemConverterImpl(this);
	}
}
