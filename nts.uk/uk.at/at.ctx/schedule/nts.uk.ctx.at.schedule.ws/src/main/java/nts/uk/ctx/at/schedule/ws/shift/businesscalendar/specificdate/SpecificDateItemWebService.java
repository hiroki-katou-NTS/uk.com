package nts.uk.ctx.at.schedule.ws.shift.businesscalendar.specificdate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate.SpecificDateItemCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate.UpdateSpecificDateItemCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate.UpdateSpecificDateSetCommand;
import nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate.UpdateSpecificDateSetCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.specificdate.SpecificDateItemDto;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.specificdate.SpecificDateItemFinder;

@Path("at/schedule/shift/businesscalendar/specificdate")
@Produces("application/json")
public class SpecificDateItemWebService extends WebService {

	@Inject
	private SpecificDateItemFinder find;
	@Inject
	private UpdateSpecificDateItemCommandHandler update;
	@Inject
	private UpdateSpecificDateSetCommandHandler updateSet;
	@POST
	@Path("getallspecificdate")
	public List<SpecificDateItemDto> getAllSpecificDateByCompany() {
		return this.find.getAllByCompany();
	}
	
	@POST
	@Path("getspecificdatebyuse/{useAtr}")
	public List<SpecificDateItemDto> getAllSpecificDateByCompany(@PathParam("useAtr") int useAtr) {
		return this.find.getSpecDateItemIsUse(useAtr);
	}
	
	@POST
	@Path("updatespecificdate")
	public void UpdateSpecificDateByCompany(List<SpecificDateItemCommand> lstSpecificDateItem) {
		this.update.handle(lstSpecificDateItem);
	}
	
	@POST
	@Path("getspecificdatebylistcode")
	public List<SpecificDateItemDto> getSpecificDateByListCode(List<Integer> lstSpecificDateItem){
		return this.find.getSpecificDateItemByListCode(lstSpecificDateItem);
	}
	@POST
	@Path("updatespecificdateSet")
	public void updateSpecificDateSet(UpdateSpecificDateSetCommand objectToUpdate){
		this.updateSet.handle(objectToUpdate);
	}
}