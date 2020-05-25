/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workdayoff.frame;

/**
 * The Interface WorkdayoffFrameGetMemento.
 */
public interface WorkdayoffFrameGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the workdayoff frame no.
	 *
	 * @return the workdayoff frame no
	 */
	WorkdayoffFrameNo getWorkdayoffFrameNo();
	
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
	WorkdayoffFrameName getTransferFrameName();
	
	/**
	 * Gets the workdayoff frame name.
	 *
	 * @return the workdayoff frame name
	 */
	WorkdayoffFrameName getWorkdayoffFrameName();
	
	/**
	 * Gets the role.
	 * 
	 * @return the role
	 */
	WorkdayoffFrameRole getRole();
}
