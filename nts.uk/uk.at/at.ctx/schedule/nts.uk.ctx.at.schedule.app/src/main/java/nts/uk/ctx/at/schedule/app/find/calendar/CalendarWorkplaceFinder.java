package nts.uk.ctx.at.schedule.app.find.calendar;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.calendar.CalendarCompanyRepository;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarWorkPlaceRepository;

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

}
