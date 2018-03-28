package nts.uk.ctx.exio.app.command.exi.execlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository;

@Stateless
@Transactional
public class RemoveExacErrorLogCommandHandler extends CommandHandler<ExacErrorLogCommand> {

	@Inject
	private ExacErrorLogRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExacErrorLogCommand> context) {
		int logSeqNumber = context.getCommand().getLogSeqNumber();
		String cid = context.getCommand().getCid();
		String externalProcessId = context.getCommand().getExternalProcessId();
		repository.remove(logSeqNumber, cid, externalProcessId);
	}
}
