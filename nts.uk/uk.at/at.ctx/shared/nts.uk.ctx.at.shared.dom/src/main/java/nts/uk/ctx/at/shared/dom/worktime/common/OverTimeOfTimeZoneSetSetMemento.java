/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Class OverTimeOfTimeZoneSetSetMemento.
 */
public interface OverTimeOfTimeZoneSetSetMemento {
	
	/**
	 * Sets the work timezone no.
	 *
	 * @param workTimezoneNo the new work timezone no
	 */
	void setWorkTimezoneNo(EmTimezoneNo workTimezoneNo);
	
	
	/**
	 * Sets the restraint time use.
	 *
	 * @param restraintTimeUse the new restraint time use
	 */
	void setRestraintTimeUse(boolean restraintTimeUse);
	
	
	/**
	 * Sets the early OT use.
	 *
	 * @param earlyOTUse the new early OT use
	 */
	void setEarlyOTUse(boolean earlyOTUse);
	
	
	/**
	 * Sets the timezone.
	 *
	 * @param timezone the new timezone
	 */
	void setTimezone(TimeZoneRounding timezone);
	
	
	/**
	 * Sets the OT frame no.
	 *
	 * @param OTFrameNo the new OT frame no
	 */
	void setOTFrameNo(OTFrameNo OTFrameNo);
	
	
	/**
	 * Sets the legal O tframe no.
	 *
	 * @param legalOTframeNo the new legal O tframe no
	 */
	void setLegalOTframeNo(OTFrameNo legalOTframeNo);
	
	
	/**
	 * Sets the settlement order.
	 *
	 * @param settlementOrder the new settlement order
	 */
	void setSettlementOrder(SettlementOrder settlementOrder);

}
