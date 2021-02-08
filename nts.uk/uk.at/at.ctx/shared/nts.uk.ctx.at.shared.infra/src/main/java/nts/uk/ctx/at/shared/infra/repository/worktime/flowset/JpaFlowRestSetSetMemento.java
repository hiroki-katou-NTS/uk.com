/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestClockCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowRestSet;

/**
 * The Class JpaFlowRestSetSetMemento.
 */
public class JpaFlowRestSetSetMemento implements FlowRestSetSetMemento {

	/** The entity. */
	KshmtFlowRestSet entity;

	/**
	 * Instantiates a new jpa flow rest set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowRestSetSetMemento(KshmtFlowRestSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetSetMemento#
	 * setUseStamp(boolean)
	 */
	@Override
	public void setUseStamp(boolean val) {
		this.entity.setUseStamp(BooleanGetAtr.getAtrByBoolean(val));
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
		this.entity.setUseStampCalcMethod(method.value);
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
		this.entity.setTimeManagerSetAtr(atr.value);
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
//		this.entity.setFixedCalculateMethod(method.value);
//	}

}
