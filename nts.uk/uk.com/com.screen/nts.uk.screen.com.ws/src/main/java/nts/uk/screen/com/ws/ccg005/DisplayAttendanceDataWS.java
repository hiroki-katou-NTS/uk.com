package nts.uk.screen.com.ws.ccg005;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.com.app.find.ccg005.attendance.data.DisplayAttendanceDataDto;
import nts.uk.screen.com.app.find.ccg005.attendance.data.DisplayAttendanceDataScreenQuery;

@Path("screen/com/ccg005")
@Produces("application/json")
public class DisplayAttendanceDataWS {
	
	@Inject
	private DisplayAttendanceDataScreenQuery displayAttendanceDataScreenQuery;

	@POST
	@Path("getDisplayAttendanceData")
	public DisplayAttendanceDataDto getDisplayAttendanceData() {
		return displayAttendanceDataScreenQuery.getDisplayAttendanceData();
	}
}
