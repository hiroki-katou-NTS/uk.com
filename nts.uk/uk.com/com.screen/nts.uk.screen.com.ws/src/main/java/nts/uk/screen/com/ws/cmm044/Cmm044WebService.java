package nts.uk.screen.com.ws.cmm044;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.command.workflow.agent.SendEmailCommand;
import nts.uk.screen.com.app.command.workflow.agent.SendMailToApproverCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("com/screen/cmm044")
@Produces("application/json")
public class Cmm044WebService extends WebService {
    @Inject
    private SendMailToApproverCommandHandler sendMailToApproverCommandHandler;

    @Path("sendMail")
    @POST
    public void sendEmail(SendEmailCommand command) {
        this.sendMailToApproverCommandHandler.handle(command);
    }
}
