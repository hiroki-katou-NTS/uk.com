package nts.uk.ctx.at.record.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data.UpdateCareDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info.AddCareInfoCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info.AddChildCareInfoCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info.RemoveCareInfoCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info.RemoveCareInfoCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info.RemoveChildCareInfoCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info.UpdateCareInfoCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info.UpdateCareInfoCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.info.UpdateChildCareInfoCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.nursingcareleave.info.CareLeaveInfoDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.nursingcareleave.info.CareLeaveInfoFinder;

@Path("at/record/remainnumber/careleaveinfo")
@Produces("application/json")
public class CareLeaveRemainingInfoWebService {
	
	@Inject
	private CareLeaveInfoFinder infoFinder;
	
	@Inject
	private AddCareInfoCommandHandler careLeaveAddHandler;
	
	@Inject
	private AddChildCareInfoCommandHandler childCareLeaveAddHandler;
	
	@Inject
	private UpdateCareInfoCommandHandler careLeaveUpdateHandler;
	
	@Inject
	private UpdateChildCareInfoCommandHandler childCareLeaveUpdateHandler;
	
	@Inject
	private RemoveCareInfoCommandHandler careLeaveRemoveHandler;
	
	@Inject
	private RemoveChildCareInfoCommandHandler childCareLeaveRemoveHandler;
	
	
	@POST
	@Path("careleave/getobject/{empId}")
	public CareLeaveInfoDto getCareLeave(@PathParam("empId") String empId){
		return infoFinder.getCareLeaveRemaining(empId);
	}
	
	@POST
	@Path("childcareleave/getobject/{empId}")
	public CareLeaveInfoDto getChildCareLeave(@PathParam("empId") String empId){
		return infoFinder.getChildCareLeaveRemaining(empId);
	}
	
	@POST
	@Path("careleave/add")
	public void careleaveAdd(UpdateCareInfoCommand command){
		careLeaveAddHandler.handle(command);
	}
	
	@POST
	@Path("childcareleave/add")
	public void childcareleaveAdd(UpdateCareInfoCommand command){
		childCareLeaveAddHandler.handle(command);
	}
	
	@POST
	@Path("careleave/update")
	public void careleaveUpdate(UpdateCareInfoCommand command){
		careLeaveUpdateHandler.handle(command);
	}
	
	@POST
	@Path("childcareleave/update")
	public void childcareleaveUpdate(UpdateCareDataCommand command){
		childCareLeaveUpdateHandler.handle(command);
	}
	
	@POST
	@Path("careleave/remove")
	public void careleaveRemove(RemoveCareInfoCommand command){
		careLeaveRemoveHandler.handle(command);
	}
	
	@POST
	@Path("childcareleave/remove")
	public void childcareleaveRemove(RemoveCareInfoCommand command){
		childCareLeaveRemoveHandler.handle(command);
	}
}
