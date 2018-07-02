package nts.uk.ctx.exio.app.command.exo.executionlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.executionlog.ExIoOperationState;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutProcessingId;
import nts.uk.ctx.exio.dom.exo.executionlog.NotUseAtr;

@Stateless
@Transactional
public class UpdateExOutOpMngCommandHandler extends CommandHandler<ExOutOpMngCommand> {

	@Inject
	private ExOutOpMngRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExOutOpMngCommand> context) {
		ExOutOpMngCommand updateCommand = context.getCommand();
		repository.update(new ExOutOpMng(new ExOutProcessingId(updateCommand.getExOutProId()),
				updateCommand.getProCnt(),
				updateCommand.getErrCnt(),
				updateCommand.getTotalProCnt(),
				EnumAdaptor.valueOf(updateCommand.getDoNotInterrupt(),NotUseAtr.class),
				updateCommand.getProUnit(),
				EnumAdaptor.valueOf(updateCommand.getOpCond(),ExIoOperationState.class)
				));

	}
}
