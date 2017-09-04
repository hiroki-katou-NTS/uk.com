package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;

public class ApplicationStampDetailDefault implements ApplicationStampDetailDomainService {

	@Override
	public void appStampPreProcess(String companyID, String appID, String employeeID, GeneralDate date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appStampGoOutPermitPreProcess(String companyID, String appID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampGoOutPermitUpdate(Application application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appStampWorkPreProcess(String companyID, String appID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampWorkUpdate(Application application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appStampCancelPreProcess(String companyID, String appID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampCancelUpdate(Application application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appStampOnlineRecordPreProcess(String companyID, String appID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampOnlineRecordUpdate(Application application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appStampOtherPreProcess(String companyID, String appID, String employeeID, GeneralDate date) {
		appStampPreProcess(companyID, appID, employeeID, date);
		
	}

	@Override
	public void appStampOtherUpdate(Application application) {
		// TODO Auto-generated method stub
		
	}
	
}
