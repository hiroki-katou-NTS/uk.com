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
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterDetailScreenProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessReleasing;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenProcessAfterOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationReleaseHandler extends CommandHandler<UpdateApplicationCommand>{

	@Inject
	private BeforeProcessReleasing beforeProcessReleasingRepo;
	
	@Inject
	private AfterDetailScreenProcess afterDetailScreenProcessRepo;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateApplicationCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdateApplicationCommand appCommand = context.getCommand();
		Application application = new  Application(
				companyID,
				appCommand.getApplicationID(),
				appCommand.getAppReasonID(),
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
				EnumAdaptor.valueOf(appCommand.getReflectPerEnforce(),ReflectPlanPerEnforce.class),
				appCommand.getStartDate(),
				appCommand.getEndDate());
		
		//10.1
		beforeProcessReleasingRepo.detailScreenProcessBeforeReleasing();
		//10.2
		DetailScreenProcessAfterOutput output = afterDetailScreenProcessRepo.getDetailScreenProcessAfter(application);
		
	}

}
