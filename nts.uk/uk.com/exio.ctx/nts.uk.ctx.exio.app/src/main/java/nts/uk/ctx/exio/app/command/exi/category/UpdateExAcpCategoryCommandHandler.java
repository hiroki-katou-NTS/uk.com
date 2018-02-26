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
public class UpdateExAcpCategoryCommandHandler extends CommandHandler<ExAcpCategoryCommand>
{
    
    @Inject
    private ExAcpCategoryRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExAcpCategoryCommand> context) {
        ExAcpCategoryCommand updateCommand = context.getCommand();
        repository.update(ExAcpCategory.createFromJavaType(updateCommand.getVersion(), updateCommand.getCategoryId(), updateCommand.getCategoryName()));
    
    }
}
