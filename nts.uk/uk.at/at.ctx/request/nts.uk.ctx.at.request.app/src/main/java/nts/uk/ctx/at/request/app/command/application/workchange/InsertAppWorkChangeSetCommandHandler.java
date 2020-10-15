package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet_Old;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class InsertAppWorkChangeSetCommandHandler extends CommandHandler<AppWorkChangeSetCommand>{
	@Inject
	private IAppWorkChangeSetRepository appWorkRep;

	@Override
	protected void handle(CommandHandlerContext<AppWorkChangeSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		AppWorkChangeSetCommand data = context.getCommand();
		Optional<AppWorkChangeSet_Old> appWork = appWorkRep.findWorkChangeSetByID(companyId);
		AppWorkChangeSet_Old appChange = AppWorkChangeSet_Old.createFromJavaType(companyId, 
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
