package nts.uk.ctx.at.request.dom.application;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface ApplicationRepository_New {
	
	public Optional<Application_New> findByID(String companyID, String appID);
	
	public List<Application_New> getApplicationIdByDate(String companyId, GeneralDate startDate, GeneralDate endDate);
	
	public List<Application_New> getApp(String applicantSID, GeneralDate appDate, int prePostAtr, int appType);
	
	public void insert(Application_New application);
	
	public void update(Application_New application);
	
	public void updateWithVersion(Application_New application);
	
	public void delete(String companyID, String appID);
	
}
