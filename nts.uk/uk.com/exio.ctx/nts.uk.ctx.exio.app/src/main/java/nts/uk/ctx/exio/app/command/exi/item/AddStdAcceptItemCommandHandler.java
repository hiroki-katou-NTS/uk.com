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
public class AddStdAcceptItemCommandHandler extends CommandHandler<StdAcceptItemCommand>
{
    
    @Inject
    private StdAcceptItemRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdAcceptItemCommand> context) {
        StdAcceptItemCommand addCommand = context.getCommand();
        repository.add(StdAcceptItem.createFromJavaType(0L, addCommand.getCid(), addCommand.getConditionSetCd(), addCommand.getCategoryId(), addCommand.getAcceptItemNumber(), addCommand.getSystemType(), addCommand.getCsvItemNumber(), addCommand.getCsvItemName(), addCommand.getItemType(), addCommand.getCategoryItemNo()));
    
    }
}
