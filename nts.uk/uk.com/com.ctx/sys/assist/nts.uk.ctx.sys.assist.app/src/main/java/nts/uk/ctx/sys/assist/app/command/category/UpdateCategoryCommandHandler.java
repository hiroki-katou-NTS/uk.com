package nts.uk.ctx.sys.assist.app.command.category;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;

@Stateless
@Transactional
public class UpdateCategoryCommandHandler extends CommandHandler<CategoryCommand>
{
    
    @Inject
    private CategoryRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CategoryCommand> context) {
        //CategoryCommand updateCommand = context.getCommand();
        //repository.update(Category.createFromJavaType(updateCommand.getSchelperSystem(), updateCommand.getCategoryId(), updateCommand.getCategoryName(), updateCommand.getPossibilitySystem(), updateCommand.getStoredProcedureSpecified(), updateCommand.getTimeStore(), updateCommand.getReFrCompanyOther(), updateCommand.getAttendanceSystem(), updateCommand.getRecoveryStorageRange(), updateCommand.getPaymentAvailability(), updateCommand.getStorageRangeSaved()));
    
    }
}
