/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ws.login;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitContractFormCommand;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitContractFormCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormOneCommand;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormOneCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormTwoCommand;
import nts.uk.ctx.sys.gateway.app.command.login.SubmitLoginFormTwoCommandHandler;
import nts.uk.ctx.sys.gateway.app.find.login.LoginFormOneFinder;
import nts.uk.ctx.sys.gateway.app.find.login.LoginFormTwoFinder;
import nts.uk.ctx.sys.gateway.app.find.login.dto.CheckContractDto;

/**
 * The Class LoginWs.
 */
@Path("ctx/sys/gateway/login")
@Produces("application/json")
@Stateless
public class LoginWs extends WebService {

	/** The login finder. */
	@Inject
	private LoginFormOneFinder loginFormOneFinder;

	@Inject
	private LoginFormTwoFinder loginFormTwoFinder;

	@Inject
	private SubmitLoginFormOneCommandHandler submitForm1;

	@Inject
	private SubmitLoginFormTwoCommandHandler submitForm2;

	@Inject
	private SubmitContractFormCommandHandler submitContract;

	/**
	 * Find.
	 *
	 * @return the string
	 */
	@POST
	@Path("submitcontract")
	public void submitContract(SubmitContractFormCommand command) {
		this.submitContract.handle(command);
	}

	/**
	 * Find.
	 *
	 * @return the string
	 */
	@POST
	@Path("checkcontract1")
	public CheckContractDto checkContractForm1() {
		return this.loginFormOneFinder.getStartStatus();
	}

	/**
	 * Find.
	 *
	 * @return the string
	 */
	@POST
	@Path("checkcontract2")
	public String checkContractForm2() {
		return this.loginFormTwoFinder.getStartStatus();
	}

	/**
	 * Submit login form 1.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("submit/form1")
	public void submitLoginForm1(SubmitLoginFormOneCommand command) {
		this.submitForm1.handle(command);
	}

	/**
	 * Submit login form 1.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("submit/form2")
	public void submitLoginForm2(SubmitLoginFormTwoCommand command) {
		this.submitForm2.handle(command);
	}
}
