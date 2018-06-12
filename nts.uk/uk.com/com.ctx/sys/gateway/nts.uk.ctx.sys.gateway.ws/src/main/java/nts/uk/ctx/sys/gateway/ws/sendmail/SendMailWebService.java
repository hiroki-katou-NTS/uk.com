package nts.uk.ctx.sys.gateway.ws.sendmail;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.command.sendmail.SendMailInfoCommand;
import nts.uk.ctx.sys.gateway.app.command.sendmail.SendMailInfoCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.sendmail.SendMailInfoFormGCommand;
import nts.uk.ctx.sys.gateway.app.command.sendmail.SendMailInfoFormGCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.sendmail.dto.SendMailReturnDto;

/**
 * The Class SendMailWebService.
 */
@Path("ctx/sys/gateway/sendmail")
@Produces("application/json")
@Stateless
public class SendMailWebService extends WebService {
	
	/** The send mail info command handler. */
	@Inject 
	private SendMailInfoCommandHandler sendMailInfoCommandHandler;
	
	/** The send mail info form G command handler. */
	@Inject 
	private SendMailInfoFormGCommandHandler sendMailInfoFormGCommandHandler;
	
	/**
	 * Submit send mail.
	 *
	 * @param command the command
	 * @return the string
	 */
	@POST
	@Path("submit")
	public SendMailReturnDto submitSendMail(SendMailInfoCommand command) {
		//sendMail
		this.sendMailInfoCommandHandler.handle(command);
		
		return new SendMailReturnDto(null);
	}
	
	/**
	 * Submit send mail 2.
	 *
	 * @param command the command
	 * @return the send mail return dto
	 */
	@POST
	@Path("submit2")
	public SendMailReturnDto submitSendMail2(SendMailInfoFormGCommand command) {
		//sendMailformG
		this.sendMailInfoFormGCommandHandler.handle(command);
		return new SendMailReturnDto(null);
	}
}
