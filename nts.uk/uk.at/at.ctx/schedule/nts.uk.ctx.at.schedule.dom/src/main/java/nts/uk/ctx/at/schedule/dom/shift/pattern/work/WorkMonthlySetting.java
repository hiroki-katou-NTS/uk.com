/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class WorkMonthlySetting.
 */
// 月間勤務就業設定
@Getter
@Setter
public class WorkMonthlySetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The work type code. */
	// 勤務種類コード
	private WorkTypeCode workTypeCode;

	/** The working code. */
	// 就業時間帯コード
	private WorkingCode workingCode;
	
	/** The ymdk. */
	// 年月日
	private GeneralDate ymdk;
	
	/** The monthly pattern code. */
	// 月間パターンコード
	private MonthlyPatternCode monthlyPatternCode;
	

	/**
	 * Instantiates a new work monthly setting.
	 *
	 * @param memento the memento
	 */
	public WorkMonthlySetting(WorkMonthlySettingGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.workTypeCode = memento.getWorkTypeCode();
		this.workingCode = memento.getWorkingCode();
		this.ymdk = memento.getYmdK();
		this.monthlyPatternCode = memento.getMonthlyPatternCode();
	}

	/**
	 * Instantiates a new work monthly setting.
	 *
	 * @param ymdk the ymdk
	 * @param monthlyPatternCode the monthly pattern code
	 */
	public WorkMonthlySetting(GeneralDate ymdk, String monthlyPatternCode) {
		this.workTypeCode = new WorkTypeCode("");
		this.workingCode = new WorkingCode("");
		this.ymdk = ymdk;
		this.monthlyPatternCode = new MonthlyPatternCode(monthlyPatternCode);
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkMonthlySettingSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setWorkTypeCode(this.workTypeCode);
		memento.setWorkingCode(this.workingCode == null ? null : this.workingCode);
		memento.setYmdK(this.ymdk);
		memento.setMonthlyPatternCode(this.monthlyPatternCode);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result
				+ ((monthlyPatternCode == null) ? 0 : monthlyPatternCode.hashCode());
		result = prime * result + ((ymdk == null) ? 0 : ymdk.hashCode());
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
		WorkMonthlySetting other = (WorkMonthlySetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (monthlyPatternCode == null) {
			if (other.monthlyPatternCode != null)
				return false;
		} else if (!monthlyPatternCode.equals(other.monthlyPatternCode))
			return false;
		if (ymdk == null) {
			if (other.ymdk != null)
				return false;
		} else if (!ymdk.equals(other.ymdk))
			return false;
		return true;
	}
	
}
