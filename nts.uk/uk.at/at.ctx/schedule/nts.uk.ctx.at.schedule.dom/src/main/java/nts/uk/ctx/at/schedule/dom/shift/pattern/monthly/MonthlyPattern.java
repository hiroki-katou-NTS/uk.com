/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class MonthlyPattern.
 */
// 月間パターン

@Getter
public class MonthlyPattern extends AggregateRoot {

	/** The company id. */
	//会社ID
	private final CompanyId companyId;
	
	/** The monthly pattern code. */
	//月間パターンコード
	private final MonthlyPatternCode monthlyPatternCode;
	
	/** The monthly pattern name. */
	// 月間パターン名称
	private MonthlyPatternName monthlyPatternName;

	/**
	 * [C-0] 月間パターン(会社ID, 月間パターンコード, 月間パターン名称)
	 * @param companyId
	 * @param monthlyPatternCode
	 * @param monthlyPatternName
	 */
	public MonthlyPattern(CompanyId companyId, MonthlyPatternCode monthlyPatternCode, MonthlyPatternName monthlyPatternName) {
		this.companyId = companyId;
		this.monthlyPatternCode = monthlyPatternCode;
		this.monthlyPatternName = monthlyPatternName;
	}

	/**
	 * Instantiates a new monthly pattern.
	 *
	 * @param memento the memento
	 */
	public MonthlyPattern(MonthlyPatternGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.monthlyPatternCode = memento.getMonthlyPatternCode();
		this.monthlyPatternName = memento.getMonthlyPatternName();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(MonthlyPatternSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setMonthlyPatternCode(this.monthlyPatternCode);
		memento.setMonthlyPatternName(this.monthlyPatternName);
		memento.setContractCd();
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
		MonthlyPattern other = (MonthlyPattern) obj;
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
		return true;
	}
	
	
}
