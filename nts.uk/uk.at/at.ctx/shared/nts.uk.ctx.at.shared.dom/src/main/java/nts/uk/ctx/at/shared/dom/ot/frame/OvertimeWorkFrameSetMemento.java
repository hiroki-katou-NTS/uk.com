/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.frame;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;

/**
 * The Interface OvertimeWorkFrameSetMemento.
 */
public interface OvertimeWorkFrameSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

	/**
	 * Sets the overtime work frame no.
	 *
	 * @param overtimeWorkFrNo the new overtime work frame no
	 */
	public void setOvertimeWorkFrameNo(OvertimeWorkFrameNo overtimeWorkFrNo);
	
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
	public void setTransferFrameName(OvertimeWorkFrameName transferFrName);
	
	/**
	 * Sets the overtime work frame name.
	 *
	 * @param overtimeWorkFrName the new overtime work frame name
	 */
	public void setOvertimeWorkFrameName(OvertimeWorkFrameName overtimeWorkFrName);

	/**
	 * Sets the role.
	 * 
	 * @param role the role
	 */
	public void setRole(RoleOvertimeWorkEnum role);
	
	/**
	 * Sets the transfer atr.
	 *
	 * @param transferAtr the transfer atr
	 */
	public void setTransferAtr(NotUseAtr transferAtr);
}
