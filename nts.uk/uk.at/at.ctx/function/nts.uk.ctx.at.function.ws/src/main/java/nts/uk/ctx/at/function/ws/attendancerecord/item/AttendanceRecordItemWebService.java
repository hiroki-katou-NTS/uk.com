package nts.uk.ctx.at.function.ws.attendancerecord.item;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.find.attendancerecord.item.CalculateAttendanceRecordFinder;
import nts.uk.ctx.at.function.app.find.attendancerecord.item.SingleAttendanceRecordFinder;

/**
 * The Class AttendanceRecordItemWebService.
 */
@Path("at/function/attendancerecord/item")
@Produces("application/json")
public class AttendanceRecordItemWebService {
	
	/** The single attendance record finder. */
	@Inject
	SingleAttendanceRecordFinder singleAttendanceRecordFinder;
	
	/** The calculate attendance record finder. */
	@Inject
	CalculateAttendanceRecordFinder calculateAttendanceRecordFinder;
	

}
