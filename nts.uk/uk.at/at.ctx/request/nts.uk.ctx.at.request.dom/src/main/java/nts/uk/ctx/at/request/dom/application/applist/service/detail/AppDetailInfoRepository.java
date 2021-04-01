package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AppCompltLeaveSyncOutput;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public interface AppDetailInfoRepository {

	/**
	 * get Application Over Time Info
	 * @param companyID
	 * @param appId
	 * @return
	 */
	public AppOverTimeInfoFull getAppOverTimeInfo(String companyID, String appId);
	/**
	 * get Application Go Back Info
	 * @param companyID
	 * @param appId
	 * @return
	 */
	public AppGoBackInfoFull getAppGoBackInfo(String companyID, String appId);
	/**
	 * get Application Holiday Work Info
	 * @param companyID
	 * @param appId
	 * @return
	 */
	public AppHolidayWorkFull getAppHolidayWorkInfo(String companyID, String appId, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime);
	/**
	 * get Application Work Change Info
	 * @param companyID
	 * @param appId
	 * @return
	 */
	public AppWorkChangeFull getAppWorkChangeInfo(String companyID, String appId, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime);
	/**
	 * 申請一覧リスト取得休暇.ドメインモデル「休暇申請」を取得してデータを作成
	 * get Application Absence Info
	 * @param companyID
	 * @param appId
	 * @param day
	 * @return
	 */
	public AppAbsenceFull getAppAbsenceInfo(String companyID, String appId, Integer day, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime);
	/**
	 * get Application Complt Leave Info
	 * @param companyID
	 * @param appId
	 * @param type
	 * @return
	 */
	public AppCompltLeaveFull getAppCompltLeaveInfo(String companyID, String appId, int type, List<WorkType> lstWkType);
	/**
	 * convert time from integer to Time_Short_HM
	 * @param time
	 * @return
	 */
	public String convertTime(Integer time);
	/**
	 * get list Application Over Time Info
	 * @param companyID
	 * @param lstAppId
	 * @return
	 */
	public List<AppOverTimeInfoFull> getListAppOverTimeInfo(String companyID, List<String> lstAppId);
	/**
	 * get list Application Holiday Work Info
	 * @param companyID
	 * @param lstAppId
	 * @param lstWkType
	 * @param lstWkTime
	 * @return
	 */
	public List<AppHolidayWorkFull> getListAppHdWorkInfo(String companyID, List<String> lstAppId, List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime);
	
	/**
	 * get list Application Go Back Info
	 * @param companyID
	 * @param lstAppId
	 * @return
	 */
	public List<AppGoBackInfoFull> getListAppGoBackInfo(String companyID, List<String> lstAppId);
	/**
	 * 
	 * @param companyID
	 * @param appId
	 * @param lstWkType
	 * @param lstWkTime
	 * @return
	 */
	public AppCompltLeaveSync getCompltLeaveSync(String companyID, String appId, List<WorkType> lstWkType);
	/**
	 * 9.同時申請された振休振出申請を取得する
	 * get data AppComplementLeaveSync
	 * @author hoatt
	 * @param companyId
	 * @param appId
	 * @return
	 */
	public AppCompltLeaveSyncOutput getAppComplementLeaveSync(String companyId, String appId);
	/**
	 * 勤務就業名称を作成 - WorkType
	 * @param lstWkType
	 * @param wkTypeCd
	 * @return
	 */
	public String findWorkTypeName(List<WorkType> lstWkType, String wkTypeCd);
	/**
	 * 勤務就業名称を作成 - WorkTime
	 * @param lstWkTime
	 * @param wkTimeCd
	 * @return
	 */
	public String findWorkTimeName(List<WorkTimeSetting> lstWkTime, String wkTimeCd);
}
