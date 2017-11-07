/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.List;

import nts.uk.ctx.at.shared.dom.personallaborcondition.AttendanceTime;
import nts.uk.ctx.at.shared.dom.personallaborcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionGetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerLaborCond;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtSingleDaySche;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaPersonalLaborConditionGetMemento.
 */
public class JpaPersonalLaborConditionGetMemento implements PersonalLaborConditionGetMemento {

	/** The entity. */
	private KshmtPerLaborCond entityCondition;
	
	/** The entity single days. */
	private List<KshmtSingleDaySche> entitySingleDays ;

	/**
	 * Instantiates a new jpa personal labor condition get memento.
	 *
	 * @param entityCondition the entity condition
	 * @param entitySingleDays the entity single days
	 */
	public JpaPersonalLaborConditionGetMemento(KshmtPerLaborCond entityCondition,
			List<KshmtSingleDaySche> entitySingleDays) {
		this.entityCondition = entityCondition;
		this.entitySingleDays = entitySingleDays;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getScheduleManagementAtr()
	 */
	@Override
	public UseAtr getScheduleManagementAtr() {
		return UseAtr.valueOf(this.entityCondition.getSchedMgmtAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getHolidayAddTimeSet()
	 */
	@Override
	public BreakdownTimeDay getHolidayAddTimeSet() {
		return new BreakdownTimeDay(new AttendanceTime(this.entityCondition.getHdAddOneDay()),
				new AttendanceTime(this.entityCondition.getHdAddMorning()),
				new AttendanceTime(this.entityCondition.getHdAddAfternoon()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getWorkCategory()
	 */
	@Override
	public PersonalWorkCategory getWorkCategory() {
		return new PersonalWorkCategory(
				new JpaPersonalWorkCategoryGetMemento(this.entitySingleDays));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getWorkDayOfWeek()
	 */
	@Override
	public PersonalDayOfWeek getWorkDayOfWeek() {
		return new PersonalDayOfWeek(new JpaPersonalDayOfWeekGetMemento(this.entitySingleDays));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getPeriod()
	 */
	@Override
	public DatePeriod getPeriod() {
		return new DatePeriod(this.entityCondition.getKshmtPerLaborCondPK().getStartYmd(),
				this.entityCondition.getKshmtPerLaborCondPK().getEndYmd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.entityCondition.getKshmtPerLaborCondPK().getSid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getAutomaticEmbossSetAtr()
	 */
	@Override
	public UseAtr getAutomaticEmbossSetAtr() {
		return UseAtr.valueOf(this.entityCondition.getAutoEmbossSetAtr());
	}

}
