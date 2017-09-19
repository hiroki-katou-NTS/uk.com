package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class InsertGoBackDirectlyCommandHandler extends CommandHandler<InsertGoBackDirectlyCommand> {
	@Inject
	private GoBackDirectlyRepository goBackDirectRepo;

	@Inject
	private ApplicationRepository appRepo;

	@Override
	protected void handle(CommandHandlerContext<InsertGoBackDirectlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		InsertGoBackDirectlyCommand command = context.getCommand();
		// Optional<GoBackDirectly> currentGoBack =
		// this.goBackDirectRepo.findByApplicationID(companyId,
		// command.getAppID());
		// Optional<Application> currentApp = this.appRepo.getAppById(companyId,
		// command.getAppID());
		// get new Application Item
		Application newApp = Application.createFromJavaType(companyId, 
				command.appCommand.getPrePostAtr(),
				command.appCommand.getInputDate(), 
				command.appCommand.getEnteredPersonSID(),
				command.appCommand.getReversionReason(), 
				command.appCommand.getApplicationDate(),
				command.appCommand.getAppReasonID() + ":" + command.appCommand.getApplicationReason(),
				command.appCommand.getApplicationType(), 
				command.appCommand.getApplicantSID(),
				command.appCommand.getReflectPlanScheReason(), 
				command.appCommand.getReflectPlanTime(),
				command.appCommand.getReflectPerState(), 
				command.appCommand.getReflectPlanEnforce(),
				command.appCommand.getReflectPerScheReason(), 
				command.appCommand.getReflectPerTime(),
				command.appCommand.getReflectPerState(), 
				command.appCommand.getReflectPlanEnforce(),
				command.appCommand.getStartDate(), 
				command.appCommand.getEndDate(), 
				null);
		// get new GoBack Direct Item
		GoBackDirectly newGoBack = new GoBackDirectly(companyId, newApp.getApplicationID(), command.workTypeCD,
				command.siftCD, command.workChangeAtr, command.goWorkAtr1, command.backHomeAtr1, command.workTimeStart1,
				command.workTimeEnd1, command.workLocationCD1, command.goWorkAtr2, command.backHomeAtr2,
				command.workTimeStart2, command.workTimeEnd2, command.workLocationCD2);
		appRepo.addApplication(newApp);
		goBackDirectRepo.insert(newGoBack);
	}
}
