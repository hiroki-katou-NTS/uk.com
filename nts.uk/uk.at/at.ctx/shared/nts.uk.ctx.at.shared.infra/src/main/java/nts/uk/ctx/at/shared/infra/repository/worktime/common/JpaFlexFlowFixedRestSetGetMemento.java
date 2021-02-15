/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculation;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSetPK;

/**
 * The Class JpaFlexFlowFixedRestSetGetMemento.
 */
public class JpaFlexFlowFixedRestSetGetMemento implements FlowFixedRestSetGetMemento {

	/** The entity. */
	private KshmtFlexRestSet entity;

	/**
	 * Instantiates a new jpa flex flow fixed rest set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlexFlowFixedRestSetGetMemento(KshmtFlexRestSet entity) {
		super();
		if (entity.getKshmtFlexRestSetPK() == null) {
			entity.setKshmtFlexRestSetPK(new KshmtFlexRestSetPK());
		}
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
		return new StampBreakCalculation(new JpaStampBreakCalculationGetMemento<KshmtFlexRestSet>(this.entity));
	}

}
