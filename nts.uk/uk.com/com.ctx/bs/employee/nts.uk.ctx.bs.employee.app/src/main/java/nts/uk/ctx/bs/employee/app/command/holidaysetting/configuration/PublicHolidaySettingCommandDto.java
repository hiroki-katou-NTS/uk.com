package nts.uk.ctx.bs.employee.app.command.holidaysetting.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.DayOfPublicHoliday;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHoliday;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayGrantDate;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementStartDate;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayPeriod;
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
	public PublicHolidayManagementStartDate getPublicHolidayManagementStartDate(Integer publicHdManageAtr) {
		// TODO Auto-generated method stub
		if (this.publicHdManagementClassification == 0) {
			return new PublicHolidayGrantDate(PublicHolidayPeriod.valueOf(this.period));
		}
		if (fullDate.length() < 8) {
			fullDate = "0" + fullDate;
		}
		int year = Integer.parseInt(fullDate.substring(4));
		int month= Integer.parseInt(fullDate.substring(0, 2));
		int day = Integer.parseInt(fullDate.substring(2, 4));
		return new PublicHoliday(GeneralDate.ymd(year, month, day), this.dayMonth, DayOfPublicHoliday.valueOf(this.determineStartD));
	}

}
