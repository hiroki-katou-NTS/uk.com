package nts.uk.ctx.at.shared.app.find.calculation.holiday.time;

/**
 * @author phongtq
 */
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.calculation.holiday.time.ZeroTime;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.HdFromHd;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.ZeroTimeRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.HdFromWeekday;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.WeekdayHoliday;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ZeroTimeFinder {

	@Inject
	private ZeroTimeRepository repository;

	/**
	 * Find all Zero Time
	 * 
	 * @return
	 */
	public List<ZeroTimeDto> findAllCalc() {
		String companyId = AppContexts.user().companyId();
		return repository.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	/**
	 * Convert to Database Zero Time
	 * 
	 * @param overdayCalc
	 * @return
	 */
	private ZeroTimeDto convertToDbType(ZeroTime overdayCalc) {

		ZeroTimeDto overdayCalcDto = new ZeroTimeDto();
			overdayCalcDto.setCalcFromZeroTime(overdayCalc.getCalcFromZeroTime());
			overdayCalcDto.setLegalHd(overdayCalc.getLegalHd());
			overdayCalcDto.setNonLegalHd(overdayCalc.getNonLegalHd());
			overdayCalcDto.setNonLegalPublicHd(overdayCalc.getNonLegalPublicHd());
			overdayCalcDto.setWeekday1(overdayCalc.getWeekday1());
			overdayCalcDto.setNonLegalHd1(overdayCalc.getNonLegalHd1());
			overdayCalcDto.setNonLegalPublicHd1(overdayCalc.getNonLegalPublicHd1());
			overdayCalcDto.setWeekday2(overdayCalc.getWeekday2());
			overdayCalcDto.setLegalHd2(overdayCalc.getLegalHd2());
			overdayCalcDto.setNonLegalHd2(overdayCalc.getNonLegalHd2());
			overdayCalcDto.setWeekday3(overdayCalc.getWeekday3());
			overdayCalcDto.setLegalHd3(overdayCalc.getLegalHd3());
			overdayCalcDto.setNonLegalPublicHd3(overdayCalc.getNonLegalPublicHd3());
			overdayCalcDto.setWeekdayHoliday(overdayCalc.getWeekdayHoliday().stream()
					.map(c -> convertToDbTypeWeekdayHoliday(c)).collect(Collectors.toList()));
			overdayCalcDto.setOverdayHolidayAtten(overdayCalc.getOverdayHolidayAtten().stream()
					.map(c -> convertToDbTypeOverdayHolidayAtten(c)).collect(Collectors.toList()));
			overdayCalcDto.setOverdayCalcHoliday(overdayCalc.getOverdayCalcHoliday().stream()
					.map(c -> convertToDbTypeOverdayCalcHoliday(c)).collect(Collectors.toList()));
		return overdayCalcDto;
	}

	/**
	 * Convert to Database Overday Holiday Atten
	 * 
	 * @param holidayAtten
	 * @return
	 */
	private HdFromWeekdayDto convertToDbTypeOverdayHolidayAtten(HdFromWeekday holidayAtten) {
		if (holidayAtten == null) {
			return null;
		}
		HdFromWeekdayDto overdayHolidayAttenDto = new HdFromWeekdayDto();
			overdayHolidayAttenDto.setHdFrameNo(holidayAtten.getHdFrameNo().v());
			overdayHolidayAttenDto.setOvertimeFrameNo(holidayAtten.getOvertimeFrameNo().v());
		return overdayHolidayAttenDto;
	}

	/**
	 * Convert to Database Overday Calc Holiday
	 * 
	 * @param overdayCalcHoliday
	 * @return
	 */
	private HdFromHdDto convertToDbTypeOverdayCalcHoliday(HdFromHd overdayCalcHoliday) {
		if (overdayCalcHoliday == null) {
			return null;
		}
		HdFromHdDto overdayHolidayDto = new HdFromHdDto();
			overdayHolidayDto.setOvertimeFrameNo(overdayCalcHoliday.getOvertimeFrameNo().v());
			overdayHolidayDto.setLegalHdNo(overdayCalcHoliday.getLegalHdNo().v());
			overdayHolidayDto.setNonLegalHdNo(overdayCalcHoliday.getNonLegalHdNo().v());
			overdayHolidayDto.setNonLegalPublicHdNo(overdayCalcHoliday.getNonLegalPublicHdNo().v());
		return overdayHolidayDto;
	}

	/**
	 * Convert to Database Weekday Holiday
	 * 
	 * @param weekdayHoliday
	 * @return
	 */
	private WeekdayHolidayDto convertToDbTypeWeekdayHoliday(WeekdayHoliday weekdayHoliday) {
		if (weekdayHoliday == null) {
			return null;
		}
		WeekdayHolidayDto weekdayHolidayDto = new WeekdayHolidayDto();
			weekdayHolidayDto.setOverTimeFrameNo(weekdayHoliday.getOverTimeFrameNo().v());
			weekdayHolidayDto.setLegalHdNo(weekdayHoliday.getLegalHdNo().v());
			weekdayHolidayDto.setNonLegalHdNo(weekdayHoliday.getNonLegalHdNo().v());
			weekdayHolidayDto.setNonLegalPublicHdNo(weekdayHoliday.getNonLegalPublicHdNo().v());
		return weekdayHolidayDto;
	}
}
