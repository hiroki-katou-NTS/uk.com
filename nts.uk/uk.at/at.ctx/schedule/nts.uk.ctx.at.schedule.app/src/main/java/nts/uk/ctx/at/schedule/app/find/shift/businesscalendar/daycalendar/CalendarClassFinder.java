package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClassRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CalendarClassFinder {
	@Inject
	private CalendarClassRepository calendarClassRepository;

	public List<CalendarClassDto> getAllCalendarClass(String classId) {
		String companyId = AppContexts.user().companyId();
		List<CalendarClassDto> list = this.calendarClassRepository.getAllCalendarClass(companyId, classId).stream()
				.map(c -> CalendarClassDto.fromDomain(c)).collect(Collectors.toList());
		return list;
	}

	public List<Integer> getCalendarClassSetByYear(String classId, String yearMonth) {
		String companyId = AppContexts.user().companyId();
		return this.calendarClassRepository.getCalendarClassSetByYear(companyId, classId, yearMonth.substring(0, 4));
	}

	public List<CalendarClassDto> getCalendarClassByYearMonth(String classId, String yearMonth) {
		String companyId = AppContexts.user().companyId();
		return this.calendarClassRepository.getCalendarClassByYearMonth(companyId, classId, yearMonth).stream()
				.map(c -> CalendarClassDto.fromDomain(c)).collect(Collectors.toList());
	}

}
