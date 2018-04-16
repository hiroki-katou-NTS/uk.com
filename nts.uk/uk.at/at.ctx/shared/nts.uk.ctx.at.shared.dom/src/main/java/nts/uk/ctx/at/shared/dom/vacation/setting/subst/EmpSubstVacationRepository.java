/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmpSubstVacationRepository.
 */
public interface EmpSubstVacationRepository {

	/**
	 * Insert.
	 *
	 * @param setting the setting
	 */
	void insert(EmpSubstVacation setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
    void update(EmpSubstVacation setting);
    
    /**
	 * Delete.
	 *
	 * @param setting the setting
	 */
    void delete(String companyId, String contractTypeCode);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param contractTypeCode the contract type code
	 * @return the optional
	 */
	Optional<EmpSubstVacation> findById(String companyId, String contractTypeCode);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<EmpSubstVacation> findAll(String companyId);

}
