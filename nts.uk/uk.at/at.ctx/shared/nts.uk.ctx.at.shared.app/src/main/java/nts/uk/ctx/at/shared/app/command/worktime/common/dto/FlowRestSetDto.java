/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestClockCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetGetMemento;

/**
 * The Class FlowRestSetDto.
 */
@Getter

/**
 * Sets the calculate method.
 *
 * @param calculateMethod the new calculate method
 */
@Setter
public class FlowRestSetDto implements FlowRestSetGetMemento {

	/** The use stamp. */
	private Boolean useStamp;

	/** The use stamp calc method. */
	private Integer useStampCalcMethod;

	/** The time manager set atr. */
	private Integer timeManagerSetAtr;

	/** The calculate method. */
	private Integer calculateMethod;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#getUseStamp()
	 */
	@Override
	public boolean getUseStamp() {
		return this.useStamp;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#getUseStampCalcMethod()
	 */
	@Override
	public FlowRestClockCalcMethod getUseStampCalcMethod() {
		return FlowRestClockCalcMethod.valueOf(this.useStampCalcMethod);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#getTimeManagerSetAtr()
	 */
	@Override
	public RestClockManageAtr getTimeManagerSetAtr() {
		return RestClockManageAtr.valueOf(this.timeManagerSetAtr);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#getCalculateMethod()
	 */
//	@Override
//	public FlowRestCalcMethod getCalculateMethod() {
//		return FlowRestCalcMethod.valueOf(this.calculateMethod);
//	}

}
