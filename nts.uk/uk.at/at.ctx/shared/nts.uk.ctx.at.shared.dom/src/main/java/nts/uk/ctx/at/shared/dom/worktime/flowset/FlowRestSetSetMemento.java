/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;

/**
 * The Interface FlowRestSetSetMemento.
 */
public interface FlowRestSetSetMemento {

	/**
	 * Sets the use stamp.
	 *
	 * @param val the new use stamp
	 */
	 void setUseStamp(boolean val);

	/**
	 * Sets the use stamp calc method.
	 *
	 * @param method the new use stamp calc method
	 */
	 void setUseStampCalcMethod(FlowRestClockCalcMethod method);

	/**
	 * Sets the time manager set atr.
	 *
	 * @param atr the new time manager set atr
	 */
	 void setTimeManagerSetAtr(RestClockManageAtr atr);

	/**
	 * Sets the calculate method.
	 *
	 * @param method the new calculate method
	 */
	 void setCalculateMethod(FlowRestCalcMethod method);
}
