/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import nts.uk.ctx.at.shared.dom.personallaborcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionGetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaPersonalLaborConditionGetMemento.
 */
public class JpaPersonalLaborConditionGetMemento implements PersonalLaborConditionGetMemento{

	@Override
	public UseAtr getScheduleManagementAtr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BreakdownTimeDay getHolidayAddTimeSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonalWorkCategory getWorkCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonalDayOfWeek getWorkDayOfWeek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatePeriod getPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmployeeId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UseAtr getAutomaticEmbossSetAtr() {
		// TODO Auto-generated method stub
		return null;
	}

}
