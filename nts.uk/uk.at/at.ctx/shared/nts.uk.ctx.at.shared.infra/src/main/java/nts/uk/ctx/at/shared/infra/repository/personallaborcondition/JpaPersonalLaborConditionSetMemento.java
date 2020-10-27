/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.ArrayList;
import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.personallaborcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionSetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerDayOfWeek;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerLaborCond;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerLaborCondPK;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtWorkcondCtgegory;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaPersonalLaborConditionSetMemento.
 */
public class JpaPersonalLaborConditionSetMemento implements PersonalLaborConditionSetMemento{
	
	/** The entity. */
	private KshmtPerLaborCond entityCondiotion;
	
	/** The entity day of weeks. */
	private List<KshmtPerDayOfWeek> entityDayOfWeeks ;
	
	/** The entity work categorys. */
	private List<KshmtWorkcondCtgegory> entityWorkCategorys ;
	
	

	/**
	 * Instantiates a new jpa personal labor condition set memento.
	 *
	 * @param entityCondiotion the entity condiotion
	 * @param entityDayOfWeeks the entity day of weeks
	 * @param entityWorkCategorys the entity work categorys
	 */
	public JpaPersonalLaborConditionSetMemento(KshmtPerLaborCond entityCondiotion,
			List<KshmtPerDayOfWeek> entityDayOfWeeks,
			List<KshmtWorkcondCtgegory> entityWorkCategorys) {
		if (entityCondiotion.getKshmtPerLaborCondPK() == null) {
			entityCondiotion.setKshmtPerLaborCondPK(new KshmtPerLaborCondPK());
		}
		this.entityCondiotion = entityCondiotion;
		
		if (CollectionUtil.isEmpty(entityDayOfWeeks)) {
			this.entityDayOfWeeks = new ArrayList<>();
		} else {
			this.entityDayOfWeeks = entityDayOfWeeks;
		}
		
		if (CollectionUtil.isEmpty(entityWorkCategorys)) {
			this.entityWorkCategorys = new ArrayList<>();
		} else {
			this.entityWorkCategorys = entityWorkCategorys;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionSetMemento#setScheduleManagementAtr(nts.uk.ctx.at.
	 * shared.dom.personallaborcondition.UseAtr)
	 */
	@Override
	public void setScheduleManagementAtr(UseAtr scheduleManagementAtr) {
		this.entityCondiotion.setScheManageAtr(scheduleManagementAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionSetMemento#setHolidayAddTimeSet(nts.uk.ctx.at.
	 * shared.dom.personallaborcondition.BreakdownTimeDay)
	 */
	@Override
	public void setHolidayAddTimeSet(BreakdownTimeDay holidayAddTimeSet) {
		this.entityCondiotion.setOneDay(holidayAddTimeSet.getOneDay().valueAsMinutes());
		this.entityCondiotion.setMorning(holidayAddTimeSet.getMorning().valueAsMinutes());
		this.entityCondiotion.setAfternoon(holidayAddTimeSet.getAfternoon().valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionSetMemento#setWorkCategory(nts.uk.ctx.at.shared.dom
	 * .personallaborcondition.PersonalWorkCategory)
	 */
	@Override
	public void setWorkCategory(PersonalWorkCategory workCategory) {
		workCategory.saveToMemento(new JpaPersonalWorkCategorySetMemento(this.entityWorkCategorys));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionSetMemento#setWorkDayOfWeek(nts.uk.ctx.at.shared.
	 * dom.personallaborcondition.PersonalDayOfWeek)
	 */
	@Override
	public void setWorkDayOfWeek(PersonalDayOfWeek workDayOfWeek) {
		workDayOfWeek.saveToMemento(new JpaPersonalDayOfWeekSetMemento(this.entityDayOfWeeks));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionSetMemento#setPeriod(nts.uk.shr.com.time.calendar.
	 * period.DatePeriod)
	 */
	@Override
	public void setPeriod(DatePeriod period) {
		this.entityCondiotion.getKshmtPerLaborCondPK().setStartYmd(period.start());
		this.entityCondiotion.getKshmtPerLaborCondPK().setEndYmd(period.end());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionSetMemento#setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.entityCondiotion.getKshmtPerLaborCondPK().setSid(employeeId);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalLaborConditionSetMemento#setAutoStampSetAtr(nts.uk.ctx.at.shared.
	 * dom.personallaborcondition.UseAtr)
	 */
	@Override
	public void setAutoStampSetAtr(UseAtr autoStampSetAtr) {
		this.entityCondiotion.setAutoStampSetAtr(autoStampSetAtr.value);
		
	}

}
