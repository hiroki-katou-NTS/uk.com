/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySetSetMemento;

/**
 * The Class OtherEmTimezoneLateEarlySetDto.
 */
@Getter
@Setter
public class OtherEmTimezoneLateEarlySetDto implements OtherEmTimezoneLateEarlySetSetMemento{
	
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

	public OtherEmTimezoneLateEarlySetDto() {
		this.delTimeRoundingSet = new TimeRoundingSettingDto();
		this.graceTimeSet = new GraceTimeSettingDto();
		this.recordTimeRoundingSet = new TimeRoundingSettingDto();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetSetMemento#setDelTimeRoundingSet(nts.uk.ctx.at
	 * .shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setDelTimeRoundingSet(TimeRoundingSetting delTimeRoundingSet) {
		this.delTimeRoundingSet = new TimeRoundingSettingDto(delTimeRoundingSet.getRoundingTime().value,
				delTimeRoundingSet.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetSetMemento#setGraceTimeSet(nts.uk.ctx.at.
	 * shared.dom.worktime.common.GraceTimeSetting)
	 */
	@Override
	public void setGraceTimeSet(GraceTimeSetting graceTimeSet) {
		graceTimeSet.saveToMemento(this.graceTimeSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetSetMemento#setRecordTimeRoundingSet(nts.uk.ctx
	 * .at.shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setRecordTimeRoundingSet(TimeRoundingSetting recordTimeRoundingSet) {
		this.recordTimeRoundingSet = new TimeRoundingSettingDto(recordTimeRoundingSet.getRoundingTime().value,
				recordTimeRoundingSet.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetSetMemento#setLateEarlyAtr(nts.uk.ctx.at.
	 * shared.dom.worktime.common.LateEarlyAtr)
	 */
	@Override
	public void setLateEarlyAtr(LateEarlyAtr lateEarlyAtr) {
		this.lateEarlyAtr = lateEarlyAtr.value;
	}

}
