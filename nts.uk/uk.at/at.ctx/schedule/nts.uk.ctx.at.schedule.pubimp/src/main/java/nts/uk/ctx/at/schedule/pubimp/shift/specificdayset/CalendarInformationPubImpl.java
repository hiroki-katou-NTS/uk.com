package nts.uk.ctx.at.schedule.pubimp.shift.specificdayset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.service.ICalendarInformationService;
import nts.uk.ctx.at.schedule.pub.shift.specificdayset.CalendarInformationExport;
import nts.uk.ctx.at.schedule.pub.shift.specificdayset.CalendarInformationPub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CalendarInformationPubImpl implements CalendarInformationPub{
	
	@Inject
	private ICalendarInformationService calendarInformationService;
	
	@Override
	public List<CalendarInformationExport> getCalendarInformation(String companyID, String workplaceID, String classCD,
			GeneralDate date) {
		return calendarInformationService.getCalendarInformation(companyID, workplaceID, classCD, date)
				.stream()
				.map(x -> new CalendarInformationExport(x.getWorkTypeCD(), x.getSiftCD(), x.getDate()))
				.collect(Collectors.toList());
	}

}
