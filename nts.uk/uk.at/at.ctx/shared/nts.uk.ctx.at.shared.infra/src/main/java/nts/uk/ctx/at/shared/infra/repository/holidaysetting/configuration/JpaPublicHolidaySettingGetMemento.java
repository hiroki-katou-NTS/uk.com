package nts.uk.ctx.at.shared.infra.repository.holidaysetting.configuration;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayGrantDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementStartDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayPeriod;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.configuration.KshmtHdpubSet;
import nts.uk.shr.com.context.AppContexts;

public class JpaPublicHolidaySettingGetMemento implements PublicHolidaySettingGetMemento {

	private final static int TRUE_VALUE = 1;
	
	private KshmtHdpubSet kshmtHdpubSet;
	
	public JpaPublicHolidaySettingGetMemento(KshmtHdpubSet entity) {
		this.kshmtHdpubSet = entity;
	}
	
	@Override
	public String getCompanyID() {
		return AppContexts.user().companyId();
	}

	@Override
	public boolean getIsManageComPublicHd() {
		if(this.kshmtHdpubSet.getIsManageComPublicHd() == TRUE_VALUE){
			return true;
		}
		return false;
	}

	@Override
	public PublicHolidayManagementClassification getPublicHdManagementClassification() {
		return PublicHolidayManagementClassification.valueOf(this.kshmtHdpubSet.getPublicHdManageAtr());
	}

	/*@Override
	public PublicHolidayManagementUsageUnit getPublicHdManagementUsageUnit() {
		return new PublicHolidayManagementUsageUnit(this.kshmtHdpubSet.getIsManageEmpPubHd() == TRUE_VALUE ? true : false,
													this.kshmtHdpubSet.getIsManageWkpPubHd() == TRUE_VALUE ? true : false,
													this.kshmtHdpubSet.getIsManageSPubHd() == TRUE_VALUE ? true : false);
		}
	*/
	
	@Override
	public boolean getIsWeeklyHdCheck() {
		if(this.kshmtHdpubSet.getIsWeeklyHdCheck() == TRUE_VALUE){
			return true;
		}
		return false;
	}

	@Override
	public PublicHolidayManagementStartDate getPublicHolidayManagementStartDate(Integer publicHdManageAtr) {
		if (publicHdManageAtr != null) {
			if (publicHdManageAtr == 0) {
				return new PublicHolidayGrantDate(PublicHolidayPeriod.valueOf(this.kshmtHdpubSet.getPeriod()));
			} else {
				return new PublicHoliday(this.kshmtHdpubSet.getFullDate(), 
											this.kshmtHdpubSet.getDayMonth(), 
											DayOfPublicHoliday.valueOf(this.kshmtHdpubSet.getDetermineStartD()));
			}
			
		} else {
			if (this.kshmtHdpubSet.getPublicHdManageAtr() == 0) {
				return new PublicHolidayGrantDate(PublicHolidayPeriod.valueOf(this.kshmtHdpubSet.getPeriod()));
			} else {
				GeneralDate fullDate = this.kshmtHdpubSet.getFullDate();
				if (fullDate == null) {
					return new PublicHoliday(null, this.kshmtHdpubSet.getDayMonth(), 
												DayOfPublicHoliday.valueOf(this.kshmtHdpubSet.getDetermineStartD()));
				} else {
					return new PublicHoliday(GeneralDate.ymd(fullDate.year(), fullDate.month(), fullDate.day()), 
							this.kshmtHdpubSet.getDayMonth(), 
							DayOfPublicHoliday.valueOf(this.kshmtHdpubSet.getDetermineStartD()));
				}
			}
		}
		
	}
}
