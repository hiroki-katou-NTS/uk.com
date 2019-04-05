package nts.uk.ctx.bs.employee.ws.wkpdep;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.wkpdep.ConfigurationDto;
import nts.uk.ctx.bs.employee.app.find.wkpdep.InformationDto;
import nts.uk.ctx.bs.employee.app.find.wkpdep.WkpDepFinder;

@Path("bs/employee/wkpdep")
@Produces("application/json")
public class WorkplaceDepartmentWebService extends WebService {
	
	@Inject
	private WkpDepFinder wkpDepFinder;
	
	@POST
	@Path("/get-configuration/{mode}")
	public ConfigurationDto getConfiguration(@PathParam("mode") int initMode) {
		return wkpDepFinder.getWkpDepConfig(initMode, GeneralDate.today());
	}

	@POST
	@Path("/get-wkpdepinfo/{mode}/{historyId}")
	public List<InformationDto> getWkpDepInfor(@PathParam("mode") int initMode, @PathParam("historyId") String historyId) {
		return wkpDepFinder.getWkpDepInfor(initMode, historyId);
	}
	
	@POST
	@Path("/get-all-configuration/{mode}")
	public List<ConfigurationDto> getAllConfiguration(@PathParam("mode") int initMode) {
		return wkpDepFinder.getAllWkpDepConfig(initMode);
	}
	
}
