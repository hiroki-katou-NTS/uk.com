/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ScheduleBreakCalculation;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculation;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlPK;

/**
 * The Class JpaFlexFlowFixedRestSetSetMemento.
 */
public class JpaFlexFlowFixedRestSetSetMemento implements FlowFixedRestSetSetMemento {

	/** The entity. */
	private KshmtWtFleBrFl entity;

	/**
	 * Instantiates a new jpa flex flow fixed rest set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlexFlowFixedRestSetSetMemento(KshmtWtFleBrFl entity) {
		super();
		if (entity.getKshmtWtFleBrFlPK() == null) {
			entity.setKshmtWtFleBrFlPK(new KshmtWtFleBrFlPK());
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
	 * setCalculateFromSchedule(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * ScheduleBreakCalculation)
	 */
	@Override
	public void setCalculateFromSchedule(ScheduleBreakCalculation val) {
		val.saveToMemento(new JpaScheduleBreakCalculationSetMemento<KshmtWtFleBrFl>(this.entity));
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
		val.saveToMemento(new JpaStampBreakCalculationSetMemento<KshmtWtFleBrFl>(this.entity));
	}

}
