package nts.uk.screen.at.app.shift.specificdayset.company;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;

/**
 * find data of Com Specific Date Set
 * 
 * @author sonnh1
 *
 */
@Stateless
public class ComSpecificDateSetScreenProcessor {

	@Inject
	private ComSpecificDateSetScreenRepository repo;

	public List<BigDecimal> findDataComSpecificDateSet(StartDateEndDateScreenParams params) {
		String companyId = AppContexts.user().companyId();
		return this.repo.findDataComSpecificDateSet(companyId, params);
	}
}
