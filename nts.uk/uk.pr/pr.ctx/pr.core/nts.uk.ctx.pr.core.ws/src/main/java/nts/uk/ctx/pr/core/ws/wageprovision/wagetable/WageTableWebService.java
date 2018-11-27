package nts.uk.ctx.pr.core.ws.wageprovision.wagetable;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.AddWageTableCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.AddWageTableCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.UpdateWageTableCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.UpdateWageTableCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.WageTableHistoryCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.ElementItemNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableFinder;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("ctx/pr/core/wageprovision/wagetable")
@Produces("application/json")
public class WageTableWebService {

	@Inject
	private WageTableFinder finder;
	
	@Inject
	private AddWageTableCommandHandler addHandler;
	
	@Inject
	private UpdateWageTableCommandHandler updateHandler;
	
	@POST
    @Path("/get-all-wagetable")
    public List<WageTableDto> getAllWageTable() {
		return finder.getAll();
    }
	
	@POST
    @Path("/get-wagetable-by-code/{code}")
    public WageTableDto getWageTable(@PathParam("code") String wageTableCode) {
		return finder.getWageTableById(wageTableCode);
    }
	
    @POST
    @Path("/addWageTable")
    public JavaTypeResult<String> addWageTable(AddWageTableCommand command) {
    	return new JavaTypeResult<String>(addHandler.handle(command));
    }

    @POST
    @Path("/updateWageTable")
    public void updateWageTable(UpdateWageTableCommand command) {
    	updateHandler.handle(command);
    }
    
    @POST
    @Path("/get-all-elements")
    public List<ElementItemNameDto> getStatementItemName() {
    	return finder.getElements();
    }

    @POST
    @Path("/addWageTableHistory")
    public void addWageTableHistory(WageTableHistoryCommand command) {

    }

    @POST
    @Path("/editHistory")
    public void editWageTableHistory(WageTableHistoryCommand command) {

    }

    @POST
    @Path("/deleteHistory")
    public void deleteWageTableHistory(WageTableHistoryCommand command) {

    }
    
}
