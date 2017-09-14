package nts.uk.ctx.at.request.dom.application.stamp;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;

@Stateless
public class ApplicationStampDetailDefault implements ApplicationStampDetailDomainService {

	@Inject
	private ApplicationStampRepository applicationStampRepository; 
	
	@Override
	public void appStampPreProcess(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appStampGoOutPermitPreProcess(ApplicationStamp applicationStamp) {
		//appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampGoOutPermitUpdate(ApplicationStamp applicationStamp) {
		this.applicationStampRepository.updateStampGoOutPermit(applicationStamp);
		
	}

	@Override
	public void appStampWorkPreProcess(ApplicationStamp applicationStamp) {
		//appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampWorkUpdate(ApplicationStamp applicationStamp) {
		this.applicationStampRepository.updateStampWork(applicationStamp);
		
	}

	@Override
	public void appStampCancelPreProcess(ApplicationStamp applicationStamp) {
		//appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampCancelUpdate(ApplicationStamp applicationStamp) {
		this.applicationStampRepository.updateStampCancel(applicationStamp);
		
	}

	@Override
	public void appStampOnlineRecordPreProcess(ApplicationStamp applicationStamp) {
		//appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampOnlineRecordUpdate(ApplicationStamp applicationStamp) {
		this.applicationStampRepository.updateStampOnlineRecord(applicationStamp);
		
	}

	@Override
	public void appStampOtherPreProcess(ApplicationStamp applicationStamp) {
		//appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampOtherUpdate(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}
	
}
