package nts.uk.ctx.at.function.ws.attendancerecord.export.setting;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;

/**
 * The Class AttendanceRecordOutputSettingWebService.
 */
@Path("at/function/attendancerecord/export/setting")
@Produces("application/json")
public class AttendanceRecordExportSettingWebService {

	/**
	 * Gets the all attendance rec out set.
	 *
	 * @param companyId
	 *            the company id
	 * @return the all attendance rec out set
	 */
	@POST
	@Path("getAllAttendanceRecExpSet")
	List<AttendanceRecordExportSetting> getAllAttendanceRecExpSet(String companyId) {
		return null;
	}

	/**
	 * Gets the attendance rec out set.
	 *
	 * @param companyId
	 *            the company id
	 * @param code
	 *            the code
	 * @return the attendance rec out set
	 */
	@POST
	@Path("getAttendanceRecExpSet")
	AttendanceRecordExportSetting getAttendanceRecExpSet(String companyId, Integer code) {
		return null;
	}

}
