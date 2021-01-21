/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class StampReflectTimezoneDto.
 */

@Getter
@Setter
public class StampReflectTimezoneDto implements StampReflectTimezoneSetMemento{

	/** The work no. */
	private Integer workNo;

	/** The classification. */
	private Integer classification;

	/** The end time. */
	private Integer endTime;

	/** The start time. */
	private Integer startTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.StampReflectTimezoneSetMemento
	 * #getWorkNo(nts.uk.ctx.at.shared.dom.worktime.fixedset.WorkNo)
	 */
	@Override
	public void setWorkNo(WorkNo workNo) {
		this.workNo = workNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.StampReflectTimezoneSetMemento
	 * #getClassification(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * GoLeavingWorkAtr)
	 */
	@Override
	public void setClassification(GoLeavingWorkAtr classification) {
		this.classification = classification.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.StampReflectTimezoneSetMemento
	 * #getEndTime(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEndTime(TimeWithDayAttr endTime) {
		this.endTime = endTime.valueAsMinutes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.StampReflectTimezoneSetMemento
	 * #getStartTime(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStartTime(TimeWithDayAttr startTime) {
		this.startTime = startTime.valueAsMinutes();
	}

}
