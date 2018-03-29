package nts.uk.ctx.exio.app.command.exi.execlog;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class AddExacErrorLogCommandHandler extends CommandHandler<ExacErrorLogCommand>
{
    @Override
    protected void handle(CommandHandlerContext<ExacErrorLogCommand> context) {
        //ExacErrorLogCommand addCommand = context.getCommand();
        //repository.add(ExacErrorLog.createFromJavaType(0L, addCommand.getLogSeqNumber(), addCommand.getCid(), addCommand.getExternalProcessId(), addCommand.getCsvErrorItemName(), addCommand.getCsvAcceptedValue(), addCommand.getErrorContents(), addCommand.getRecordNumber(), addCommand.getLogRegDateTime(), addCommand.getItemName(), addCommand.getErrorAtr()));
    
    }
}
