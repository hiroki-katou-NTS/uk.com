package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AggregatePeriodDomainService {

public AggProcessState checkAggrPeriod(String companyId, String employeeId, DatePeriod datePeriod );
}
