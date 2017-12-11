/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface ExtraordWorkOTFrameSetSetMemento.
 */
public interface ExtraordWorkOTFrameSetSetMemento {

	/**
	 * Sets the OT frame no.
	 *
	 * @param no the new OT frame no
	 */
 	void setOTFrameNo(OTFrameNo no);

	/**
	 * Sets the in legal work frame no.
	 *
	 * @param no the new in legal work frame no
	 */
	 void setInLegalWorkFrameNo(OTFrameNo no);

	/**
	 * Sets the settlement order.
	 *
	 * @param order the new settlement order
	 */
	 void setSettlementOrder(SettlementOrder order);
}
