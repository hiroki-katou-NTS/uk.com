package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.businesscalendar;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.businesscalendar.StartDayRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StartDayFinder {
	@Inject
	private StartDayRepository startDayRepo;
	
	
	public StartDayDto findByCompanyId(){
		String companyId = AppContexts.user().companyId();
		return startDayRepo.findByCompany(companyId)
				.map(item-> StartDayDto.fromDomain(item))
				.orElse(null);
	}
}
