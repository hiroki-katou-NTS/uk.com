/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeSetMemento;

/**
 * The Class FlowTimeSettingDto.
 */

@Getter
@Setter
public class FlTimeSettingDto implements FlTimeSetMemento {

	/** The rounding. */
	private TimeRoundingSettingDto rounding;

	/** The passage time. */
	private Integer passageTime;

	/**
	 * Sets the rouding.
	 *
	 * @param trSet the new rouding
	 */
	@Override
	public void setRouding(TimeRoundingSetting trSet) {
		this.rounding = new TimeRoundingSettingDto(trSet.getRoundingTime().value, trSet.getRounding().value);
	}

	/**
	 * Sets the passage time.
	 *
	 * @param at the new passage time
	 */
	@Override
	public void setPassageTime(AttendanceTime at) {
		this.passageTime = at.v();
	}
}
