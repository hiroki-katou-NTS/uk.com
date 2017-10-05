package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterRelease;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessReleasing;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationReleaseHandler {

	@Inject
	private ApplicationRepository appRepo;
	
	@Inject
	private BeforeProcessReleasing beforeProcessReleasingRepo;
	
	@Inject
	private DetailAfterRelease detailAfterRelease;
	

	public void releaseApp(String appID) {
		String companyID = AppContexts.user().companyId();
		String loginID = AppContexts.user().userId();
		Application application = appRepo.getAppById(companyID, appID).get();
		
		//10.1
		beforeProcessReleasingRepo.detailScreenProcessBeforeReleasing();
		//10.2
		detailAfterRelease.detailAfterRelease(application, loginID);
		
	}

}
