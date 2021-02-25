package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.workchange;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class SaveAppWorkChangeSetCommandHandler extends CommandHandler<AppWorkChangeSetCommand>{
	@Inject
	private AppWorkChangeSetRepository appWorkRep;

	@Override
	protected void handle(CommandHandlerContext<AppWorkChangeSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		AppWorkChangeSetCommand data = context.getCommand();
		if (appWorkRep.findByCompanyId(companyId).isPresent()) {
			appWorkRep.update(data.toDomain(companyId), data.getWorkTimeReflectAtr());
		} else {
			appWorkRep.add(data.toDomain(companyId), data.getWorkTimeReflectAtr());
		}
	}
	
}
