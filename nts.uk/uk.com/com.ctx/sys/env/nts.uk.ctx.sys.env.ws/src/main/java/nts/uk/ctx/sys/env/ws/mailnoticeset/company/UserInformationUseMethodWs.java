package nts.uk.ctx.sys.env.ws.mailnoticeset.company;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.UserInformationUseMethodSaveCommand;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.UserInformationUseMethodSaveCommandHandler;

@Path("sys/env/userinformationusermethod")
@Produces(MediaType.APPLICATION_JSON)
public class UserInformationUseMethodWs extends WebService {
	@Inject
	private UserInformationUseMethodSaveCommandHandler userInformationUseMethodSaveCommandHandler;

	@POST
	@Path("insertorupdate")
	public void insertOrUpdate(UserInformationUseMethodSaveCommand command) {
		this.userInformationUseMethodSaveCommandHandler.handle(command);
	}
}
