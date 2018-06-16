package nts.uk.ctx.at.request.dom.application;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

public interface ApplicationRepository_New {
	
	public Optional<Application_New> findByID(String companyID, String appID);
	
	public List<Application_New> findByListID(String companyID, List<String> listAppID);
	
	public List<Application_New> getApplicationIdByDate(String companyId, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * getApplicationBySIDs
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Application_New> getApplicationBySIDs(List<String> employeeID,GeneralDate startDate, GeneralDate endDate);
	
	public List<Application_New> getApp(String applicantSID, GeneralDate appDate, int prePostAtr, int appType);
	
	/**
	 * 事前申請を取得したい
	 * @param companyId: 社員ID
	 * @param appDate: 申請日
	 * @param inputDate: 入力日
	 * @param appType: 申請種類
	 * @param prePostAtr: 事前事後区分
	 * @return
	 */
	public List<Application_New>  getBeforeApplication(String companyId, GeneralDate appDate, GeneralDateTime inputDate, int appType, int prePostAtr);
	
	public void insert(Application_New application);
	
	public void update(Application_New application);
	
	public void updateWithVersion(Application_New application);
	
	public void delete(String companyID, String appID);
	/**
	 * get list application by sID
	 * @param companyId
	 * @param sID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Application_New> getListAppBySID(String companyId, String sID, GeneralDate startDate, GeneralDate endDate);
	/**
	 * get List Application By Reflect
	 * @param companyId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Application_New> getListAppByReflect(String companyId, GeneralDate startDate, GeneralDate endDate);
	/**
	 * get List Application Pre
	 * @param companyId
	 * @param sID
	 * @param appDate
	 * @param prePostAtr
	 * @return
	 */
	public List<Application_New> getListAppPre(String companyId, String sID, GeneralDate appDate, int prePostAtr);
	
	/**
	 * Request list No.236
	 * @param sID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Application_New> getListApp(String sID, GeneralDate startDate, GeneralDate endDate);
	
	public List<Application_New> getByListRefStatus(String employeeID ,GeneralDate startDate, GeneralDate endDate , List<Integer> listReflecInfor  );
}
