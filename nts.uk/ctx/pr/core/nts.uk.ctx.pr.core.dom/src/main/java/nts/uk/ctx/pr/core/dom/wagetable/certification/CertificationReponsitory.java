/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.certification;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface CertificationReponsitory.
 */
public interface CertificationReponsitory {

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<Certification> findAll(CompanyCode companyCode);
}
