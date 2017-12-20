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
 	 * Gets the OT frame no.
 	 *
 	 * @return the OT frame no
 	 */
 	OTFrameNo getOTFrameNo();

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
