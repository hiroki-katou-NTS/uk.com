/**
 * 
 */
package nts.uk.ctx.hr.develop.ws.databeforereflecting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.databeforereflecting.command.DataBeforeReflectCommand;
import nts.uk.ctx.hr.develop.app.databeforereflecting.command.RegisterNewEmpCommandHandler;
import nts.uk.ctx.hr.develop.app.databeforereflecting.find.CheckStatusRegistration;
import nts.uk.ctx.hr.develop.app.databeforereflecting.find.DataBeforeReflectDto;
import nts.uk.ctx.hr.develop.app.databeforereflecting.find.DatabeforereflectingFinder;

@Path("databeforereflecting")
@Produces(MediaType.APPLICATION_JSON)
public class DataBeforeReflectingPerInfoWS {

	@Inject
	private DatabeforereflectingFinder finder;
	
	@Inject
	private CheckStatusRegistration checkStatusRegistration;
	
	@Inject
	private RegisterNewEmpCommandHandler addCommand;

	@POST
	@Path("/getData")
	public List<DataBeforeReflectDto> getGuideDispSetting() {
		List<DataBeforeReflectDto> result = finder.getDataBeforeReflect();
		return result;
	}
	
	@POST
	@Path("/checkStatus")
	public Boolean checkStatusRegistration(String sid) {
		return this.checkStatusRegistration(sid);
	}

	@POST
	@Path("/add")
	public void add(DataBeforeReflectCommand command) {
		this.addCommand.handle(command);
	}

	@POST
	@Path("/update")
	public void update() {
		
	}

	@POST
	@Path("/remove")
	public void remove() {
		
	}
}
