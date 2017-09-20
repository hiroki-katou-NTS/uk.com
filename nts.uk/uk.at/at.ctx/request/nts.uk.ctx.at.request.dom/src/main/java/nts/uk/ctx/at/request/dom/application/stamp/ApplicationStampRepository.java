package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.time.GeneralDate;

public interface ApplicationStampRepository {
	
	public ApplicationStamp findByAppID(String companyID, String appID);
	
	public ApplicationStamp findByAppDate(String companyID, GeneralDate appDate, StampRequestMode stampRequestMode, String employeeID);
	
	public void addStampGoOutPermit(ApplicationStamp applicationStamp);
	
	public void addStampWork(ApplicationStamp applicationStamp);
	
	public void addStampCancel(ApplicationStamp applicationStamp);
	
	public void addStampOnlineRecord(ApplicationStamp applicationStamp);
	
	public void updateStampGoOutPermit(ApplicationStamp applicationStamp);
	
	public void updateStampWork(ApplicationStamp applicationStamp);
	
	public void updateStampCancel(ApplicationStamp applicationStamp);
	
	public void updateStampOnlineRecord(ApplicationStamp applicationStamp);
	
}
