package nts.uk.ctx.exio.app.command.exi.item;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;

@Stateless
@Transactional
public class RemoveStdAcceptItemCommandHandler extends CommandHandler<StdAcceptItemCommand>
{
    
    @Inject
    private StdAcceptItemRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdAcceptItemCommand> context) {
//        String cid = context.getCommand().getCid();
//        String conditionSetCd = context.getCommand().getConditionSetCd();
//        String categoryId = context.getCommand().getCategoryId();
//        int acceptItemNumber = context.getCommand().getAcceptItemNumber();
//        repository.remove(cid, conditionSetCd, categoryId, acceptItemNumber);
    }
}
