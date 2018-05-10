package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.ItemOutTblBookRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateItemOutTblBookCommandHandler extends CommandHandler<ItemOutTblBookCommand>
{
    
    @Inject
    private ItemOutTblBookRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ItemOutTblBookCommand> context) {
        ItemOutTblBookCommand updateCommand = context.getCommand();
        repository.update(ItemOutTblBook.createFromJavaType(AppContexts.user().companyId(), updateCommand.getSetOutCd(),
                                                            updateCommand.getCd(), updateCommand.getSortBy(),
                                                            updateCommand.getHeadingName(), updateCommand.getUseClass(),
                                                            updateCommand.getValOutFormat()));
    
    }
}
