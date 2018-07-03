package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AggregatePeriodDomainService;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.ExecuteAggrPeriodDomainService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ExecuteAggrPeriodCommandHandler extends AsyncCommandHandler<ExecuteAggrPeriodCommand>{

	@Inject
	private ExecuteAggrPeriodDomainService periodDomainService;
	
	@Inject
	private AggregatePeriodDomainService aggrPeriodService;
	
	@Override
	protected void handle(CommandHandlerContext<ExecuteAggrPeriodCommand> context) {
		val asyncContext = context.asAsync();
		val dataSetter = asyncContext.getDataSetter();
		String companyId = AppContexts.user().companyId();
		ExecuteAggrPeriodCommand command = context.getCommand();
		periodDomainService.excuteOptionalPeriod(companyId, command.excuteId, asyncContext);
		dataSetter.setData("endTime", GeneralDateTime.now().toString());
		DatePeriod periodTime = new DatePeriod(
				command.getStartDate(),
				command.getEndDate());

		aggrPeriodService.checkAggrPeriod(companyId, command.employeeId, periodTime );
		
	}

}
