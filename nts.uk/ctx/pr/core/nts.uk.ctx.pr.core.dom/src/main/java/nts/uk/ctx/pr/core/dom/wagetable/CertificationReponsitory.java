/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;

public interface CertificationReponsitory {
	
	/**
	 * Adds the.
	 *
	 * @param certification the certification
	 */
    void add(Certification certification);

	/**
	 * Update.
	 *
	 * @param certification the certification
	 */
    void update(Certification certification);

	/**
	 * Removes the.
	 *
	 * @param id the id
	 * @param version the version
	 */
    void remove(String id, Long version);

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<Certification> findAll(CompanyCode companyCode);

	/**
	 * Find by id.
	 *
	 * @param companyCode the company code
	 * @param code the code
	 * @return the certification
	 */
	Certification findById(CompanyCode companyCode, String code);

	/**
	 * Checks if is duplicate code.
	 *
	 * @param companyCode the company code
	 * @param code the code
	 * @return true, if is duplicate code
	 */
	boolean isDuplicateCode(CompanyCode companyCode, String code);
}
