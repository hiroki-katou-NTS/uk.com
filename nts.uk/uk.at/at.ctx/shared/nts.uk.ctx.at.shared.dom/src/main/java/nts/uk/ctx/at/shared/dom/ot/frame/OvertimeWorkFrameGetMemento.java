/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.frame;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;

/**
 * The Interface OvertimeWorkFrameGetMemento.
 */
public interface OvertimeWorkFrameGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the overtime work frame no.
	 *
	 * @return the overtime work frame no
	 */
	OvertimeWorkFrameNo getOvertimeWorkFrameNo();
	
	/**
	 * Gets the use classification.
	 *
	 * @return the use classification
	 */
	NotUseAtr getUseClassification();
	
	/**
	 * Gets the transfer frame name.
	 *
	 * @return the transfer frame name
	 */
	OvertimeWorkFrameName getTransferFrameName();
	
	/**
	 * Gets the overtime work frame name.
	 *
	 * @return the overtime work frame name
	 */
	OvertimeWorkFrameName getOvertimeWorkFrameName();

	/**
	 * Gets the role.
	 * 
	 * @return the role
	 */
	RoleOvertimeWorkEnum getRole();
	
	/**
	 * Gets the transfer atr.
	 *
	 * @return the transfer atr
	 */
	NotUseAtr getTransferAtr();
}
