/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeRoundingSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySetGetMemento;

/**
 * The Class OtherEmTimezoneLateEarlySetDto.
 */
@Value
public class OtherEmTimezoneLateEarlySetDto implements OtherEmTimezoneLateEarlySetGetMemento {

	/** The del time rounding set. */
	private TimeRoundingSettingDto delTimeRoundingSet;

	/** The stamp exactly time is late early. */
	private boolean stampExactlyTimeIsLateEarly;

	/** The grace time set. */
	private GraceTimeSettingDto graceTimeSet;

	/** The record time rounding set. */
	private TimeRoundingSettingDto recordTimeRoundingSet;

	/** The late early atr. */
	private Integer lateEarlyAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetGetMemento#getDelTimeRoundingSet()
	 */
	@Override
	public TimeRoundingSetting getDelTimeRoundingSet() {
		return new TimeRoundingSetting(this.delTimeRoundingSet.getRoundingTime(),
				this.delTimeRoundingSet.getRounding());
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetGetMemento#getGraceTimeSet()
	 */
	@Override
	public GraceTimeSetting getGraceTimeSet() {
		return new GraceTimeSetting(this.graceTimeSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetGetMemento#getRecordTimeRoundingSet()
	 */
	@Override
	public TimeRoundingSetting getRecordTimeRoundingSet() {
		return new TimeRoundingSetting(this.recordTimeRoundingSet.getRoundingTime(),
				this.recordTimeRoundingSet.getRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetGetMemento#getLateEarlyAtr()
	 */
	@Override
	public LateEarlyAtr getLateEarlyAtr() {
		return LateEarlyAtr.valueOf(this.lateEarlyAtr);
	}

}
