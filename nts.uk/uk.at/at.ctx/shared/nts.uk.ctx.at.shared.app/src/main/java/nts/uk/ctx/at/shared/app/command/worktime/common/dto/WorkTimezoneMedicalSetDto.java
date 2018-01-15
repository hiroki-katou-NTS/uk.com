/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetGetMemento;

/**
 * The Class WorkTimezoneMedicalSetDto.
 */
@Value
public class WorkTimezoneMedicalSetDto implements WorkTimezoneMedicalSetGetMemento {

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
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetGetMemento
	 * #getRoundingSet()
	 */
	@Override
	public TimeRoundingSetting getRoundingSet() {
		return new TimeRoundingSetting(this.roundingSet.getRoundingTime(), this.roundingSet.getRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetGetMemento
	 * #getWorkSystemAtr()
	 */
	@Override
	public WorkSystemAtr getWorkSystemAtr() {
		return WorkSystemAtr.valueOf(this.workSystemAtr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetGetMemento
	 * #getApplicationTime()
	 */
	@Override
	public OneDayTime getApplicationTime() {
		return new OneDayTime(this.applicationTime);
	}

}
