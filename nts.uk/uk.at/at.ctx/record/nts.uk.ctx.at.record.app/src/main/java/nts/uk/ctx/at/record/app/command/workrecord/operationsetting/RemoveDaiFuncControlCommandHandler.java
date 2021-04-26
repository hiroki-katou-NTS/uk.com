package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiFuncControlRepository;

@Stateless
@Transactional
public class RemoveDaiFuncControlCommandHandler extends CommandHandler<DaiFuncControlCommand> {

	@Inject
	private DaiFuncControlRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<DaiFuncControlCommand> context) {
		String cid = context.getCommand().getCid();
        repository.remove(cid);
	}

}
