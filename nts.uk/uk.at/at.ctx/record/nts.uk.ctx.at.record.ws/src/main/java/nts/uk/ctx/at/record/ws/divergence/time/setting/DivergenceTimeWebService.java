package nts.uk.ctx.at.record.ws.divergence.time.setting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.divergence.time.setting.DivergenceReasonSelectCommandHandler;
import nts.uk.ctx.at.record.app.command.divergence.time.setting.DivergenceTimeAttendanceCommandHandler;
import nts.uk.ctx.at.record.app.command.divergence.time.setting.DivergenceTimeCommand;
import nts.uk.ctx.at.record.app.command.divergence.time.setting.DivergenceTimeCommandHandler;
import nts.uk.ctx.at.record.app.command.divergencetime.AddDivergenceReasonCommand;
import nts.uk.ctx.at.record.app.command.divergencetime.DeleteDivergenceReasonCommand;
import nts.uk.ctx.at.record.app.command.divergencetime.UpdateDivergenceItemSetCommand;
import nts.uk.ctx.at.record.app.command.divergencetime.UpdateDivergenceReasonCommand;
import nts.uk.ctx.at.record.app.command.divergencetime.UpdateDivergenceTimeCommand;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceReasonSelectFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTimeAttendanceFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTimeDto;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTimeFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.setting.DivergenceTimeInputMethodFinder;
import nts.uk.ctx.at.record.app.find.divergencetime.DivergenceItemSetDto;
import nts.uk.ctx.at.record.app.find.divergencetime.DivergenceReasonDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapterDto;

@Path("at/record/divergence.time.setting")
@Produces("application/json")
public class DivergenceTimeWebService extends WebService{
	
	@Inject
	private DivergenceTimeInputMethodFinder divTimeInputFinder;
	
	@Inject
	private DivergenceTimeFinder divTimeFinder;
	
	@Inject
	private DivergenceReasonSelectFinder divReasonselectFinder;
	
	@Inject
	private DivergenceTimeAttendanceFinder divTimeAttendanceFinder;
	
	@Inject
	private DivergenceTimeCommandHandler divTimeCommandHandler;
	
	@Inject
	private DivergenceReasonSelectCommandHandler divReasonSelectCommandHandler;
	
	@Inject
	private DivergenceTimeAttendanceCommandHandler divTimeAttendanceCommandHandler;
	/**
	 * get all divergence time
	 * @return
	 */
	@POST
	@Path("getalldivtime")
	public List<DivergenceTimeDto> getAllDivTime(){
		return this.divTimeFinder.getAllDivTime();
	}
	
	/**
	 * update divergence time
	 * @param command
	 */
	@POST
	@Path("updatedivtime")
	public void updateDivTime(DivergenceTimeCommand command){
		//this.divTimeCommandHandler.handle(command);
	}
	/**
	 * get all divergence reason
	 * @param divTimeId
	 * @return
	 */
	@POST
	@Path("getalldivreason/{divTimeId}")
	public List<DivergenceReasonDto> getAllDivReason(@PathParam("divTimeId") String divTimeId){
		//return this.divReasonselectFinder.getAllDivReasonByCode(divTimeId);
		return null;
	}
	/**
	 * add divergence reason
	 * @param command
	 */
	@POST
	@Path("adddivreason")
	public void addDivReason(AddDivergenceReasonCommand command){
		//this.divReasonSelectCommandHandler.handle(command);
	}
	/**
	 * update divergence reason
	 * @param command
	 */
	@POST
	@Path("updatedivreason")
	public void updateDivReason(UpdateDivergenceReasonCommand command){
		//this.divReasonSelectCommandHandler.handle(command);
	}
	/**
	 * delete divergence reason
	 * @param command
	 */
	@POST
	@Path("deletedivreason")
	public void deleteDivReason(DeleteDivergenceReasonCommand command){
		//this.divReasonSelectCommandHandler.handle(command);
	}
	/**
	 * get item set
	 * @param divTimeId
	 * @return
	 */
	@POST
	@Path("getitemset/{divTimeId}")
	public List<DivergenceItemSetDto> getItemSet(@PathParam("divTimeId") String divTimeId){
		//return this.divTimeAttendanceFinder.getAllDivReasonByCode(divTimeId);
		return null;
	}
	/**
	 * update time item id
	 * @param command
	 */
	@POST
	@Path("updateTimeItemId")
	public void updateTimeItemId(List<UpdateDivergenceItemSetCommand> command){
		//this.divTimeAttendanceCommandHandler.handle(command);
	}
	@POST
	@Path("getAttendanceDivergenceItem")
	public List<AttendanceTypeDivergenceAdapterDto> getAtType(){
		//乖離時間：1
		//return this.divTimeAttendanceFinder.getAllAtType(1);
		return null;
	}
	@POST
	@Path("AttendanceDivergenceName")
	public List<AttendanceNameDivergenceDto> getAtName(List<Integer> dailyAttendanceItemIds){
		//return this.divTimeAttendanceFinder.getAtName(dailyAttendanceItemIds);
		return null;
	}
	

}
