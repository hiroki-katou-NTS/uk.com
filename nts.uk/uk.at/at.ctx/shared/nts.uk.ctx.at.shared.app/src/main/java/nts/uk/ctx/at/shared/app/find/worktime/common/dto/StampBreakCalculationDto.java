/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationSetMemento;

/**
 * The Class StampBreakCalculationDto.
 */
@Getter
@Setter
public class StampBreakCalculationDto implements StampBreakCalculationSetMemento {

	/** The use private go out rest. */
	private boolean usePrivateGoOutRest;

	/** The use asso go out rest. */
	private boolean useAssoGoOutRest;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationSetMemento
	 * #setUsePrivateGoOutRest(boolean)
	 */
	@Override
	public void setUsePrivateGoOutRest(boolean val) {
		this.usePrivateGoOutRest = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculationSetMemento
	 * #setUseAssoGoOutRest(boolean)
	 */
	@Override
	public void setUseAssoGoOutRest(boolean val) {
		this.useAssoGoOutRest = val;
	}

}
