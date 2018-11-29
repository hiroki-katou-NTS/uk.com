package nts.uk.ctx.exio.app.command.exi.execlog;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class UpdateExacErrorLogCommandHandler extends CommandHandler<ExacErrorLogCommand>
{
    
    @Override
    protected void handle(CommandHandlerContext<ExacErrorLogCommand> context) {
        //ExacErrorLogCommand updateCommand = context.getCommand();
        //repository.update(ExacErrorLog.createFromJavaType(updateCommand.getVersion(), updateCommand.getLogSeqNumber(), updateCommand.getCid(), updateCommand.getExternalProcessId(), updateCommand.getCsvErrorItemName(), updateCommand.getCsvAcceptedValue(), updateCommand.getErrorContents(), updateCommand.getRecordNumber(), updateCommand.getLogRegDateTime(), updateCommand.getItemName(), updateCommand.getErrorAtr()));
    
    }
}
