/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern;

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
	private CompanyId companyId;
	
	/** The monthly pattern code. */
	//月間パターンコード
	private MonthlyPatternCode monthlyPatternCode;
	
	/** The monthly pattern name. */
	// 月間パターン名称
	private MonthlyPatternName monthlyPatternName;
	
	
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
	}
}
