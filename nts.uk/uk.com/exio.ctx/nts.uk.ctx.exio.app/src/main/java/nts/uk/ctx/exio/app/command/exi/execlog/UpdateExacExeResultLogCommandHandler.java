package nts.uk.ctx.exio.app.command.exi.execlog;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExtExecutionMode;
import nts.uk.ctx.exio.dom.exi.execlog.ExtResultStatus;
import nts.uk.ctx.exio.dom.exi.execlog.ProcessingFlg;
import nts.uk.ctx.exio.dom.exi.execlog.StandardFlg;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;

@Stateless
@Transactional
public class UpdateExacExeResultLogCommandHandler extends CommandHandler<ExacExeResultLogCommand> {

	@Inject
	private ExacExeResultLogRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExacExeResultLogCommand> context) {
		ExacExeResultLogCommand command = context.getCommand();
		ExacExeResultLog domain = new ExacExeResultLog(command.getCid(),
				command.getConditionSetCd(),
				command.getExternalProcessId(),
				command.getExecutorId(),
				command.getUserId(),
				command.getProcessStartDatetime(),
				EnumAdaptor.valueOf(command.getStandardAtr(), StandardFlg.class),
				EnumAdaptor.valueOf(command.getExecuteForm(), ExtExecutionMode.class),
				command.getTargetCount(),
				command.getErrorCount(),
				command.getFileName(),
				EnumAdaptor.valueOf(command.getSystemType(), SystemType.class),
				Optional.ofNullable(command.getResultStatus() == null ? null : EnumAdaptor.valueOf(command.getResultStatus(), ExtResultStatus.class)),
				Optional.ofNullable(command.getProcessEndDatetime()),
				EnumAdaptor.valueOf(command.getProcessAtr(), ProcessingFlg.class));
		repository.update(domain);

	}
}
