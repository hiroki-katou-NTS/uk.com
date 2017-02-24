/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn;

import java.util.List;
import java.util.Optional;

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
	 * @param healthInsuranceAvgearns the health insurance avgearns
	 * @param ccd the ccd
	 * @param officeCd the office cd
	 */
	void update(List<HealthInsuranceAvgearn> healthInsuranceAvgearns, String ccd, String officeCd);

	/**
	 * Removes the.
	 *
	 * @param ccd the ccd
	 * @param officeCd the office cd
	 * @param histId the hist id
	 * @param levelCode the level code
	 */
	void remove(String ccd, String officeCd, String histId, Integer levelCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the list
	 */
	List<HealthInsuranceAvgearn> findById(String id);

	/**
	 * Find.
	 *
	 * @param ccd the ccd
	 * @param officeCd the office cd
	 * @param historyId the history id
	 * @param levelCode the level code
	 * @return the optional
	 */
	Optional<HealthInsuranceAvgearn> find(String ccd, String officeCd, String historyId, Integer levelCode);
}
