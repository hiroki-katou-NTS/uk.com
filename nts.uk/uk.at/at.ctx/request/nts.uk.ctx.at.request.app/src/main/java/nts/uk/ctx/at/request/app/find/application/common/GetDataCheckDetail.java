package nts.uk.ctx.at.request.app.find.application.common;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InputGetDetailCheck;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetDataCheckDetail {

	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private ApplicationRepository appRepo;
	/**
	 * 
	 * @param inputGetDetailCheck
	 * @return
	 */
	public OutputDetailCheckDto getDataCheckDetail(InputGetDetailCheck inputGetDetailCheck) {
		String companyID = AppContexts.user().companyId();
		OutputDetailCheckDto ouput = null;
		Application app = appRepo.getAppById(companyID, inputGetDetailCheck.getApplicationID()).get();
		if(app!= null) {
			ouput = OutputDetailCheckDto.fromDomain(beforePreBootMode.judgmentDetailScreenMode(app, app.getApplicationDate()))	;
		}
		return ouput;
	}
	
	
}
