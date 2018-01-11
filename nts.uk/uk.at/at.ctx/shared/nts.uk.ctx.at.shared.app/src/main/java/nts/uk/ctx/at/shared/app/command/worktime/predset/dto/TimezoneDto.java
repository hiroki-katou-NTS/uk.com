/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.predset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimezoneDto.
 */
@Getter
@Setter
public class TimezoneDto implements TimezoneGetMemento{
	
	/** The use atr. */
	public boolean useAtr;

	/** The work no. */
	public Integer workNo;

	/** The start. */
	public int start;

	/** The end. */
	public int end;


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento#getUseAtr()
	 */
	@Override
	public UseSetting getUseAtr() {
		return UseSetting.valueOf(BooleanGetAtr.getAtrByBoolean(this.useAtr));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento#getWorkNo()
	 */
	@Override
	public int getWorkNo() {
		return this.workNo;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento#getStart()
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.start);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento#getEnd()
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.end);
	}
	

}
