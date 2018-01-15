package nts.uk.screen.at.app.shift.specificdayset.workplace;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

/**
 * find data of Workplace Specific Date Set
 * 
 * @author sonnh1
 *
 */
@Stateless
public class WorkplaceSpecificDateSetScreenProcessor {
	@Inject
	private WorkplaceSpecificDateSetScreenRepository repo;

	public List<GeneralDate> findDataWkpSpecificDateSet(
			WorkplaceIdAndDateScreenParams params) {
		return this.repo.findDataWkpSpecificDateSet(params);
	}
}
