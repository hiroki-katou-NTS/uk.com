/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;

/**
 * The Class EmTimeZoneSetDto.
 */
@Getter
@Setter
public class EmTimeZoneSetDto implements EmTimeZoneSetGetMemento {

	/** The employment time frame no. */
	private Integer employmentTimeFrameNo;

	/** The timezone. */
	private TimeZoneRoundingDto timezone;


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetGetMemento#getEmploymentTimeFrameNo()
	 */
	@Override
	public EmTimeFrameNo getEmploymentTimeFrameNo() {
		return new EmTimeFrameNo(this.employmentTimeFrameNo);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetGetMemento#getTimezone()
	 */
	@Override
	public TimeZoneRounding getTimezone() {
		return new TimeZoneRounding(this.timezone);
	}
	
}
