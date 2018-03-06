/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee;

import java.util.Optional;

/**
 * The Interface EmployeeLaborDeforSetTemporaryRepository.
 */
public interface EmployeeLaborDeforSetTemporaryRepository {

	/**
	 * Find emp labor defor set temp by cid and emp id.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @return the optional
	 */
	Optional<EmployeeLaborDeforSetTemporary> findEmpLaborDeforSetTempByCidAndEmpId(String cid, String empId);

	/**
	 * Adds the.
	 *
	 * @param EmpLaborDeforSetTemp the emp labor defor set temp
	 */
	void add(EmployeeLaborDeforSetTemporary EmpLaborDeforSetTemp);

	/**
	 * Update.
	 *
	 * @param EmpLaborDeforSetTemp the emp labor defor set temp
	 */
	void update(EmployeeLaborDeforSetTemporary EmpLaborDeforSetTemp);

	/**
	 * Delete.
	 *
	 * @param EmpLaborDeforSetTemp the emp labor defor set temp
	 */
	void delete(EmployeeLaborDeforSetTemporary EmpLaborDeforSetTemp);

}
