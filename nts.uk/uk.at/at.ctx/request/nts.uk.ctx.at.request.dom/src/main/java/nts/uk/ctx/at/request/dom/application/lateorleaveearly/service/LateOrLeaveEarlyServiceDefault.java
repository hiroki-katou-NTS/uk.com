package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
@Stateless
public class LateOrLeaveEarlyServiceDefault implements LateOrLeaveEarlyService {

	@Inject
	LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository ;
	
	@Inject
	ApplicationRepository applicationRepository;
	
	@Override
	public boolean isExist(String companyID, String appID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteLateOrLeaveEarly(String companyID, String appID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerLateOrLeaveEarly(String companyID) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeApplication(String companyID , String appID, GeneralDate applicationDate, int actualCancelAtr, int early1,
			int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2, int late2, int lateTime2,
			String reasonTemp, String appReason) {
		// LateOrLeaveEarly
		LateOrLeaveEarly lateOrLeaveEarly = lateOrLeaveEarlyRepository.findByCode(companyID, appID).get();
		lateOrLeaveEarly.changeApplication(actualCancelAtr, early1, earlyTime1, late1, lateTime1, early2, earlyTime2, late2, lateTime2);
		lateOrLeaveEarlyRepository.update(lateOrLeaveEarly);
		// Application
		Application application = applicationRepository.getAppById(companyID, appID).get();
		application.setApplicationDate(applicationDate);
		application.setApplicationReason(new AppReason(appReason));
		applicationRepository.updateApplication(application);
	}

}
