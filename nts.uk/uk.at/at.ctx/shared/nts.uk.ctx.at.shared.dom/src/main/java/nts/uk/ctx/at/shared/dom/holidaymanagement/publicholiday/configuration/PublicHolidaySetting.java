/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
/**
 * The Class PublicHolidaySetting.
 */
// 公休設定
public class PublicHolidaySetting extends AggregateRoot {
	
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The is manage com public hd. */
	// 会社の公休管理をする
	private boolean isManageComPublicHd;
	
	/** The public hd management classification. */
	// 公休管理区分
	private PublicHolidayManagementClassification publicHdManagementClassification;
	
	/** The is weekly hd check. */
	// 週間休日チェックをする
	private boolean isWeeklyHdCheck;
	
	/** The public hd management start date. */
	//公休管理開始日
	private PublicHolidayManagementStartDate publicHdManagementStartDate;
	
	/**
	 * Instantiates a new public holiday setting.
	 *
	 * @param memento the memento
	 */
	public PublicHolidaySetting(PublicHolidaySettingGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.isManageComPublicHd = memento.getIsManageComPublicHd();
		this.publicHdManagementClassification = memento.getPublicHdManagementClassification();
		this.isWeeklyHdCheck = memento.getIsWeeklyHdCheck();
		this.publicHdManagementStartDate = memento.getPublicHolidayManagementStartDate(null);
	}
	
	public PublicHolidaySetting(PublicHolidaySettingGetMemento memento, Integer publicHdManageAtr) {
		this.companyID = memento.getCompanyID();
		this.isManageComPublicHd = memento.getIsManageComPublicHd();
		this.publicHdManagementClassification = memento.getPublicHdManagementClassification();
		this.isWeeklyHdCheck = memento.getIsWeeklyHdCheck();
		this.publicHdManagementStartDate = memento.getPublicHolidayManagementStartDate(publicHdManageAtr);
	}	

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PublicHolidaySettingSetMemento memento) {
		memento.setCompanyID(this.companyID);
		memento.setIsManageComPublicHd(this.isManageComPublicHd);
		memento.setPublicHdManagementClassification(this.publicHdManagementClassification);
		memento.setIsWeeklyHdCheck(this.isWeeklyHdCheck);
		memento.setPublicHolidayManagementStartDate(this.publicHdManagementStartDate);
	}
	
	public void saveToMemento(PublicHolidaySettingSetMemento memento, Integer publicHdManageAtr) {
		memento.setCompanyID(this.companyID);
		memento.setIsManageComPublicHd(this.isManageComPublicHd);
		memento.setPublicHdManagementClassification(this.publicHdManagementClassification);
		memento.setIsWeeklyHdCheck(this.isWeeklyHdCheck);
		memento.setPublicHolidayManagementStartDate(this.publicHdManagementStartDate, publicHdManageAtr);
	}
}
