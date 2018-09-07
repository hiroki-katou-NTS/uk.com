package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.DetailPrintingMonRepository;

@Stateless
@Transactional
public class RemoveDetailPrintingMonCommandHandler extends CommandHandler<DetailPrintingMonCommand>
{
    
    @Inject
    private DetailPrintingMonRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<DetailPrintingMonCommand> context) {
        int processCateNo = context.getCommand().getProcessCateNo();
        String cid = context.getCommand().getCid();
        repository.remove(processCateNo, cid);
    }
}
