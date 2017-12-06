/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface HolidayFramsetSetMemento.
 */
public interface HolidayFramsetSetMemento {

	
	/**
	 * Sets the in legal breakout frame no.
	 *
	 * @param inLegalBreakoutFrameNo the new in legal breakout frame no
	 */
	void setInLegalBreakoutFrameNo(BreakoutFrameNo inLegalBreakoutFrameNo);
	
	
	/**
	 * Sets the out legal breakout frame no.
	 *
	 * @param outLegalBreakoutFrameNo the new out legal breakout frame no
	 */
	void setOutLegalBreakoutFrameNo(BreakoutFrameNo outLegalBreakoutFrameNo);
	
	
	/**
	 * Sets the out legal pub hol frame no.
	 *
	 * @param outLegalPubHolFrameNo the new out legal pub hol frame no
	 */
	void setOutLegalPubHolFrameNo(BreakoutFrameNo outLegalPubHolFrameNo);
}
