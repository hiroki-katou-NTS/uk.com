package nts.uk.ctx.sys.gateway.ws.sendmail;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.error.BusinessException;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.command.sendmail.SendMailInfoCommand;
import nts.uk.ctx.sys.gateway.app.command.sendmail.SendMailInfoCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.sendmail.dto.SendMailReturnDto;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImport;

/**
 * The Class SendMailWebService.
 */
@Path("ctx/sys/gateway/sendmail")
@Produces("application/json")
@Stateless
public class SendMailWebService extends WebService {
	
	/** The user adapter. */
	@Inject
	private UserAdapter userAdapter;
	
	/** The send mail info command handler. */
	@Inject 
	private SendMailInfoCommandHandler sendMailInfoCommandHandler;
	
	/**
	 * Submit send mail.
	 *
	 * @param infor the infor
	 * @return the string
	 */
	@POST
	@Path("submit")
	public SendMailReturnDto submitSendMail(SendMailInfoCommand infor) {
		//Get userInfor
		Optional<UserImport> userInfor = this.userAdapter.findUserByContractAndLoginId(infor.getContractCode(), infor.getLoginId());
		
		if (userInfor.isPresent()){
			//check mail of user
			if (userInfor.get().getMailAddress().isEmpty()){
				throw new BusinessException("Msg_1129");
			} else {
				//sendMail
				return this.sendMailInfoCommandHandler.handle(infor);
			}
		}
		
		return new SendMailReturnDto(null);
	}
}
