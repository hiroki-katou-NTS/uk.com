/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimezoneDto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimezoneDto implements TimezoneSetMemento{
	
	/** The use atr. */
	public boolean useAtr;

	/** The work no. */
	public Integer workNo;

	/** The start. */
	public Integer start;

	/** The end. */
	public Integer end;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneSetMemento#setUseAtr(
	 * nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting)
	 */
	@Override
	public void setUseAtr(UseSetting useAtr) {
		this.useAtr = BooleanGetAtr.getAtrByInteger(useAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneSetMemento#setWorkNo(
	 * int)
	 */
	@Override
	public void setWorkNo(int workNo) {
		this.workNo = workNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneSetMemento#setStart(nts
	 * .uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStart(TimeWithDayAttr start) {
		this.start = start.valueAsMinutes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneSetMemento#setEnd(nts.
	 * uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.end = end.valueAsMinutes();
	}

}
