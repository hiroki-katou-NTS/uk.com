/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface ExtraordWorkOTFrameSetGetMemento.
 */
public interface ExtraordWorkOTFrameSetGetMemento {

	/**
	 * Gets the ot frame no.
	 *
	 * @return the ot frame no
	 */
 	OTFrameNo getOtFrameNo();

	/**
	 * Gets the in legal work frame no.
	 *
	 * @return the in legal work frame no
	 */
	 OTFrameNo getInLegalWorkFrameNo();

	/**
	 * Gets the settlement order.
	 *
	 * @return the settlement order
	 */
	 SettlementOrder getSettlementOrder();
}
