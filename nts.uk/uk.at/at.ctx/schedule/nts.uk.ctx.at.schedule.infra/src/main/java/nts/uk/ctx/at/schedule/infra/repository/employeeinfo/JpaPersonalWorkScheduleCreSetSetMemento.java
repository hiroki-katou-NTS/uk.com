/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.employeeinfo;

import nts.uk.ctx.at.schedule.dom.employeeinfo.MonthlyPatternWorkScheduleCre;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSetSetMemento;
import nts.uk.ctx.at.schedule.dom.employeeinfo.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.schedule.dom.employeeinfo.WorkScheduleBusCal;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.KscstScheCreSet;

/**
 * The Class JpaPersonalWorkScheduleCreSetSetMemento.
 */
public class JpaPersonalWorkScheduleCreSetSetMemento
		implements PersonalWorkScheduleCreSetSetMemento {
	
	/** The entity. */
	private KscstScheCreSet entity;

	/**
	 * Instantiates a new jpa personal work schedule cre set set memento.
	 *
	 * @param entity the entity
	 */
	public JpaPersonalWorkScheduleCreSetSetMemento(KscstScheCreSet entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.employeeinfo.
	 * PersonalWorkScheduleCreSetSetMemento#setBasicCreateMethod(nts.uk.ctx.at.
	 * schedule.dom.employeeinfo.WorkScheduleBasicCreMethod)
	 */
	@Override
	public void setBasicCreateMethod(WorkScheduleBasicCreMethod basicCreateMethod) {
		this.entity.setBasicCreMethod(basicCreateMethod.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.employeeinfo.
	 * PersonalWorkScheduleCreSetSetMemento#setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.entity.setSid(employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.employeeinfo.
	 * PersonalWorkScheduleCreSetSetMemento#setMonthlyPatternWorkScheduleCre(nts
	 * .uk.ctx.at.schedule.dom.employeeinfo.MonthlyPatternWorkScheduleCre)
	 */
	@Override
	public void setMonthlyPatternWorkScheduleCre(
			MonthlyPatternWorkScheduleCre monthlyPatternWorkScheduleCre) {
		this.entity.setRefType(monthlyPatternWorkScheduleCre.getReferenceType().value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.employeeinfo.
	 * PersonalWorkScheduleCreSetSetMemento#setWorkScheduleBusCal(nts.uk.ctx.at.
	 * schedule.dom.employeeinfo.WorkScheduleBusCal)
	 */
	@Override
	public void setWorkScheduleBusCal(WorkScheduleBusCal workScheduleBusCal) {
		this.entity.setRefBusDayCal(workScheduleBusCal.getReferenceBusinessDayCalendar().value);
		this.entity.setRefBasicWork(workScheduleBusCal.getReferenceBasicWork().value);
		this.entity.setRefWorkingHour(workScheduleBusCal.getReferenceWorkingHours().value);

	}

}
