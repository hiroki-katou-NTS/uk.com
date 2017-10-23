package nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.applicationapproval.application.Application;
import nts.uk.ctx.at.request.dom.applicationapproval.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.ReflectPlanPerState;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class ProcessCancelImpl implements ProcessCancel {

	@Inject
	private ApplicationRepository appRepo;
	
	@Override
	public void detailScreenCancelProcess(String companyID, String appID) {
		//get application by appID
		Application app = appRepo.getAppById(companyID, appID).get();
		//change ReflectPlanPerState = WAITCANCEL
		app.setReflectPerState(ReflectPlanPerState.WAITCANCEL);
		//update application
		appRepo.updateApplication(app);
	}

}
