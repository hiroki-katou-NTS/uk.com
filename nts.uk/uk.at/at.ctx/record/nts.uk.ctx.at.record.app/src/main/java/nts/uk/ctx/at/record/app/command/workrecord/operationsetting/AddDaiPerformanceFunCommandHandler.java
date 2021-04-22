package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFunRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;

@Stateless
@Transactional
public class AddDaiPerformanceFunCommandHandler extends CommandHandler<DaiPerformanceFunCommand>
{
    
    @Inject
    private DaiPerformanceFunRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<DaiPerformanceFunCommand> context) {
        DaiPerformanceFunCommand addCommand = context.getCommand();
        repository.add(DaiPerformanceFun.createFromJavaType(addCommand.getCid(), addCommand.getComment(), 
																addCommand.isDisp36Atr() ? 1 : 0, 
																addCommand.isFlexDispAtr() ? 1 : 0, 
																addCommand.isCheckErrRefDisp()? 1 : 0));

    }
}
