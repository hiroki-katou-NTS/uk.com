package nts.uk.screen.at.app.shift.businesscalendar.holiday;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.shift.specificdayset.company.StartDateEndDateScreenParams;
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

	public List<BigDecimal> findDataPublicHoliday(StartDateEndDateScreenParams params) {
		String companyId = AppContexts.user().companyId();
		return this.repo.findDataPublicHoliday(companyId, params);
	}
}
