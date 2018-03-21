/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employee;

import java.util.Optional;

/**
 * The Interface EmployeeLaborDeforSetTemporaryRepository.
 */
public interface ShaDeforLaborMonthActCalSetRepository {

	/**
	 * Find emp labor defor set temp by cid and emp id.
	 *
	 * @param cid the cid
	 * @param empId the emp id
	 * @return the optional
	 */
	Optional<ShaDeforLaborMonthActCalSet> findEmpLaborDeforSetTempByCidAndEmpId(String cid, String empId);

	/**
	 * Adds the.
	 *
	 * @param EmpLaborDeforSetTemp the emp labor defor set temp
	 */
	void add(ShaDeforLaborMonthActCalSet EmpLaborDeforSetTemp);

	/**
	 * Update.
	 *
	 * @param EmpLaborDeforSetTemp the emp labor defor set temp
	 */
	void update(ShaDeforLaborMonthActCalSet EmpLaborDeforSetTemp);

	/**
	 * Delete.
	 *
	 * @param EmpLaborDeforSetTemp the emp labor defor set temp
	 */
	void delete(ShaDeforLaborMonthActCalSet EmpLaborDeforSetTemp);

}
