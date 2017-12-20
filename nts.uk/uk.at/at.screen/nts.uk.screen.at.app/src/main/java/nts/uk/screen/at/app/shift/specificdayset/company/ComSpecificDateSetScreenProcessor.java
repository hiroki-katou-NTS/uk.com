package nts.uk.screen.at.app.shift.specificdayset.company;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceIdAndDateScreenParams;
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

	public List<GeneralDate> findDataComSpecificDateSet(WorkplaceIdAndDateScreenParams params) {
		String companyId = AppContexts.user().companyId();
		return this.repo.findDataComSpecificDateSet(companyId, params);
	}
}
