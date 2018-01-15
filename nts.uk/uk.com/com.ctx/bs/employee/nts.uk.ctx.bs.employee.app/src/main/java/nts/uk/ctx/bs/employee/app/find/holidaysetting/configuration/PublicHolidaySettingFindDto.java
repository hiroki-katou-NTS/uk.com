package nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento;

/**
 * The Class PublicHolidaySettingFindDto.
 */
@Data
public class PublicHolidaySettingFindDto implements PublicHolidaySettingSetMemento {
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The Constant FALSE_VALUE. */
	private final static int FALSE_VALUE = 0;
	
	/** The is manage com public hd. */
	private int isManageComPublicHd;
	
	/** The public hd management classification. */
	private int publicHdManagementClassification;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String CompanyID) {
		// do not code
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setIsManageComPublicHd(boolean)
	 */
	@Override
	public void setIsManageComPublicHd(boolean isManageComPublicHd) {
		if(isManageComPublicHd){
			this.isManageComPublicHd = TRUE_VALUE;
		}
		this.isManageComPublicHd = FALSE_VALUE;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setPublicHdManagementClassification(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification)
	 */
	@Override
	public void setPublicHdManagementClassification(
			PublicHolidayManagementClassification publicHdManagementClassification) {
		this.publicHdManagementClassification = publicHdManagementClassification.value;
	}

	@Override
	public void setPublicHdManagementUsageUnit(PublicHolidayManagementUsageUnit publicHdManagementUsageUnit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIsWeeklyHdCheck(boolean isWeeklyHdCheck) {
		// TODO Auto-generated method stub
		
	}

}
