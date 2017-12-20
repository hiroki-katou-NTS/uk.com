/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkTimezoneSetting;

/**
 * The Class FlowHalfDayWorkTimezoneDto.
 */
@Getter
@Setter
public class FlHalfDayWorkTzDto implements FlHalfDayWtzSetMemento {

	/** The rest timezone. */
	private FlowWorkRestTimezoneDto restTimezone;

	/** The work time zone. */
	private FlWorkTzSettingDto workTimeZone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowHalfDayWorkTimezoneSetMemento#setRestTimezone(nts.uk.ctx.at.shared.
	 * dom.worktime.common.FlowWorkRestTimezone)
	 */
	@Override
	public void setRestTimezone(FlowWorkRestTimezone tzone) {
		tzone.saveToMemento(this.restTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowHalfDayWorkTimezoneSetMemento#setWorkTimeZone(nts.uk.ctx.at.shared.
	 * dom.worktime.flowset.FlowWorkTimezoneSetting)
	 */
	@Override
	public void setWorkTimeZone(FlowWorkTimezoneSetting tzone) {
		tzone.saveToMemento(this.workTimeZone);
	}
}
