package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppHolidayWorkFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppOverTimeInfoFull;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AppCompltLeaveSyncOutput;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
/**
 * 
 * @author hoatt
 *
 */
public interface AppListInitialRepository {
	/**
	 * 0 - 申請一覧事前必須チェック
	 * @param　申請種類 appType
	 * @param 職場ID　wkpID
	 * @param 会社ID　companyId
	 * @return
	 */
	public Boolean checkAppPredictRequire(int appType, String wkpID, String companyId);
	
	/**
	 * 1- 申請一覧リスト取得
	 * @param 抽出条件　param
	 * @param displaySet
	 * @param デバイス　device
	 * @param 申請種類リスト　lstAppType
	 * @return
	 */
	public AppListOutPut getApplicationList(AppListExtractCondition param, ApprovalListDisplaySetting displaySet,
			int device, List<Integer> lstAppType);
	/**
	 * 2 - 申請一覧リスト取得申請
	 * @param param
	 * @param appReasonDisAtr
	 * @param デバイス　device
	 * @param lstAppType
	 * @return
	 */
	public AppListInfo getApplicationListByApp(AppListExtractCondition param, int device, AppListInfo appListInfo);
	/**
	 * 2.1 - 申請一覧対象申請者取得
	 * @param 抽出条件　param
	 * @return
	 */
	public ListApplicantOutput getListApplicantForListApp(AppListExtractCondition param);
	/**
	 * 3 - 申請一覧リスト取得承認
	 * @param 抽出条件　param
	 * @param displaySet
	 * @param デバイス　device
	 * @param 申請種類リスト　lstAppType
	 * @return
	 */
	public AppListOutPut getAppListByApproval(AppListExtractCondition param, ApprovalListDisplaySetting displaySet,
			int device, List<Integer> lstAppType);
	/**
	 * 4 - 申請一覧リスト取得承認件数
	 * @param 申請一覧　lstApp
	 * @param 社員ID　sID
	 * @param lstSync
	 * @return
	 */
	public AppInfoStatus countAppListApproval(List<ApplicationFullOutput> lstApp, String sID, List<AppCompltLeaveSync> lstSync);
	/**
	 * 5 - 申請一覧リスト取得実績
	 * @param 申請一覧　lstApp
	 * @param displaySet
	 * @param 会社ID　companyId
	 * @param 社員ID　sID
	 * @param lstSync //loai don complt truong hop sync se x2
	 * @param lstWkType
	 * @param lstWkTime
	 * @return
	 */
	public AppListAtrOutput getAppListAchievement(List<ApplicationFullOutput> lstApp, List<AppOverTimeInfoFull> lstAppOt, List<AppHolidayWorkFull> lstAppHdWork, ApprovalListDisplaySetting displaySet, String companyId,
			String sID, List<AppCompltLeaveSync> lstSync, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime, int device);
	/**
	 * 5.1 - 申請一覧リスト取得実績(休出申請・残業申請）
	 * @param sID
	 * @param date
	 * @param time
	 * @param appType
	 * @param lstWkType
	 * @param lstWkTime
	 * @return
	 */
	public TimeResultOutput getDataActual(String sID, GeneralDate date, List<OverTimeFrame> time, 
			ApplicationType appType, String wkTypeCd, String wkTimeCd, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime);
	/**
	 * 6 - 申請一覧リスト取得振休振出
	 * @param 申請　application
	 * @param 会社ID　companyId
	 * @return
	 */
	public AppCompltLeaveSyncOutput getListAppComplementLeave(Application_New application, String companyId);
	/**
	 * 7 - 申請一覧リスト取得打刻取消
	 * @param 申請　application
	 * @return
	 */
	public Boolean getListAppStampIsCancel(Application_New application, String companyID);
	/**
	 * 8 - 申請一覧リスト取得休暇
	 * @param 申請　application
	 * @param 会社ID　companyID
	 * @return
	 */
	public List<Application_New> getListAppAbsence(Application_New application, String companyID);
	/**
	 * 9 - 申請一覧リスト取得マスタ情報
	 * @param 申請一覧　lstApp
	 * @param 会社ID　companyId
	 * @param 期間　period
	 * @param デバイス　device
	 * @return
	 */
	public DataMasterOutput getListAppMasterInfo(List<Application_New> lstApp, String companyId, DatePeriod period, int device);
	/**
	 * 12 - 申請一覧初期日付期間
	 * @param 会社ID　companyId
	 * @return
	 */
	public DatePeriod getInitialPeriod(String companyId);
	/**
	 * 12.1 - 申請一覧初期日付期間_申請
	 * @param 会社ID　companyId
	 * @return
	 */
	public DatePeriod getInitPeriodApp(String companyId);
	/**
	 * 申請一覧リスト取得承認設定情報
	 * @param 会社ID companyId
	 * @param 職場ID　wkpId
	 * @param 申請種類　appType
	 * @param 日　date
	 * @return
	 */
	public int detailSet(String companyId, String wkpId, Integer appType, GeneralDate date);
	/**
	 * 職場IDから申請承認設定情報取得
	 * @param companyId
	 * @param wkpId
	 * @param appType
	 * @param date
	 * @return
	 */
	// public List<ApprovalFunctionSetting> detailSetKAF022(String companyId, String wkpId, GeneralDate date);
}
