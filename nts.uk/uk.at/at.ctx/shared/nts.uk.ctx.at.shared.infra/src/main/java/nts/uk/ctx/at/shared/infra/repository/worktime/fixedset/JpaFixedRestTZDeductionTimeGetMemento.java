/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFixedRestTZDeductionTimeGetMemento.
 */
public class JpaFixedRestTZDeductionTimeGetMemento implements DeductionTimeGetMemento {

	/** The start time. */
	private Integer startTime;

	/** The end time. */
	private Integer endTime;

	/**
	 * Instantiates a new jpa fixed rest TZ deduction time get memento.
	 *
	 * @param startTime the start time
	 * @param endTime the end time
	 */
	JpaFixedRestTZDeductionTimeGetMemento(Integer startTime, Integer endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getStart
	 * ()
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(startTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento#getEnd()
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(endTime);
	}

}
