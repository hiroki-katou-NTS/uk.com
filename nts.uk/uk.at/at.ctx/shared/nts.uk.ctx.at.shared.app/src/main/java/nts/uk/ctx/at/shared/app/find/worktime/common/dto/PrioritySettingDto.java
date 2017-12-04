/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.MultiStampTimePiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.StampPiorityAtr;

/**
 * The Class PrioritySettingDto.
 */
@Getter
@Setter
public class PrioritySettingDto implements PrioritySettingSetMemento{
	
	/** The priority atr. */
	private Integer priorityAtr;
	
	/** The stamp atr. */
	private Integer stampAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingSetMemento#
	 * setPriorityAtr(nts.uk.ctx.at.shared.dom.worktime.common.
	 * MultiStampTimePiorityAtr)
	 */
	@Override
	public void setPriorityAtr(MultiStampTimePiorityAtr atr) {
		this.priorityAtr = atr.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingSetMemento#
	 * setStampAtr(nts.uk.ctx.at.shared.dom.worktime.common.StampPiorityAtr)
	 */
	@Override
	public void setStampAtr(StampPiorityAtr atr) {
		this.stampAtr = atr.value;
	}



}
