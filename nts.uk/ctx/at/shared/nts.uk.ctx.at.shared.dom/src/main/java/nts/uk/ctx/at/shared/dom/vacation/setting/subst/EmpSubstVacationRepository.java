/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import java.util.Optional;

/**
 * The Interface EmpSubstVacationRepository.
 */
public interface EmpSubstVacationRepository {

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
    void update(EmpSubstVacation setting);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param contractTypeCode the contract type code
	 * @return the optional
	 */
	Optional<EmpSubstVacation> findById(String companyId, String contractTypeCode);

}
