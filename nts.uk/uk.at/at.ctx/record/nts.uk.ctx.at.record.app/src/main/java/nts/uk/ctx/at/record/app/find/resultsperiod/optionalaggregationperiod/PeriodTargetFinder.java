package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;

@Stateless
public class PeriodTargetFinder {

	@Inject 
	private AggrPeriodTargetRepository targetRepository;
	
}
