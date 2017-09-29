package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeProcessRegister;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationApproveHandler {

	@Inject
	private ApplicationRepository appRepo;
	
	//4-1.詳細画面登録前の処理
	@Inject 
	private DetailBeforeProcessRegister beforeRegisterRepo;
	//8-2.詳細画面承認後の処理
	@Inject 
	private AfterApprovalProcess afterApprovalProcessRepo;
	

	public void approveApp(String appID) {
		String companyID = AppContexts.user().companyId();
		
		Application application = appRepo.getAppById(companyID, appID).get();

		//if approve
		//4-1.
		beforeRegisterRepo.processBeforeDetailScreenRegistration(
				companyID, 
				application.getApplicantSID(), 
				application.getApplicationDate(), 
				1, 
				appID,
				application.getPrePostAtr());

		//8.2.1.
		afterApprovalProcessRepo.invidialApplicationErrorCheck(appID);
		//8.2.2.
		afterApprovalProcessRepo.invidialApplicationUpdate(appID);
		//8-2.
		afterApprovalProcessRepo.detailScreenAfterApprovalProcess(companyID, 
				appID, 
				application);
	}

}
