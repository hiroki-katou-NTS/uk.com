package nts.uk.ctx.exio.app.command.exo.condset;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;

@Transactional
@Stateless
public class RegisterStdOutputCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand>{
	
	@Inject
	private StdOutputCondSetService stdOutputCondSetService;
	
	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		StdOutputCondSetCommand command = context.getCommand();
		boolean isNewMode = command.isNewMode();
		int standType = command.getStandType();
		List<StandardOutputItemOrder> stdOutItemOrder = command.getStdOutItemOrder();
		StdOutputCondSet stdOutputCondSet = new StdOutputCondSet(command.getCid(), command.getConditionSetCd(),
				command.getCategoryId(), command.getDelimiter(), command.getItemOutputName(),
				command.getAutoExecution(), command.getConditionSetName(), command.getConditionOutputName(),
				command.getStringFormat());
		stdOutputCondSetService.registerOutputSet(isNewMode, standType, stdOutputCondSet,
				command.isCheckAutoExecution(), stdOutItemOrder);
	}
	
}
