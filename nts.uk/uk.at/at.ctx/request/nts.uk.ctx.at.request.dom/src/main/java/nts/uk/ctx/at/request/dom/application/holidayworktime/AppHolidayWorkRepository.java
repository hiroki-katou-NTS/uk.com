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
}
