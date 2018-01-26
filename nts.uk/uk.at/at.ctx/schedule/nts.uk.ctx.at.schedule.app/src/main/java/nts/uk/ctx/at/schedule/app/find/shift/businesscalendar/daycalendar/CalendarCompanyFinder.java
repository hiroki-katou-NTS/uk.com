package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CalendarCompanyFinder {

	@Inject
	private CalendarCompanyRepository calendarCompanyRepo;
	
	
	
	public List<CalendarCompanyDto> getAllCalendarCompany(){
		String companyId = AppContexts.user().companyId();
		List<CalendarCompanyDto> list = this.calendarCompanyRepo.getAllCalendarCompany(companyId)
				.stream()
				.map(c->CalendarCompanyDto.fromDomain(c))
				.collect(Collectors.toList());
		return list;
	}
	
	public List<Integer> getCalendarCompanySetByYear(String yearMonth){
		String companyId = AppContexts.user().companyId();
		return this.calendarCompanyRepo.getCalendarCompanySetByYear(companyId, yearMonth.substring(0, 4));
	}
	
	public List<CalendarCompanyDto> getCalendarCompanyByYearMonth(String yearMonth){
		String companyId = AppContexts.user().companyId();
		return this.calendarCompanyRepo.getCalendarCompanyByYearMonth(companyId, yearMonth)
				.stream()
				.map(c->CalendarCompanyDto.fromDomain(c))
				.collect(Collectors.toList());
	}
}
