/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FixedChangeAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSetMemento;

/**
 * The Class FlowOTSetDto.
 */
@Getter
@Setter
public class FlOTSetDto implements FlowOTSetMemento {

	/** The fixed change atr. */
	private Integer fixedChangeAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSetSetMemento#
	 * setFixedChangeAtr(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FixedChangeAtr)
	 */
	@Override
	public void setFixedChangeAtr(FixedChangeAtr atr) {
		this.fixedChangeAtr = atr.value;
	}
}
