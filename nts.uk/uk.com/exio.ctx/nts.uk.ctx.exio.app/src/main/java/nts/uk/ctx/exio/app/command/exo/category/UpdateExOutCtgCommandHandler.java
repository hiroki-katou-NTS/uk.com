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
public class UpdateExOutCtgCommandHandler extends CommandHandler<ExOutCtgCommand>
{
    
    @Inject
    private ExOutCtgRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ExOutCtgCommand> context) {
        ExOutCtgCommand updateCommand = context.getCommand();
        repository.update(new ExOutCtg(updateCommand.getCategoryId(), updateCommand.getOfficeHelperSysAtr(), updateCommand.getCategoryName(), updateCommand.getCategorySet(), updateCommand.getPersonSysAtr(), updateCommand.getAttendanceSysAtr(), updateCommand.getPayrollSysAtr(), updateCommand.getFunctionNo(), updateCommand.getFunctionName(), updateCommand.getExplanation(), updateCommand.getDisplayOrder(), updateCommand.getDefaultValue()));
    
    }
}
