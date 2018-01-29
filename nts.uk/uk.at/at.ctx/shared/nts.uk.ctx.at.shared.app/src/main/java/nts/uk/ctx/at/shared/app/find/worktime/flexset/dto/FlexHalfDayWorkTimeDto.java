/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;

/**
 * The Class FlexHalfDayWorkTimeDto.
 */

@Getter
@Setter
public class FlexHalfDayWorkTimeDto implements FlexHalfDayWorkTimeSetMemento {

	/** The rest timezone. */
	private FlowWorkRestTimezoneDto restTimezone;

	/** The work timezone. */
	private FixedWorkTimezoneSetDto workTimezone;

	/** The ampm atr. */
	private Integer ampmAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setLstRestTimezone(java.util.List)
	 */
	@Override
	public void setRestTimezone(FlowWorkRestTimezone restTimezone) {
		if (restTimezone != null) {
			this.restTimezone = new FlowWorkRestTimezoneDto();
			restTimezone.saveToMemento(this.restTimezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setLstWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixedWorkTimezoneSet)
	 */
	@Override
	public void setWorkTimezone(FixedWorkTimezoneSet workTimezone) {
		if (workTimezone != null) {
			this.workTimezone = new FixedWorkTimezoneSetDto();
			workTimezone.saveToMemento(this.workTimezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setAmPmAtr(nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr)
	 */
	@Override
	public void setAmpmAtr(AmPmAtr ampmAtr) {
		this.ampmAtr = ampmAtr.value;
	}

}
