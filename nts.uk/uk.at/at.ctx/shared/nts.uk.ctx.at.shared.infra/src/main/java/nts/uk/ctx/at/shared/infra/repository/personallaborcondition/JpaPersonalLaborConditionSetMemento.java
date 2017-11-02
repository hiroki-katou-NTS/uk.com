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
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerLaborCond;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtPerLaborCondPK;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtSingleDaySche;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaPersonalLaborConditionSetMemento.
 */
public class JpaPersonalLaborConditionSetMemento implements PersonalLaborConditionSetMemento{
	
	/** The entity. */
	private KshmtPerLaborCond entityCondiotion;
	
	/** The entity single days. */
	private List<KshmtSingleDaySche> entitySingleDays ;
	
	

	/**
	 * Instantiates a new jpa personal labor condition set memento.
	 *
	 * @param entityCondiotion the entity condiotion
	 * @param entitySingleDays the entity single days
	 */
	public JpaPersonalLaborConditionSetMemento(KshmtPerLaborCond entityCondiotion,
			List<KshmtSingleDaySche> entitySingleDays) {
		if (entityCondiotion.getKshmtPerLaborCondPK() == null) {
			entityCondiotion.setKshmtPerLaborCondPK(new KshmtPerLaborCondPK());
		}
		this.entityCondiotion = entityCondiotion;
		if (CollectionUtil.isEmpty(entitySingleDays)) {
			this.entitySingleDays = new ArrayList<>();
		} else
			this.entitySingleDays = entitySingleDays;
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
		this.entityCondiotion.setSchedMgmtAtr(scheduleManagementAtr.value);
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
		this.entityCondiotion.setHdAddOneDay(holidayAddTimeSet.getOneDay().valueAsMinutes());
		this.entityCondiotion.setHdAddMorning(holidayAddTimeSet.getMorning().valueAsMinutes());
		this.entityCondiotion.setHdAddAfternoon(holidayAddTimeSet.getAfternoon().valueAsMinutes());
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
		workCategory.saveToMemento(new JpaPersonalWorkCategorySetMemento(this.entitySingleDays));
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
		workDayOfWeek.saveToMemento(new JpaPersonalDayOfWeekSetMemento(this.entitySingleDays));
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
	 * PersonalLaborConditionSetMemento#setAutomaticEmbossSetAtr(nts.uk.ctx.at.
	 * shared.dom.personallaborcondition.UseAtr)
	 */
	@Override
	public void setAutomaticEmbossSetAtr(UseAtr automaticEmbossSetAtr) {
		this.entityCondiotion.setAutoEmbossSetAtr(automaticEmbossSetAtr.value);
	}

}
