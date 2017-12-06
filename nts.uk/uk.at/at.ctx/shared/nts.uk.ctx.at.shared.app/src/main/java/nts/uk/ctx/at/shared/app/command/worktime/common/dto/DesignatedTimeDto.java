/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTimeGetMemento;

/**
 * The Class DesignatedTimeDto.
 */
@Getter
@Setter
public class DesignatedTimeDto implements DesignatedTimeGetMemento {

	/** The one day time. */
	private Integer oneDayTime;

	/** The half day time. */
	private Integer halfDayTime;

	/**
	 * Gets the one day time.
	 *
	 * @return the one day time
	 */
	@Override
	public OneDayTime getOneDayTime() {
		return new OneDayTime(this.oneDayTime);
	}

	/**
	 * Gets the half day time.
	 *
	 * @return the half day time
	 */
	@Override
	public OneDayTime getHalfDayTime() {
		return new OneDayTime(this.halfDayTime);
	}
	


}
