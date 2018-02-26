package nts.uk.ctx.exio.app.command.exi.category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exi.category.ExAcpCategoryRepository;
import nts.uk.ctx.exio.dom.exi.category.ExAcpCategory;

@Stateless
@Transactional
public class RemoveExAcpCategoryCommandHandler extends CommandHandler<ExAcpCategoryCommand>
{
    
    @Inject
    private ExAcpCategoryRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExAcpCategoryCommand> context) {
        String categoryId = context.getCommand().getCategoryId();
        repository.remove(categoryId);
    }
}
