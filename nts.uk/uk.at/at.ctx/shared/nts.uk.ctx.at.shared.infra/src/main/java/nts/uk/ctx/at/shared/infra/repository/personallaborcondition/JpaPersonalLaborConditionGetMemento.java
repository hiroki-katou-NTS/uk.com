/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.personallaborcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionGetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerDayOfWeek;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerLaborCond;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtWorkcondCtgegory;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaPersonalLaborConditionGetMemento.
 */
public class JpaPersonalLaborConditionGetMemento implements PersonalLaborConditionGetMemento {

	/** The entity. */
	private KshmtPerLaborCond entityCondition;
	
	/** The entity day of weeks. */
	private List<KshmtPerDayOfWeek> entityDayOfWeeks ;
	
	/** The entity work categorys. */
	private List<KshmtWorkcondCtgegory> entityWorkCategorys ;

	/**
	 * Instantiates a new jpa personal labor condition get memento.
	 *
	 * @param entityCondition the entity condition
	 * @param entityDayOfWeeks the entity day of weeks
	 */
	public JpaPersonalLaborConditionGetMemento(KshmtPerLaborCond entityCondition,
			List<KshmtPerDayOfWeek> entityDayOfWeeks,
			List<KshmtWorkcondCtgegory> entityWorkCategorys) {
		this.entityCondition = entityCondition;
		this.entityDayOfWeeks = entityDayOfWeeks;
		this.entityWorkCategorys = entityWorkCategorys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getScheduleManagementAtr()
	 */
	@Override
	public UseAtr getScheduleManagementAtr() {
		return UseAtr.valueOf(this.entityCondition.getScheManageAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getHolidayAddTimeSet()
	 */
	@Override
	public BreakdownTimeDay getHolidayAddTimeSet() {
		return new BreakdownTimeDay(new AttendanceTime(this.entityCondition.getOneDay()),
				new AttendanceTime(this.entityCondition.getMorning()),
				new AttendanceTime(this.entityCondition.getAfternoon()));
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
				new JpaPersonalWorkCategoryGetMemento(this.entityWorkCategorys));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionGetMemento#getWorkDayOfWeek()
	 */
	@Override
	public PersonalDayOfWeek getWorkDayOfWeek() {
		return new PersonalDayOfWeek(new JpaPersonalDayOfWeekGetMemento(this.entityDayOfWeeks));
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
	public UseAtr getAutoStampSetAtr() {
		return UseAtr.valueOf(this.entityCondition.getAutoStampSetAtr());
	}

}
