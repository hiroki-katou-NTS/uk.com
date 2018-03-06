/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import java.util.Optional;

/**
 * The Interface EmploymentLaborDeforSetTemporaryRepository.
 */
public interface EmploymentLaborDeforSetTemporaryRepository {

	/**
	 * Find by id.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @return the optional
	 */
	Optional<EmploymentLaborDeforSetTemporary> findById(String cid, String emplCode);

	/**
	 * Adds the.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void add(EmploymentLaborDeforSetTemporary emplLaborDeforSetTemporary);

	/**
	 * Update.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void update(EmploymentLaborDeforSetTemporary emplLaborDeforSetTemporary);

	/**
	 * Delete.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void delete(EmploymentLaborDeforSetTemporary emplLaborDeforSetTemporary);
}
