package nts.uk.ctx.at.schedule.app.find.calendar;


import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.calendar.CalendarClassRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CalendarClassFinder {
	@Inject
	private CalendarClassRepository calendarClassRepository;
	
	String companyId = AppContexts.user().companyId();
	
	public List<CalendarClassDto> getAllCalendarClass(String classId){
		List<CalendarClassDto> list = this.calendarClassRepository.getAllCalendarClass(companyId,classId)
				.stream()
				.map(c->CalendarClassDto.fromDomain(c))
				.collect(Collectors.toList());
		return list;
	}

}
