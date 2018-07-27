/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingGoOutTimeSheet;

/**
 * The Class GoOutTimeRoundingSettingDto.
 */
@Getter
@Setter
public class GoOutTimeRoundingSettingDto implements GoOutTimeRoundingSettingSetMemento {

	/** The rounding method. */
	private Integer roundingMethod;
	
	/** The rounding setting. */
	private TimeRoundingSettingDto roundingSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimeRoundingSettingSetMemento#setRoundingMethod(nts.uk.ctx.at.shared
	 * .dom.worktime.common.GoOutTimeRoundingMethod)
	 */
	@Override
	public void setRoundingMethod(RoundingGoOutTimeSheet roundingMethod) {
		this.roundingMethod = roundingMethod.value;
	}

	@Override
	public void setRoundingSetting(TimeRoundingSetting roundingSetting) {
		this.roundingSetting = new TimeRoundingSettingDto(roundingSetting.getRoundingTime().value,
				roundingSetting.getRounding().value);
	}

}
