/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import java.util.Set;

import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface CertifyGroupSetMemento.
 */
public interface CertifyGroupSetMemento {

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	void setCompanyCode(CompanyCode companyCode);

	/**
	 * Sets the wage table code.
	 *
	 * @param wageTableCode the new wage table code
	 */
	void setWageTableCode(WageTableCode wageTableCode);

	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	void setHistoryId(String historyId);

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	void setCode(String code);

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(String name);

	/**
	 * Sets the multi apply set.
	 *
	 * @param multiApplySet the new multi apply set
	 */
	void setMultiApplySet(MultipleTargetSetting multiApplySet);

	/**
	 * Sets the certifies.
	 *
	 * @param certifies the new certifies
	 */
	void setCertifies(Set<Certification> certifies);

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	void setVersion(Long version);

}
