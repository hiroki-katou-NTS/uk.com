package nts.uk.ctx.at.request.dom.application.applist.service.detail;

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
	public AppHolidayWorkFull getAppHolidayWorkInfo(String companyID, String appId);
	/**
	 * get Application Work Change Info
	 * @param companyID
	 * @param appId
	 * @return
	 */
	public AppWorkChangeFull getAppWorkChangeInfo(String companyID, String appId);
	/**
	 * get Application Absence Info
	 * @param companyID
	 * @param appId
	 * @param day
	 * @return
	 */
	public AppAbsenceFull getAppAbsenceInfo(String companyID, String appId, Integer day);
	/**
	 * get Application Complt Leave Info
	 * @param companyID
	 * @param appId
	 * @param type
	 * @return
	 */
	public AppCompltLeaveFull getAppCompltLeaveInfo(String companyID, String appId, int type);
	/**
	 * convert time from integer to Time_Short_HM
	 * @param time
	 * @return
	 */
	public String convertTime(Integer time);
}
