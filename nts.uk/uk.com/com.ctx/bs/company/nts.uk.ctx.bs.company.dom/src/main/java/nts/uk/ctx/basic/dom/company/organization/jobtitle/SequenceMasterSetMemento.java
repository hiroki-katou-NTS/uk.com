/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

/**
 * The Interface SequenceMasterSetMemento.
 */
public interface SequenceMasterSetMemento {

	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	void setOrder (int order);

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId) ;

	/**
	 * Sets the sequence code.
	 *
	 * @param sequenceCode the new sequence code
	 */
	void setSequenceCode(SequenceCode sequenceCode);

	/**
	 * Sets the sequence name.
	 *
	 * @param sequenceName the new sequence name
	 */
	void setSequenceName(SequenceName sequenceName);
}
