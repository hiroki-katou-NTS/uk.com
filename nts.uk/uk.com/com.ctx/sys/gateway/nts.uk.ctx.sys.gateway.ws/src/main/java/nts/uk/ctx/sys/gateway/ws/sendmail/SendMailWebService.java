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
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImport;

@Path("ctx/sys/gateway/sendmail")
@Produces("application/json")
@Stateless
public class SendMailWebService extends WebService {
	
	@Inject
	private UserAdapter userAdapter;
	
	@POST
	@Path("submit")
	public String submitSendMail(SendMailInfoCommand infor) {
		
		Optional<UserImport> userInfor = this.userAdapter.findUserByContractAndLoginId(infor.getContractCode(), infor.getLoginId());
		
		if (!userInfor.isPresent())
			return null;
		
		if (userInfor.get().getMailAddress().isEmpty()){
			throw new BusinessException("Msg_1129");
		} else {
			this.sendMail();
			
			return null;
		}
	}
	
	private void sendMail(){
		//get URL from CCG033
		//todo
		
	}
}
