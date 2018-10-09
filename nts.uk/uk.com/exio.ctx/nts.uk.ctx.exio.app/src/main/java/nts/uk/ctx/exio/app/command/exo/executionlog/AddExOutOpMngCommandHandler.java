package nts.uk.ctx.exio.app.command.exo.executionlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;;

@Stateless
@Transactional
public class AddExOutOpMngCommandHandler extends CommandHandler<ExOutOpMngCommand> {

	@Inject
	private ExOutOpMngRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExOutOpMngCommand> context) {
		ExOutOpMngCommand addCommand = context.getCommand();
		repository.add(new ExOutOpMng(IdentifierUtil.randomUniqueId(), addCommand.getProCnt(), addCommand.getErrCnt(),
				addCommand.getTotalProCnt(), addCommand.getDoNotInterrupt(), addCommand.getProUnit(),
				addCommand.getOpCond()));
	}
}
