package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;

@Stateless
public class CalendarWorkplaceFinder {
	@Inject
	private CalendarWorkPlaceRepository calendarWorkPlaceRepository;
	
	public List<CalendarWorkplaceDto> getAllCalendarWorkplace(String workPlaceId){
		List<CalendarWorkplaceDto> list  = this.calendarWorkPlaceRepository.getAllCalendarWorkplace(workPlaceId)
				.stream()
				.map(c->CalendarWorkplaceDto.fromDomain(c))
				.collect(Collectors.toList());
		return list;
	}
	
	public List<Integer> getCalendarWorkPlaceSetByYear(String workPlaceId, String yearMonth){
		return this.calendarWorkPlaceRepository.getCalendarWorkPlaceSetByYear(workPlaceId, yearMonth.substring(0, 4));
	}
	
	public List<CalendarWorkplaceDto> getWorkplaceByYearMonth(String workPlaceId, String yearMonth){
		return this.calendarWorkPlaceRepository.getCalendarWorkPlaceByYearMonth(workPlaceId, yearMonth)
				.stream()
				.map(c->CalendarWorkplaceDto.fromDomain(c))
				.collect(Collectors.toList());
	}

}
