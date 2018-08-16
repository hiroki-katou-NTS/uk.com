package nts.uk.ctx.at.request.dom.application.holidayworktime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AppHolidayWorkRepository {
	/**
	 * getAppHolidayWork
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public Optional<AppHolidayWork> getAppHolidayWork(String companyID, String appID);
	/**
	 * ドメインモデル「休日出勤申請」の登録処理を実行する(INSERT)
	 * @param domain : 休日出勤申請
	 */
	public void Add(AppHolidayWork domain);
	
	/**
	 * getFullAppHolidayWork
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public Optional<AppHolidayWork> getFullAppHolidayWork(String companyID, String appID);
	/**
	 * 「休日出勤申請」の登録する
	 * @param domain
	 */
	public void update(AppHolidayWork domain);
	/**
	 * delete
	 * @param companyID
	 * @param appID
	 */
	public void delete(String companyID, String appID);
	/**
	 * get Application Holiday Work and Frame
	 * @author hoatt
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public Optional<AppHolidayWork> getAppHolidayWorkFrame(String companyID, String appID);
	/**
	 * get list Application Holiday Work and Frame
	 * @author hoatt
	 * @param companyID
	 * @param lstAppID
	 * @return map: key - appID, value - AppHolidayWork
	 */
	public Map<String,AppHolidayWork> getListAppHdWorkFrame(String companyID, List<String> lstAppID);
}
