/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeGetMemento;

/**
 * The Class FlowTimeSettingDto.
 */

public class FlTimeSettingDto implements FlTimeGetMemento {

	/** The rounding. */
	private TimeRoundingSettingDto rounding;

	/** The passage time. */
	private Integer passageTime;

	@Override
	public TimeRoundingSetting getRouding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttendanceTime getPassageTime() {
		// TODO Auto-generated method stub
		return null;
	}
}
