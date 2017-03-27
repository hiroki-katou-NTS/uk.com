/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.avgearn;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface AvgEarnLevelMasterSettingRepository.
 */
public interface AvgEarnLevelMasterSettingRepository {

	/**
	 * Adds the.
	 *
	 * @param level the level
	 */
	void add(AvgEarnLevelMasterSetting level);

	/**
	 * Update.
	 *
	 * @param level the level
	 */
	void update(AvgEarnLevelMasterSetting level);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 * @param version the version
	 */
	void remove(String id, Long version);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<AvgEarnLevelMasterSetting> findAll(CompanyCode companyCode);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the avg earn level master setting
	 */
	Optional<AvgEarnLevelMasterSetting> findById(String id);
}
