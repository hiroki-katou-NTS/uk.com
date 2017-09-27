package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
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
public class UpdateApplicationReleaseHandler {

	@Inject
	private ApplicationRepository appRepo;
	
	@Inject
	private BeforeProcessReleasing beforeProcessReleasingRepo;
	
	@Inject
	private AfterDetailScreenProcess afterDetailScreenProcessRepo;
	

	public void releaseApp(String appID) {
		String companyID = AppContexts.user().companyId();
		Application application = appRepo.getAppById(companyID, appID).get();
		
		//10.1
		beforeProcessReleasingRepo.detailScreenProcessBeforeReleasing();
		//10.2
		DetailScreenProcessAfterOutput output = afterDetailScreenProcessRepo.getDetailScreenProcessAfter(application);
		
	}

}
