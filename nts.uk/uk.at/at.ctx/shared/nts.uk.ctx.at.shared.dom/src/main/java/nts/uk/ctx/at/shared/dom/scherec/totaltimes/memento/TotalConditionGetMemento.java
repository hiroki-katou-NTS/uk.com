/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

/**
 * The Interface TotalConditionGetMemento.
 */
public interface TotalConditionGetMemento {

	/**
	 * Gets the upper limit setting atr.
	 *
	 * @return the upper limit setting atr
	 */
	UseAtr getUpperLimitSettingAtr();

	/**
	 * Gets the lower limit setting atr.
	 *
	 * @return the lower limit setting atr
	 */
	UseAtr getLowerLimitSettingAtr();

	/**
	 * Gets the thresold upper limit.
	 *
	 * @return the thresold upper limit
	 */
	Optional<ConditionThresholdLimit> getThresoldUpperLimit();

	/**
	 * Gets the thresold lower limit.
	 *
	 * @return the thresold lower limit
	 */
	Optional<ConditionThresholdLimit> getThresoldLowerLimit();
	
	
	/**
	 * Gets the attendance item id.
	 *
	 * @return the attendance item id
	 */
	Optional<Integer> getAttendanceItemId();
}
