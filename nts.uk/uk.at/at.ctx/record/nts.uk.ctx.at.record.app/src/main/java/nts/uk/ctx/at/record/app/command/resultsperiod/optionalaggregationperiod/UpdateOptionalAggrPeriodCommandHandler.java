package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;

@Stateless
public class UpdateOptionalAggrPeriodCommandHandler extends CommandHandler<String>{
	
	@Inject
	private AggrPeriodExcutionRepository excutionrRepository;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		String command = context.getCommand();
		excutionrRepository.updateStopState(command);
		
	}

}
