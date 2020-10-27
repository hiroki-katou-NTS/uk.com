/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.estcomparison;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstComparisonAtr;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.estcomparison.KscmtEstComparison;

/**
 * The Class JpaEstComparisonSetMemento.
 */
public class JpaEstComparisonSetMemento implements EstimateComparisonSetMemento {

	/** The typed value. */
	private KscmtEstComparison typedValue;

	/**
	 * Instantiates a new jpa est comparison set memento.
	 *
	 * @param typedValue
	 *            the typed value
	 */
	public JpaEstComparisonSetMemento(KscmtEstComparison typedValue) {
		super();
		this.typedValue = typedValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.
	 * EstimateComparisonSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typedValue.setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.
	 * EstimateComparisonSetMemento#setComparisonAtr(nts.uk.ctx.at.schedule.dom.
	 * shift.estimate.EstComparisonAtr)
	 */
	@Override
	public void setComparisonAtr(EstComparisonAtr comparisonAtr) {
		this.typedValue.setComparisonAtr(comparisonAtr.value);
	}

}
