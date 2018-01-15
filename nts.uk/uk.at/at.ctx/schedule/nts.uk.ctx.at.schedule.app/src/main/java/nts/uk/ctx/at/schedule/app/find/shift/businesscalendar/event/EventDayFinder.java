/**
 * 2:11:48 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class EventDayFinder {

	@Inject
	private CompanyEventRepository companyEventRepository;

	@Inject
	private WorkplaceEventRepository workplaceEventRepository;

	public List<CompanyEventDto> getCompanyEventsByListDate(List<GeneralDate> lstDate) {
		return this.companyEventRepository.getCompanyEventsByListDate(AppContexts.user().companyId(), lstDate).stream()
				.map(domain -> CompanyEventDto.fromDomain(domain)).collect(Collectors.toList());
	}

	public List<WorkplaceEventDto> getWorkplaceEventsByListDate(String workplaceId, List<GeneralDate> lstDate) {
		return this.workplaceEventRepository.getWorkplaceEventsByListDate(workplaceId, lstDate)
				.stream().map(domain -> WorkplaceEventDto.fromDomain(domain)).collect(Collectors.toList());
	}

}
