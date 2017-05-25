/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import java.util.List;
import java.util.Optional;

/**
 * The Interface ComSubstVacationRepository.
 */
public interface ComSubstVacationRepository {

	/**
	 * Adds the.
	 *
	 * @param setting the setting
	 */
    void add(ComSubstVacation setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
    void update(ComSubstVacation setting);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<ComSubstVacation> findAll(String companyId);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<ComSubstVacation> findById(String companyId);

}
