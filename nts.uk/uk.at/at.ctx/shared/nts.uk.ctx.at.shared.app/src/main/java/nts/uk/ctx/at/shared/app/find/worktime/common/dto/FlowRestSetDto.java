/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestClockCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetSetMemento;

/**
 * The Class FlowRestSetDto.
 */
@Getter
@Setter
public class FlowRestSetDto implements FlowRestSetSetMemento {

	/** The use stamp. */
	private Boolean useStamp;

	/** The use stamp calc method. */
	private Integer useStampCalcMethod;

	/** The time manager set atr. */
	private Integer timeManagerSetAtr;

	/** The calculate method. */
	private Integer calculateMethod;

	/**
	 * Instantiates a new flow rest set dto.
	 */
	public FlowRestSetDto() {}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetSetMemento#
	 * setUseStamp(boolean)
	 */
	@Override
	public void setUseStamp(boolean val) {
		this.useStamp = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetSetMemento#
	 * setUseStampCalcMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowRestClockCalcMethod)
	 */
	@Override
	public void setUseStampCalcMethod(FlowRestClockCalcMethod method) {
		this.useStampCalcMethod = method.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetSetMemento#
	 * setTimeManagerSetAtr(nts.uk.ctx.at.shared.dom.worktime.common.
	 * RestClockManageAtr)
	 */
	@Override
	public void setTimeManagerSetAtr(RestClockManageAtr atr) {
		this.timeManagerSetAtr = atr.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetSetMemento#
	 * setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowRestCalcMethod)
	 */
//	@Override
//	public void setCalculateMethod(FlowRestCalcMethod method) {
//		this.calculateMethod = method.value;
//	}
}
