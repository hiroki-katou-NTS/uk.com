package nts.uk.ctx.at.schedule.dom.shift.total.times;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.ConditionThresholdLimit;

/**
 * Gets the thresold lower limit.
 *
 * @return the thresold lower limit
 */

/**
 * Gets the thresold lower limit.
 *
 * @return the thresold lower limit
 */
@Getter
public class TotalCondition extends DomainObject{
	
	/** The upper limit setting atr. */
	//上限設定区分
	private String upperLimitSettingAtr;
	
	/** The lower limit setting atr. */
	//下限設定区分
	private String lowerLimitSettingAtr;
	
	/** The hresold upper limit. */
	//閾値上限
	private ConditionThresholdLimit thresoldUpperLimit;
	
	/** The thresold lower limit. */
	//閾値下限
	private ConditionThresholdLimit thresoldLowerLimit;
	
	/**
	 * Instantiates a new total condition.
	 *
	 * @param upperLimitSettingAtr the upper limit setting atr
	 * @param lowerLimitSettingAtr the lower limit setting atr
	 * @param thresoldUpperLimit the thresold upper limit
	 * @param thresoldLowerLimit the thresold lower limit
	 */
	public TotalCondition(String upperLimitSettingAtr, String lowerLimitSettingAtr,
			ConditionThresholdLimit thresoldUpperLimit, ConditionThresholdLimit thresoldLowerLimit) {
		this.upperLimitSettingAtr = upperLimitSettingAtr;
		this.lowerLimitSettingAtr = lowerLimitSettingAtr;
		this.thresoldUpperLimit = thresoldUpperLimit;
		this.thresoldLowerLimit = thresoldLowerLimit;
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TotalConditionSetMemento memento) {
		memento.setLowerLimitSettingAtr(this.lowerLimitSettingAtr);
		memento.setThresoldLowerLimit(this.thresoldLowerLimit);
		memento.setThresoldUpperLimit(this.thresoldUpperLimit);
		memento.setUpperLimitSettingAtr(this.upperLimitSettingAtr);
	}
	
}
