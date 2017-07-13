package nts.uk.ctx.at.schedule.app.find.calendar;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.calendar.CalendarRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CalendarCompanyFinder {

	@Inject
	private CalendarRepository calendarCompanyRepo;
	
	String companyId = AppContexts.user().companyId();
	
	public List<CalendarCompanyDto> getAllCalendarCompany(){
		List<CalendarCompanyDto> list = this.calendarCompanyRepo.getAllCalendarCompany(companyId)
				.stream()
				.map(c->CalendarCompanyDto.fromDomain(c))
				.collect(Collectors.toList());
		return list;
	}
}
