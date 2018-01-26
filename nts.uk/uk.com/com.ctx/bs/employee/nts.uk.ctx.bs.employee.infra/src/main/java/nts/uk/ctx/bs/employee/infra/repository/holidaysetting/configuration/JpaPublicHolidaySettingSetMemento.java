package nts.uk.ctx.bs.employee.infra.repository.holidaysetting.configuration;

import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHoliday;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayGrantDate;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementStartDate;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento;
import nts.uk.ctx.bs.employee.infra.entity.holidaysetting.configuration.KshmtPublicHdSet;

/**
 * The Class JpaPublicHolidaySettingSetMemento.
 */
public class JpaPublicHolidaySettingSetMemento implements PublicHolidaySettingSetMemento{
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The Constant FALSE_VALUE. */
	private final static int FALSE_VALUE = 0;
	
	/** The kshmt public hd set. */
	private KshmtPublicHdSet kshmtPublicHdSet;
	
	/**
	 * Instantiates a new jpa public holiday setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaPublicHolidaySettingSetMemento(KshmtPublicHdSet entity) {
		this.kshmtPublicHdSet = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String CompanyID) {
		this.kshmtPublicHdSet.setCid(CompanyID);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setIsManageComPublicHd(boolean)
	 */
	@Override
	public void setIsManageComPublicHd(boolean isManageComPublicHd) {
		if(isManageComPublicHd){
			this.kshmtPublicHdSet.setIsManageComPublicHd(TRUE_VALUE);
		} else {
			this.kshmtPublicHdSet.setIsManageComPublicHd(FALSE_VALUE);
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setPublicHdManagementClassification(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification)
	 */
	@Override
	public void setPublicHdManagementClassification(
			PublicHolidayManagementClassification publicHdManagementClassification) {
		this.kshmtPublicHdSet.setPublicHdManageAtr(publicHdManagementClassification.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setIsWeeklyHdCheck(boolean)
	 */
	@Override
	public void setIsWeeklyHdCheck(boolean isWeeklyHdCheck) {
		if(isWeeklyHdCheck){
			this.kshmtPublicHdSet.setIsWeeklyHdCheck(TRUE_VALUE);
		} else {
			this.kshmtPublicHdSet.setIsWeeklyHdCheck(FALSE_VALUE);
		}
	}

	@Override
	public void setPublicHolidayManagementStartDate(PublicHolidayManagementStartDate publicHolidayManagementStartDate) {
		if (this.kshmtPublicHdSet.getPublicHdManageAtr() == 0) {
			PublicHolidayGrantDate publicHolidayGrantDate = (PublicHolidayGrantDate) publicHolidayManagementStartDate;
			this.kshmtPublicHdSet.setPeriod(publicHolidayGrantDate.getPeriod().value);
		} else {
			PublicHoliday publicHoliday = (PublicHoliday) publicHolidayManagementStartDate;
			this.kshmtPublicHdSet.setDayMonth(publicHoliday.getDayMonth());
			this.kshmtPublicHdSet.setFullDate(publicHoliday.getDate());
			this.kshmtPublicHdSet.setDetermineStartD(publicHoliday.getDetermineStartDate().value);
		}
	}

	@Override
	public void setPublicHolidayManagementStartDate(PublicHolidayManagementStartDate publicHolidayManagementStartDate,
			Integer type) {
		
	}

}
