package nts.uk.ctx.sys.auth.ws.grant.roleindividual;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.CreateRoleIndividualGrantCommand;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.CreateRoleIndividualGrantCommandHanlder;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.DeleteRoleIndividualGrantCommand;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.DeleteRoleIndividualGrantCommandHandler;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.RoleIndividualCommand;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.RoleIndividualComDto;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.RoleIndividualComFinder;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.RoleIndividualDto;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.RoleIndividualFinder;

@Path("ctx/sys/auth/grant/roleindividual")
@Produces("application/json")
public class RoleIndividualWebService extends WebService {

	@Inject
	private RoleIndividualFinder roleIndividualFinder;
	
	@Inject
	private CreateRoleIndividualGrantCommandHanlder create;
	
	@Inject
	private DeleteRoleIndividualGrantCommandHandler delete;
	

	@POST
	@Path("findall")
	public RoleIndividualDto getAll(RoleIndividualCommand command) {
		return this.roleIndividualFinder.getScreenResult(command.getSelectedCompany(), command.getSelectedRoleType());
	}
	
	@POST
	@Path("create")
	public void create(CreateRoleIndividualGrantCommand command) {
		this.create.handle(command);
			
	}

	@POST
	@Path("delete")
	public void delete(DeleteRoleIndividualGrantCommand command) {
		this.delete.handle(command);
			
	}

	@POST
	@Path("findcompany")

	public RoleIndividualDto getCompany() {
		return this.roleIndividualFinder.getCompany();
	}

}
