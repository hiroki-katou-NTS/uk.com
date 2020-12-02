/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculation;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowRestSet;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaStampBreakCalculationGetMemento;

/**
 * The Class JpaFlowFixedRestSetGetMemento.
 */
public class JpaFlowFixedRestSetGetMemento implements FlowFixedRestSetGetMemento {

	/** The entity. */
	KshmtFlowRestSet entity;

	/**
	 * Instantiates a new jpa flow fixed rest set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowFixedRestSetGetMemento(KshmtFlowRestSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento#
	 * getCalculateMethod()
	 */
	@Override
	public FlowFixedRestCalcMethod getCalculateMethod() {
		return FlowFixedRestCalcMethod.valueOf(this.entity.getFixedRestCalcMethod());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetGetMemento#
	 * getCalculateFromStamp()
	 */
	@Override
	public StampBreakCalculation getCalculateFromStamp() {
		return new StampBreakCalculation(new JpaStampBreakCalculationGetMemento<KshmtFlowRestSet>(this.entity));
	}

}
