package nts.uk.ctx.at.schedule.app.find.calendar;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.calendar.CalendarRepository;

@Stateless
public class CalendarWorkplaceFinder {
	@Inject
	private CalendarRepository calendarWorkplaceRepo;
	
	String workPlaceId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d").toString();
	
	public List<CalendarWorkplaceDto> getAllCalendarWorkplace(){
		List<CalendarWorkplaceDto> list  = this.calendarWorkplaceRepo.getAllCalendarWorkplace(workPlaceId)
				.stream()
				.map(c->CalendarWorkplaceDto.fromDomain(c))
				.collect(Collectors.toList());
		return list;
	}

}
