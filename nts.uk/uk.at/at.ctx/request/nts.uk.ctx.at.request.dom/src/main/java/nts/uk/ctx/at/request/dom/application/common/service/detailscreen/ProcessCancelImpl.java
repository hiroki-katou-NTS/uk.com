package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class ProcessCancelImpl implements ProcessCancel {

	@Inject
	private ApplicationRepository_New appRepo;
	
	@Override
	public void detailScreenCancelProcess(String companyID, String appID, int version) {
		//get application by appID
		Application_New app = appRepo.findByID(companyID, appID).get();
		// app.setVersion(version);
		//change ReflectPlanPerState = WAITCANCEL
		app.getReflectionInformation().setStateReflectionReal(ReflectedState_New.WAITCANCEL);
		//update application
		appRepo.updateWithVersion(app);
	}

}
