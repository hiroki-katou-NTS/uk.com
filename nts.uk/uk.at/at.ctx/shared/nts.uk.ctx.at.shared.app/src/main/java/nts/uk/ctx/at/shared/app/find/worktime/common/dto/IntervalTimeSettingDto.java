/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTime;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento;

/**
 * The Class IntervalTimeSettingDto.
 */
@Getter
@Setter
public class IntervalTimeSettingDto implements IntervalTimeSettingSetMemento{

	/** The use interval exemption time. */
	private boolean useIntervalExemptionTime;

	/** The interval exemption time round. */
	private TimeRoundingSettingDto intervalExemptionTimeRound;

	/** The interval time. */
	private IntervalTimeDto intervalTime;

	/** The use interval time. */
	private boolean useIntervalTime;

	/**
	 * Instantiates a new interval time setting dto.
	 */
	public IntervalTimeSettingDto() {
		this.intervalExemptionTimeRound = new TimeRoundingSettingDto();
		this.intervalTime = new IntervalTimeDto();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento#
	 * setUseIntervalExemptionTime(boolean)
	 */
	@Override
	public void setUseIntervalExemptionTime(boolean useIntervalExemptionTime) {
		this.useIntervalExemptionTime = useIntervalExemptionTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento#
	 * setIntervalExemptionTimeRound(nts.uk.ctx.at.shared.dom.common.
	 * timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setIntervalExemptionTimeRound(TimeRoundingSetting intervalExemptionTimeRound) {
		this.intervalExemptionTimeRound = new TimeRoundingSettingDto(intervalExemptionTimeRound.getRoundingTime().value,
				intervalExemptionTimeRound.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento#
	 * setIntervalTime(nts.uk.ctx.at.shared.dom.worktime.common.IntervalTime)
	 */
	@Override
	public void setIntervalTime(IntervalTime intervalTime) {
		intervalTime.saveToMemento(this.intervalTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingSetMemento#
	 * setUseIntervalTime(boolean)
	 */
	@Override
	public void setUseIntervalTime(boolean useIntervalTime) {
		this.useIntervalTime = useIntervalTime;
	}

	
}
