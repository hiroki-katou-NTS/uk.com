/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class CompanyCompensatoryLeave.
 */
// 振休管理設定
@Getter
public class ComSubstVacation extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The setting. */
	// 振休取得・使用方法
	private SubstVacationSetting setting;
	//管理区分 
	private ManageDistinct manageDistinct;
	
	//紐付け管理区分
	private ManageDistinct  linkingManagementATR;
	
	/**
	 * Checks if is managed.
	 *
	 * @return true, if is managed
	 */
	public boolean isManaged() {
		return this.manageDistinct.equals(ManageDistinct.YES);
	}

	/**
	 * Instantiates a new company compensatory leave.
	 *
	 * @param companyId
	 *            the company id
	 * @param setting
	 *            the setting
	 */
	public ComSubstVacation(String companyId, SubstVacationSetting setting ,ManageDistinct manageDistinct ,ManageDistinct linkingManagementATR ) {
		super();
		this.companyId = companyId;
		this.setting = setting;
		this.manageDistinct = manageDistinct;
		this.linkingManagementATR = linkingManagementATR;
		
	}
	
	/**
	 * 	[1] 振休に対応する日次の勤怠項目を取得する
	 */
	public List<Integer> getDailyAttendanceItemsHolidays() {
		// 振休に対応する日次の勤怠項目
		return Arrays.asList(1144);
	}
	
	/**
	 * 	[2] 振休に対応する月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItemsHolidays() {
		// 振休に対応する月次の勤怠項目
		return Arrays.asList(1270, 1271, 1272, 1273, 1274, 1801, 2194);
	}

	/**
	 * 	[3] 利用できない日次の勤怠項目を取得する
	 */
	public List<Integer> getDailyAttendanceItems() {
		// @管理区分 == 管理しない
		if ( this.manageDistinct == ManageDistinct.NO)
			return this.getDailyAttendanceItemsHolidays();
		return new ArrayList<>();
	}
	
	/**
	 * 	[4] 利用できない月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItems() {
		// @管理区分 == 管理しない
		if ( this.manageDistinct == ManageDistinct.NO)
			return this.getMonthlyAttendanceItemsHolidays();
		return new ArrayList<>();
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public ComSubstVacation(ComSubstVacationGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.setting = memento.getSetting();
		this.manageDistinct = memento.getManageDistinct();
		this.linkingManagementATR = memento.getLinkingManagementATR();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComSubstVacationSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setSetting(this.setting);
		memento.setManageDistinct(this.manageDistinct);
		memento.setLinkingManagementATR(this.linkingManagementATR);
	}

}
