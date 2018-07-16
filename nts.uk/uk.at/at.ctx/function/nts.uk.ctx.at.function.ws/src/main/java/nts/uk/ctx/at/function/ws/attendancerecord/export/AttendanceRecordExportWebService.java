package nts.uk.ctx.at.function.ws.attendancerecord.export;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.find.attendancerecord.export.AttendanceIdItemDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.AttendanceIdItemFinder;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.AttendanceRecordExportDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.AttendanceRecordExportFinder;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AttendanceRecordExportWebService.
 */
@Path("com/function/attendancerecord/export")
@Produces("application/json")
public class AttendanceRecordExportWebService {

	/** The attendance rec exp finder. */
	@Inject
	AttendanceRecordExportFinder attendanceRecExpFinder;

	/** The attendance item finder. */
	@Inject
	AttendanceIdItemFinder attendanceItemFinder;

	/**
	 * Gets the all attendance record export daily.
	 *
	 * @param code
	 *            the code
	 * @return the all attendance record export daily
	 */
	@POST
	@Path("getAllAttendanceRecordDailyExport/{code}")
	public List<AttendanceRecordExportDto> getAllAttendanceRecordExportDaily(@PathParam("code") long code) {
		String companyId = AppContexts.user().companyId();
		return this.attendanceRecExpFinder.getAllAttendanceRecordExportDaily(companyId, code);
	}

	/**
	 * Gets the all attendance record export monthly.
	 *
	 * @param code
	 *            the code
	 * @return the all attendance record export monthly
	 */
	@POST
	@Path("getAllAttendanceRecordExportMonthly/{code}")
	public List<AttendanceRecordExportDto> getAllAttendanceRecordExportMonthly(@PathParam("code") long code) {
		String companyId = AppContexts.user().companyId();
		return this.attendanceRecExpFinder.getAllAttendanceRecordExportMonthly(companyId, code);
	}

	/**
	 * Gets the attendance single.
	 *
	 * @return the attendance single
	 */
	@POST
	@Path("getAttendanceListSingle")
	public List<AttendanceIdItemDto> getAttendanceSingle() {
		List<Integer> screenUse = new ArrayList<Integer>();
		screenUse.add(13);
		screenUse.add(14);
		screenUse.add(15);

		return attendanceItemFinder.getAttendanceItem(screenUse,1);

	}
	
	/**
	 * Gets the attendance calculate.
	 *
	 * @return the attendance calculate
	 */
	@POST
	@Path("getAttendanceListCalculate/{type}")
	public List<AttendanceIdItemDto> getAttendanceCalculate(@PathParam("type") int attendanceType) {
		List<Integer> screenUse = new ArrayList<Integer>();
		screenUse.add(16);
		screenUse.add(17);
		screenUse.add(18);

		return attendanceItemFinder.getAttendanceItem(screenUse, attendanceType);

	}
}
