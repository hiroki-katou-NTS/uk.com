/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.certification;

import java.util.List;
import java.util.Optional;

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
	List<Certification> findAll(String companyCode);

	/**
	 * Find all none of group.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<Certification> findAllNoneOfGroup(String companyCode);

	/**
	 * Find by id.
	 *
	 * @param companyCode the company code
	 * @param certifyGroupCode the certify group code
	 * @return the optional
	 */
	Optional<Certification> findById (String companyCode,String certificationcode,String certifyGroupCode);

}
