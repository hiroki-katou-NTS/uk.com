package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;

public interface ExecuteAggrPeriodDomainService {
	<C> void excuteOptionalPeriod(String companyId, String excuteId, AsyncCommandHandlerContext<C> asyn);

}
