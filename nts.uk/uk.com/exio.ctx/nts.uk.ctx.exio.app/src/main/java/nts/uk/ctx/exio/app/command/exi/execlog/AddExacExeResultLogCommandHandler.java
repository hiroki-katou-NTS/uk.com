package nts.uk.ctx.exio.app.command.exi.execlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;

@Stateless
@Transactional
public class AddExacExeResultLogCommandHandler extends CommandHandler<ExacExeResultLogCommand>
{
    
    @Inject
    private ExacExeResultLogRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExacExeResultLogCommand> context) {
        ExacExeResultLogCommand addCommand = context.getCommand();
        repository.add(ExacExeResultLog.createFromJavaType(0L, addCommand.getCid(), addCommand.getConditionSetCd(), addCommand.getExternalProcessId(), addCommand.getExecutorId(), addCommand.getUserId(), addCommand.getProcessStartDatetime(), addCommand.getStandardAtr(), addCommand.getExecuteForm(), addCommand.getTargetCount(), addCommand.getErrorCount(), addCommand.getFileName(), addCommand.getSystemType(), addCommand.getResultStatus(), addCommand.getProcessEndDatetime(), addCommand.getProcessAtr()));
    
    }
}
