/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.totaltimes;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class TotalCondition.
 */
@Getter
@Setter
public class TotalCondition {

	/** The upper limit setting atr. */
	// 上限設定区分
	private UseAtr upperLimitSettingAtr;

	/** The lower limit setting atr. */
	// 下限設定区分
	private UseAtr lowerLimitSettingAtr;

	/** The hresold upper limit. */
	// 閾値上限
	private ConditionThresholdLimit thresoldUpperLimit;

	/** The thresold lower limit. */
	// 閾値下限
	private ConditionThresholdLimit thresoldLowerLimit;

	/**
	 * Instantiates a new total condition.
	 *
	 * @param upperLimitSettingAtr
	 *            the upper limit setting atr
	 * @param lowerLimitSettingAtr
	 *            the lower limit setting atr
	 * @param thresoldUpperLimit
	 *            the thresold upper limit
	 * @param thresoldLowerLimit
	 *            the thresold lower limit
	 */
	public TotalCondition(TotalConditionGetMemento memento) {
		this.upperLimitSettingAtr = memento.getUpperLimitSettingAtr();
		this.lowerLimitSettingAtr = memento.getLowerLimitSettingAtr();
		this.thresoldUpperLimit = memento.getThresoldUpperLimit();
		this.thresoldLowerLimit = memento.getThresoldLowerLimit();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(TotalConditionSetMemento memento) {
		memento.setLowerLimitSettingAtr(this.lowerLimitSettingAtr);
		memento.setThresoldLowerLimit(this.thresoldLowerLimit);
		memento.setThresoldUpperLimit(this.thresoldUpperLimit);
		memento.setUpperLimitSettingAtr(this.upperLimitSettingAtr);
	}

}
