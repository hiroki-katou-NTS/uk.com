/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import java.util.Set;

import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface CertifyGroupGetMemento.
 */
public interface CertifyGroupGetMemento {

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	String getCode();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the multi apply set.
	 *
	 * @return the multi apply set
	 */
	MultipleTargetSetting getMultiApplySet();

	/**
	 * Gets the certifies.
	 *
	 * @return the certifies
	 */
	Set<Certification> getCertifies();

}
