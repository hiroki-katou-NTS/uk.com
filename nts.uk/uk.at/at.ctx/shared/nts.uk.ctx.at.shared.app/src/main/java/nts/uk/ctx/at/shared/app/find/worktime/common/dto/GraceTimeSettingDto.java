/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime;

/**
 * The Class GraceTimeSettingDto.
 */
@Getter
@Setter
public class GraceTimeSettingDto implements GraceTimeSettingSetMemento{

	/** The include working hour. */
	private boolean includeWorkingHour;
	
	/** The grace time. */
	private Integer graceTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSettingSetMemento#
	 * setIncludeWorkingHour(boolean)
	 */
	@Override
	public void setIncludeWorkingHour(boolean includeWorkingHour) {
		this.includeWorkingHour = includeWorkingHour;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSettingSetMemento#
	 * setGraceTime(nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime)
	 */
	@Override
	public void setGraceTime(LateEarlyGraceTime graceTime) {
		this.graceTime = graceTime.valueAsMinutes();
	}
}
