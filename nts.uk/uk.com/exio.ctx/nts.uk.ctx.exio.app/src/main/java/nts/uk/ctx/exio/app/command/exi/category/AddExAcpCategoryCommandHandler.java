package nts.uk.ctx.exio.app.command.exi.category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.category.ExAcpCategoryRepository;
import nts.uk.ctx.exio.dom.exi.category.ExAcpCategory;

@Stateless
@Transactional
public class AddExAcpCategoryCommandHandler extends CommandHandler<ExAcpCategoryCommand>
{
    
    @Inject
    private ExAcpCategoryRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExAcpCategoryCommand> context) {
        ExAcpCategoryCommand addCommand = context.getCommand();
        repository.add(ExAcpCategory.createFromJavaType(0L, addCommand.getCategoryId(), addCommand.getCategoryName()));
    
    }
}
