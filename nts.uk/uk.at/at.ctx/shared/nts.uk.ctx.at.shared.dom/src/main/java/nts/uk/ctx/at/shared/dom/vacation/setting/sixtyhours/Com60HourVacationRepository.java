/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import java.util.Optional;

/**
 * The Interface ComSubstVacationRepository.
 */
public interface Com60HourVacationRepository {
	
	/**
	 * Insert.
	 *
	 * @param setting the setting
	 */
	void insert(Com60HourVacation setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
    void update(Com60HourVacation setting);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<Com60HourVacation> findById(String companyId);
	
	/**
	 * Copy master data.
	 *
	 * @param sourceCid the source cid
	 * @param targetCid the target cid
	 * @param isReplace the is replace
	 */
	void copyMasterData(String sourceCid, String targetCid, boolean isReplace);

}
