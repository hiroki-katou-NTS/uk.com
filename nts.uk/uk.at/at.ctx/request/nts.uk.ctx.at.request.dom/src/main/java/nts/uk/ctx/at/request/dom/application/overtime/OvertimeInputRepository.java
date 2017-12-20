package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.List;

/**
 * @author loivt
 * OvertimeInputRepository
 */
public interface OvertimeInputRepository {
	/**
	 * @param companyID
	 * @param appID
	 * @return List<OverTimeInput>
	 */
	List<OverTimeInput> getOvertimeInput(String companyID, String appID);
	/**
	 * @param companyID
	 * @param appID
	 * @param attendanceId
	 * @return
	 */
	List<OverTimeInput> getOvertimeInputByAttendanceId(String companyID, String appID,int attendanceId);
}
