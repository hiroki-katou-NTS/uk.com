/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Class OverTimeOfTimeZoneSetGetMemento.
 */
public interface OverTimeOfTimeZoneSetGetMemento {

	/**
	 * Gets the work timezone no.
	 *
	 * @return the work timezone no
	 */
	EmTimezoneNo getWorkTimezoneNo();
	
	
	/**
	 * Gets the restraint time use.
	 *
	 * @return the restraint time use
	 */
	boolean getRestraintTimeUse();
	
	
	/**
	 * Gets the early OT use.
	 *
	 * @return the early OT use
	 */
	boolean getEarlyOTUse();
	
	
	/**
	 * Gets the timezone.
	 *
	 * @return the timezone
	 */
	TimeZoneRounding getTimezone();
	
	/**
	 * Gets the OT frame no.
	 *
	 * @return the OT frame no
	 */
	OTFrameNo getOTFrameNo();
	
	
	/**
	 * Gets the legal O tframe no.
	 *
	 * @return the legal O tframe no
	 */
	OTFrameNo getLegalOTframeNo();
	
	
	/**
	 * Gets the settlement order.
	 *
	 * @return the settlement order
	 */
	SettlementOrder getSettlementOrder();
}
