/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;

/**
 * The Interface FlowRestSetGetMemento.
 */
public interface FlowRestSetGetMemento {

	/**
	 * Gets the use stamp.
	 *
	 * @return the use stamp
	 */
	 boolean getUseStamp();

	/**
	 * Gets the use stamp calc method.
	 *
	 * @return the use stamp calc method
	 */
	 FlowRestClockCalcMethod getUseStampCalcMethod();

	/**
	 * Gets the time manager set atr.
	 *
	 * @return the time manager set atr
	 */
	 RestClockManageAtr getTimeManagerSetAtr();

	/**
	 * Gets the calculate method.
	 *
	 * @return the calculate method
	 */
//	 FlowRestCalcMethod getCalculateMethod();
}
