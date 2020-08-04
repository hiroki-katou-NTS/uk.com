/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employment;

import java.util.Optional;

/**
 * The Interface EmploymentLaborDeforSetTemporaryRepository.
 */
public interface EmpDeforLaborMonthActCalSetRepository {

	/**
	 * Find by id.
	 *
	 * @param cid the cid
	 * @param empCode the empl code
	 * @return the optional
	 */
	Optional<EmpDeforLaborMonthActCalSet> find(String cid, String empCode);

	/**
	 * Adds the.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void add(EmpDeforLaborMonthActCalSet emplLaborDeforSetTemporary);

	/**
	 * Update.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void update(EmpDeforLaborMonthActCalSet emplLaborDeforSetTemporary);

	/**
	 * Delete.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void remove(String cid, String empCode);
}
