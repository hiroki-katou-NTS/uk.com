package nts.uk.ctx.exio.app.command.exo.outputitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
@Transactional
public class RemoveStdOutItemCommandHandler extends CommandHandler<StdOutItemCommand>
{
    
    @Inject
    private StandardOutputItemRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdOutItemCommand> context) {
        String cid = context.getCommand().getCid();
        String outItemCd = context.getCommand().getOutItemCd();
        String condSetCd = context.getCommand().getCondSetCd();
        repository.remove(cid, outItemCd, condSetCd);
    }
}