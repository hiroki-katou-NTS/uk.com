package nts.uk.ctx.at.request.dom.application.stamp;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppStampRepository {
	
	public AppStamp findByAppID(String companyID, String appID);
	
	public void addStamp(AppStamp appStamp);
	
	public void updateStamp(AppStamp appStamp);
	
	public void delete(String companyID, String appID);
	
}
