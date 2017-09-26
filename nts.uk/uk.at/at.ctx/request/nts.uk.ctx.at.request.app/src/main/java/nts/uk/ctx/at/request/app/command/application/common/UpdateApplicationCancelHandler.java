package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ProcessCancel;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateApplicationCancelHandler {

	
	@Inject
	private ProcessCancel processCancelRepo;

	public void cancelApp(String appID) {
		String companyID = AppContexts.user().companyId();
		//12
		processCancelRepo.detailScreenCancelProcess(companyID,appID);
	}

}
