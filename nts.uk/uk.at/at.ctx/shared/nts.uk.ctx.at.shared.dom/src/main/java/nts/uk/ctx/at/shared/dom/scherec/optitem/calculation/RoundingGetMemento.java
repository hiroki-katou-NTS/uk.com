/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface RoundingGetMemento.
 */
public interface RoundingGetMemento {

	/**
	 * Gets the number rounding.
	 *
	 * @return the number rounding
	 */
	NumberRoundingSetting getNumberRounding();

	/**
	 * Gets the time rounding setting.
	 *
	 * @return the time rounding setting
	 */
	TimeRoundingSetting getTimeRoundingSetting();

	/**
	 * Gets the amount rounding.
	 *
	 * @return the amount rounding
	 */
	AmountRoundingSetting getAmountRounding();

}
