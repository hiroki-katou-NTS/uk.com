/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSetGetMemento;

/**
 * The Class TotalRoundingSetDto.
 */
@Value
public class TotalRoundingSetDto implements TotalRoundingSetGetMemento {

	/** The set same frame rounding. */
	private Integer setSameFrameRounding;

	/** The frame stradd rounding set. */
	private Integer frameStraddRoundingSet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSetGetMemento#
	 * getSetSameFrameRounding()
	 */
	@Override
	public GoOutTimeRoundingMethod getSetSameFrameRounding() {
		return GoOutTimeRoundingMethod.valueOf(this.setSameFrameRounding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSetGetMemento#
	 * getFrameStraddRoundingSet()
	 */
	@Override
	public GoOutTimeRoundingMethod getFrameStraddRoundingSet() {
		return GoOutTimeRoundingMethod.valueOf(this.frameStraddRoundingSet);
	}

}
