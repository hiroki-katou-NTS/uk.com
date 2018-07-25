package nts.uk.ctx.at.function.ws.attendancerecord.export.setting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.command.attendancerecord.export.setting.*;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.setting.AttendanceRecordExportSettingDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.setting.AttendanceRecordExportSettingFinder;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AttendanceRecordOutputSettingWebService.
 */
@Path("com/function/attendancerecord/export/setting")
@Produces("application/json")
public class AttendanceRecordExportSettingWebService {

	/** The attendance ec exp set finder. */
	@Inject
	AttendanceRecordExportSettingFinder attendanceEcExpSetFinder;

	/** The attendance ec exp set save command handler. */
	@Inject
	AttendanceRecordExportSettingSaveCommandHandler attendanceEcExpSetSaveCommandHandler;

	/** The attendance ec exp set add command handler. */
//	@Inject
//	AttendanceRecordExportSettingAddCommandHandler attendanceEcExpSetAddCommandHandler

	@Inject
	NewAttendanceRecordExportSettingCommandHandler addHandler;

	@Inject
	DeleteAttendanceRecordExportSettingCommandHandler delHandler;

	/**
	 * Gets the all attendance rec out set.
	 *
	 * @return the all attendance rec out set
	 */
	@POST
	@Path("getAllAttendanceRecExpSet")
	public List<AttendanceRecordExportSettingDto> getAllAttendanceRecExpSet() {
		String companyId = AppContexts.user().companyId();
		return attendanceEcExpSetFinder.getAllAttendanceRecordExportSetting(companyId);
	}

	/**
	 * Gets the attendance rec out set.
	 *
	 * @param code
	 *            the code
	 * @return the attendance rec out set
	 */
	@POST
	@Path("getAttendanceRecExpSet/{code}")
	public AttendanceRecordExportSettingDto getAttendanceRecExpSet(@PathParam("code") long code) {
		String companyId = AppContexts.user().companyId();
		return attendanceEcExpSetFinder.getAttendanceRecordExportSettingDto(companyId, code);
	}

	/**
	 * Adds the attendance rec exp set.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("addAttendanceRecExpSet")
	public void AddAttendanceRecExpSet(NewAttendanceRecordExportSettingCommand command) {
		this.addHandler.handle(command);
	}

	/**
	 * Update attendance rec exp set.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("updateAttendanceRecExpSet")
	public void UpdateAttendanceRecExpSet(AttendanceRecordExportSettingSaveCommand command) {
		this.attendanceEcExpSetSaveCommandHandler.handle(command);
	}

	/**
	 * Delete attendance rec exp set.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("deteleAttendanceRecExpSet")
	public void DeleteAttendanceRecExpSet(DeleteAttendanceRecordExportSettingCommand command) {
		this.delHandler.handle(command);
	}

	/**
	 * Gets the seal stamp.
	 *
	 * @param code
	 *            the code
	 * @return the seal stamp
	 */
	@POST
	@Path("getSealStamp/{code}")
	public List<String> getSealStamp(@PathParam("code") long code) {
		return attendanceEcExpSetFinder.getSealStamp(code);
	}
	
	/**
	 * Gets the permission.
	 *
	 * @return the permission
	 */
	@POST
	@Path("getPermission")
	public Boolean getPermission(){
		return attendanceEcExpSetFinder.havePermission();
	}
}
