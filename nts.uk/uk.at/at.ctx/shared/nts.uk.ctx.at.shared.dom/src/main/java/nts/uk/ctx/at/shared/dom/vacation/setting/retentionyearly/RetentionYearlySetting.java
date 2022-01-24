/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

/**
 * The Class RetentionYearlySetting.
 */
// 積立年休設定
@Getter
public class RetentionYearlySetting extends AggregateRoot {
	
	/** The company id. */
	private String companyId;
	
	/** The upper limit setting. */
	private UpperLimitSetting upperLimitSetting;
	
	/** The management category. */
	private ManageDistinct managementCategory;
	
	/**
	 * [C-0] 積立年休設定
	 */
	public RetentionYearlySetting(String companyId, UpperLimitSetting upperLimitSetting,
			ManageDistinct managementCategory) {
		super();
		this.companyId = companyId;
		this.upperLimitSetting = upperLimitSetting;
		this.managementCategory = managementCategory;
	}
	
	/**
	 * [1] 積立年休に対応する日次の勤怠項目を取得する
	 */
	public List<Integer> getDailyAttendanceItemsRetentionYearly() {
		// 積立年休に対応する日次の勤怠項目
		return Arrays.asList(547);
	}
	
	/**
	 * [2] 積立年休に対応する月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItemsRetentionYearly() {
		// 積立年休に対応する月次の勤怠項目
		return Arrays.asList(187, 830, 831, 832, 834, 835, 836, 837, 838, 1790, 1791);
	}
	
	/**
	 * [3] 利用できない月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItems(Require require) {
		if (!this.isManageRetentionYearly(require))
			return this.getMonthlyAttendanceItemsRetentionYearly();
		return new ArrayList<>();
	}
	
	/**
	 * [4] 積立年休を管理するか
	 */
	public boolean isManageRetentionYearly(Require require) {
		AnnualPaidLeaveSetting annualPaid = require.findByCid(companyId);
		if (annualPaid == null || (annualPaid != null && annualPaid.getYearManageType() == ManageDistinct.NO) || managementCategory == ManageDistinct.NO)
			return false;
		return true;
	}
	
	/**
	 * [R-1] 年休設定を取得する
	 */
	public static interface Require {
		// AnnualPaidLeaveSettingRepository
		AnnualPaidLeaveSetting findByCid(String companyId);
	}
	
	/**
	 * Instantiates a new retention yearly setting.
	 *
	 * @param memento the memento
	 */
	public RetentionYearlySetting(RetentionYearlySettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.upperLimitSetting = memento.getUpperLimitSetting();
		this.managementCategory = memento
				.getManagementCategory();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(RetentionYearlySettingSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setUpperLimitSetting(this.upperLimitSetting);
		memento.setManagementCategory(this.managementCategory);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetentionYearlySetting other = (RetentionYearlySetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
}
