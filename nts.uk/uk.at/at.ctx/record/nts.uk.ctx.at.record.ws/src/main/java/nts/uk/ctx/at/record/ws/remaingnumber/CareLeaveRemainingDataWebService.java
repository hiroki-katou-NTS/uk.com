package nts.uk.ctx.at.record.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.AddCareDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.AddChildCareCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.RemoveCareDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.RemoveCareDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.RemoveChildCareDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.UpdateCareDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.UpdateCareDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.UpdateChildCareDataCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.nursingcareleave.data.CareLeaveDataDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.nursingcareleave.data.CareLeaveDataFinder;

@Path("at/record/remainnumber/careleavedata")
@Produces("application/json")
public class CareLeaveRemainingDataWebService  extends WebService{

	@Inject
	private CareLeaveDataFinder dataFinder;
	
	@Inject
	private AddCareDataCommandHandler careLeaveAddHandler;
	
	@Inject
	private AddChildCareCommandHandler childCareLeaveAddHandler;
	
	@Inject
	private UpdateCareDataCommandHandler careLeaveUpdateHandler;
	
	@Inject
	private UpdateChildCareDataCommandHandler childCareLeaveUpdateHandler;
	
	@Inject
	private RemoveCareDataCommandHandler careLeaveRemoveHandler;
	
	@Inject
	private RemoveChildCareDataCommandHandler childCareLeaveRemoveHandler;
	
	
	@POST
	@Path("careleave/getobject/{empId}")
	public CareLeaveDataDto getCareLeave(@PathParam("empId") String empId){
		return dataFinder.getCareLeaveRemaining(empId);
	}
	
	@POST
	@Path("childcareleave/getobject/{empId}")
	public CareLeaveDataDto getChildCareLeave(@PathParam("empId") String empId){
		return dataFinder.getChildCareLeaveRemaining(empId);
	}
	
	@POST
	@Path("careleave/add")
	public void careleaveAdd(UpdateCareDataCommand command){
		careLeaveAddHandler.handle(command);
	}
	
	@POST
	@Path("childcareleave/add")
	public void childcareleaveAdd(UpdateCareDataCommand command){
		childCareLeaveAddHandler.handle(command);
	}
	
	@POST
	@Path("careleave/update")
	public void careleaveUpdate(UpdateCareDataCommand command){
		careLeaveUpdateHandler.handle(command);
	}
	
	@POST
	@Path("childcareleave/update")
	public void childcareleaveUpdate(UpdateCareDataCommand command){
		childCareLeaveUpdateHandler.handle(command);
	}
	
	@POST
	@Path("careleave/remove")
	public void careleaveRemove(RemoveCareDataCommand command){
		careLeaveRemoveHandler.handle(command);
	}
	
	@POST
	@Path("childcareleave/remove")
	public void childcareleaveRemove(RemoveCareDataCommand command){
		childCareLeaveRemoveHandler.handle(command);
	}
}
