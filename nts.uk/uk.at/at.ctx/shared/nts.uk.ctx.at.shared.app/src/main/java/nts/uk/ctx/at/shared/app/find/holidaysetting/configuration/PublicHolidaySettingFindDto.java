package nts.uk.ctx.at.shared.app.find.holidaysetting.configuration;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayGrantDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementStartDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingSetMemento;

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
	private Integer isManageComPublicHd;
	
	/** The public hd management classification. */
	private Integer publicHdManagementClassification;
	
	/** The is weekly hd check. */
//	private Integer isWeeklyHdCheck;
	
	/** The period. */
	private Integer period;
	
	/** The full date. */
	private String fullDate;
	
	/** The day month. */
//	private Integer dayMonth;
	
	/** The determine start D. */
//	private Integer determineStartD;

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
		} else {
			this.isManageComPublicHd = FALSE_VALUE;
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setPublicHdManagementClassification(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification)
	 */
	@Override
	public void setPublicHdManagementClassification(
			PublicHolidayManagementClassification publicHdManagementClassification) {
		this.publicHdManagementClassification = publicHdManagementClassification.value;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingSetMemento#setIsWeeklyHdCheck(boolean)
	 */
	@Override
	public void setIsWeeklyHdCheck(boolean isWeeklyHdCheck) {
		if(isWeeklyHdCheck){
//			this.isWeeklyHdCheck = TRUE_VALUE;
		} else {
//			this.isWeeklyHdCheck = FALSE_VALUE;
		}
	}

	@Override
	public void setPublicHolidayManagementStartDate(PublicHolidayManagementStartDate publicHolidayManagementStartDate) {
		// TODO Auto-generated method stub
		if (this.publicHdManagementClassification == 1) {
			PublicHoliday publicHoliday = (PublicHoliday) publicHolidayManagementStartDate;
//			this.dayMonth = publicHoliday.getDayMonth();
//			this.fullDate = publicHoliday.getDate().toString();
//			this.determineStartD = publicHoliday.getDetermineStartDate().value;
		} else {
			PublicHolidayGrantDate holidayGrantDate = (PublicHolidayGrantDate) publicHolidayManagementStartDate;
			this.period = holidayGrantDate.getPeriod().value;
		}
	}

	@Override
	public void setPublicHolidayManagementStartDate(PublicHolidayManagementStartDate publicHolidayManagementStartDate,
			Integer type) {
		if (type == 1) { 
			PublicHoliday publicHoliday = (PublicHoliday) publicHolidayManagementStartDate;
			if (publicHoliday.getDayMonth() != null) {
//				this.dayMonth = publicHoliday.getDayMonth();
			}
			if (publicHoliday.getDate() != null) {
				this.fullDate = publicHoliday.getDate().toString();
			} 
			if (publicHoliday.getDetermineStartDate() != null) {
//				this.determineStartD = publicHoliday.getDetermineStartDate().value;
			} else {
//				this.determineStartD = 0; // 0 is value Year - month - day
			}
			
		} else {
			PublicHolidayGrantDate holidayGrantDate = (PublicHolidayGrantDate) publicHolidayManagementStartDate;
			if (holidayGrantDate.getPeriod() == null) {
				this.period = 0; // default value
			} else {
				this.period = holidayGrantDate.getPeriod().value;
			}
			
		}
	}

}
