package nts.uk.ctx.sys.gateway.ws.changepassword;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.command.changepassword.ChangePasswordCommand;
import nts.uk.ctx.sys.gateway.app.command.changepassword.ChangePasswordCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.changepassword.ForgotPasswordCommand;
import nts.uk.ctx.sys.gateway.app.command.changepassword.ForgotPasswordCommandHandler;

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
}
