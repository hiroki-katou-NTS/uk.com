package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;
import nts.uk.ctx.at.request.dom.application.applist.service.AppPrePostGroup;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public interface AppContentDetailCMM045 {

	/**
	 * get content over timeBf
	 * 残業申請 kaf005 - appType = 0
	 * ※申請モード、承認モード(事前)用レイアウト
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	public String getContentOverTimeBf(AppOverTimeInfoFull overTime, String companyId, String appId, int detailSet, Integer appReasonDisAtr, String appReason, int screenAtr);
	/**
	 * get content over timeAf
	 * 残業申請 kaf005 - appType = 0
	 * ※承認モード(事後)用レイアウト
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	public String getContentOverTimeAf(AppOverTimeInfoFull overTime, int detailSet, Integer appReasonDisAtr, String appReason, AppPrePostGroup subData);
	/**
	 * 申請一覧リスト取得休暇
	 * get content absence
	 * 休暇申請 kaf006 - appType = 1
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	public String getContentAbsence(AppAbsenceFull absence, String companyId, String appId, Integer appReasonDisAtr, String appReason,
			int day, int screenAtr, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime);
	/**
	 * 勤務変更申請データを作成
	 * get content work change
	 * 勤務変更申請 kaf007 - appType = 2
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	public String getContentWorkChange(AppWorkChangeFull wkChange, String companyId, String appId, Integer appReasonDisAtr,
			String appReason, int screenAtr, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime);
	/**
	 * 直行直帰申請データを作成
	 * get content go back
	 * 直行直帰申請 kaf009 - appType = 4
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	public String getContentGoBack(AppGoBackInfoFull goBack, String companyId, String appId, Integer appReasonDisAtr, String appReason, int screenAtr);
	/**
	 * get Content HdWorkBf
	 * 休日出勤時間申請 kaf010 - appTYpe = 6
	 * ※申請モード、承認モード(事前)用レイアウト
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	public String getContentHdWorkBf(AppHolidayWorkFull hdWork, String companyId, String appId, Integer appReasonDisAtr, String appReason,
			int screenAtr, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime);
	/**
	 * get Content HdWorkAf
	 * 休日出勤時間申請 kaf010 - appTYpe = 6
	 * ※承認モード(事後)用レイアウト
	 * @param appId
	 * @param detailSet
	 * @return
	 */
	public String getContentHdWorkAf(AppHolidayWorkFull hdWork, Integer appReasonDisAtr, String appReason, AppPrePostGroup subData);
	/**
	 * get Content Stamp
	 * 打刻申請 kaf002 - appType = 7
	 * @param companyId
	 * @param appId
	 * @param detailSet
	 * @param screenAtr
	 * @return
	 */
	public String getContentStamp(String companyId, String appId, Integer appReasonDisAtr, String appReason, int screenAtr);
	/**
	 * get Content EarlyLeave
	 * 遅刻早退取消申請 kaf004 - appType = 9
	 * @param companyId
	 * @param appId
	 * @param detailSet
	 * @param screenAtr
	 * @return
	 */
	public String getContentEarlyLeave(String companyId, String appId, Integer appReasonDisAtr, String appReason, int prePostAtr,int screenAtr);
	/**
	 * get Content CompltLeave
	 * 振休振出申請 kaf011 - appType = 10
	 * @param companyId
	 * @param appId
	 * @param detailSet
	 * @param screenAtr
	 * @return
	 */
	public String getContentCompltLeave(AppCompltLeaveSync compltSync, String companyId, String appId, Integer appReasonDisAtr,
			String appReason, int screenAtr, List<WorkType> lstWkType);
	/**
	 * get Content CompltSync
	 * 振休振出申請 kaf011 - appType = 10
     * 同期
	 * @param companyId
	 * @param appId
	 * @param appReasonDisAtr
	 * @param appReason
	 * @param type
	 * @param screenAtr
	 * @return
	 */
	public String getContentCompltSync(AppCompltLeaveSync compltSync,String companyId, String appId, Integer appReasonDisAtr,
			String appReason, int type, int screenAtr, List<WorkType> lstWkType);
	/**
	 * 振休振出申請データを作成
	 * get Content Complt
	 * @param companyId
	 * @param appId
	 * @param appReasonDisAtr
	 * @param appReason
	 * @param screenAtr
	 * @return
	 */
	public String getContentComplt(AppCompltLeaveSync complt, String companyId, String appId, Integer appReasonDisAtr, String appReason,
			int screenAtr, List<WorkType> lstWkType);
}
