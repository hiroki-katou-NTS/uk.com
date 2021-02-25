/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestClockCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;

/**
 * The Class JpaFlexFlowRestSetGetMemento.
 */
public class JpaFlexFlowRestSetGetMemento implements FlowRestSetGetMemento {

	/** The entity. */
	private KshmtWtFleBrFl entity;
	
	/**
	 * Instantiates a new jpa flex flow rest set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexFlowRestSetGetMemento(KshmtWtFleBrFl entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#
	 * getUseStamp()
	 */
	@Override
	public boolean getUseStamp() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseStamp());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#
	 * getUseStampCalcMethod()
	 */
	@Override
	public FlowRestClockCalcMethod getUseStampCalcMethod() {
		return FlowRestClockCalcMethod.valueOf(this.entity.getUseStampCalcMethod());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#
	 * getTimeManagerSetAtr()
	 */
	@Override
	public RestClockManageAtr getTimeManagerSetAtr() {
		return RestClockManageAtr.valueOf(this.entity.getTimeManagerSetAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#
	 * getCalculateMethod()
	 */
	@Override
	public FlowRestCalcMethod getCalculateMethod() {
		return FlowRestCalcMethod.valueOf(this.entity.getCalculateMethod());
	}

}
