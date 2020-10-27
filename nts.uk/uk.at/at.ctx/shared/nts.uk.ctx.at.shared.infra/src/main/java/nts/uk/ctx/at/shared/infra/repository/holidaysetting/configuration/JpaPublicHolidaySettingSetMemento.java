package nts.uk.ctx.at.shared.infra.repository.holidaysetting.configuration;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayGrantDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementStartDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.configuration.KshmtHdpubSet;

/**
 * The Class JpaPublicHolidaySettingSetMemento.
 */
public class JpaPublicHolidaySettingSetMemento implements PublicHolidaySettingSetMemento{
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The Constant FALSE_VALUE. */
	private final static int FALSE_VALUE = 0;
	
	/** The kshmt public hd set. */
	private KshmtHdpubSet kshmtHdpubSet;
	
	/**
	 * Instantiates a new jpa public holiday setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaPublicHolidaySettingSetMemento(KshmtHdpubSet entity) {
		this.kshmtHdpubSet = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String CompanyID) {
		this.kshmtHdpubSet.setCid(CompanyID);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setIsManageComPublicHd(boolean)
	 */
	@Override
	public void setIsManageComPublicHd(boolean isManageComPublicHd) {
		if(isManageComPublicHd){
			this.kshmtHdpubSet.setIsManageComPublicHd(TRUE_VALUE);
		} else {
			this.kshmtHdpubSet.setIsManageComPublicHd(FALSE_VALUE);
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setPublicHdManagementClassification(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification)
	 */
	@Override
	public void setPublicHdManagementClassification(
			PublicHolidayManagementClassification publicHdManagementClassification) {
		this.kshmtHdpubSet.setPublicHdManageAtr(publicHdManagementClassification.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setIsWeeklyHdCheck(boolean)
	 */
	@Override
	public void setIsWeeklyHdCheck(boolean isWeeklyHdCheck) {
		if(isWeeklyHdCheck){
			this.kshmtHdpubSet.setIsWeeklyHdCheck(TRUE_VALUE);
		} else {
			this.kshmtHdpubSet.setIsWeeklyHdCheck(FALSE_VALUE);
		}
	}

	@Override
	public void setPublicHolidayManagementStartDate(PublicHolidayManagementStartDate publicHolidayManagementStartDate) {
		if (this.kshmtHdpubSet.getPublicHdManageAtr() == 0) {
			PublicHolidayGrantDate publicHolidayGrantDate = (PublicHolidayGrantDate) publicHolidayManagementStartDate;
			this.kshmtHdpubSet.setPeriod(publicHolidayGrantDate.getPeriod().value);
		} else {
			PublicHoliday publicHoliday = (PublicHoliday) publicHolidayManagementStartDate;
			this.kshmtHdpubSet.setDetermineStartD(publicHoliday.getDetermineStartDate().value);
			if (publicHoliday.getDetermineStartDate().value == DayOfPublicHoliday.DESIGNATE_BY_YEAR_MONTH_DAY.value) {
				this.kshmtHdpubSet.setFullDate(publicHoliday.getDate());
			} else {
				this.kshmtHdpubSet.setDayMonth(publicHoliday.getDayMonth());
			}
		}
	}

	@Override
	public void setPublicHolidayManagementStartDate(PublicHolidayManagementStartDate publicHolidayManagementStartDate,
			Integer type) {
		
	}

}
