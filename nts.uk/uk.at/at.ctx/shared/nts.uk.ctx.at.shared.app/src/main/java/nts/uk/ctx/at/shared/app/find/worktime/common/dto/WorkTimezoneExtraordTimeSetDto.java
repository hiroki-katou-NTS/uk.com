/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordTimeCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSet;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramset;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneExtraordTimeSetSetMemento;

/**
 * The Class WorkTimezoneExtraordTimeSetDto.
 */
@Getter
@Setter
public class WorkTimezoneExtraordTimeSetDto implements WorkTimezoneExtraordTimeSetSetMemento{
	
	/** The holiday frame set. */
	private HolidayFramsetDto holidayFrameSet;

	/** The time rounding set. */
	private TimeRoundingSettingDto timeRoundingSet;

	/** The ot frame set. */
	private ExtraordWorkOTFrameSetDto otFrameSet;

	/** The calculate method. */
	private Integer calculateMethod;

	public WorkTimezoneExtraordTimeSetDto() {
		this.holidayFrameSet = new HolidayFramsetDto();
		this.timeRoundingSet = new TimeRoundingSettingDto();
		this.otFrameSet = new ExtraordWorkOTFrameSetDto();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetSetMemento#setHolidayFrameSet(nts.uk.ctx.at.
	 * shared.dom.worktime.common.HolidayFramset)
	 */
	@Override
	public void setHolidayFrameSet(HolidayFramset set) {
		set.saveToMemento(this.holidayFrameSet);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetSetMemento#setTimeRoundingSet(nts.uk.ctx.at.
	 * shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setTimeRoundingSet(TimeRoundingSetting set) {
		this.timeRoundingSet = new TimeRoundingSettingDto(set.getRoundingTime().value, set.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetSetMemento#setOTFrameSet(nts.uk.ctx.at.shared.
	 * dom.worktime.common.ExtraordWorkOTFrameSet)
	 */
	@Override
	public void setOTFrameSet(ExtraordWorkOTFrameSet set) {
		set.saveToMemento(this.otFrameSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetSetMemento#setCalculateMethod(nts.uk.ctx.at.
	 * shared.dom.worktime.common.ExtraordTimeCalculateMethod)
	 */
	@Override
	public void setCalculateMethod(ExtraordTimeCalculateMethod method) {
		this.calculateMethod = method.value;
	}

}
