/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingSetMemento;

/**
 * The Class FlowWorkTimezoneSettingDto.
 */
@Getter
@Setter
public class FlWorkTzSettingDto implements FlWtzSettingSetMemento {

	/** The work time rounding. */
	private TimeRoundingSettingDto workTimeRounding;

	/** The lst OT timezone. */
	private List<FlOTTimezoneDto> lstOTTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkTimezoneSettingSetMemento#setWorkTimeRounding(nts.uk.ctx.at.
	 * shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setWorkTimeRounding(TimeRoundingSetting trSet) {
		this.workTimeRounding = new TimeRoundingSettingDto(trSet.getRoundingTime().value, trSet.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkTimezoneSettingSetMemento#setLstOTTimezone(java.util.List)
	 */
	@Override
	public void setLstOTTimezone(List<FlowOTTimezone> lstTzone) {
		this.lstOTTimezone = lstTzone.stream().map(item -> {
			FlOTTimezoneDto dto = new FlOTTimezoneDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
