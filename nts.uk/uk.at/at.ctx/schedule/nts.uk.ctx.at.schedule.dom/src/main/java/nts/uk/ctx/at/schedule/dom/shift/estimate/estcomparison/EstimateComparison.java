/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstComparisonAtr;

/**
 * The Class EstimateComparison.
 */
// 目安比較対象
@Getter
public class EstimateComparison extends AggregateRoot {

	/** The company id. */
	private String companyId;
	
	/** The comparison atr. */
	private EstComparisonAtr comparisonAtr;
	
	/**
	 * Instantiates a new estimate comparison.
	 *
	 * @param memento the memento
	 */
	public EstimateComparison (EstimateComparisonGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.comparisonAtr = memento.getComparisonAtr();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (EstimateComparisonSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setComparisonAtr(this.comparisonAtr);
	}
	
	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}
	
	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstimateComparison other = (EstimateComparison) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}
	
	
}
