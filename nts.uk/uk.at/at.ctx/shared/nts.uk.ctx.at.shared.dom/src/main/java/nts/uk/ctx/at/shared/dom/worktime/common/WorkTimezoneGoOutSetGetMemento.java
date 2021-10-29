/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface WorkTimezoneGoOutSetGetMemento.
 */
public interface WorkTimezoneGoOutSetGetMemento {

	/**
	 * Gets the rounding method.
	 *
	 * @return the rounding method
	 */
	//丸め設定
	 GoOutTimeRoundingMethod getRoundingMethod();

	/**
	 * Gets the diff timezone setting.
	 *
	 * @return the diff timezone setting
	 */
	//時間帯別設定
	 GoOutTimezoneRoundingSet getDiffTimezoneSetting();
}
