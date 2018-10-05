package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;

@Stateless
@Transactional
public class AddStdOutputCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand> {

	@Inject
	private StdOutputCondSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		StdOutputCondSetCommand addCommand = context.getCommand();
		repository.add(StdOutputCondSet.createFromJavaType(addCommand.getCid(), addCommand.getConditionSetCd(),
				addCommand.getCategoryId(), addCommand.getDelimiter(), addCommand.getItemOutputName(),
				addCommand.getAutoExecution(), addCommand.getConditionSetName(), addCommand.getConditionOutputName(),
				addCommand.getStringFormat()));

	}
}
