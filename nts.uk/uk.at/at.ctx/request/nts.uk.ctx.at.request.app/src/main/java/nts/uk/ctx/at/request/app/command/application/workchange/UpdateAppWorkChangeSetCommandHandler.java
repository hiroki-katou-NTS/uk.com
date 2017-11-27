package nts.uk.ctx.at.request.app.command.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;

@Stateless
@Transactional
public class UpdateAppWorkChangeSetCommandHandler extends CommandHandler<AppWorkChangeSetCommand>
{
    
    @Inject
    private IAppWorkChangeSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AppWorkChangeSetCommand> context) {
        AppWorkChangeSetCommand updateCommand = context.getCommand();
        repository.update(AppWorkChangeSet.createFromJavaType(updateCommand.getCid(), updateCommand.getExcludeHoliday(), updateCommand.getWorkChangeTimeAtr(), updateCommand.getDisplayResultAtr(), updateCommand.getInitDisplayWorktime(), updateCommand.getCommentContent1(), updateCommand.getCommentFontWeight1(), updateCommand.getCommentFontColor1(), updateCommand.getCommentContent2(), updateCommand.getCommentFontWeight2(), updateCommand.getCommentFontColor2()));
    
    }
}
