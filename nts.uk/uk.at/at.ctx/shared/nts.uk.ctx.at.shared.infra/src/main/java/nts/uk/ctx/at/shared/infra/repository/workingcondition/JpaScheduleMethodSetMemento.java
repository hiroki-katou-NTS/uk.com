/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCre;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondScheMeth;

/**
 * The Class JpaScheduleMethodSetMemento.
 */
public class JpaScheduleMethodSetMemento implements ScheduleMethodSetMemento {

	/** The kshmt schedule method. */
	private KshmtWorkcondScheMeth entity;

	/**
	 * Instantiates a new jpa schedule method set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaScheduleMethodSetMemento(String historyId, KshmtWorkcondScheMeth entity) {
		this.entity = entity;
		// Check exist
		if (this.entity.getHistoryId() == null) {
			this.entity.setHistoryId(historyId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodSetMemento#
	 * setBasicCreateMethod(nts.uk.ctx.at.shared.dom.workingcondition.
	 * WorkScheduleBasicCreMethod)
	 */
	@Override
	public void setBasicCreateMethod(WorkScheduleBasicCreMethod basicCreateMethod) {
		this.entity.setBasicCreateMethod(basicCreateMethod.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodSetMemento#
	 * setWorkScheduleBusCal(nts.uk.ctx.at.shared.dom.workingcondition.
	 * WorkScheduleBusCal)
	 */
	@Override
	public void setWorkScheduleBusCal(Optional<WorkScheduleBusCal> workScheduleBusCal) {
		if (!workScheduleBusCal.isPresent()) {
			this.entity.setRefBusinessDayCalendar(null);
			this.entity.setRefBasicWork(null);
			this.entity.setRefWorkingHours(null);
			return;
		}
		workScheduleBusCal.get().saveToMemento(new JpaWorkScheduleBusCalSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethodSetMemento#
	 * setMonthlyPatternWorkScheduleCre(nts.uk.ctx.at.shared.dom.
	 * workingcondition.MonthlyPatternWorkScheduleCre)
	 */
	@Override
	public void setMonthlyPatternWorkScheduleCre(
			Optional<MonthlyPatternWorkScheduleCre> monthlyPatternWorkScheduleCre) {
		if (!monthlyPatternWorkScheduleCre.isPresent()) {
			this.entity.setRefWorkingHours(null);
			return;
		}

		monthlyPatternWorkScheduleCre.get()
				.saveToMemento(new JpaMPatternWorkScheCreSetMemento(this.entity));
	}

}
