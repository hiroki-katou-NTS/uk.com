package nts.uk.ctx.exio.app.command.exo.executionlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;

@Stateless
@Transactional
public class UpdateExOutOpMngCommandHandler extends CommandHandler<ExOutOpMngCommand> {

	@Inject
	private ExOutOpMngRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExOutOpMngCommand> context) {
		ExOutOpMngCommand updateCommand = context.getCommand();
		repository.update(new ExOutOpMng(updateCommand.getExOutProId(), updateCommand.getProCnt(),
				updateCommand.getErrCnt(), updateCommand.getTotalProCnt(), updateCommand.getDoNotInterrupt(),
				updateCommand.getProUnit(), updateCommand.getOpCond()));

	}
}
