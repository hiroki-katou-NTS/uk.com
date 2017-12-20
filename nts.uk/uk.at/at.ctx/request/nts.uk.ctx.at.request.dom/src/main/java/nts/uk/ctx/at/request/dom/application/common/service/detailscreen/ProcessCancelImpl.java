package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;

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
	public void detailScreenCancelProcess(String companyID, String appID, Long version) {
		//get application by appID
		Application app = appRepo.getAppById(companyID, appID).get();
		app.setVersion(version);
		//change ReflectPlanPerState = WAITCANCEL
		app.setReflectPerState(ReflectPlanPerState.WAITCANCEL);
		//update application
		appRepo.updateApplication(app);
	}

}
