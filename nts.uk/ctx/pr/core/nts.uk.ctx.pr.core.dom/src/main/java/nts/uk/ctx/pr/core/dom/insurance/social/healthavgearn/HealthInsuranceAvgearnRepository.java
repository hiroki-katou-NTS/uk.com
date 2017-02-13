/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn;

import java.util.List;

/**
 * The Interface HealthInsuranceAvgearnRepository.
 */
public interface HealthInsuranceAvgearnRepository {

	/**
	 * Adds the.
	 *
	 * @param healthInsuranceAvgearn the health insurance avgearn
	 */
	void add(HealthInsuranceAvgearn healthInsuranceAvgearn);

	/**
	 * Update.
	 *
	 * @param healthInsuranceAvgearn the health insurance avgearn
	 */
	void update(HealthInsuranceAvgearn healthInsuranceAvgearn);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 * @param version the version
	 */
	void remove(String id, Long version);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the list
	 */
	List<HealthInsuranceAvgearn> findById(String id);
}
