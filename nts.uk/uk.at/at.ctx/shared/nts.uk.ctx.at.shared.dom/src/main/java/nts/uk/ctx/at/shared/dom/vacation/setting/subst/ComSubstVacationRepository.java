/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import java.util.Optional;

/**
 * The Interface ComSubstVacationRepository.
 */
public interface ComSubstVacationRepository {

	/**
	 * Insert.
	 *
	 * @param setting the setting
	 */
    void insert(ComSubstVacation setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
    void update(ComSubstVacation setting);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<ComSubstVacation> findById(String companyId);
	
	/**
	 * Copy master data.
	 *
	 * @param sourceCid the source cid
	 * @param targetCid the target cid
	 * @param isReplace the is replace
	 */
	void copyMasterData(String sourceCid, String targetCid, boolean isReplace);

}
