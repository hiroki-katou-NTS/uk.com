/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.estcomparison;

import lombok.Data;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstComparisonAtr;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonSetMemento;

/**
 * The Class EstimateComparisonFindDto.
 */
@Data
public class EstimateComparisonFindDto implements EstimateComparisonSetMemento {
	
	/** The comparison atr. */
	private int comparisonAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.
	 * EstimateComparisonSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// Do nothing
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
		this.comparisonAtr = comparisonAtr.value;
	}

}
