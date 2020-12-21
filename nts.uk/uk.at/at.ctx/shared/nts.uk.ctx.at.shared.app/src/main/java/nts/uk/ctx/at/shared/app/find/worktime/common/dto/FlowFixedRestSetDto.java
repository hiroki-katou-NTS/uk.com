/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculation;

/**
 * The Class FlowFixedRestSetDto.
 */
@Getter
@Setter
public class FlowFixedRestSetDto implements FlowFixedRestSetSetMemento {

	/** The calculate method. */
	private Integer calculateMethod;

	/** The calculate from schedule. */
	private ScheduleBreakCalculationDto calculateFromSchedule = new ScheduleBreakCalculationDto();

	/** The calculate from stamp. */
	private StampBreakCalculationDto calculateFromStamp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetSetMemento#
	 * setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowFixedRestCalcMethod)
	 */
	@Override
	public void setCalculateMethod(FlowFixedRestCalcMethod val) {
		this.calculateMethod = val.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetSetMemento#
	 * setCalculateFromStamp(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * StampBreakCalculation)
	 */
	@Override
	public void setCalculateFromStamp(StampBreakCalculation val) {
		this.calculateFromStamp = new StampBreakCalculationDto();
		val.saveToMemento(this.calculateFromStamp);
	}

}
