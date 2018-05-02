/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationGetMemento;

/**
 * The Class StampBreakCalculationDto.
 */
@Getter
@Setter
public class StampBreakCalculationDto implements StampBreakCalculationGetMemento {

	/** The use private go out rest. */
	private Boolean usePrivateGoOutRest;

	/** The use asso go out rest. */
	private Boolean useAssoGoOutRest;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationGetMemento
	 * #getUsePrivateGoOutRest()
	 */
	@Override
	public boolean getUsePrivateGoOutRest() {
		return this.usePrivateGoOutRest;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationGetMemento
	 * #getUseAssoGoOutRest()
	 */
	@Override
	public boolean getUseAssoGoOutRest() {
		return this.useAssoGoOutRest;
	}

}
