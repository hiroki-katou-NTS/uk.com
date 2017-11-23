package nts.uk.ctx.at.request.app.command.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;

@Stateless
@Transactional
public class UpdateAppWorkChangeCommandHandler extends CommandHandler<AppWorkChangeCommand>
{
    
    @Inject
    private IAppWorkChangeRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AppWorkChangeCommand> context) {
        AppWorkChangeCommand updateCommand = context.getCommand();
        repository.update(AppWorkChange.createFromJavaType(updateCommand.getCid(), updateCommand.getAppId(), updateCommand.getWorkTypeCd(), updateCommand.getWorkTimeCd(), updateCommand.getExcludeHolidayAtr(), updateCommand.getWorkChangeAtr(), updateCommand.getGoWorkAtr1(), updateCommand.getBackHomeAtr1(), updateCommand.getBreakTimeStart1(), updateCommand.getBreakTimeEnd1(), updateCommand.getWorkTimeStart1(), updateCommand.getWorkTimeEnd1(), updateCommand.getWorkTimeStart2(), updateCommand.getWorkTimeEnd2(), updateCommand.getGoWorkAtr2(), updateCommand.getBackHomeAtr2()));
    
    }
}
