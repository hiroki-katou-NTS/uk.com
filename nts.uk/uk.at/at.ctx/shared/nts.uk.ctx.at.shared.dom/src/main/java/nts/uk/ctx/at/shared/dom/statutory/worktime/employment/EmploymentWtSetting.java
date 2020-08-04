/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;

/**
 * 雇用労働時間設定.
 */
@Getter
public class EmploymentWtSetting extends AggregateRoot {

	/** 会社ID. */
	private CompanyId companyId;
	
	/** 年. */
	private Year year;
	
	/** 雇用コード. */
	private String employmentCode;
	
	/** フレックス勤務労働時間設定. */
	private FlexSetting flexSetting;

	/** 変形労働労働時間設定. */
	private DeformationLaborSetting deformationLaborSetting;

	/** 通常勤務労働時間設定. */
	private NormalSetting normalSetting;

	/**
	 * Instantiates a new employment setting.
	 *
	 * @param memento the memento
	 */
	public EmploymentWtSetting(EmploymentWtSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.flexSetting = memento.getFlexSetting();
		this.deformationLaborSetting = memento.getDeformationLaborSetting();
		this.normalSetting = memento.getNormalSetting();
		this.year = memento.getYear();
		this.employmentCode = memento.getEmploymentCode();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmploymentWtSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setYear(this.year);
		memento.setDeformationLaborSetting(this.deformationLaborSetting);
		memento.setFlexSetting(this.flexSetting);
		memento.setNormalSetting(this.normalSetting);
		memento.setEmploymentCode(this.employmentCode);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		EmploymentWtSetting other = (EmploymentWtSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

}
