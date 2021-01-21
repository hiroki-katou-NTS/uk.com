/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ScheduleBreakCalculation;
import nts.uk.ctx.at.shared.dom.worktime.flowset.StampBreakCalculation;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaScheduleBreakCalculationGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaStampBreakCalculationGetMemento;

/**
 * The Class JpaFlowFixedRestSetGetMemento.
 */
public class JpaFlowFixedRestSetGetMemento implements FlowFixedRestSetGetMemento {

	/** The entity. */
	KshmtWtFloBrFlAll entity;

	/**
	 * Instantiates a new jpa flow fixed rest set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowFixedRestSetGetMemento(KshmtWtFloBrFlAll entity) {
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
	 * getCalculateFromSchedule()
	 */
	@Override
	public ScheduleBreakCalculation getCalculateFromSchedule() {
		return new ScheduleBreakCalculation(new JpaScheduleBreakCalculationGetMemento<KshmtWtFloBrFlAll>(this.entity));
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
		return new StampBreakCalculation(new JpaStampBreakCalculationGetMemento<KshmtWtFloBrFlAll>(this.entity));
	}

}
