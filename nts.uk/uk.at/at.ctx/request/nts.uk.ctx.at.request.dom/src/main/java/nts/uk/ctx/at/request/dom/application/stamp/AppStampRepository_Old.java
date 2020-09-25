package nts.uk.ctx.at.request.dom.application.stamp;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AppStampRepository_Old {
	
	public AppStamp_Old findByAppID(String companyID, String appID);
	
	public void addStamp(AppStamp_Old appStamp);
	
	public void updateStamp(AppStamp_Old appStamp);
	
	public void delete(String companyID, String appID);
	
}
