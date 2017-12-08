package nts.uk.screen.at.app.shift.businesscalendar.holiday;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceIdAndDateScreenParams;
import nts.uk.shr.com.context.AppContexts;

/**
 * find data of Public Holiday
 * 
 * @author sonnh1
 *
 */
@Stateless
public class PublicHolidayScreenProcessor {

	@Inject
	private PublicHolidayScreenRepository repo;

	public List<GeneralDate> findDataPublicHoliday(WorkplaceIdAndDateScreenParams params) {
		String companyId = AppContexts.user().companyId();
		return this.repo.findDataPublicHoliday(companyId, params);
	}
}
