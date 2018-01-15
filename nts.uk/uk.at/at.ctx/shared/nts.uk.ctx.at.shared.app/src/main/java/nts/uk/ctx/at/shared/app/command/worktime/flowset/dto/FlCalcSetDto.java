/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlCalcGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.PrePlanWorkTimeCalcMethod;

/**
 * The Class FlCalcSetDto.
 */
@Value
public class FlCalcSetDto implements FlCalcGetMemento {

	/** The calc start time set. */
	private Integer calcStartTimeSet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlCalcGetMemento#
	 * getCalcStartTimeSet()
	 */
	@Override
	public PrePlanWorkTimeCalcMethod getCalcStartTimeSet() {
		return PrePlanWorkTimeCalcMethod.valueOf(this.calcStartTimeSet);
	}

}
