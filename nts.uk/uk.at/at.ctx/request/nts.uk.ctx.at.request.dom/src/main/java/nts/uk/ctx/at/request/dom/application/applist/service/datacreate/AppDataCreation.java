package nts.uk.ctx.at.request.dom.application.applist.service.datacreate;

import java.util.List;
import java.util.Map;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4
 * @author Doan Duy Hung
 *
 */
public interface AppDataCreation {
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4.打刻申請データを作成.打刻申請データを作成
	 * @param application 申請
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param screenAtr ScreenID
	 * @param companyID 会社ID
	 * @param listOfAppTypes 申請種類リスト
	 * @return
	 */
	public String createAppStampData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, String companyID, ListOfAppTypes listOfAppTypes);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4.遅刻早退取消申請データを作成.遅刻早退取消申請データを作成
	 * @param application
	 * @param appReasonDisAtr
	 * @param screenID
	 * @param companyID
	 * @return
	 */
	public String createArrivedLateLeaveEarlyData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, String companyID);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧リストのデータを作成.申請一覧リストのデータを作成
	 * @param companyID
	 * @param appLst
	 * @param period
	 * @param mode
	 * @param mapApproval
	 * @param device
	 * @param appListExtractCondition
	 * @return
	 */
	public AppListInfo createAppLstData(String companyID, List<ListOfApplication> appLst, DatePeriod period, ApplicationListAtr mode, 
			Map<String,List<ApprovalPhaseStateImport_New>> mapApproval, int device, AppListExtractCondition appListExtractCondition, 
			AppListInfo appListInfo);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請一覧の並び順を変更する.申請一覧の並び順を変更する
	 * @param appListInfo 申請一覧情報
	 * @param appListExtractCondition 申請一覧抽出条件
	 * @param device デバイス
	 * @return
	 */
	public AppListInfo changeOrderOfAppLst(AppListInfo appListInfo, AppListExtractCondition appListExtractCondition, int device);
	
}
