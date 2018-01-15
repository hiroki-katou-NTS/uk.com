/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DeductionTimeDto.
 */
@Getter
@Setter
public class DeductionTimeDto implements DeductionTimeSetMemento{

	/** The start. */
	private Integer start;

	/** The end. */
	private Integer end;

	/**
	 * Instantiates a new deduction time dto.
	 */
	public DeductionTimeDto() {}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento#setStart
	 * (nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStart(TimeWithDayAttr start) {
		this.start = start.valueAsMinutes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeSetMemento#setEnd(
	 * nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.end = end.valueAsMinutes();
	}

	
}
