package nts.uk.ctx.at.request.dom.application.applist.service.datacreate;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
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
	 * @param screenID ScreenID
	 * @param companyID 会社ID
	 * @param listOfAppTypes 申請種類リスト
	 */
	public void createAppStampData(Application application, DisplayAtr appReasonDisAtr, String screenID, String companyID, ListOfAppTypes listOfAppTypes);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4.遅刻早退取消申請データを作成.遅刻早退取消申請データを作成
	 * @param application
	 * @param appReasonDisAtr
	 * @param screenID
	 * @param companyID
	 * @return
	 */
	public String createArrivedLateLeaveEarlyData(Application application, DisplayAtr appReasonDisAtr, String screenID, String companyID);
	
}
