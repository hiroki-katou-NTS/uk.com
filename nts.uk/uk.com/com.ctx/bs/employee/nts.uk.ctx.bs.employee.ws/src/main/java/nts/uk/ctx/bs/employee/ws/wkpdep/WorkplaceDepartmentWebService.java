package nts.uk.ctx.bs.employee.ws.wkpdep;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.command.wkpdep.AddWkpDepConfigCommand;
import nts.uk.ctx.bs.employee.app.command.wkpdep.AddWkpDepConfigCommandHandler;
import nts.uk.ctx.bs.employee.app.command.wkpdep.DeleteWkpDepConfigCommand;
import nts.uk.ctx.bs.employee.app.command.wkpdep.DeleteWkpDepConfigCommandHandler;
import nts.uk.ctx.bs.employee.app.command.wkpdep.UpdateWkpDepConfigCommand;
import nts.uk.ctx.bs.employee.app.command.wkpdep.UpdateWkpDepConfigCommandHandler;
import nts.uk.ctx.bs.employee.app.find.wkpdep.*;

@Path("bs/employee/wkpdep")
@Produces("application/json")
public class WorkplaceDepartmentWebService extends WebService {

	@Inject
	private WkpDepFinder wkpDepFinder;

	@Inject
	private AddWkpDepConfigCommandHandler addWkpDepConfigHandler;

	@Inject
	private UpdateWkpDepConfigCommandHandler updateWkpDepConfigHandler;
	
	@Inject
	private DeleteWkpDepConfigCommandHandler deleteWkpDepConfigHandler;

	@POST
	@Path("/get-configuration/{mode}")
	public ConfigurationDto getConfiguration(@PathParam("mode") int initMode) {
		return wkpDepFinder.getWkpDepConfig(initMode, GeneralDate.today());
	}

	@POST
	@Path("/get-wkpdepinfo/{mode}/{historyId}")
	public List<InformationDto> getWkpDepInfor(@PathParam("mode") int initMode,
			@PathParam("historyId") String historyId) {
		return wkpDepFinder.getWkpDepInfor(initMode, historyId);
	}
	
	@POST
	@Path("/get-wkpdepinfo-tree/{mode}/{historyId}")
	public List<WkpDepTreeDto> getWkpDepInforTree(@PathParam("mode") int initMode,
			@PathParam("historyId") String historyId) {
		return wkpDepFinder.getWkpDepInforTree(initMode, historyId);
	}

	@POST
	@Path("/get-all-configuration/{mode}")
	public List<ConfigurationDto> getAllConfiguration(@PathParam("mode") int initMode) {
		return wkpDepFinder.getAllWkpDepConfig(initMode);
	}

	@POST
	@Path("/add-configuration")
	public JavaTypeResult<String> addConfiguration(AddWkpDepConfigCommand command) {
		return new JavaTypeResult<String>(addWkpDepConfigHandler.handle(command));
	}

	@POST
	@Path("/update-configuration")
	public void updateConfiguration(UpdateWkpDepConfigCommand command) {
		updateWkpDepConfigHandler.handle(command);
	}
	
	@POST
	@Path("/delete-configuration")
	public void deleteConfiguration(DeleteWkpDepConfigCommand command) {
		deleteWkpDepConfigHandler.handle(command);
	}
	
	@POST
	@Path("/check-total-wkpdepinfo/{mode}/{historyId}")
	public void checkTotalWkpDep(@PathParam("mode") int initMode, @PathParam("historyId") String historyId) {
		wkpDepFinder.checkTotalWkpDep(initMode, historyId);
	}

	@POST
	@Path("/get-wkpdepinfo-kcp004")
	public List<WkpDepTreeDto> getDepWkpInfoTree(DepWkpInfoFindObject findObject) {
		return wkpDepFinder.getDepWkpInfoTree(findObject);
	}
}
