package nts.uk.ctx.exio.app.command.exi.execlog;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLog;

@Stateless
@Transactional
public class AddExacErrorLogCommandHandler extends CommandHandler<ExacErrorLogCommand>
{
    
    @Inject
    private ExacErrorLogRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExacErrorLogCommand> context) {
        //ExacErrorLogCommand addCommand = context.getCommand();
        //repository.add(ExacErrorLog.createFromJavaType(0L, addCommand.getLogSeqNumber(), addCommand.getCid(), addCommand.getExternalProcessId(), addCommand.getCsvErrorItemName(), addCommand.getCsvAcceptedValue(), addCommand.getErrorContents(), addCommand.getRecordNumber(), addCommand.getLogRegDateTime(), addCommand.getItemName(), addCommand.getErrorAtr()));
    
    }
}
