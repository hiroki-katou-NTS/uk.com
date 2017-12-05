/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingGetMemento;

/**
 * The Class FlowWorkTimezoneSettingDto.
 */
public class FlWorkTzSettingDto implements FlWtzSettingGetMemento {

	/** The work time rounding. */
	private TimeRoundingSettingDto workTimeRounding;

	/** The lst OT timezone. */
	private List<FlOTTimezoneDto> lstOTTimezone;

	@Override
	public TimeRoundingSetting getWorkTimeRounding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlOTTimezone> getLstOTTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

}
