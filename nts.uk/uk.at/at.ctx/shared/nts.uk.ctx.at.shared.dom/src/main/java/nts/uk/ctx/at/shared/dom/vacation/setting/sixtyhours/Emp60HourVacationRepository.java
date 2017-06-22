/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmpSubstVacationRepository.
 */
public interface Emp60HourVacationRepository {
	
	/**
	 * Insert.
	 *
	 * @param setting the setting
	 */
	void insert(Emp60HourVacation setting);
	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
    void update(Emp60HourVacation setting);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param contractTypeCode the contract type code
	 * @return the optional
	 */
	Optional<Emp60HourVacation> findById(String companyId, String contractTypeCode);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<Emp60HourVacation> findAll(String companyId);

}
