package nts.uk.ctx.at.request.dom.application;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

public interface ApplicationRepository {
	
	/**
	 * get All application 
	 * @return list application
	 */
	List<Application> getAllApplication(String companyID);
	
	
	List<Application> getAllApplicationByPhaseID(String comanyID,String appID, String phaseID);
	/**
	 * get Application by applicationID
	 * @param companyID
	 * @param applicationID
	 * @return a application
	 */
	Optional<Application> getAppById(String companyID,String applicationID);
	/**
	 * get all application by applicationDate
	 * @param companyID
	 * @param applicationDate
	 * @return
	 */
	List<Application> getAllAppByDate(String companyID,GeneralDate applicationDate);
	/**
	 * get all application by applicationType
	 * @param companyID
	 * @param applicationType
	 * @return
	 */
	List<Application> getAllAppByAppType(String companyID,int applicationType);
	
	/**
	 * add application
	 * @param application
	 */
	void addApplication(Application application);
	/**
	 * update application
	 * @param application
	 */
	void updateApplication(Application application);
	/**
	 * delete application
	 * @param companyID
	 * @param applicationID
	 */
	void deleteApplication(String companyID,String applicationID, Long version);
	
	
	List<Application> getApplicationIdByDate(String companyId, GeneralDate startDate, GeneralDate endDate);
	
	List<Application> getApp(String applicantSID, GeneralDate appDate, int prePostAtr, int appType);
	
	/**
	 * 事前申請を取得したい
	 * @param companyId: 社員ID
	 * @param appDate: 申請日
	 * @param inputDate: 入力日
	 * @param appType: 申請種類
	 * @param prePostAtr: 事前事後区分
	 * @return
	 */
	List<Application>  getBeforeApplication(String companyId, GeneralDate appDate, GeneralDateTime inputDate, int appType, int prePostAtr);
	
	void fullUpdateApplication(Application application);
	
	
}
