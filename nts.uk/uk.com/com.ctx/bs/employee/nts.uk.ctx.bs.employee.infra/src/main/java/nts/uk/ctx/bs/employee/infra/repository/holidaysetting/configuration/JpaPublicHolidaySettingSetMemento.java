package nts.uk.ctx.bs.employee.infra.repository.holidaysetting.configuration;

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
		// not coding
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setIsManageComPublicHd(boolean)
	 */
	@Override
	public void setIsManageComPublicHd(boolean isManageComPublicHd) {
		if(isManageComPublicHd){
			this.kshmtPublicHdSet.setIsManageComPublicHd(TRUE_VALUE);
		}
		this.kshmtPublicHdSet.setIsManageComPublicHd(FALSE_VALUE);
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
		}
		this.kshmtPublicHdSet.setIsWeeklyHdCheck(FALSE_VALUE);
	}

	@Override
	public void setPublicHolidayManagementStartDate(PublicHolidayManagementStartDate publicHolidayManagementStartDate) {
		// TODO Auto-generated method stub
		
	}

}
