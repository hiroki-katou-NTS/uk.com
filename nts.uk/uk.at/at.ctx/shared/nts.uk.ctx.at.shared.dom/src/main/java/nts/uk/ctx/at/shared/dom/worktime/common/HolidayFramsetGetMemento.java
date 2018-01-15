/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface HolidayFramsetGetMemento.
 */
public interface HolidayFramsetGetMemento {
	
	
	/**
	 * Gets the in legal breakout frame no.
	 *
	 * @return the in legal breakout frame no
	 */
	BreakoutFrameNo getInLegalBreakoutFrameNo();
	
	/**
	 * Gets the out legal breakout frame no.
	 *
	 * @return the out legal breakout frame no
	 */
	BreakoutFrameNo getOutLegalBreakoutFrameNo();
	
	
	/**
	 * Gets the out legal pub hol frame no.
	 *
	 * @return the out legal pub hol frame no
	 */
	BreakoutFrameNo getOutLegalPubHolFrameNo();

}
