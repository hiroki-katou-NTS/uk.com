package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.Application;

/**
 * Refactor4
 * @author hoangnd
 *
 */
public interface AppStampRepository {

	public Optional<AppStamp> findByAppID(String companyID, String appID);
	
	public void addStamp(AppStamp appStamp);
	
	public void updateStamp(AppStamp appStamp);
	
	public void delete(String companyID, String appID);
	
	public Optional<AppStamp> findByAppID(String companyID, String appID, Application app);
	
}
