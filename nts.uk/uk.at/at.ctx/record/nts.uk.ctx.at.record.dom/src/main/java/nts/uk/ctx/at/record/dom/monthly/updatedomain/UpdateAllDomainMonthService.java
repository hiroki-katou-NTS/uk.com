package nts.uk.ctx.at.record.dom.monthly.updatedomain;

import java.util.List;

import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;

public interface UpdateAllDomainMonthService {

	public void insertUpdateAll(List<IntegrationOfMonthly> domains);
	
	public void merge(List<IntegrationOfMonthly> domains);
}
