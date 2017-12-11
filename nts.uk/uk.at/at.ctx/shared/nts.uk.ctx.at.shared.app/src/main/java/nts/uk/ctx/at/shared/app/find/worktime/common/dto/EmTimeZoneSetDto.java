/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;

/**
 * The Class EmTimeZoneSetDto.
 */

@Getter
public class EmTimeZoneSetDto implements EmTimeZoneSetSetMemento {

	/** The Employment time frame no. */
	private Integer employmentTimeFrameNo;

	/** The timezone. */
	private TimeZoneRoundingDto timezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetSetMemento#
	 * setEmploymentTimeFrameNo(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * EmTimeFrameNo)
	 */
	@Override
	public void setEmploymentTimeFrameNo(EmTimeFrameNo no) {
		this.employmentTimeFrameNo = no.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetSetMemento#
	 * setTimezone(nts.uk.ctx.at.shared.dom.worktime.fixedset.TimeZoneRounding)
	 */
	@Override
	public void setTimezone(TimeZoneRounding rounding) {
		rounding.saveToMemento(this.timezone);
	}
}
