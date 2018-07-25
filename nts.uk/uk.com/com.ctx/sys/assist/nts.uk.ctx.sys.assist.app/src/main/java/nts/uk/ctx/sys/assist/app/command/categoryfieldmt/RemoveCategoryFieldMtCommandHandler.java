package nts.uk.ctx.sys.assist.app.command.categoryfieldmt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMtRepository;

@Stateless
@Transactional
public class RemoveCategoryFieldMtCommandHandler extends CommandHandler<CategoryFieldMtCommand>
{
    
    @Inject
    private CategoryFieldMtRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CategoryFieldMtCommand> context) {
        repository.remove();
    }
}
