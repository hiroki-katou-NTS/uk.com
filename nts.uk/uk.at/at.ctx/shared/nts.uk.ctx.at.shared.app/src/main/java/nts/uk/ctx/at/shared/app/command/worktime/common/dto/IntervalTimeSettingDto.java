/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTime;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento;

/**
 * The Class IntervalTimeSettingDto.
 */
@Getter
@Setter
public class IntervalTimeSettingDto implements IntervalTimeSettingGetMemento {

	/** The use interval exemption time. */
	private boolean useIntervalExemptionTime;

	/** The interval exemption time round. */
	private TimeRoundingSettingDto intervalExemptionTimeRound;

	/** The interval time. */
	private IntervalTimeDto intervalTime;

	/** The use interval time. */
	private boolean useIntervalTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento#
	 * getuseIntervalExemptionTime()
	 */
	@Override
	public boolean getuseIntervalExemptionTime() {
		return this.useIntervalExemptionTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento#
	 * getIntervalExemptionTimeRound()
	 */
	@Override
	public TimeRoundingSetting getIntervalExemptionTimeRound() {
		return new TimeRoundingSetting(this.intervalExemptionTimeRound.getRoundingTime(),
				this.intervalExemptionTimeRound.getRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento#
	 * getIntervalTime()
	 */
	@Override
	public IntervalTime getIntervalTime() {
		return new IntervalTime(this.intervalTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSettingGetMemento#
	 * getuseIntervalTime()
	 */
	@Override
	public boolean getuseIntervalTime() {
		return this.useIntervalTime;
	}
}
