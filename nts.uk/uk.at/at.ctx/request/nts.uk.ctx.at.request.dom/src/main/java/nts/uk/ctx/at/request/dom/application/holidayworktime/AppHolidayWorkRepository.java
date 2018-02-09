package nts.uk.ctx.at.request.dom.application.holidayworktime;

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
}
