package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterDenialProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessDenial;

import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationDenyHandler {

	@Inject
	private BeforeProcessDenial beforeProcessDenialRepo;
	@Inject 
	private AfterDenialProcess afterDenialProcessRepo;
	
	public void denyApp(String appID) {
		String companyID = AppContexts.user().companyId();
		//9-1 .詳細画面否認前の処理
		beforeProcessDenialRepo.detailedScreenProcessBeforeDenial();
		//9.2 
		afterDenialProcessRepo.detailedScreenAfterDenialProcess(companyID, appID);
		
		
	}

}
