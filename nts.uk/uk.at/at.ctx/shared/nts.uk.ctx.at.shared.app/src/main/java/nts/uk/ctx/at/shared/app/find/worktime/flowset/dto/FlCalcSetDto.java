/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowCalculateSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.PrePlanWorkTimeCalcMethod;

/**
 * The Class FlowCalculateSetDto.
 */
@Getter
@Setter
public class FlCalcSetDto implements FlowCalculateSetMemento {

	/** The calc start time set. */
	private Integer calcStartTimeSet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowCalculateSetSetMemento#
	 * setCalcStartTimeSet(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * PrePlanWorkTimeCalcMethod)
	 */
	@Override
	public void setCalcStartTimeSet(PrePlanWorkTimeCalcMethod method) {
		this.calcStartTimeSet = method.value;
	}
}
