/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculation;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSetPK;

/**
 * The Class JpaFlexFlowFixedRestSetSetMemento.
 */
public class JpaFlexFlowFixedRestSetSetMemento implements FlowFixedRestSetSetMemento {

	/** The entity. */
	private KshmtFlexRestSet entity;

	/**
	 * Instantiates a new jpa flex flow fixed rest set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlexFlowFixedRestSetSetMemento(KshmtFlexRestSet entity) {
		super();
		if (entity.getKshmtFlexRestSetPK() == null) {
			entity.setKshmtFlexRestSetPK(new KshmtFlexRestSetPK());
		}
		this.entity = entity;
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
		this.entity.setFixedRestCalcMethod(method.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetSetMemento#
	 * setCalculateFromStamp(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * StampBreakCalculation)
	 */
	@Override
	public void setCalculateFromStamp(StampBreakCalculation val) {
		val.saveToMemento(new JpaStampBreakCalculationSetMemento<KshmtFlexRestSet>(this.entity));
	}

}
