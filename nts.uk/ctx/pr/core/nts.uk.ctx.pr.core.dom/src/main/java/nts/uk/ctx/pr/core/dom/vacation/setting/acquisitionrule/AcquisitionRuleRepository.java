/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule;

import java.util.List;
import java.util.Optional;

public interface AcquisitionRuleRepository {
	
	/**
	 * Creates the.
	 *
	 * @param acquisitionRule the acquisition rule
	 */
	void create(AcquisitionRule acquisitionRule);
	
	/**
	 * Update.
	 *
	 * @param acquisitionRule the acquisition rule
	 */
	void update(AcquisitionRule acquisitionRule);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 */
	void remove(String companyId);
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<AcquisitionRule> findById(String companyId);
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<AcquisitionRule> findAll();
	
	
}
