package nts.uk.ctx.exio.app.command.exi.opmanage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.opmanage.ExAcOpManageRepository;

@Stateless
@Transactional
public class RemoveExAcOpManageCommandHandler extends CommandHandler<ExAcOpManageCommand> {

	@Inject
	private ExAcOpManageRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExAcOpManageCommand> context) {
		String cid = context.getCommand().getCid();
		String processId = context.getCommand().getProcessId();
		repository.remove(cid, processId);
	}
}
