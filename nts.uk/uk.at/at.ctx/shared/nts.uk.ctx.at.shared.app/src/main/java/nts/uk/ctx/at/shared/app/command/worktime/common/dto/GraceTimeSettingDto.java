/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime;

/**
 * The Class GraceTimeSettingDto.
 */
@Getter
@Setter
public class GraceTimeSettingDto implements GraceTimeSettingGetMemento{

	/** The include working hour. */
	private boolean includeWorkingHour;
	
	/** The grace time. */
	private Integer graceTime;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSettingGetMemento#getIncludeWorkingHour()
	 */
	@Override
	public boolean getIncludeWorkingHour() {
		return this.includeWorkingHour;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSettingGetMemento#getGraceTime()
	 */
	@Override
	public LateEarlyGraceTime getGraceTime() {
		return new LateEarlyGraceTime(this.graceTime);
	}
	

}
