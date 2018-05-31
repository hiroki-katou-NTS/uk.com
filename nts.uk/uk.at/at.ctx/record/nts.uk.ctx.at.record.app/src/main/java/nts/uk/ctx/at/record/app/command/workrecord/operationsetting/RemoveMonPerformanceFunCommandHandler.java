package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFunRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;

@Stateless
@Transactional
public class RemoveMonPerformanceFunCommandHandler extends CommandHandler<MonPerformanceFunCommand>
{
    
    @Inject
    private MonPerformanceFunRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<MonPerformanceFunCommand> context) {
        String cid = context.getCommand().getCid();
        repository.remove(cid);
    }
}
