/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.MultiStampTimePiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.StampPiorityAtr;

/**
 * The Class PrioritySettingDto.
 */
@Value
public class PrioritySettingDto implements PrioritySettingGetMemento {

	/** The priority atr. */
	private Integer priorityAtr;

	/** The stamp atr. */
	private Integer stampAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingGetMemento#
	 * getPriorityAtr()
	 */
	@Override
	public MultiStampTimePiorityAtr getPriorityAtr() {
		return MultiStampTimePiorityAtr.valueOf(this.priorityAtr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingGetMemento#
	 * getStampAtr()
	 */
	@Override
	public StampPiorityAtr getStampAtr() {
		return StampPiorityAtr.valueOf(this.stampAtr);
	}

}
