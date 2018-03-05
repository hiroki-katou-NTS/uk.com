package nts.uk.ctx.exio.app.command.exi.item;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;

@Stateless
@Transactional
public class UpdateStdAcceptItemCommandHandler extends CommandHandler<StdAcceptItemCommand>
{
    
    @Inject
    private StdAcceptItemRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdAcceptItemCommand> context) {
        StdAcceptItemCommand updateCommand = context.getCommand();
//        repository.update(StdAcceptItem.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(), updateCommand.getConditionSetCd(), updateCommand.getCategoryId(), updateCommand.getAcceptItemNumber(), updateCommand.getSystemType(), updateCommand.getCsvItemNumber(), updateCommand.getCsvItemName(), updateCommand.getItemType(), updateCommand.getCategoryItemNo()));
    
    }
}
