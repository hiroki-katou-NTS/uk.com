/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employment;

import java.util.Optional;

/**
 * The Interface EmploymentLaborDeforSetTemporaryRepository.
 */
public interface EmpLaborDeforSetTemporaryRepository {

	/**
	 * Find by id.
	 *
	 * @param cid the cid
	 * @param emplCode the empl code
	 * @return the optional
	 */
	Optional<EmpLaborDeforSetTemporary> findById(String cid, String emplCode);

	/**
	 * Adds the.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void add(EmpLaborDeforSetTemporary emplLaborDeforSetTemporary);

	/**
	 * Update.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void update(EmpLaborDeforSetTemporary emplLaborDeforSetTemporary);

	/**
	 * Delete.
	 *
	 * @param emplLaborDeforSetTemporary the empl labor defor set temporary
	 */
	void delete(EmpLaborDeforSetTemporary emplLaborDeforSetTemporary);
}
