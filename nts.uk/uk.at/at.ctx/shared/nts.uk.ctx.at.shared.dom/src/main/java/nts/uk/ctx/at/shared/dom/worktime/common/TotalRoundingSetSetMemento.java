/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface TotalRoundingSetSetMemento.
 */
public interface TotalRoundingSetSetMemento {

	/**
	 * Sets the sets the same frame rounding.
	 *
	 * @param method the new sets the same frame rounding
	 */
	 void  setSetSameFrameRounding(GoOutTimeRoundingMethod method);

	/**
	 * Sets the frame stradd rounding set.
	 *
	 * @param method the new frame stradd rounding set
	 */
	 void setFrameStraddRoundingSet(GoOutTimeRoundingMethod method);
}
