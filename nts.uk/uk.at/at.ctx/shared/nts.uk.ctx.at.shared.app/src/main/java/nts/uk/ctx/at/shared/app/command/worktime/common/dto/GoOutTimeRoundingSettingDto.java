/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingGoOutTimeSheet;

/**
 * The Class GoOutTimeRoundingSettingDto.
 */
@Getter
@Setter
public class GoOutTimeRoundingSettingDto implements GoOutTimeRoundingSettingGetMemento {

	/** The rounding method. */
	private Integer roundingMethod;
	
	/** The rounding setting. */
	private TimeRoundingSettingDto roundingSetting;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingSettingGetMemento#getRoundingMethod()
	 */
	@Override
	public RoundingGoOutTimeSheet getRoundingMethod() {
		return RoundingGoOutTimeSheet.valueOf(this.roundingMethod) ;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingSettingGetMemento#getRoundingSetting()
	 */
	@Override
	public TimeRoundingSetting getRoundingSetting() {
		return new TimeRoundingSetting(this.roundingSetting.getRoundingTime(), this.roundingSetting.getRounding());
	}
	

}
