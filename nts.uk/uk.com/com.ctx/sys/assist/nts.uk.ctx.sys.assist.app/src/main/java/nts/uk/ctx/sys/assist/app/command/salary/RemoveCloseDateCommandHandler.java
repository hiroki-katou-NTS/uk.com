package nts.uk.ctx.sys.assist.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.salary.CloseDateRepository;

@Stateless
@Transactional
public class RemoveCloseDateCommandHandler extends CommandHandler<CloseDateCommand>
{
    
    @Inject
    private CloseDateRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CloseDateCommand> context) {
        int processCateNo = context.getCommand().getProcessCateNo();
        String cid = context.getCommand().getCid();
        repository.remove(processCateNo, cid);
    }
}
