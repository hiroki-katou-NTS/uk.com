package nts.uk.ctx.at.schedule.ws.shift.specificdayset.workplace;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace.DeleteWorkplaceSpecificDateCommand;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace.DeleteWorkplaceSpecificDateCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace.InsertWorkplaceSpecificDateCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace.UpdateWorkplaceSpecificDateCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace.WorkplaceSpecificDateCommand;
import nts.uk.ctx.at.schedule.app.find.shift.specificdayset.workplace.WokplaceSpecificDateDto;
import nts.uk.ctx.at.schedule.app.find.shift.specificdayset.workplace.WorkplaceSpecificDateFinder;

@Path("at/schedule/shift/specificdayset/workplace")
@Produces("application/json")
public class WorkplaceSpecificDateWebService extends WebService {

	@Inject
	private WorkplaceSpecificDateFinder find;
	@Inject
	private InsertWorkplaceSpecificDateCommandHandler insertCommnad;
	@Inject
	private UpdateWorkplaceSpecificDateCommandHandler updateCommnad;
	@Inject
	private DeleteWorkplaceSpecificDateCommandHandler deleteCommand;

	// @POST
	// @Path("getworkplacespecificdaysetbydate/{processDate}")
	// public List<WokplaceSpecificDateDto>
	// getworkplaceSpecificDateByworkplace(@PathParam("processDate") int
	// processDate) {
	// return this.find.getWpSpecByDate(processDate);
	// }

	@POST
	@Path("getworkplacespecificdate")
	public List<WokplaceSpecificDateDto> getworkplaceSpecificDateByworkplaceWithName(WorkplaceSpectificParam param) {
		return this.find.getWpSpecByDateWithName(param.getWorkPlaceId(), param.getWorkPlaceDate());
	}

	@POST
	@Path("insertworkplacespecificdate")
	public void InsertWorkplaceSpecificDate(List<WorkplaceSpecificDateCommand> lstWorkplaceSpecificDateItem) {
		this.insertCommnad.handle(lstWorkplaceSpecificDateItem);
	}
	
	@POST
	@Path("updateworkplacespecificdate")
	public void updateWorkplaceSpecificDate(List<WorkplaceSpecificDateCommand> lstWorkplaceSpecificDateItem) {
		this.updateCommnad.handle(lstWorkplaceSpecificDateItem);
	}

	@POST
	@Path("deleteworkplacespecificdate")
	public void DeleteworkplaceSpecificDate(DeleteWorkplaceSpecificDateCommand deleteCommand) {
		this.deleteCommand.handle(deleteCommand);
	}
}
@Value
class WorkplaceSpectificParam{
	String workPlaceId;
	String workPlaceDate;
}