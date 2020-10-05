/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmploymentRegularWorkHourRepository.
 */
public interface RegularLaborTimeEmpRepo {

	
	/**
	 * Find by cid.
	 *
	 * @param cid the cid
	 * @return the list
	 */
	List<RegularLaborTimeEmp> findListByCid(String cid);
	
	/**
	 * Find by id.
	 *
	 * @param cid the cid
	 * @param employmentCode the employment code
	 * @return the optional
	 */
	Optional<RegularLaborTimeEmp> findById(String cid, String employmentCode);

	/**
	 * Adds the.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 */
	void add(RegularLaborTimeEmp emplRegWorkHour);

	/**
	 * Update.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 */
	void update(RegularLaborTimeEmp emplRegWorkHour);

	/**
	 * Delete.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 */
	void delete(String cid, String employmentCode);

}
