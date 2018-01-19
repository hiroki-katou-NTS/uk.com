package nts.uk.ctx.at.request.app.command.setting.request;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationDeadlineCommandHandler extends CommandHandler<ApplicationDeadlineCommand>{
	@Inject
	private ApplicationDeadlineRepository appRep;
	/**
	 * update application deadline
	 */
	@Override
	protected void handle(CommandHandlerContext<ApplicationDeadlineCommand> context) {
		ApplicationDeadlineCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		ApplicationDeadline appDeadline = ApplicationDeadline.createSimpleFromJavaType(companyId, data.getClosureId(),
																				data.getUserAtr(), data.getDeadline(), 
																				data.getDeadlineCriteria());
		appDeadline.validate();
		appRep.update(appDeadline);
	}
}
