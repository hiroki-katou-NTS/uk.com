/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import nts.uk.ctx.at.shared.dom.personallaborcondition.AttendanceTime;
import nts.uk.ctx.at.shared.dom.personallaborcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionGetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerLaborCond;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerLaborCondPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaPersonalLaborConditionGetMemento.
 */
public class JpaPersonalLaborConditionGetMemento implements PersonalLaborConditionGetMemento {

	/** The entity. */
	private KshmtPerLaborCond entity;

	/**
	 * Instantiates a new jpa personal labor condition get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaPersonalLaborConditionGetMemento(KshmtPerLaborCond entity) {
		if (entity.getKshmtPerLaborCondPK() == null) {
			entity.setKshmtPerLaborCondPK(new KshmtPerLaborCondPK());
		}

		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getScheduleManagementAtr()
	 */
	@Override
	public UseAtr getScheduleManagementAtr() {
		return UseAtr.valueOf(this.entity.getSchedMgmtAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getHolidayAddTimeSet()
	 */
	@Override
	public BreakdownTimeDay getHolidayAddTimeSet() {
		return new BreakdownTimeDay(new AttendanceTime(this.entity.getHdAddOneDay()),
				new AttendanceTime(this.entity.getHdAddMorning()), new AttendanceTime(this.entity.getHdAddAfternoon()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getWorkCategory()
	 */
	@Override
	public PersonalWorkCategory getWorkCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getWorkDayOfWeek()
	 */
	@Override
	public PersonalDayOfWeek getWorkDayOfWeek() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getPeriod()
	 */
	@Override
	public DatePeriod getPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getAutomaticEmbossSetAtr()
	 */
	@Override
	public UseAtr getAutomaticEmbossSetAtr() {
		// TODO Auto-generated method stub
		return null;
	}

}
