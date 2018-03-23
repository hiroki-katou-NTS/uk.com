/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmploymentRegularWorkHourRepository.
 */
public interface EmpRegularWorkTimeRepository {

	
	/**
	 * Find by cid.
	 *
	 * @param cid the cid
	 * @return the list
	 */
	List<EmpRegularWorkTime> findListByCid(String cid);
	
	/**
	 * Find by id.
	 *
	 * @param cid the cid
	 * @param employmentCode the employment code
	 * @return the optional
	 */
	Optional<EmpRegularWorkTime> findById(String cid, String employmentCode);

	/**
	 * Adds the.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 */
	void add(EmpRegularWorkTime emplRegWorkHour);

	/**
	 * Update.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 */
	void update(EmpRegularWorkTime emplRegWorkHour);

	/**
	 * Delete.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 */
	void delete(String cid, String employmentCode);

}
