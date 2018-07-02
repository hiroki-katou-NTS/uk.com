package nts.uk.ctx.exio.app.command.exo.category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtgRepository;

@Stateless
@Transactional
public class AddExOutCtgCommandHandler extends CommandHandler<ExOutCtgCommand>
{
    
    @Inject
    private ExOutCtgRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExOutCtgCommand> context) {
        ExOutCtgCommand addCommand = context.getCommand();
        repository.add(new ExOutCtg(addCommand.getCategoryId(), addCommand.getOfficeHelperSysAtr(), addCommand.getCategoryName(), addCommand.getCategorySet(), addCommand.getPersonSysAtr(), addCommand.getAttendanceSysAtr(), addCommand.getPayrollSysAtr(), addCommand.getFunctionNo(), addCommand.getFunctionName(), addCommand.getExplanation(), addCommand.getDisplayOrder(), addCommand.getDefaultValue()));
    
    }
}
