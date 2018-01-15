/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSetSetMemento;

/**
 * The Class TotalRoundingSetDto.
 */
@Getter
@Setter
public class TotalRoundingSetDto implements TotalRoundingSetSetMemento{
	
	/** The set same frame rounding. */
	private Integer setSameFrameRounding;
	
	/** The frame stradd rounding set. */
	private Integer frameStraddRoundingSet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSetSetMemento#
	 * setSetSameFrameRounding(nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimeRoundingMethod)
	 */
	@Override
	public void setSetSameFrameRounding(GoOutTimeRoundingMethod method) {
		this.setSameFrameRounding = method.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSetSetMemento#
	 * setFrameStraddRoundingSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimeRoundingMethod)
	 */
	@Override
	public void setFrameStraddRoundingSet(GoOutTimeRoundingMethod method) {
		this.frameStraddRoundingSet = method.value;
	}

	
	

}
