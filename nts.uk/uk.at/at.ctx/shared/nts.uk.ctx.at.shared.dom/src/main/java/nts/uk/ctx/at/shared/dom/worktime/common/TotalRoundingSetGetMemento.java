/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface TotalRoundingSetGetMemento.
 */
public interface TotalRoundingSetGetMemento {

	/**
	 * Gets the sets the same frame rounding.
	 *
	 * @return the sets the same frame rounding
	 */
	 GoOutTimeRoundingMethod getSetSameFrameRounding();

	/**
	 * Gets the frame stradd rounding set.
	 *
	 * @return the frame stradd rounding set
	 */
	 GoOutTimeRoundingMethod getFrameStraddRoundingSet();
}
