package nts.uk.ctx.bs.employee.app.command.holidaysetting.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementStartDate;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingGetMemento;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PublicHolidaySettingCommandDto.
 */
//@Data
@Getter
@Setter
@NoArgsConstructor
public class PublicHolidaySettingCommandDto implements PublicHolidaySettingGetMemento {
	
	/** The Constant TRUE_VALUE. */
	private final static int TRUE_VALUE = 1;
	
	/** The is manage com public hd. */
	private int isManageComPublicHd;
	
	/** The public hd management classification. */
	private int publicHdManagementClassification;
	
	/** The is weekly hd check. */
	private int isWeeklyHdCheck;
	
	/** The period. */
	private int period;
	
	/** The full date. */
	private String fullDate;
	
	/** The day month. */
	private int dayMonth;
	
	/** The determine start D. */
	private int determineStartD;

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
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingGetMemento#getIsWeeklyHdCheck()
	 */
	@Override
	public boolean getIsWeeklyHdCheck() {
		if(this.isWeeklyHdCheck == TRUE_VALUE){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingGetMemento#getPublicHolidayManagementStartDate()
	 */
	@Override
	public PublicHolidayManagementStartDate getPublicHolidayManagementStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

}
