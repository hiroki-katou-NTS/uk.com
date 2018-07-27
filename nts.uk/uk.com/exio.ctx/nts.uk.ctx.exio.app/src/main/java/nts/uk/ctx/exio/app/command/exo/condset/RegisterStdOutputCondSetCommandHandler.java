package nts.uk.ctx.exio.app.command.exo.condset;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.RegisterMode;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class RegisterStdOutputCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand>{
	
	@Inject
	private StdOutputCondSetService stdOutputCondSetService;
	
	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		StdOutputCondSetCommand command = context.getCommand();
		int mode;
		if(command.isNewMode()) {
			mode = RegisterMode.NEW.value;
		} else {
			mode = RegisterMode.UPDATE.value;
		}
		int standType = command.getStandType();
		String cId = AppContexts.user().companyId();
		StdOutputCondSet stdOutputCondSet = new StdOutputCondSet(cId, command.getConditionSetCd(),
				command.getCategoryId(), command.getDelimiter(), command.getItemOutputName(),
				command.getAutoExecution(), command.getConditionSetName(), command.getConditionOutputName(),
				command.getStringFormat());
		stdOutputCondSetService.registerOutputSet(mode, standType, stdOutputCondSet,
				command.getAutoExecution());
	}
	
}
