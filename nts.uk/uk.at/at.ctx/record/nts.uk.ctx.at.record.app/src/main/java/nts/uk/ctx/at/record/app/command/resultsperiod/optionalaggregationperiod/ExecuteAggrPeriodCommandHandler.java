package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.byperiod.ByPeriodAggregationService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExecuteAggrPeriodCommandHandler extends AsyncCommandHandler<String>{

	@Inject
	private ByPeriodAggregationService byPeriodAggregationService;
	//private ExecuteAggrPeriodDomainService periodDomainService;
	
	@Override
	protected void handle(CommandHandlerContext<String> context) {
		val asyncContext = context.asAsync();
		val dataSetter = asyncContext.getDataSetter();
		String companyId = AppContexts.user().companyId();
		String excuteId = context.getCommand();
		//任意期間集計Mgrクラス
		//periodDomainService.excuteOptionalPeriod(companyId, excuteId, asyncContext);
		this.byPeriodAggregationService.manager(companyId, excuteId, asyncContext);
		dataSetter.setData("endTime", GeneralDateTime.now().toString());
	}

}
