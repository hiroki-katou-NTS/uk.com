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
	 * @param cId the cid
	 * @param sId the emp id
	 * @return the optional
	 */
	Optional<ShaDeforLaborMonthActCalSet> find(String cId, String sId);

	/**
	 * Adds the.
	 *
	 * @param laborDeforSetTemp the emp labor defor set temp
	 */
	void add(ShaDeforLaborMonthActCalSet laborDeforSetTemp);

	/**
	 * Update.
	 *
	 * @param laborDeforSetTemp the emp labor defor set temp
	 */
	void update(ShaDeforLaborMonthActCalSet laborDeforSetTemp);

	/**
	 * Delete.
	 *
	 * @param laborDeforSetTemp the emp labor defor set temp
	 */
	void remove(String cId, String sId);

}
