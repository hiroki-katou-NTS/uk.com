/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FixedChangeAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTGetMemento;

/**
 * The Class FlOTSetDto.
 */
@Value
public class FlOTSetDto implements FlowOTGetMemento {

	/** The fixed change atr. */
	private Integer fixedChangeAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTGetMemento#
	 * getFixedChangeAtr()
	 */
	@Override
	public FixedChangeAtr getFixedChangeAtr() {
		return FixedChangeAtr.valueOf(this.fixedChangeAtr);
	}
}
