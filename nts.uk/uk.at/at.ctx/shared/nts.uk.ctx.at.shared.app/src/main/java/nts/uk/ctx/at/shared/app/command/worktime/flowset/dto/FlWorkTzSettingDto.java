/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingGetMemento;

/**
 * The Class FlWorkTzSettingDto.
 */
@Value
public class FlWorkTzSettingDto implements FlWtzSettingGetMemento {

	/** The work time rounding. */
	private TimeRoundingSettingDto workTimeRounding;

	/** The lst OT timezone. */
	private List<FlOTTimezoneDto> lstOTTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingGetMemento#
	 * getWorkTimeRounding()
	 */
	@Override
	public TimeRoundingSetting getWorkTimeRounding() {
		return new TimeRoundingSetting(this.workTimeRounding.getRoundingTime(), this.workTimeRounding.getRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingGetMemento#
	 * getLstOTTimezone()
	 */
	@Override
	public List<FlowOTTimezone> getLstOTTimezone() {
		return this.lstOTTimezone.stream().map(item -> new FlowOTTimezone(item)).collect(Collectors.toList());
	}

}
