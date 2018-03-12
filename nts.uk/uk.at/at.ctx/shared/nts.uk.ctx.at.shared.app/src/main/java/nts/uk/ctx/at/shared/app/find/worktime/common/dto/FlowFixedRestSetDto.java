/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetSetMemento;

/**
 * The Class FlowFixedRestSetDto.
 */
@Getter
@Setter
public class FlowFixedRestSetDto implements FlowFixedRestSetSetMemento {

	/** The is refer rest time. */
	private boolean isReferRestTime;

	/** The use private go out rest. */
	private boolean usePrivateGoOutRest;

	/** The use asso go out rest. */
	private boolean useAssoGoOutRest;

	/** The calculate method. */
	private Integer calculateMethod;

	/**
	 * Instantiates a new flow fixed rest set dto.
	 */
	public FlowFixedRestSetDto() {}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetSetMemento#
	 * setIsReferRestTime(boolean)
	 */
	@Override
	public void setIsReferRestTime(boolean val) {
		this.isReferRestTime = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetSetMemento#
	 * setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowFixedRestCalcMethod)
	 */
	@Override
	public void setCalculateMethod(FlowFixedRestCalcMethod method) {
		this.calculateMethod = method.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetSetMemento#
	 * setUsePrivateGoOutRest(boolean)
	 */
	@Override
	public void setUsePrivateGoOutRest(boolean val) {
		this.usePrivateGoOutRest = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetSetMemento#
	 * setUseAssoGoOutRest(boolean)
	 */
	@Override
	public void setUseAssoGoOutRest(boolean val) {
		this.useAssoGoOutRest = val;
	}
}
