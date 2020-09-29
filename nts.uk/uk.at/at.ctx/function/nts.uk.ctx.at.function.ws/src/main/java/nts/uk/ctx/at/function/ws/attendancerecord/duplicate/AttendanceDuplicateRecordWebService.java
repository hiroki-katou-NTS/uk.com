package nts.uk.ctx.at.function.ws.attendancerecord.duplicate;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.find.attendancerecord.duplicate.AttendanceRecordDuplicateDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.duplicate.AttendanceRecordDuplicateFinder;
import nts.uk.ctx.at.function.app.find.attendancerecord.duplicate.DataInfoReturnDto;

/**
 * Screen F - KWR 002 
 * The Class AttendanceDuplicateRecordWS.
 */
@Path("com/function/attendancerecord/duplicate")
@Produces("application/json")
public class AttendanceDuplicateRecordWebService {
	
	@Inject
	AttendanceRecordDuplicateFinder attendanceRecordDuplicateFinder;
	
	@Path("executeCopy")
	@POST
	public void executeCopy(AttendanceRecordDuplicateDto dto) {
		this.attendanceRecordDuplicateFinder.executeCopy(dto);
	}
	
	@POST
	@Path("findCopyAttendance")
	public DataInfoReturnDto findCopyAttendance(AttendanceRecordDuplicateDto dto) {
		return this.attendanceRecordDuplicateFinder.findCopyAttendance(dto);
	}
	
}
