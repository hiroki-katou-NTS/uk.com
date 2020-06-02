/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workdayoff.frame;

/**
 * The Interface WorkdayoffFrameSetMemento.
 */
public interface WorkdayoffFrameSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

	/**
	 * Sets the workdayoff frame no.
	 *
	 * @param workdayoffFrNo the new workdayoff frame no
	 */
	public void setWorkdayoffFrameNo(WorkdayoffFrameNo workdayoffFrNo);
	
	/**
	 * Sets the use classification.
	 *
	 * @param useClassification the new use classification
	 */
	public void setUseClassification(NotUseAtr useClassification);
	
	/**
	 * Sets the transfer frame name.
	 *
	 * @param transferFrName the new transfer frame name
	 */
	public void setTransferFrameName(WorkdayoffFrameName transferFrName);
	
	/**
	 * Sets the workdayoff frame name.
	 *
	 * @param workdayoffFrName the new workdayoff frame name
	 */
	public void setWorkdayoffFrameName(WorkdayoffFrameName workdayoffFrName);
	
	/**
	 * Sets the role.
	 * 
	 * @param role the role
	 */
	public void setRole(WorkdayoffFrameRole role);
}
