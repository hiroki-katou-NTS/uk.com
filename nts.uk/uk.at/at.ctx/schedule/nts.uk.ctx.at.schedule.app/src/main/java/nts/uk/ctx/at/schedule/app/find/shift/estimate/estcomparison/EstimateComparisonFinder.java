/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.estcomparison;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparison;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EstimateComparisonFinder.
 */
@Stateless
public class EstimateComparisonFinder {

	/** The repository. */
	@Inject
	private EstimateComparisonRepository repository;
	
	/**
	 * Find by company id.
	 *
	 * @return the estimate comparison find dto
	 */
	public EstimateComparisonFindDto findByCompanyId () {
		// Get the company id.
		String companyId = AppContexts.user().companyId();
		
		Optional<EstimateComparison> estComparisonOpt = this.repository.findByCompanyId(companyId);
		
		EstimateComparisonFindDto dto = new EstimateComparisonFindDto();
		
		if (estComparisonOpt.isPresent()) {
			estComparisonOpt.get().saveToMemento(dto);
		}
		return dto;
	}
}
