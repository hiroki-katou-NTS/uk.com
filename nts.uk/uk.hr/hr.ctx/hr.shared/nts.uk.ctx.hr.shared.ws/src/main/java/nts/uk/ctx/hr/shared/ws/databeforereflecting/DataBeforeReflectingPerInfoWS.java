/**
 * 
 */
package nts.uk.ctx.hr.shared.ws.databeforereflecting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.shared.app.databeforereflecting.command.DataBeforeReflectCommand;
import nts.uk.ctx.hr.shared.app.databeforereflecting.command.ModifyRetireeInformationCommandHandler;
import nts.uk.ctx.hr.shared.app.databeforereflecting.command.PreCheckCommandHandler;
import nts.uk.ctx.hr.shared.app.databeforereflecting.command.RegisterNewEmpCommandHandler;
import nts.uk.ctx.hr.shared.app.databeforereflecting.command.RemoveCommandHandler;
import nts.uk.ctx.hr.shared.app.databeforereflecting.command.UpdateEmpApprovedCommandHandler;
import nts.uk.ctx.hr.shared.app.databeforereflecting.find.CheckStatusRegistration;
import nts.uk.ctx.hr.shared.app.databeforereflecting.find.DataBeforeReflectResultDto;
import nts.uk.ctx.hr.shared.app.databeforereflecting.find.DatabeforereflectingFinder;

@Path("databeforereflecting")
@Produces(MediaType.APPLICATION_JSON)
public class DataBeforeReflectingPerInfoWS {

	@Inject
	private DatabeforereflectingFinder finder;
	
	@Inject
	private CheckStatusRegistration checkStatusRegis;
	
	@Inject
	private RegisterNewEmpCommandHandler addCommand;
	
	@Inject
	private UpdateEmpApprovedCommandHandler updateCommnad;
	
	@Inject
	private  ModifyRetireeInformationCommandHandler modifyRetireeInfo;
	
	@Inject
	private  RemoveCommandHandler removeCommnad;
	
	@Inject
	private PreCheckCommandHandler preCheck;

	@POST
	@Path("/getData")
	public DataBeforeReflectResultDto getData() {
		DataBeforeReflectResultDto result = finder.getDataBeforeReflect();
		return result;
	}
	
	@POST
	@Path("/checkStatusRegistration/{sid}")
	public void checkStatusRegistration(@PathParam("sid") String sid) {
		 this.checkStatusRegis.CheckStatusRegistration(sid);
	}
	
	@POST
	@Path("/register/preCheck")
	public void preCheck(DataBeforeReflectCommand command) {
		 this.preCheck.handle(command);
	}
	
	@POST
	@Path("/register-new-employee")
	public void registerNewEmployee(DataBeforeReflectCommand command) {
		this.addCommand.handle(command);
	}

	@POST
	@Path("/register-new-retirees-approved")
	public void registerNewRetireesApproved(DataBeforeReflectCommand command) {
		this.updateCommnad.handle(command);
	}
	
	@POST
	@Path("/modify-retiree-information")
	public void modifyRetireeInformation(DataBeforeReflectCommand command) {
		this.modifyRetireeInfo.handle(command);
	}

	@POST
	@Path("/remove/{hisId}")
	public void remove(@PathParam("hisId") String hisId) {
		this.removeCommnad.remove(hisId);
	}
}
