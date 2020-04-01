/**
 * 
 */
package nts.uk.screen.hr.ws.databeforereflecting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.algorithm.RetirementRelatedInfoDto;
import nts.uk.screen.hr.app.databeforereflecting.command.AlgorithmPreCheck;
import nts.uk.screen.hr.app.databeforereflecting.command.ApprovedCommand;
import nts.uk.screen.hr.app.databeforereflecting.command.ApprovedCommandHandler;
import nts.uk.screen.hr.app.databeforereflecting.command.DataBeforeReflectCommand;
import nts.uk.screen.hr.app.databeforereflecting.command.ModifyRetireeInformationCommandHandler;
import nts.uk.screen.hr.app.databeforereflecting.command.RegisterNewEmpCommandHandler;
import nts.uk.screen.hr.app.databeforereflecting.command.RemoveCommandHandler;
import nts.uk.screen.hr.app.databeforereflecting.command.UpdateEmpApprovedCommandHandler;
import nts.uk.screen.hr.app.databeforereflecting.find.ChangeRetirementDate;
import nts.uk.screen.hr.app.databeforereflecting.find.CheckStatusRegistration;
import nts.uk.screen.hr.app.databeforereflecting.find.DataBeforeReflectResultDto;
import nts.uk.screen.hr.app.databeforereflecting.find.DatabeforereflectingFinder;

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
	private AlgorithmPreCheck preCheck;
	
	@Inject
	private ApprovedCommandHandler approve;

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
	public void preCheck(DataBeforeReflectCommand input) {
		 this.preCheck.preCheck(input);
	}
	
	@POST
	@Path("/register-new-employee")
	public JavaTypeResult<Boolean> registerNewEmployee(DataBeforeReflectCommand command) {
		return new JavaTypeResult<Boolean>(this.addCommand.handle(command));
	}

	@POST
	@Path("/register-new-retirees-approved")
	public void registerNewRetireesApproved(DataBeforeReflectCommand command) {
		this.updateCommnad.handle(command);
	}
	
	@POST
	@Path("/modify-retiree-information")
	public JavaTypeResult<Boolean> modifyRetireeInformation(DataBeforeReflectCommand command) {
		return new JavaTypeResult<Boolean>(this.modifyRetireeInfo.handle(command));
	}

	@POST
	@Path("/remove/{hisId}")
	public JavaTypeResult<Boolean> remove(@PathParam("hisId") String hisId) {
		return new JavaTypeResult<Boolean>(this.removeCommnad.handle(hisId));
	}
	
	//jcm007 update ver 2 
	@POST
	@Path("/event-change-retirementdate")
	public RetirementRelatedInfoDto eventChangeRetirementDate(ChangeRetirementDate changeRetirementDateObj) {
		return this.finder.processRetirementDateChanges(changeRetirementDateObj);
	}

	@POST
	@Path("/approved")
	public void eventChangeRetirementDate(ApprovedCommand command) {
		this.approve.handle(command);
	}
	
	
}
