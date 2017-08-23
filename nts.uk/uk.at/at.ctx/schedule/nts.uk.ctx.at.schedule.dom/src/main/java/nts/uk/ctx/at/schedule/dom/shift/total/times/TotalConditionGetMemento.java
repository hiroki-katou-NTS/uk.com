package nts.uk.ctx.at.schedule.dom.shift.total.times;

import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.ConditionThresholdLimit;

/**
 * The Interface TotalConditionGetMamento.
 */
public interface TotalConditionGetMemento {

	/**
	 * Gets the upper limit setting atr.
	 *
	 * @return the upper limit setting atr
	 */
	String getUpperLimitSettingAtr();
	
	/**
	 * Gets the lower limit setting atr.
	 *
	 * @return the lower limit setting atr
	 */
	String getLowerLimitSettingAtr();
	
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
}
