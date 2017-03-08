/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.certification;

import java.util.List;
import java.util.Set;

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

	/**
	 * Find all none of group.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<Certification> findAllNoneOfGroup(CompanyCode companyCode);

	/**
	 * Checks if is dulicate certification.
	 *
	 * @param companyCode the company code
	 * @param lstCertification the lst certification
	 * @param certifyGroupCode the certify group code
	 * @return true, if is dulicate certification
	 */
	boolean isDulicateCertification (CompanyCode companyCode, Set<Certification> setCertification, CertifyGroupCode certifyGroupCode );
}
