package nts.uk.ctx.exio.app.command.exi.execlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;

@Stateless
@Transactional
public class RemoveExacExeResultLogCommandHandler extends CommandHandler<ExacExeResultLogCommand> {

	@Inject
	private ExacExeResultLogRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExacExeResultLogCommand> context) {
		String cid = context.getCommand().getCid();
		String conditionSetCd = context.getCommand().getConditionSetCd();
		String externalProcessId = context.getCommand().getExternalProcessId();
		repository.remove(cid, conditionSetCd, externalProcessId);
	}
}
