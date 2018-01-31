package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author hoatt
 *
 */
public interface ApplicationListInitialRepository {
	/**
	 * 12 - 申請一覧初期日付期間
	 * @param companyId
	 * @return
	 */
	public DatePeriod getInitialPeriod(String companyId);
	/**
	 * 0 - 申請一覧事前必須チェック
	 * @param appType
	 * @param wkpID
	 * @return
	 */
	public Boolean checkAppPredictRequire(int appType, String wkpID);
	
	/**
	 * 1- 申請一覧リスト取得
	 * @param param
	 * @param displaySet
	 * @return
	 */
	public List<Application_New> getApplicationList(AppListExtractCondition param, ApprovalListDisplaySetting displaySet);
	/**
	 * 2 - 申請一覧リスト取得申請
	 * @param param
	 * @return
	 */
	public List<Application_New> getApplicationListByApp(AppListExtractCondition param);
	/**
	 * 3 - 申請一覧リスト取得承認
	 * @param param
	 * @param displaySet
	 * @return
	 */
	public List<Application_New> getAppListByApproval(AppListExtractCondition param, ApprovalListDisplaySetting displaySet);
	/**
	 * 4 - 申請一覧リスト取得承認件数
	 * @param lstApp
	 * @return
	 */
	public List<ApplicationStatus> countAppListApproval(List<Application_New> lstApp);
	/**
	 * 5 - 申請一覧リスト取得実績
	 * @param lstApp
	 * @param displaySet
	 * @return
	 */
	public AppListAtrOutput getAppListAchievement(List<Application_New> lstApp, ApprovalListDisplaySetting displaySet);
	/**
	 * 5.1 - 申請一覧リスト取得実績休出申請
	 * @param sID
	 * @param date
	 * @return
	 */
	public Boolean getAppListAchievementBreak(String sID, GeneralDate date);
	/**
	 * 6 - 申請一覧リスト取得振休振出
	 * @param application
	 * @return
	 */
	public List<Application_New> getListAppComplementLeave(Application_New application);
	/**
	 * 7 - 申請一覧リスト取得打刻取消
	 * @param application
	 * @return
	 */
	public Boolean getListAppStampIsCancel(Application_New application);
	/**
	 * 8 - 申請一覧リスト取得休暇
	 * @param application
	 * @return
	 */
	public List<Application_New> getListAppAbsence(Application_New application);
	/**
	 * 9 - 申請一覧リスト取得マスタ情報
	 * @param lstApp
	 * @return
	 */
	public List<Application_New> getListAppMasterInfo(List<Application_New> lstApp);
}
