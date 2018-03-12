/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezoneSetMemento;

/**
 * The Class FlowWorkRestTimezoneDto.
 */
@Getter
@Setter
public class FlowWorkRestTimezoneDto implements FlowWorkRestTimezoneSetMemento {
	/** The fix rest time. */
	private boolean fixRestTime;

	/** The timezone. */
	private TimezoneOfFixedRestTimeSetDto fixedRestTimezone;

	/** The flow rest timezone. */
	private FlowRestTimezoneDto flowRestTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemeto#
	 * setFixRestTime(boolean)
	 */
	@Override
	public void setFixRestTime(boolean fixRestTime) {
		this.fixRestTime = fixRestTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemeto#
	 * setFixedRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * TimezoneOfFixedRestTimeSet)
	 */
	@Override
	public void setFixedRestTimezone(TimezoneOfFixedRestTimeSet fixedRestTimezone) {
		if (fixedRestTimezone != null) {
			this.fixedRestTimezone = new TimezoneOfFixedRestTimeSetDto();
			fixedRestTimezone.saveToMemento(this.fixedRestTimezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemeto#
	 * setFlowRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowRestTimezone)
	 */
	@Override
	public void setFlowRestTimezone(FlowRestTimezone flowRestTimezone) {
		if (flowRestTimezone != null) {
			this.flowRestTimezone = new FlowRestTimezoneDto();
			flowRestTimezone.saveToMemento(this.flowRestTimezone);
		}
	}

}
