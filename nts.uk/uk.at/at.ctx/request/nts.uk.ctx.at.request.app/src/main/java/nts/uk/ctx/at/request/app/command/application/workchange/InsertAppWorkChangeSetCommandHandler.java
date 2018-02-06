package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;

@Stateless
public class InsertAppWorkChangeSetCommandHandler extends CommandHandler<AppWorkChangeSetCommand>{
	@Inject
	private IAppWorkChangeSetRepository appWorkRep;

	@Override
	protected void handle(CommandHandlerContext<AppWorkChangeSetCommand> context) {
		AppWorkChangeSetCommand data = context.getCommand();
		Optional<AppWorkChangeSet> appWork = appWorkRep.findWorkChangeSetByID(data.getCid());
		AppWorkChangeSet appChange = AppWorkChangeSet.createFromJavaType(data.getCid(), 
				data.getExcludeHoliday(), data.getWorkChangeTimeAtr(), data.getDisplayResultAtr(), 
				data.getInitDisplayWorktime(), data.getCommentContent1(), data.getCommentFontWeight1(), 
				data.getCommentFontColor1(), data.getCommentContent2(), data.getCommentFontWeight2(), 
				data.getCommentFontColor2());
		if(appWork.isPresent()){
			appWorkRep.update(appChange);
			return;
		}
		appWorkRep.add(appChange);
	}
	
}
