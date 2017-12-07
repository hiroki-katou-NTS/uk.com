package nts.uk.ctx.sys.auth.ws.grant.roleindividual;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.CreateRoleIndividualGrantCommand;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.CreateRoleIndividualGrantCommandHandler;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.CreateRoleIndividualGrantCommandResult;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.DeleteRoleIndividualGrantCommand;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.DeleteRoleIndividualGrantCommandHandler;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.RoleIndividualCommand;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.UpdateRoleIndividualGrantCommand;
import nts.uk.ctx.sys.auth.app.command.grant.roleindividual.UpdateRoleIndividualGrantCommandHandler;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.RoleIndividualDto;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.RoleIndividualFinder;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto.RoleIndividualGrantMetaDto;

@Path("ctx/sys/auth/grant/roleindividual")
@Produces("application/json")
public class RoleIndividualWebService extends WebService {

	@Inject
	private RoleIndividualFinder roleIndividualFinder;

	@Inject
	private CreateRoleIndividualGrantCommandHandler createHandler;

	@Inject
	private UpdateRoleIndividualGrantCommandHandler updateHandler;
	
	@Inject
	private DeleteRoleIndividualGrantCommandHandler deleteHandler;

	@POST
	@Path("findall")
	public RoleIndividualDto getAll(RoleIndividualCommand command) {
		return this.roleIndividualFinder.findByCompanyAndRoleType(command.getSelectedCompany(), command.getSelectedRoleType());
	}

	@POST
	@Path("getmetadata")
	public RoleIndividualGrantMetaDto getCompany() {
		return this.roleIndividualFinder.getMetadata();
	}

	@POST
	@Path("create")
	public CreateRoleIndividualGrantCommandResult create(CreateRoleIndividualGrantCommand command) {
		return this.createHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(UpdateRoleIndividualGrantCommand command) {
		updateHandler.handle(command);
	}

	@POST
	@Path("delete")
	public void delete(DeleteRoleIndividualGrantCommand command) {
		this.deleteHandler.handle(command);
	}

}
