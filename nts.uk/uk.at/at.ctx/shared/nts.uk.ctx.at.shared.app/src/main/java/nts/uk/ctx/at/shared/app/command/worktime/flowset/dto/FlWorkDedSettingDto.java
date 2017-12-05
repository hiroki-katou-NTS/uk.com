/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlCalcSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedGetMemento;

/**
 * The Class FlWorkDedSettingDto.
 */
@Value
public class FlWorkDedSettingDto implements FlWorkDedGetMemento {

	/** The overtime setting. */
	private FlOTSetDto overtimeSetting;

	/** The calculate setting. */
	private FlCalcSetDto calculateSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedGetMemento#
	 * getOvertimeSetting()
	 */
	@Override
	public FlOTSet getOvertimeSetting() {
		return new FlOTSet(this.overtimeSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedGetMemento#
	 * getCalculateSetting()
	 */
	@Override
	public FlCalcSet getCalculateSetting() {
		return new FlCalcSet(this.calculateSetting);
	}
}
