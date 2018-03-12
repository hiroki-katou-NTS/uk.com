/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowCalculateSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSet;

/**
 * The Class FlowWorkDedicateSettingDto.
 */
@Getter
@Setter
public class FlWorkDedSettingDto implements FlWorkDedSetMemento {

	/** The overtime setting. */
	private FlOTSetDto overtimeSetting;

	/** The calculate setting. */
	private FlCalcSetDto calculateSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkDedicateSettingSetMemento#setOvertimeSetting(nts.uk.ctx.at.shared
	 * .dom.worktime.flowset.FlowOTSet)
	 */
	@Override
	public void setOvertimeSetting(FlowOTSet otSet) {
		if (otSet != null) {
			this.overtimeSetting = new FlOTSetDto();
			otSet.saveToMemento(this.overtimeSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkDedicateSettingSetMemento#setCalculateSetting(nts.uk.ctx.at.
	 * shared.dom.worktime.flowset.FlowCalculateSet)
	 */
	@Override
	public void setCalculateSetting(FlowCalculateSet fcSet) {
		if (fcSet != null) {
			this.calculateSetting = new FlCalcSetDto();
			fcSet.saveToMemento(this.calculateSetting);
		}		
	}
}
