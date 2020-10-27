/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.ScheduleBreakCalculationSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowRestSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JpaScheduleBreakCalculationSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaScheduleBreakCalculationSetMemento<T extends ContractUkJpaEntity>
		implements ScheduleBreakCalculationSetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa schedule break calculation set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaScheduleBreakCalculationSetMemento(T entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * ScheduleBreakCalculationSetMemento#setIsReferRestTime(boolean)
	 */
	@Override
	public void setIsReferRestTime(boolean val) {
		if (this.entity instanceof KshmtFlowRestSet) {
			((KshmtFlowRestSet) this.entity).setIsReferRestTime(BooleanGetAtr.getAtrByBoolean(val));
			return;
		}
		if (this.entity instanceof KshmtFlexRestSet) {
			((KshmtFlexRestSet) this.entity).setIsReferRestTime(BooleanGetAtr.getAtrByBoolean(val));
			return;
		}
		throw new IllegalStateException("entity type is not valid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * ScheduleBreakCalculationSetMemento#setIsCalcFromSchedule(boolean)
	 */
	@Override
	public void setIsCalcFromSchedule(boolean val) {
		if (this.entity instanceof KshmtFlowRestSet) {
			((KshmtFlowRestSet) this.entity).setIsCalcFromSchedule(BooleanGetAtr.getAtrByBoolean(val));
			return;
		}
		if (this.entity instanceof KshmtFlexRestSet) {
			((KshmtFlexRestSet) this.entity).setIsCalcFromSchedule(BooleanGetAtr.getAtrByBoolean(val));
			return;
		}
		throw new IllegalStateException("entity type is not valid");
	}

}
