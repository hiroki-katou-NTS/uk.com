package nts.uk.ctx.sys.auth.ws.grant.rolesetjob;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.auth.app.command.grant.rolesetjob.RoleSetGrantedJobTitleCommand;
import nts.uk.ctx.sys.auth.app.find.grant.rolesetperson.RoleSetGrantedPersonDto;
import nts.uk.ctx.sys.auth.app.find.grant.rolesetperson.RoleSetGrantedPersonFinder;

/**
 * 
 * @author HungTT
 *
 */

@Path("ctx/sys/auth/grant/rolesetperson")
@Produces("application/json")
public class RoleSetGrantedPersonWebService {
	@Inject
	private RoleSetGrantedPersonFinder finder;

	// @Inject
	// private RegisterRoleSetGrantedJobTitleCommandHandler handler;

	@POST
	@Path("start")
	public List<RoleSetGrantedPersonDto> start() {
		return this.finder.getAllData();
	}

	@POST
	@Path("register")
	public void register(RoleSetGrantedJobTitleCommand command) {
		// this.handler.handle(command);

		System.out.println("register done! - command: " + command.isApplyToConcurrentPerson() + " - details lengt: "
				+ command.getDetails().size());
	}

}
