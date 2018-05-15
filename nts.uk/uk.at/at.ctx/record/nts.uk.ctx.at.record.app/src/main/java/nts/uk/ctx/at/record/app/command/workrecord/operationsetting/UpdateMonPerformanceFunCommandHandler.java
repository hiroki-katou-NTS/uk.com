package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFunRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;

@Stateless
@Transactional
public class UpdateMonPerformanceFunCommandHandler extends CommandHandler<MonPerformanceFunCommand>
{
    
    @Inject
    private MonPerformanceFunRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<MonPerformanceFunCommand> context) {
        MonPerformanceFunCommand updateCommand = context.getCommand();
        repository.update(MonPerformanceFun.createFromJavaType(updateCommand.getCid(), updateCommand.getComment(), updateCommand.isDailySelfChkDispAtr() ? 1 : 0));    
    }
}
