package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ProcessCancel;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateApplicationCancelHandler extends CommandHandler<UpdateApplicationCommand> {

	@Inject
	private ProcessCancel processCancelRepo;
	@Override
	protected void handle(CommandHandlerContext<UpdateApplicationCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdateApplicationCommand appCommand = context.getCommand();
		Application application = new  Application(
				companyID,
				appCommand.getApplicationID(),
				EnumAdaptor.valueOf(appCommand.getPrePostAtr(),PrePostAtr.class),
				appCommand.getInputDate(), 
				appCommand.getEnteredPersonSID(), 
				new AppReason(appCommand.getReversionReason()), 
				appCommand.getApplicationDate(), 
				new AppReason(appCommand.getApplicationReason()),
				EnumAdaptor.valueOf(appCommand.getApplicationType(),ApplicationType.class), 
				appCommand.getApplicantSID(), 
				EnumAdaptor.valueOf(appCommand.getReflectPlanScheReason(),ReflectPlanScheReason.class), 
				appCommand.getReflectPlanTime(), 
				EnumAdaptor.valueOf(appCommand.getReflectPlanState(),ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(appCommand.getReflectPlanEnforce(),ReflectPlanPerEnforce.class), 
				EnumAdaptor.valueOf(appCommand.getReflectPerScheReason(),ReflectPerScheReason.class), 
				appCommand.getReflectPerTime(), 
				EnumAdaptor.valueOf(appCommand.getReflectPerState(),ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(appCommand.getReflectPerEnforce(),ReflectPlanPerEnforce.class));
		//12
		processCancelRepo.detailScreenCancelProcess(companyID, application.getApplicationID());
	}

}
