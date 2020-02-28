package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;

@Stateless
@Transactional
public class RemoveValPayDateSetCommandHandler extends CommandHandler<ValPayDateSetCommand>
{
    
    @Inject
    private ValPayDateSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ValPayDateSetCommand> context) {
        String cid = context.getCommand().getCid();
        int processCateNo = context.getCommand().getProcessCateNo();
        repository.remove(cid, processCateNo);
    }
}
