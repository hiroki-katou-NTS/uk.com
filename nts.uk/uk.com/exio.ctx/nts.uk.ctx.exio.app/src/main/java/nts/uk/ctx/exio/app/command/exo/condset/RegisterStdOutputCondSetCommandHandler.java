package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;

@Stateless
public class RegisterStdOutputCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand>{
	
	@Inject
	private StdOutputCondSetService stdOutputCondSetService;
	
	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		StdOutputCondSetCommand command = context.getCommand();
		String screenMode = command.getScreenMode();
		String standType = command.getStandType();
		StdOutputCondSet stdOutputCondSet = StdOutputCondSet.createFromJavaType(command.getCid(), command.getConditionSetCd(), command.getCategoryId(), command.getDelimiter(),
				command.getItemOutputName(),command.getAutoExecution(), command.getConditionSetName(), command.getConditionOutputName(), command.getStringFormat());
		stdOutputCondSetService.registerOutputSet(screenMode, standType, stdOutputCondSet, command.isCheckAutoExecution());
	}
	
}
