package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApplicationRepository {
	
	public Optional<Application> findByID(String companyID, String appID);
	
	public void insert(Application application);
	
	public void update(Application application);
	
	public void remove(String appID);
	
}
