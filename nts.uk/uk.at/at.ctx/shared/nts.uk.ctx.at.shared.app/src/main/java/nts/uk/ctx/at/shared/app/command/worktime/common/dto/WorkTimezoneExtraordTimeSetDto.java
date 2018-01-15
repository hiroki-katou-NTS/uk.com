/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordTimeCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSet;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramset;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneExtraordTimeSetGetMemento;

/**
 * The Class WorkTimezoneExtraordTimeSetDto.
 */
@Value
public class WorkTimezoneExtraordTimeSetDto implements WorkTimezoneExtraordTimeSetGetMemento {

	/** The holiday frame set. */
	private HolidayFramsetDto holidayFrameSet;

	/** The time rounding set. */
	private TimeRoundingSettingDto timeRoundingSet;

	/** The ot frame set. */
	private ExtraordWorkOTFrameSetDto otFrameSet;

	/** The calculate method. */
	private Integer calculateMethod;

	/**
	 * Gets the holiday frame set.
	 *
	 * @return the holiday frame set
	 */
	@Override
	public HolidayFramset getHolidayFrameSet() {
		return new HolidayFramset(this.holidayFrameSet);
	}

	/**
	 * Gets the time rounding set.
	 *
	 * @return the time rounding set
	 */
	@Override
	public TimeRoundingSetting getTimeRoundingSet() {
		return new TimeRoundingSetting(this.timeRoundingSet.getRoundingTime(), this.timeRoundingSet.getRounding());
	}

	/**
	 * Gets the OT frame set.
	 *
	 * @return the OT frame set
	 */
	@Override
	public ExtraordWorkOTFrameSet getOTFrameSet() {
		return new ExtraordWorkOTFrameSet(this.otFrameSet);
	}

	/**
	 * Gets the calculate method.
	 *
	 * @return the calculate method
	 */
	@Override
	public ExtraordTimeCalculateMethod getCalculateMethod() {
		return ExtraordTimeCalculateMethod.valueOf(this.calculateMethod);
	}

}
