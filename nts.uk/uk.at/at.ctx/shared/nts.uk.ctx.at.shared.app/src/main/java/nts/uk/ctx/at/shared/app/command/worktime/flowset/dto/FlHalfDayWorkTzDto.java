/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSetting;

/**
 * The Class FlowHalfDayWorkTimezoneDto.
 */
public class FlHalfDayWorkTzDto implements FlHalfDayWtzGetMemento {

	/** The rest timezone. */
	private FlowWorkRestTimezoneDto restTimezone;

	/** The work time zone. */
	private FlWorkTzSettingDto workTimeZone;

	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlWtzSetting getWorkTimeZone() {
		return new FlWtzSetting(this.workTimeZone);
	}

}
