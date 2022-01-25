package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AttendanceNameItem;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public interface AppContentDetailCMM045 {

	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.各申請データを作成.勤務変更申請データを作成.勤務変更申請データを作成
	 * @param application 申請
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param workTimeLst 就業時間帯リスト
	 * @param workTypeLst 勤務種類リスト
	 * @param companyID 会社ID
	 * @return
	 */
	public String getContentWorkChange(Application application, DisplayAtr appReasonDisAtr, List<WorkTimeSetting> workTimeLst, List<WorkType> workTypeLst, String companyID);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.各申請データを作成.直行直帰申請データを作成.直行直帰申請データを作成
	 * @param application 申請
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param workTimeLst 就業時間帯リスト
	 * @param workTypeLst 勤務種類リスト
	 * @param screenAtr ScreenID
	 * @return
	 */
	public String getContentGoBack(Application application, DisplayAtr appReasonDisAtr, List<WorkTimeSetting> workTimeLst, List<WorkType> workTypeLst, ScreenAtr screenAtr);
	
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
	public AppStampDataOutput createAppStampData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, String companyID, ListOfAppTypes listOfAppTypes);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4.遅刻早退取消申請データを作成.遅刻早退取消申請データを作成
	 * @param application 申請
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param screenID ScreenID
	 * @param companyID 会社ID
	 * @return
	 */
	public String createArrivedLateLeaveEarlyData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, String companyID);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4.出張申請データを作成.出張申請データを作成
	 * @param application 申請
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param screenAtr ScreenID
	 * @param companyID 会社ID
	 * @return
	 */
	public String createBusinessTripData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, String companyID);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4.任意申請データを作成.任意申請データを作成
	 * @param application 申請
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param screenAtr ScreenID
	 * @param companyID 会社ID
	 * @return
	 */
	public String createOptionalItemApp(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, String companyID);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.各申請データを作成.残業申請データを作成.残業申請データを作成
	 * @param application
	 * @param workTypeLst
	 * @param workTimeSettingLst
	 * @param attendanceNameItemLst
	 * @param applicationListAtr
	 * @param approvalListDisplaySetting
	 * @param companyID
	 * @return
	 */
	public AppOvertimeDataOutput createOvertimeContent(Application application, List<WorkType> workTypeLst, List<WorkTimeSetting> workTimeSettingLst, 
			List<AttendanceNameItem> attendanceNameItemLst, ApplicationListAtr applicationListAtr, ApprovalListDisplaySetting approvalListDisplaySetting,
			String companyID, Map<String, Pair<Integer, Integer>> cache, ScreenAtr screenAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.各申請データを作成.休出時間申請データを作成.休出時間申請データを作成
	 * @param application
	 * @param workTypeLst
	 * @param workTimeSettingLst
	 * @param attendanceNameItemLst
	 * @param applicationListAtr
	 * @param approvalListDisplaySetting
	 * @param companyID
	 * @return
	 */
	public AppHolidayWorkDataOutput createHolidayWorkContent(Application application, List<WorkType> workTypeLst, List<WorkTimeSetting> workTimeSettingLst, 
			List<AttendanceNameItem> attendanceNameItemLst, ApplicationListAtr applicationListAtr, ApprovalListDisplaySetting approvalListDisplaySetting,
			String companyID, Map<String, Pair<Integer, Integer>> cache, ScreenAtr screenAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.各申請データを作成.振休振出申請データを作成.振休振出申請データを作成
	 * @param application 申請
	 * @param companyID 会社ID
	 * @param workTypeLst 勤務種類リスト
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param screenAtr ScreenID
	 * @return
	 */
	public CompLeaveAppDataOutput getContentComplementLeave(Application application, String companyID, List<WorkType> workTypeLst, DisplayAtr appReasonDisAtr,
			ScreenAtr screenAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.各申請データを作成.振休振出申請データを作成.振休振出申請紐付けを取得.振休振出申請紐付けを取得
	 * @param appID
	 * @param workTypeLst
	 * @param companyID
	 * @return
	 */
	public LinkComplementLeaveOutput getLinkComplementLeave(String appID, List<WorkType> workTypeLst, String companyID, ScreenAtr screenAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.各申請データを作成.申請一覧リスト取得休暇.申請一覧リスト取得休暇
	 * @param application 申請
	 * @param companyID 会社ID
	 * @param workTypeLst 勤務種類リスト
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param screenAtr ScreenID
	 * @return
	 */
	public String getContentApplyForLeave(Application application, String companyID, List<WorkType> workTypeLst, DisplayAtr appReasonDisAtr,
			ScreenAtr screenAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.申請データ作成ver4.時間年休申請データを作成.時間年休申請データを作成
	 * @param application 申請
	 * @param appReasonDisAtr 申請理由表示区分
	 * @param screenAtr ScreenID
	 * @param companyID 会社ID
	 * @return
	 */
	public String createAnnualHolidayData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr, String companyID);
	
}
