/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento;

/**
 * The Class FlexHalfDayWorkTimeDto.
 */
@Getter
@Setter
public class FlexHalfDayWorkTimeDto implements FlexHalfDayWorkTimeGetMemento{
	
	/** The lst rest timezone. */
	private FlowWorkRestTimezoneDto restTimezone;
	
	/** The work timezone. */
	private FixedWorkTimezoneSetDto workTimezone;
	
	/** The Am pm atr. */
	private Integer ampmAtr;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#getLstRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		return new FlowWorkRestTimezone(this.restTimezone);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#getWorkTimezone()
	 */
	@Override
	public FixedWorkTimezoneSet getWorkTimezone() {
		return new FixedWorkTimezoneSet(this.workTimezone);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#getAmPmAtr()
	 */
	@Override
	public AmPmAtr getAmpmAtr() {
		return AmPmAtr.valueOf(this.ampmAtr);
	}

}
