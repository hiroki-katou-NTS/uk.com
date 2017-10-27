/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.personal;

import java.util.List;
import java.util.Optional;

/**
 * The Class PersonalEstablishmentRepository.
 */
public interface PersonalEstablishmentRepository {

	/**
	 * Find by id.
	 *
	 * @param employeeId the employee id
	 * @param targetYear the target year
	 * @return the optional
	 */
	public Optional<PersonalEstablishment> findById(String employeeId, int targetYear);
	
	
	/**
	 * Save personal establishment.
	 *
	 * @param personalEstablishment the personal establishment
	 */
	public void savePersonalEstablishment(PersonalEstablishment personalEstablishment);
	
	
	
	/**
	 * Removes the personal establishment.
	 *
	 * @param employeeId the employee id
	 * @param targetYear the target year
	 */
	public void removePersonalEstablishment(String employeeId, int targetYear);
	
	
	/**
	 * Find all.
	 *
	 * @param targetYear the target year
	 * @return the list
	 */
	public List<String> findAll(int targetYear);
}

