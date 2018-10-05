package nts.uk.ctx.exio.app.command.exo.outputitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveStdOutItemCommandHandler extends CommandHandler<StdOutItemCommand>
{
    
    @Inject
    private StandardOutputItemRepository repository;
    
    @Inject
    private StandardOutputItemOrderRepository repositoryOrder;
    
    @Override
    protected void handle(CommandHandlerContext<StdOutItemCommand> context) {
        String cid = AppContexts.user().companyId();
        String outItemCd = context.getCommand().getOutItemCd();
        String condSetCd = context.getCommand().getCondSetCd();
        repository.remove(cid, outItemCd, condSetCd);
        repositoryOrder.remove(cid, outItemCd, condSetCd);
    }
}