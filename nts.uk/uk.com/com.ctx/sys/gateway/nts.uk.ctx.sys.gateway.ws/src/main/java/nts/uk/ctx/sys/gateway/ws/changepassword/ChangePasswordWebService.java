package nts.uk.ctx.sys.gateway.ws.changepassword;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.command.changepassword.ChangePasswordCommand;
import nts.uk.ctx.sys.gateway.app.command.changepassword.ChangePasswordCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.changepassword.ForgotPasswordCommand;
import nts.uk.ctx.sys.gateway.app.command.changepassword.ForgotPasswordCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.dto.LoginInforDto;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImport;

/**
 * The Class ChangePasswordWebService.
 */
@Path("ctx/sys/gateway/changepassword")
@Produces("application/json")
@Stateless
public class ChangePasswordWebService extends WebService{
	
	/** The change pass command handler. */
	@Inject
	private ChangePasswordCommandHandler changePassCommandHandler;
	
	/** The forgot password command handler. */
	@Inject
	private ForgotPasswordCommandHandler forgotPasswordCommandHandler;
	
	@Inject
	private UserAdapter userAdapter;
	/**
	 * Channge pass word.
	 *
	 * @param command the command
	 */
	@POST
	@Path("submitchangepass")
	public void channgePassWord(ChangePasswordCommand command) {
		this.changePassCommandHandler.handle(command);
	}

	
	/**
	 * Submit forgot pass.
	 *
	 * @param command the command
	 */
	@POST
	@Path("submitforgotpass")
	public void submitForgotPass(ForgotPasswordCommand command) {
		this.forgotPasswordCommandHandler.handle(command);
	}
	
	/**
	 * Submit login form 3.
	 *
	 * @param contractCode the contract code
	 * @param loginId the login id
	 * @return the login infor dto
	 */
	@POST
	@Path("getUserNameByLoginId/{contractCode}/{loginId}")
	public LoginInforDto submitLoginForm3(@PathParam("contractCode") String contractCode, @PathParam("loginId") String loginId) {
		Optional<UserImport> user = this.userAdapter.findUserByContractAndLoginId(contractCode, loginId);
		if (user.isPresent()){
			return new LoginInforDto(loginId, user.get().getUserName());
		}
		return new LoginInforDto(null, null);
	}
}
