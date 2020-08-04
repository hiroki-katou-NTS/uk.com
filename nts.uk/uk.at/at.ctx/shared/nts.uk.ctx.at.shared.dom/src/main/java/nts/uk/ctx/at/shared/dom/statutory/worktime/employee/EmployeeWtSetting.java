/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employee;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;

/**
 * 社員労働時間設定.
 */
@Getter
public class EmployeeWtSetting extends AggregateRoot {

	/** 会社ID. */
	private CompanyId companyId;
	
	/** 社員ID. */
	private String employeeId;
	
	/** 年月. */
	private YearMonth yearMonth;

	/** 労働時間設定. */
	private WorkingTimeSetting workingTimeSetting;
	
	/** 通常労働時間設定*/
	private NormalSetting normalSetting;
	
	/** フレックス労働時間設定　*/
	private FlexSetting flexSetting;
	
	/** 変形労働時間設定 */
	private DeformationLaborSetting deformatinLaborSetting;

	/**
	 * Instantiates a new employee setting.
	 *
	 * @param memento the memento
	 */
	public EmployeeWtSetting(EmployeeWtSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workingTimeSetting = memento.getWorkingTimeSetting();
		this.yearMonth = memento.getYearMonth();
		this.employeeId = memento.getEmployeeId();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmployeeWtSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setWorkingTimeSetting(this.workingTimeSetting);
		memento.setYearMonth(this.yearMonth);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((yearMonth == null) ? 0 : yearMonth.hashCode());
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
		EmployeeWtSetting other = (EmployeeWtSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		return true;
	}

}
