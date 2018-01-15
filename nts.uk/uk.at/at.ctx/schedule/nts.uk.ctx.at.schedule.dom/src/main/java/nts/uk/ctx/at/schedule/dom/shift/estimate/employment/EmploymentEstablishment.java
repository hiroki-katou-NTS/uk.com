/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentEstablishment.
 */
// 雇用目安設定

@Getter
public class EmploymentEstablishment extends AggregateRoot{
	
	/** The company id. */
	//会社ID
	private CompanyId companyId;

	/** The employment code. */
	//雇用コード
	private EmploymentCode employmentCode;
	
	/** The target year. */
	// 対象年
	private Year targetYear;
	
	/** The advanced setting. */
	//詳細設定
	private EstimateDetailSetting advancedSetting;
	
	
	/**
	 * Instantiates a new employment establishment.
	 *
	 * @param memento the memento
	 */
	public EmploymentEstablishment(EmploymentEstablishmentGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.targetYear = memento.getTargetYear();
		this.advancedSetting = memento.getAdvancedSetting();
		this.employmentCode = memento.getEmploymentCode();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmploymentEstablishmentSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setTargetYear(this.targetYear);
		memento.setAdvancedSetting(this.advancedSetting);
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
		result = prime * result + ((employmentCode == null) ? 0 : employmentCode.hashCode());
		result = prime * result + ((targetYear == null) ? 0 : targetYear.hashCode());
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
		EmploymentEstablishment other = (EmploymentEstablishment) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (employmentCode == null) {
			if (other.employmentCode != null)
				return false;
		} else if (!employmentCode.equals(other.employmentCode))
			return false;
		if (targetYear == null) {
			if (other.targetYear != null)
				return false;
		} else if (!targetYear.equals(other.targetYear))
			return false;
		return true;
	}
	
}
