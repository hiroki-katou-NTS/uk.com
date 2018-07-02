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
public class UpdateStdOutItemCommandHandler extends CommandHandler<StdOutItemCommand>
{
    
    @Inject
    private StdOutItemRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<StdOutItemCommand> context) {
        StdOutItemCommand updateCommand = context.getCommand();
        repository.update(StdOutItem.createFromJavaType(updateCommand.getCid(), updateCommand.getOutItemCd(), updateCommand.getCondSetCd(), updateCommand.getOutItemName(), updateCommand.getItemType()));
    
    }
}
