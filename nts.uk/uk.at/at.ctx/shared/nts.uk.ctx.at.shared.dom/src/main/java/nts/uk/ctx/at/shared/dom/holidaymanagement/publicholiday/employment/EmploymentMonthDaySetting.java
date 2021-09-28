/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;

/**
 * The Class EmploymentMonthDaySetting.
 */
//雇用月間日数設定
@Getter
@Setter
public class EmploymentMonthDaySetting extends AggregateRoot{
	
	/** The company ID. */
	// 会社ID
	private CompanyId companyId;
	
	/** The management year. */
	// 管理年度
	private Year managementYear;
	
	/** The employment code. */
	// 雇用コード
	private String employmentCode;
	
	/** The public holiday month settings. */
	// 月間公休日数
	private List<PublicHolidayMonthSetting> publicHolidayMonthSettings;
	
	
	/**
	 * Instantiates a new employment month day setting.
	 *
	 * @param memento the memento
	 */
	public EmploymentMonthDaySetting(EmploymentMonthDaySettingGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.managementYear = memento.getManagementYear();
		this.employmentCode = memento.getEmploymentCode();
		this.publicHolidayMonthSettings = memento.getPublicHolidayMonthSettings();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmploymentMonthDaySettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setManagementYear(this.managementYear);
		memento.setEmploymentCode(this.employmentCode);
		memento.setPublicHolidayMonthSettings(this.publicHolidayMonthSettings);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((managementYear == null) ? 0 : managementYear.hashCode());
		result = prime * result + ((employmentCode == null) ? 0 : employmentCode.hashCode());
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
		EmploymentMonthDaySetting other = (EmploymentMonthDaySetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (managementYear == null) {
			if (other.managementYear != null)
				return false;
		} else if (!managementYear.equals(other.managementYear))
			return false;
		if (employmentCode == null) {
			if (other.employmentCode != null)
				return false;
		} else if (!employmentCode.equals(other.employmentCode))
			return false;
		return true;
	}
	
	//年月リストから雇用月間日数設定を取得する
	/**
	 * 年月リストから雇用月間日数設定を取得する
	 * @param yearMonthList 年月リスト
	 * @return　List＜月間公休日数設定＞
	 */
	public List<PublicHolidayMonthSetting> getPublicHolidayMonthSetting(List<YearMonth> yearMonthList){
		List<PublicHolidayMonthSetting> publicHolidayMonthSetting = new ArrayList<>();
		
		for(YearMonth yearMonth : yearMonthList){
			publicHolidayMonthSetting.addAll(this.publicHolidayMonthSettings
					.stream()
					.filter(x -> yearMonth.equals(x.createYearMonth()))
					.collect(Collectors.toList())); 
			}
		return publicHolidayMonthSetting;
	}
}
