package nts.uk.ctx.at.record.ws.divergence.time.setting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.divergence.time.setting.DivergenceTimeInputMethodSaveCommand;
import nts.uk.ctx.at.record.app.command.divergence.time.setting.DivergenceTimeInputMethodSaveCommandHandler;
import nts.uk.ctx.at.record.app.command.divergencetime.AddDivergenceReasonCommand;
import nts.uk.ctx.at.record.app.command.divergencetime.DeleteDivergenceReasonCommand;
import nts.uk.ctx.at.record.app.command.divergencetime.UpdateDivergenceItemSetCommand;
import nts.uk.ctx.at.record.app.command.divergencetime.UpdateDivergenceReasonCommand;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceAttendanceItemFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceReasonSelectDto;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceReasonSelectFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTimeAttendanceFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTimeDto;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTimeSettingFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTypeDto;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTimeInputMethodDto;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTimeInputMethodFinder;
import nts.uk.ctx.at.record.app.find.divergencetime.DivergenceItemSetDto;
import nts.uk.ctx.at.record.app.find.divergencetime.DivergenceReasonDto;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.DivergenceAttendanceNameDto;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.DivergenceAttendanceTypeDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapterDto;

@Path("at/record/divergencetime/setting")
@Produces("application/json")
public class DivergenceTimeWebService extends WebService {

	// @Inject
	// private DivergenceTimeInputMethodFinder divTimeInputFinder;

	/** The div time finder. */
	@Inject
	private DivergenceTimeSettingFinder divTimeFinder;

	@Inject
	private DivergenceTimeInputMethodFinder divTimeInputmethodFinder;

	@Inject
	private DivergenceTimeInputMethodSaveCommandHandler divTimeSaveCommandHandler;

	@Inject
	private DivergenceAttendanceItemFinder divTimeAttendanceFinder;
	
	@Inject
	private DivergenceReasonSelectFinder divReasonselectFinder;

	/**
	 * get all divergence time.
	 *
	 * @return the all div time
	 */
	@POST
	@Path("getalldivtime")
	public List<DivergenceTimeDto> getAllDivTime() {
		return this.divTimeFinder.getAllDivTime();
	}

	/**
	 * get all divergence time.
	 *
	 * @return the all div time reason input
	 */
	@POST
	@Path("getalldivtimereasoninput")
	public List<DivergenceTimeInputMethodDto> getAllDivTimeReasonInput() {
		return this.divTimeInputmethodFinder.getAllDivTime();
	}

	/**
	 * Gets the div time info.
	 *
	 * @param divTimeNo
	 *            the div time no
	 * @return the div time info
	 */
	@POST
	@Path("getdivtimeinfo/{divTimeId}")
	public DivergenceTimeInputMethodDto getDivTimeInfo(@PathParam("divTimeId") int divTimeNo) {
		DivergenceTimeInputMethodDto result = this.divTimeInputmethodFinder.getDivTimeInputMethodInfo(divTimeNo);
		return result;
	}

	/**
	 * update divergence time.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("updatedivtime")
	public void updateDivTime(DivergenceTimeInputMethodSaveCommand command) {
		this.divTimeSaveCommandHandler.handle(command);
	}

	@POST
	@Path("getDivType")
	public DivergenceTypeDto getDivTime() {
		return null;
	}

	/**
	 * get all divergence reason.
	 *
	 * @param divTimeId
	 *            the div time id
	 * @return the all div reason
	 */
	@POST
	@Path("getalldivreason/{divTimeId}")
	public List<DivergenceReasonSelectDto> getAllDivReason(@PathParam("divTimeId") String divTimeNo) {
		List<DivergenceReasonSelectDto> result = this.divReasonselectFinder.getAllReason(Integer.parseInt(divTimeNo));
		return result;
		
	}

	/**
	 * add divergence reason.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("adddivreason")
	public void addDivReason(AddDivergenceReasonCommand command) {
		// this.divReasonSelectCommandHandler.handle(command);
	}

	/**
	 * update divergence reason.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("updatedivreason")
	public void updateDivReason(UpdateDivergenceReasonCommand command) {
		// this.divReasonSelectCommandHandler.handle(command);
	}

	/**
	 * delete divergence reason.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("deletedivreason")
	public void deleteDivReason(DeleteDivergenceReasonCommand command) {
		// this.divReasonSelectCommandHandler.handle(command);
	}

	/**
	 * get item set.
	 *
	 * @param divTimeId
	 *            the div time id
	 * @return the item set
	 */
	@POST
	@Path("getitemset/{divTimeId}")
	public List<DivergenceItemSetDto> getItemSet(@PathParam("divTimeId") String divTimeId) {
		// return this.divTimeAttendanceFinder.getAllDivReasonByCode(divTimeId);
		return null;
	}

	/**
	 * update time item id.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("updateTimeItemId")
	public void updateTimeItemId(List<UpdateDivergenceItemSetCommand> command) {
		// this.divTimeAttendanceCommandHandler.handle(command);
	}

	/**
	 * Gets the at type.
	 *
	 * @return the at type
	 */
	@POST
	@Path("getAttendanceDivergenceItem")
	public List<DivergenceAttendanceTypeDto> getAtType() {

		return this.divTimeAttendanceFinder.getAllAtType(1);
	}

	/**
	 * Gets the at name.
	 *
	 * @param dailyAttendanceItemIds
	 *            the daily attendance item ids
	 * @return the at name
	 */
	@POST
	@Path("AttendanceDivergenceName")
	public List<DivergenceAttendanceNameDto> getAtName(List<Integer> dailyAttendanceItemIds) {
		return this.divTimeAttendanceFinder.getAtName(dailyAttendanceItemIds);

	}

}
