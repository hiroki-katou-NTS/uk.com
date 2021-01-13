package nts.uk.ctx.at.function.ws.attendancerecord.export.setting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.command.attendancerecord.export.setting.AttendanceRecordExportSettingSaveCommand;
import nts.uk.ctx.at.function.app.command.attendancerecord.export.setting.AttendanceRecordExportSettingSaveCommandHandler;
import nts.uk.ctx.at.function.app.command.attendancerecord.export.setting.DeleteAttendanceRecordExportSettingCommand;
import nts.uk.ctx.at.function.app.command.attendancerecord.export.setting.DeleteAttendanceRecordExportSettingCommandHandler;
import nts.uk.ctx.at.function.app.command.attendancerecord.export.setting.NewAttendanceRecordExportSettingCommand;
import nts.uk.ctx.at.function.app.command.attendancerecord.export.setting.NewAttendanceRecordExportSettingCommandHandler;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.setting.AttendaceMonthDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.setting.AttendanceAuthorityOfWorkPerform;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.setting.AttendanceRecordExportSettingDto;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.setting.AttendanceRecordExportSettingFinder;
import nts.uk.ctx.at.function.app.find.attendancerecord.export.setting.AttendanceRecordExportSettingWrapperDto;

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
	public AttendanceRecordExportSettingWrapperDto getAllAttendanceRecExpSet() {
		return attendanceEcExpSetFinder.getAllAttendanceRecordExportSetting();
	}

	/**
	 * Gets the attendance rec out set.
	 *
	 * @param code
	 *            the code
	 * @return the attendance rec out set
	 */
	@POST
	@Path("getAttendanceRecExpSet/{code}/{selectionType}")
	public AttendanceRecordExportSettingDto getAttendanceRecExpSet(@PathParam("code") String code
			, @PathParam("selectionType") Integer selectionType) {
		return attendanceEcExpSetFinder.getAttendanceRecordExportSettingDto(code, selectionType);
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
	@Path("getSealStamp/{layoutId}")
	public List<String> getSealStamp(@PathParam("layoutId") String layoutId) {
		return attendanceEcExpSetFinder.getSealStamp(layoutId);
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
	
	@POST
	@Path("getClosureMonth")
	public AttendaceMonthDto getClosureMonth() {
		return attendanceEcExpSetFinder.getClosureMonth();
	}
	
	@POST 
	@Path("getAuthorityOfWorkPerformance")
	public AttendanceAuthorityOfWorkPerform getAuthorityOfWorkPerformance() {
		return attendanceEcExpSetFinder.getAuthorityOfWorkPerformance();
	}
}
