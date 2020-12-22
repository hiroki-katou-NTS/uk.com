package nts.uk.ctx.at.request.dom.application;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;

public interface ApplicationRepository {
	
	// public Optional<Application_New> findByID(String companyID, String appID);
	
	public List<Application> findByListID(String companyID, List<String> listAppID);
	
	public List<Application_New> getApplicationIdByDate(String companyId, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * getApplicationBySIDs
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Application> getApplicationBySIDs(List<String> employeeID,GeneralDate startDate, GeneralDate endDate);

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
	public List<Application_New>  getBeforeApplication(String companyId, String employeeID, GeneralDate appDate, int appType, int prePostAtr);
	
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
	 * refactor 4
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.アルゴリズム.承認一覧の申請を取得.承認一覧の申請を取得
	 * @author hoatt
	 * get List Application
	 * Phuc vu CMM045
	 * @param companyId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Application> getListAppModeApprCMM045(String companyID, DatePeriod period, List<String> lstAppId,
			boolean unapprovalStatus, boolean approvalStatus, boolean denialStatus, 
			boolean agentApprovalStatus, boolean remandStatus, boolean cancelStatus, List<Integer> lstType,
			List<PrePostAtr> prePostAtrLst, List<String> employeeIDLst, List<StampRequestMode> stampRequestModeLst);

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
	
	public List<Application> getByListRefStatus(String companyID, String employeeID ,GeneralDate startDate, GeneralDate endDate , List<Integer> listReflecInfor  );
	
	public List<Application> getListLateOrLeaveEarly(String companyID, String employeeID, GeneralDate startDate, GeneralDate endDate);
	/**
	 * 検索
	 * @param sid
	 * @param dateData　申請日　期間
	 * @param reflect　反映情報.実績反映状態
	 * @param appType　申請種類
	 * @return
	 */
	public List<Application> getByPeriodReflectType(String sid, DatePeriod dateData, List<Integer> reflect, List<Integer> appType);
	/**
	 * @author hoatt
	 * 申請者ID＝社員ID（リスト）　　または　入力者ID＝社員ID（リスト）
	 * get By List SID
	 * @param companyId
	 * @param lstSID
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public List<Application_New> getByListSID(String companyId, List<String> lstSID, GeneralDate sDate, GeneralDate eDate);
	/**
	 * @author hoatt
	 * 申請者ID＝社員ID（リスト）
	 * get By List Applicant
	 * @param companyId
	 * @param lstSID
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public List<Application_New> getByListApplicant(String companyId, List<String> lstSID, GeneralDate sDate, GeneralDate eDate, List<Integer> lstType);
	/**
	 * getListAppByType
	 * sort：申請日（ASC）、入力日（DESC）
	 * @param companyId
	 * @param sID: 申請者＝パラメータ．社員ID
	 * @param appDate: パラメータ．期間．開始日＜＝　申請日　＜＝パラメータ．期間．終了日
	 * @param prePostAtr: 事前事後区分＝事後
	 * @param appType: 休日出勤申請
	 * @param lstRef: 実績反映状態＝未承認 or 反映待ち
	 * @return
	 */
	public List<Application> getListAppByType(String companyId, String sID, GeneralDate startDate, GeneralDate endDate, 
			int prePostAtr, int appType, List<Integer> lstRef);
	
	/**
	 * 反映の申請
	 * @param sid 社員ID
	 * @param dateData　期間
	 * @param recordStatus　実績反映状態
	 * @param scheStatus　実績反映状態
	 * @param appType 申請種類
	 * @return
	 */
	public List<Application> getAppForReflect(String sid, DatePeriod dateData, List<Integer> recordStatus,
			List<Integer> scheStatus, List<Integer> appType);
	/**
	 * 検索
	 * @param sid
	 * @param dateData　申請日 リスト
	 * @param reflect　反映情報.実績反映状態
	 * @param appType　申請種類
	 * @return
	 */
	public List<Application> getByListDateReflectType(String sid, List<GeneralDate> dateData, List<Integer> reflect, List<Integer> appType);
	/**
	 * 
	 * @param companyId
	 * @param configName
	 * @param subName
	 * @return
	 */
	public Map<String, Integer> getParamCMMS45(String companyId, String configName, List<String> subName);
	
	// refactor 4
	
	public Optional<Application> findByID(String companyID, String appID);
	
	public void insert(Application application);
	
	public void update(Application application);
	
	public void remove(String appID);
	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.アルゴリズム.申請IDを使用して申請一覧を取得する.申請IDを使用して申請一覧を取得する
	 * @param appID
	 * @return
	 */
	public Optional<Application> findByID(String appID);
	
	public List<Application> getByAppTypeList(List<String> employeeLst, GeneralDate startDate, GeneralDate endDate, 
			List<ApplicationType> appTypeLst, List<PrePostAtr> prePostAtrLst, List<StampRequestMode> stampRequestModeLst);

	public List<Application> getAppForKAF008(String sID, GeneralDate startDate, GeneralDate endDate);

	/**
	 * 申請を取得	(反映状態="反映済み",対象日=ループ中の申請日)
	 * @param sid
	 * @param appDate
	 * @return
	 */
	public List<Application> getAppReflected(String sid, GeneralDate appDate);
	
	//申請を取得する
	// 事前事後区分, 入力日, 申請日, 申請種類, 申請者
	public List<Application> getApplication(PrePostAtr prePostAtr, GeneralDateTime inputDate, GeneralDate appDate,
			ApplicationType appType, String employeeID);
	
	public List<Application> getApprSttByEmpPeriod(String employeeID, DatePeriod period);

}
