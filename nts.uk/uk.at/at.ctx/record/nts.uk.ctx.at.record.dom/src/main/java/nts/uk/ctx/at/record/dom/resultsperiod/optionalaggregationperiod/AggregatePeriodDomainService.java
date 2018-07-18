package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AggregatePeriodDomainService {

public <C> AggProcessState checkAggrPeriod(String companyId, String employeeId, DatePeriod datePeriod, AsyncCommandHandlerContext<C> asyn);
}
