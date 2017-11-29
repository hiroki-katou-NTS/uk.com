/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

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
	ConditionThresholdLimit getThresoldUpperLimit();

	/**
	 * Gets the thresold lower limit.
	 *
	 * @return the thresold lower limit
	 */
	ConditionThresholdLimit getThresoldLowerLimit();
	
	
	/**
	 * Gets the attendance item id.
	 *
	 * @return the attendance item id
	 */
	Integer getAttendanceItemId();
}
