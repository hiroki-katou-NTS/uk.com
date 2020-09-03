package nts.uk.ctx.at.function.ws.attendancerecord.duplicate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.find.attendancerecord.duplicate.AttendanceRecordDuplicateFinder;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.setting.AttendanceRecordExportSettingDto;

/**
 * Screen F - KWR 002 
 * The Class AttendanceDuplicateRecordWS.
 */
@Path("com/function/attendancerecord/duplicate")
@Produces("application/json")
public class AttendanceDuplicateRecordWebService {
	
	@Inject
	AttendanceRecordDuplicateFinder attendanceRecordDuplicateFinder;
	
	@POST
	@Path("checkDuplicate")
	public void getAllAttendanceRecExpSet() {
		attendanceRecordDuplicateFinder.copyRegistionProcess();
	}
}
