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
public class AddAppWorkChangeSetCommandHandler extends CommandHandler<AppWorkChangeSetCommand>
{
    
    @Inject
    private IAppWorkChangeSetRepository repository;
    
	@Override
	protected void handle(CommandHandlerContext<AppWorkChangeSetCommand> context) {
		AppWorkChangeSetCommand addCommand = context.getCommand();
		repository.add(AppWorkChangeSet.createFromJavaType(addCommand.getCid(), addCommand.getExcludeHoliday(),
				addCommand.getWorkChangeTimeAtr(), addCommand.getDisplayResultAtr(),
				addCommand.getInitDisplayWorktime(), addCommand.getCommentContent1(),
				addCommand.getCommentFontWeight1(), addCommand.getCommentFontColor1(), addCommand.getCommentContent2(),
				addCommand.getCommentFontWeight2(), addCommand.getCommentFontColor2()));

	}
}
