package nts.uk.ctx.at.request.ws.application.common;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.common.SendMailCommand;
import nts.uk.ctx.at.request.app.command.application.common.SendMailCommandHandler;

@Path("at/request/mail")
@Produces("application/json")
public class MailWebservice extends WebService{
	
	@Inject
	private SendMailCommandHandler sendMail;
	
	@POST
	@Path("send")
	public List<Integer> sendMail(SendMailCommand command){
		 return sendMail.handle(command);
	}
}
