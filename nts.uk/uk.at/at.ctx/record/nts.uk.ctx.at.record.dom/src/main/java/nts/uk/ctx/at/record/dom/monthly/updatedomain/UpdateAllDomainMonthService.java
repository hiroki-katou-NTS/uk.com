package nts.uk.ctx.at.record.dom.monthly.updatedomain;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;

public interface UpdateAllDomainMonthService {
	
	public void merge(List<IntegrationOfMonthly> domains, GeneralDate targetDate);
}
