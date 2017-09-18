package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppStampRepository {
	
	public AppStamp findByAppID(String companyID, String appID);
	
	public AppStamp findByAppDate(String companyID, GeneralDate appDate, StampRequestMode stampRequestMode, String employeeID);
	
	public void addStampGoOutPermit(AppStamp appStamp);
	
	public void addStampWork(AppStamp appStamp);
	
	public void addStampCancel(AppStamp appStamp);
	
	public void addStampOnlineRecord(AppStamp appStamp);
	
	public void updateStampGoOutPermit(AppStamp appStamp);
	
	public void updateStampWork(AppStamp appStamp);
	
	public void updateStampCancel(AppStamp appStamp);
	
	public void updateStampOnlineRecord(AppStamp appStamp);
	
}
