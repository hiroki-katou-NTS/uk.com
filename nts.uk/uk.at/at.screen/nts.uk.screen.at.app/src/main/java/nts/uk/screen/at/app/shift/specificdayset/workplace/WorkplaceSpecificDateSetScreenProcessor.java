package nts.uk.screen.at.app.shift.specificdayset.workplace;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

	public List<BigDecimal> findDataWkpSpecificDateSet(
			WorkplaceSpecificDateSetScreenParams params) {
		return this.repo.findDataWkpSpecificDateSet(params);
	}
}
