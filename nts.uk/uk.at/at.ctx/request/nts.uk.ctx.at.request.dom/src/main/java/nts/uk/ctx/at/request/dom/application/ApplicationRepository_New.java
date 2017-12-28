package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

public interface ApplicationRepository_New {
	
	public Optional<Application_New> findByID(String companyID, String appID);
	
	public void insert(Application_New application);
	
	public void update(Application_New application);
	
}
