/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface OperationStartSetDailyPerformRepository.
 */
public interface OperationStartSetDailyPerformRepository {
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the operation start set daily perform
	 */
	Optional<OperationStartSetDailyPerform> findByCid(CompanyId companyId);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(OperationStartSetDailyPerform domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(OperationStartSetDailyPerform domain);
}

