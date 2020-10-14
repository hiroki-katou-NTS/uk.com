package nts.uk.ctx.sys.auth.ws.user.information;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.auth.app.command.user.information.AccountInformationCommand;
import nts.uk.ctx.sys.auth.app.command.user.information.AccountInformationCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/sys/auth/user/information")
@Produces("application/json")
public class UserInformationWebService extends WebService {

    @Inject
    private AccountInformationCommandHandler handler;

    @Path("update")
    @POST
    public void updateAccount(AccountInformationCommand command) {
        handler.handle(command);
    }
}
