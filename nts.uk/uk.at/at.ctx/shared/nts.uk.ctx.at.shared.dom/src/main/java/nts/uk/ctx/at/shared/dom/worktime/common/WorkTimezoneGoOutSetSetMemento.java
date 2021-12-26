/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface WorkTimezoneGoOutSetSetMemento.
 */
public interface WorkTimezoneGoOutSetSetMemento {

	/**
	 * Sets the rounding method.
	 *
	 * @param set the new rounding method
	 */
	 void setRoundingMethod(GoOutTimeRoundingMethod set);

	/**
	 * Sets the diff timezone setting.
	 *
	 * @param set the new diff timezone setting
	 */
	 void setDiffTimezoneSetting(GoOutTimezoneRoundingSet set);
}
