package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;

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
		//change ReflectPlanPerState = canceled
		app.setReflectPerState(ReflectPlanPerState.CANCELED);
		//update application
		appRepo.updateApplication(app);
	}

}
