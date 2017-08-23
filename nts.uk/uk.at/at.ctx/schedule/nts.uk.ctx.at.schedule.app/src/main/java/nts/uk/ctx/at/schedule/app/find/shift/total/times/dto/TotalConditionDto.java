package nts.uk.ctx.at.schedule.app.find.shift.total.times.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalConditionSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.total.times.setting.ConditionThresholdLimit;

/**
 * Gets the thresold lower limit.
 *
 * @return the thresold lower limit
 */
@Getter
@Setter
public class TotalConditionDto implements TotalConditionSetMemento {
	
	/** The upper limit setting atr. */
	//上限設定区分
	private String upperLimitSettingAtr;
	
	/** The lower limit setting atr. */
	//下限設定区分
	private String lowerLimitSettingAtr;
	
	/** The hresold upper limit. */
	//閾値上限
	private long thresoldUpperLimit;
	
	/** The thresold lower limit. */
	//閾値下限
	private long thresoldLowerLimit;

	/**
	 * Instantiates a new total condition dto.
	 *
	 * @param upperLimitSettingAtr the upper limit setting atr
	 * @param lowerLimitSettingAtr the lower limit setting atr
	 * @param thresoldUpperLimit the thresold upper limit
	 * @param thresoldLowerLimit the thresold lower limit
	 */
	public TotalConditionDto(String upperLimitSettingAtr, String lowerLimitSettingAtr, long thresoldUpperLimit,
			long thresoldLowerLimit) {
		this.upperLimitSettingAtr = upperLimitSettingAtr;
		this.lowerLimitSettingAtr = lowerLimitSettingAtr;
		this.thresoldUpperLimit = thresoldUpperLimit;
		this.thresoldLowerLimit = thresoldLowerLimit;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.total.times.TotalConditionSetMemento#setUpperLimitSettingAtr(java.lang.String)
	 */
	@Override
	public void setUpperLimitSettingAtr(String setUpperLimitSettingAtr) {
			this.upperLimitSettingAtr = setUpperLimitSettingAtr;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.total.times.TotalConditionSetMemento#setLowerLimitSettingAtr(java.lang.String)
	 */
	@Override
	public void setLowerLimitSettingAtr(String setLowerLimitSettingAtr) {
			this.lowerLimitSettingAtr = setLowerLimitSettingAtr;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.total.times.TotalConditionSetMemento#setThresoldUpperLimit(nts.uk.ctx.at.schedule.dom.shift.total.times.setting.ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldUpperLimit(ConditionThresholdLimit setThresoldUpperLimit) {
			this.thresoldUpperLimit = setThresoldUpperLimit.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.total.times.TotalConditionSetMemento#setThresoldLowerLimit(nts.uk.ctx.at.schedule.dom.shift.total.times.setting.ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldLowerLimit(ConditionThresholdLimit setThresoldLowerLimit) {
			this.thresoldLowerLimit = setThresoldLowerLimit.v();
	}

}
