package nts.uk.ctx.bs.employee.app.command.holidaysetting.configuration;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingGetMemento;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PublicHolidaySettingCommandDto.
 */
@Data
public class PublicHolidaySettingCommandDto implements PublicHolidaySettingGetMemento {
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The is manage com public hd. */
	private int isManageComPublicHd;
	
	/** The public hd management classification. */
	private int publicHdManagementClassification;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return AppContexts.user().companyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingGetMemento#getIsManageComPublicHd()
	 */
	@Override
	public boolean getIsManageComPublicHd() {
		if(this.isManageComPublicHd == TRUE_VALUE){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingGetMemento#getPublicHdManagementClassification()
	 */
	@Override
	public PublicHolidayManagementClassification getPublicHdManagementClassification() {
		return PublicHolidayManagementClassification.valueOf(this.publicHdManagementClassification);
	}

	@Override
	public PublicHolidayManagementUsageUnit getPublicHdManagementUsageUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getIsWeeklyHdCheck() {
		// TODO Auto-generated method stub
		return false;
	}

}
