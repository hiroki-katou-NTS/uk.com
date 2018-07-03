package nts.uk.ctx.exio.app.command.exo.executionlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutProcessingId;
import nts.uk.ctx.exio.dom.exo.executionlog.NotUseAtr;
import nts.uk.ctx.exio.dom.exo.executionlog.ExIoOperationState;;

@Stateless
@Transactional
public class AddExOutOpMngCommandHandler extends CommandHandler<ExOutOpMngCommand> {

	@Inject
	private ExOutOpMngRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExOutOpMngCommand> context) {
		ExOutOpMngCommand addCommand = context.getCommand();
		repository.add(new ExOutOpMng(new ExOutProcessingId(addCommand.getExOutProId()),
				addCommand.getProCnt(),
				addCommand.getErrCnt(),
				addCommand.getTotalProCnt(),
				EnumAdaptor.valueOf(addCommand.getDoNotInterrupt(),NotUseAtr.class),
				addCommand.getProUnit(),
				EnumAdaptor.valueOf(addCommand.getOpCond(),ExIoOperationState.class)
				));

	}
}
