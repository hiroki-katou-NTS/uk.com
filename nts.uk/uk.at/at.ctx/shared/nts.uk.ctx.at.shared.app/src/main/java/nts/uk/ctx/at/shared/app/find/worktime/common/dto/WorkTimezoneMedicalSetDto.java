/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetSetMemento;

/**
 * The Class WorkTimezoneMedicalSetDto.
 */
@Getter
@Setter
public class WorkTimezoneMedicalSetDto implements WorkTimezoneMedicalSetSetMemento{
	
	/** The rounding set. */
	private TimeRoundingSettingDto roundingSet;

	/** The work system atr. */
	private Integer workSystemAtr;

	/** The application time. */
	private Integer applicationTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetSetMemento
	 * #setRoundingSet(nts.uk.ctx.at.shared.dom.common.timerounding.
	 * TimeRoundingSetting)
	 */
	@Override
	public void setRoundingSet(TimeRoundingSetting set) {
		this.roundingSet = new TimeRoundingSettingDto(set.getRoundingTime().value, set.getRounding().value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetSetMemento
	 * #setWorkSystemAtr(nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr)
	 */
	@Override
	public void setWorkSystemAtr(WorkSystemAtr atr) {
		this.workSystemAtr =atr.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetSetMemento
	 * #setApplicationTime(nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime)
	 */
	@Override
	public void setApplicationTime(OneDayTime time) {
		this.applicationTime = time.valueAsMinutes();
	}

}
