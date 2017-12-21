package nts.uk.ctx.at.shared.app.find.calculation.holiday.time;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalc;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalcHoliday;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalcHolidayRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayHolidayAtten;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.WeekdayHoliday;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class OverdayCalcFinder {
	
@Inject
private OverdayCalcHolidayRepository repository; 

public List<OverdayCalcDto> findAllCalc() {
	String companyId = AppContexts.user().companyId();
	return repository.findByCompanyId(companyId).stream().map(e -> {
		return convertToDbType(e);
	}).collect(Collectors.toList());
}

private OverdayCalcDto convertToDbType(OverdayCalc overdayCalc) {
	
	OverdayCalcDto overdayCalcDto = new OverdayCalcDto();
	overdayCalcDto.setCalcOverDayEnd(overdayCalc.getCalcOverDayEnd());
	overdayCalcDto.setStatutoryHd(overdayCalc.getStatutoryHd());
	overdayCalcDto.setExcessHd(overdayCalc.getExcessHd());
	overdayCalcDto.setExcessSpecialHoliday(overdayCalc.getExcessSpecialHoliday());
	overdayCalcDto.setWeekDayStatutoryHd(overdayCalc.getWeekDayStatutoryHd());
	overdayCalcDto.setExcessStatutoryHd(overdayCalc.getExcessStatutoryHd());
	overdayCalcDto.setExcessStatutorSphd(overdayCalc.getExcessStatutorSphd());
	overdayCalcDto.setWeekDayLegalHd(overdayCalc.getWeekDayLegalHd());
	overdayCalcDto.setExcessLegalHd(overdayCalc.getExcessLegalHd());
	overdayCalcDto.setExcessLegalSphd(overdayCalc.getExcessLegalSphd());
	overdayCalcDto.setWeekDayPublicHd(overdayCalc.getWeekDayPublicHd());
	overdayCalcDto.setExcessPublicHd(overdayCalc.getExcessPublicHd());
	overdayCalcDto.setExcessPublicSphd(overdayCalc.getExcessPublicSphd());
	overdayCalcDto.setWeekdayHoliday(overdayCalc.getWeekdayHoliday().stream().map(c -> convertToDbTypeWeekdayHoliday(c)).collect(Collectors.toList()));
	overdayCalcDto.setOverdayHolidayAtten(overdayCalc.getOverdayHolidayAtten().stream().map(c -> convertToDbTypeOverdayHolidayAtten(c)).collect(Collectors.toList()));
	overdayCalcDto.setOverdayCalcHoliday(overdayCalc.getOverdayCalcHoliday().stream().map(c -> convertToDbTypeOverdayCalcHoliday(c)).collect(Collectors.toList()));
	return overdayCalcDto;
}

/**
 * Convert to Database Overday Holiday Atten
 * @param holidayAtten
 * @return
 */
private OverdayHolidayAttenDto convertToDbTypeOverdayHolidayAtten(OverdayHolidayAtten holidayAtten) {
	if (holidayAtten == null) {
		return null;
	}
	OverdayHolidayAttenDto overdayHolidayAttenDto = new OverdayHolidayAttenDto();
	overdayHolidayAttenDto.setHolidayWorkFrameNo(holidayAtten.getHolidayWorkFrameNo());
	overdayHolidayAttenDto.setOverWorkNo(holidayAtten.getOverWorkNo());
	return overdayHolidayAttenDto;
}

/**
 * Convert to Database Overday Calc Holiday
 * @param overdayCalcHoliday
 * @return
 */
private OverdayCalcHolidayDto convertToDbTypeOverdayCalcHoliday(OverdayCalcHoliday overdayCalcHoliday) {
	if (overdayCalcHoliday == null) {
		return null;
	}
	OverdayCalcHolidayDto overdayHolidayDto = new OverdayCalcHolidayDto();
	overdayHolidayDto.setHolidayWorkFrameNo(overdayCalcHoliday.getHolidayWorkFrameNo());
	overdayHolidayDto.setCalcOverDayEnd(overdayCalcHoliday.getCalcOverDayEnd());
	overdayHolidayDto.setStatutoryHd(overdayCalcHoliday.getStatutoryHd());
	overdayHolidayDto.setExcessHd(overdayCalcHoliday.getExcessHd());
	return overdayHolidayDto;
}

/**
 * Convert to Database Weekday Holiday
 * @param weekdayHoliday
 * @return
 */
private WeekdayHolidayDto convertToDbTypeWeekdayHoliday(WeekdayHoliday weekdayHoliday){
	if (weekdayHoliday == null) {
		return null;
	}
	WeekdayHolidayDto weekdayHolidayDto = new WeekdayHolidayDto();
	weekdayHolidayDto.setOverworkFrameNo(weekdayHoliday.getOverworkFrameNo());
	weekdayHolidayDto.setWeekdayNo(weekdayHoliday.getWeekdayNo());
	weekdayHolidayDto.setExcessHolidayNo(weekdayHoliday.getExcessHolidayNo());
	weekdayHolidayDto.setExcessSphdNo(weekdayHoliday.getExcessSphdNo());
	return weekdayHolidayDto;
}
}
