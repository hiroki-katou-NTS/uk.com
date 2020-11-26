package nts.uk.ctx.sys.auth.ws.user.information;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.auth.app.command.user.information.UserChangeCommand;
import nts.uk.ctx.sys.auth.app.command.user.information.UserChangeCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/sys/auth/user/information")
@Produces("application/json")
public class UserInformationWebService extends WebService {

    @Inject
    private UserChangeCommandHandler handler;

    @Path("update")
    @POST
    public void updateAccount(UserChangeCommand command) {
        handler.handle(command);
    }
}
