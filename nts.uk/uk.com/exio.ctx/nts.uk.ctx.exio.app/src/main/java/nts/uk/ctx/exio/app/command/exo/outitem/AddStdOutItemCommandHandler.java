package nts.uk.ctx.exio.app.command.exo.outitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItemRepository;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItem;

@Stateless
@Transactional
public class AddStdOutItemCommandHandler extends CommandHandler<StdOutItemCommand>
{
    
    @Inject
    private StdOutItemRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdOutItemCommand> context) {
        StdOutItemCommand addCommand = context.getCommand();
        repository.add(StdOutItem.createFromJavaType(addCommand.getCid(), addCommand.getOutItemCd(), addCommand.getCondSetCd(), addCommand.getOutItemName(), addCommand.getItemType()));
    
    }
}
