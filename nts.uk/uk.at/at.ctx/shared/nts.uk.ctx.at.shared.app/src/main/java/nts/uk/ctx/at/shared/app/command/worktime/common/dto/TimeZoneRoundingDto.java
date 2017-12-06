/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeZoneRoundingDto.
 */
@Value
public class TimeZoneRoundingDto implements TimeZoneRoundingGetMemento {

	/** The rounding. */
	private TimeRoundingSettingDto rounding;

	/** The start. */
	private Integer start;

	/** The end. */
	private Integer end;

	/**
	 * Gets the rounding.
	 *
	 * @return the rounding
	 */
	@Override
	public TimeRoundingSetting getRounding() {
		return new TimeRoundingSetting(this.rounding.getRoundingTime(), this.rounding.getRounding());
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.start);
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.end);
	}
}
