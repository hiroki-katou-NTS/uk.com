/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DeductionTimeDto.
 */
@Getter
@Setter
public class DeductionTimeDto implements DeductionTimeGetMemento{

	/** The start. */
	private Integer start;

	/** The end. */
	private Integer end;

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.start);
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.end);
	}
	

	
}
