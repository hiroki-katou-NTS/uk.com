package nts.uk.ctx.at.schedule.dom.shift.total.times;

import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.ConditionThresholdLimit;

/**
 * The Interface TotalConditionSetMamento.
 */
public interface TotalConditionSetMemento {
	
	/**
	 * Sets the upper limit setting atr.
	 *
	 * @param setUpperLimitSettingAtr the new upper limit setting atr
	 */
	void  setUpperLimitSettingAtr(String setUpperLimitSettingAtr);
	
	/**
	 * Sets the lower limit setting atr.
	 *
	 * @param setLowerLimitSettingAtr the new lower limit setting atr
	 */
	void  setLowerLimitSettingAtr(String setLowerLimitSettingAtr);
	
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
	
	
}
