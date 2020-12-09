/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ScheduleBreakCalculation;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculation;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaScheduleBreakCalculationSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaStampBreakCalculationSetMemento;

/**
 * The Class JpaFlowFixedRestSetSetMemento.
 */
public class JpaFlowFixedRestSetSetMemento implements FlowFixedRestSetSetMemento {

	/** The entity. */
	KshmtWtFloBrFlAll entity;

	/**
	 * Instantiates a new jpa flow fixed rest set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowFixedRestSetSetMemento(KshmtWtFloBrFlAll entity) {
		super();
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
		val.saveToMemento(new JpaScheduleBreakCalculationSetMemento<KshmtWtFloBrFlAll>(this.entity));
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
		val.saveToMemento(new JpaStampBreakCalculationSetMemento<KshmtWtFloBrFlAll>(this.entity));
	}

}
