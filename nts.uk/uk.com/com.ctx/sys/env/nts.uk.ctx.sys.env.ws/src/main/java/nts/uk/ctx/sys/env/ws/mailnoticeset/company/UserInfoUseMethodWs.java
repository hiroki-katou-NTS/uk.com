package nts.uk.ctx.sys.env.ws.mailnoticeset.company;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.UserInfoUseMethod_SaveCommand;
import nts.uk.ctx.sys.env.app.command.mailnoticeset.company.UserInfoUseMethod_SaveCommandHandler;

@Path("sys/env/userinfousermethod")
@Produces(MediaType.APPLICATION_JSON)
public class UserInfoUseMethodWs extends WebService {
	@Inject
	private UserInfoUseMethod_SaveCommandHandler userInfoUseMethod_SaveCommandHandler;

	@POST
	@Path("insertorupdate")
	public void insertOrUpdate(UserInfoUseMethod_SaveCommand command) {
		this.userInfoUseMethod_SaveCommandHandler.handle(command);
	}
}
