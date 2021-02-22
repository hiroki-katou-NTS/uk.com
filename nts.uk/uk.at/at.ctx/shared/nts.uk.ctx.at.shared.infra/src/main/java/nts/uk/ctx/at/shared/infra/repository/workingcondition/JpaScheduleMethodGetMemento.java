/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCre;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondScheMeth;

/**
 * The Class JpaScheduleMethodGetMemento.
 */
public class JpaScheduleMethodGetMemento implements ScheduleMethodGetMemento {

	/** The kshmt schedule method. */
	private KshmtWorkcondScheMeth kshmtScheduleMethod;

	/**
	 * Instantiates a new jpa schedule method get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaScheduleMethodGetMemento(KshmtWorkcondScheMeth entity) {
		this.kshmtScheduleMethod = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodGetMemento#
	 * getBasicCreateMethod()
	 */
	@Override
	public WorkScheduleBasicCreMethod getBasicCreateMethod() {
		try {
			return WorkScheduleBasicCreMethod.valueOf(this.kshmtScheduleMethod.getBasicCreateMethod());
		} catch (Exception e) {
			return WorkScheduleBasicCreMethod.BUSINESS_DAY_CALENDAR;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodGetMemento#
	 * getWorkScheduleBusCal()
	 */
	@Override
	public Optional<WorkScheduleBusCal> getWorkScheduleBusCal() {
		return this.kshmtScheduleMethod.getRefBusinessDayCalendar() != null
				? Optional.of(new WorkScheduleBusCal(
						new JpaWorkScheduleBusCalGetMemento(this.kshmtScheduleMethod)))
				: Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodGetMemento#
	 * getMonthlyPatternWorkScheduleCre()
	 */
	@Override
	public Optional<MonthlyPatternWorkScheduleCre> getMonthlyPatternWorkScheduleCre() {
		return this.kshmtScheduleMethod.getRefWorkingHours() != null
				? Optional.of(new MonthlyPatternWorkScheduleCre(
						new JpaMPatternWorkScheCreGetMemento(this.kshmtScheduleMethod)))
				: Optional.empty();
	}

}
