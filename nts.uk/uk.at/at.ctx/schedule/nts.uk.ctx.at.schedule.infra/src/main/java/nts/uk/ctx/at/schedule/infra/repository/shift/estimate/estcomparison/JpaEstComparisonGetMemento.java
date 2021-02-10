/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.estcomparison;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstComparisonAtr;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.estcomparison.KscmtEstComparison;

/**
 * The Class JpaEstComparisonGetMemento.
 */
public class JpaEstComparisonGetMemento implements EstimateComparisonGetMemento {

	/** The typed value. */
	private KscmtEstComparison typedValue;
	
	/**
	 * Instantiates a new jpa est comparison get memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaEstComparisonGetMemento(KscmtEstComparison typedValue) {
		super();
		this.typedValue = typedValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typedValue.getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonGetMemento#getComparisonAtr()
	 */
	@Override
	public EstComparisonAtr getComparisonAtr() {
		return EstComparisonAtr.valueOf(this.typedValue.getComparisonAtr());
	}

}
