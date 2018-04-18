package nts.uk.screen.com.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ManagerSettingDto;
import nts.uk.screen.com.app.find.approvermanagement.workroot.SettingOfManagerFinder;

/**
 * @author sang.nv
 *
 */
@Path("screen/com/cmm053")
@Produces("application/json")
public class Cmm053Webservice {

	@Inject
	private SettingOfManagerFinder comFinder;

	@POST
	@Path("find/settingOfManager/{employeeId}")
	public ManagerSettingDto getPsAppRootBySettingOfManager(@PathParam("employeeId") String employeeId) {
		return comFinder.getPsAppRootBySettingOfManager(employeeId);
	}
}
