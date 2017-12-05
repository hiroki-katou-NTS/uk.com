/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;

/**
 * The Class FlowWorkRestTimezoneDto.
 */
@Getter
@Setter
public class FlowWorkRestTimezoneDto implements FlowWorkRestTimezoneGetMemento{
	
	/** The fix rest time. */
	private boolean fixRestTime;
	
	/** The fixed rest timezone. */
	private TimezoneOfFixedRestTimeSetDto fixedRestTimezone;
	
	/** The flow rest timezone. */
	private FlowRestTimezoneDto flowRestTimezone;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFixRestTime()
	 */
	@Override
	public boolean getFixRestTime() {
		return this.fixRestTime;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFixedRestTimezone()
	 */
	@Override
	public TimezoneOfFixedRestTimeSet getFixedRestTimezone() {
		return new TimezoneOfFixedRestTimeSet(this.fixedRestTimezone);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFlowRestTimezone()
	 */
	@Override
	public FlowRestTimezone getFlowRestTimezone() {
		return new FlowRestTimezone(this.flowRestTimezone);
	}
	
}
