package nts.uk.ctx.exio.app.command.exo.outitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItemRepository;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItem;

@Stateless
@Transactional
public class RemoveStdOutItemCommandHandler extends CommandHandler<StdOutItemCommand>
{
    
    @Inject
    private StdOutItemRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdOutItemCommand> context) {
        String cid = context.getCommand().getCid();
        String outItemCd = context.getCommand().getOutItemCd();
        String condSetCd = context.getCommand().getCondSetCd();
        repository.remove(cid, outItemCd, condSetCd);
    }
}
