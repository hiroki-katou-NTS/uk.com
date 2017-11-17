/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

/**
 * The Class TotalConditionDto.
 */
@Getter
@Setter
public class TotalConditionDto implements TotalConditionSetMemento {

	/** The upper limit setting atr. */
	private Integer upperLimitSettingAtr;

	/** The lower limit setting atr. */
	private Integer lowerLimitSettingAtr;

	/** The thresold upper limit. */
	private Long thresoldUpperLimit;

	/** The thresold lower limit. */
	private Long thresoldLowerLimit;

	/** The atd item id. */
	private Integer atdItemId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.total.times.TotalConditionSetMemento#
	 * setThresoldUpperLimit(nts.uk.ctx.at.schedule.dom.shift.total.times.
	 * setting.ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldUpperLimit(ConditionThresholdLimit setThresoldUpperLimit) {
		this.thresoldUpperLimit = setThresoldUpperLimit.v().longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.total.times.TotalConditionSetMemento#
	 * setThresoldLowerLimit(nts.uk.ctx.at.schedule.dom.shift.total.times.
	 * setting.ConditionThresholdLimit)
	 */
	@Override
	public void setThresoldLowerLimit(ConditionThresholdLimit setThresoldLowerLimit) {
		this.thresoldLowerLimit = setThresoldLowerLimit.v().longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#
	 * setUpperLimitSettingAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * UseAtr)
	 */
	@Override
	public void setUpperLimitSettingAtr(UseAtr upperLimitSettingAtr) {
		this.upperLimitSettingAtr = upperLimitSettingAtr.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#
	 * setLowerLimitSettingAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.
	 * UseAtr)
	 */
	@Override
	public void setLowerLimitSettingAtr(UseAtr lowerLimitSettingAtr) {
		this.lowerLimitSettingAtr = lowerLimitSettingAtr.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalConditionSetMemento#
	 * setAtdItemId(java.lang.Integer)
	 */
	@Override
	public void setAtdItemId(Integer atdItemId) {
		this.atdItemId = atdItemId;
	}

}
