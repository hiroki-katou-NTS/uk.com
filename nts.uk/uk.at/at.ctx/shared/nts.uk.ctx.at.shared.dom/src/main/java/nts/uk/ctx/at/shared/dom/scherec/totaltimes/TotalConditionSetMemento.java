/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

/**
 * The Interface TotalConditionSetMemento.
 */
public interface TotalConditionSetMemento {

	/**
	 * Sets the upper limit setting atr.
	 *
	 * @param setUpperLimitSettingAtr the new upper limit setting atr
	 */
	void  setUpperLimitSettingAtr(UseAtr setUpperLimitSettingAtr);

	/**
	 * Sets the lower limit setting atr.
	 *
	 * @param setLowerLimitSettingAtr the new lower limit setting atr
	 */
	void  setLowerLimitSettingAtr(UseAtr setLowerLimitSettingAtr);

	/**
	 * Sets the thresold upper limit.
	 *
	 * @param setThresoldUpperLimit the new thresold upper limit
	 */
	void  setThresoldUpperLimit(ConditionThresholdLimit setThresoldUpperLimit);

	/**
	 * Sets the thresold lower limit.
	 *
	 * @param setThresoldLowerLimit the new thresold lower limit
	 */
	void  setThresoldLowerLimit(ConditionThresholdLimit setThresoldLowerLimit);

	
	/**
	 * Sets the attendance item id.
	 *
	 * @param attendanceItemId the new attendance item id
	 */
	void setAttendanceItemId(Integer attendanceItemId);
}
