package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;

@Stateless
@Transactional
public class UpdateStdOutputCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand> {

	@Inject
	private StdOutputCondSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		String cId = AppContexts.user().companyId();
		StdOutputCondSetCommand updateCommand = context.getCommand();
		repository.update(new StdOutputCondSet(cId, updateCommand.getConditionSetCd(),
				updateCommand.getCategoryId(), updateCommand.getDelimiter(), updateCommand.getItemOutputName(),
				updateCommand.getAutoExecution(), updateCommand.getConditionSetName(),
				updateCommand.getConditionOutputName(), updateCommand.getStringFormat()));
	}
}
