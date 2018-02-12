package nts.uk.ctx.at.request.dom.application.holidayworktime;

import java.util.List;

public interface HolidayWorkInputRepository {
	/**
	 * getHolidayWorkInputByAttendanceID
	 * @param companyID
	 * @param appID
	 * @param attendanceId
	 * @return
	 */
	public List<HolidayWorkInput> getHolidayWorkInputByAttendanceType(String companyID, String appID,int attendanceType);
	/**
	 * getHolidayWorkInputByAppID
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public List<HolidayWorkInput> getHolidayWorkInputByAppID(String companyID, String appID);
}
