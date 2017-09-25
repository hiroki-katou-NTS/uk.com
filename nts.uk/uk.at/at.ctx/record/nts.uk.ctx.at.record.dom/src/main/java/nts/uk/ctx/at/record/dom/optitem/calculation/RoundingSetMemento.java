/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface RoundingSetMemento.
 */
public interface RoundingSetMemento {

	/**
	 * Sets the number rounding.
	 *
	 * @param rounding the new number rounding
	 */
	void setNumberRounding(NumberRounding rounding);

	/**
	 * Sets the time rounding setting.
	 *
	 * @param rounding the new time rounding setting
	 */
	void setTimeRoundingSetting(TimeRoundingSetting rounding);

	/**
	 * Sets the amount rounding.
	 *
	 * @param rounding the new amount rounding
	 */
	void setAmountRounding(AmountRounding rounding);

}
