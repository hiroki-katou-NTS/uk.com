package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.ValueOuputFormat;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.ItemOutTblBookRepository;

@Stateless
@Transactional
public class UpdateItemOutTblBookCommandHandler extends CommandHandler<ItemOutTblBookCommand>
{
    
    @Inject
    private ItemOutTblBookRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ItemOutTblBookCommand> context) {
        ItemOutTblBookCommand updateCommand = context.getCommand();
        repository.update(ItemOutTblBook.createFromJavaType(updateCommand.getCid(), updateCommand.getCd(),
                          updateCommand.getSetOutCd(), updateCommand.getSortBy(),
                          updateCommand.getHeadingName(), updateCommand.getUseClass(),
                          updateCommand.getValOutFormat()));
    
    }
}
