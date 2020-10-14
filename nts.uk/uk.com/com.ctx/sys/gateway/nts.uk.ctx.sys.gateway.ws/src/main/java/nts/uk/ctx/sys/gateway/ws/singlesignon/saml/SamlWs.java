package nts.uk.ctx.sys.gateway.ws.singlesignon.saml;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.command.login.saml.AuthenticateInfo;
import nts.uk.ctx.sys.gateway.app.command.login.saml.SamlAuthenticateCommand;
import nts.uk.ctx.sys.gateway.app.command.login.saml.SamlAuthenticateCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.saml.SamlValidateCommand;
import nts.uk.ctx.sys.gateway.app.command.login.saml.SamlValidateCommandHandler;

/**
 * The Class SamlWs.
 */
@Path("ctx/sys/gateway/singlesignon/saml")
@Produces("application/json")
public class SamlWs extends WebService {

	/** The submit contract with sso. */
	@Inject
	private SamlAuthenticateCommandHandler authenticate;

	@Inject
	private SamlValidateCommandHandler validate;

	/**
	 * テナント認証＆SAMLRequest生成
	 * @param command
	 * @return
	 */
	@POST
	@Path("authenticate")
	public AuthenticateInfo authenticate(SamlAuthenticateCommand command) {
		return this.authenticate.handle(command);
	}

	/**
	 * SAMLResponseの検証＆ログイン
	 * @param request
	 * 
	 * ※Idpから叩いてもらう
	 */
	@POST
	@Path("validateandlogin")
	public void validateAndLogin(@Context final HttpServletRequest request) {
		this.validate.handle(new SamlValidateCommand(request));
	}
}
