package nts.uk.ctx.sys.gateway.ws.login.sso.saml;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.gateway.app.command.login.saml.start.StartSamlLoginCommand;
import nts.uk.ctx.sys.gateway.app.command.login.saml.start.StartSamlLoginCommandHandler;
import nts.uk.ctx.sys.gateway.app.command.login.saml.start.StartSamlLoginResult;
import nts.uk.ctx.sys.gateway.app.command.login.saml.validate.SamlLoginResult;
import nts.uk.ctx.sys.gateway.app.command.login.saml.validate.SamlValidateCommand;
import nts.uk.ctx.sys.gateway.app.command.login.saml.validate.SamlValidateCommandHandler;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * The Class SamlWs.
 */
@Path("ctx/sys/gateway/singlesignon/saml")
@Produces(MediaType.APPLICATION_JSON)
public class SamlWs extends WebService {

	@Inject
	private StartSamlLoginCommandHandler authenticate;

	@Inject
	private SamlValidateCommandHandler validate;

	/**
	 * テナント認証＆SAMLRequest生成
	 * 
	 * @param command
	 * @return
	 */
	@POST
	@Path("authenticate")
	public StartSamlLoginResult authenticate(@Context final HttpServletRequest request, StartSamlLoginCommand command) {
		command.setRequest(request);
		return this.authenticate.handle(command);
	}

	/**
	 * SAMLResponseの検証＆ログイン
	 * 
	 * @param request
	 * 
	 * ※Idpから叩いてもらう
	 */
	@POST
	@Path("validateandlogin")
	public Response validateAndLogin(@Context final HttpServletRequest request) {

		val result = this.validate.handle(new SamlValidateCommand(request));

		if (result.isLoginSucceeded()) {
			return Response.status(Status.FOUND)
					.header("Location", result.getRedirectUrl())
					.build();
		}

		return Response.status(Status.OK).type(MediaType.TEXT_HTML)
				.entity(createHtml(result))
				.build();
	}

	private static String createHtml(SamlLoginResult result) {

		if (result.isLoginSucceeded()) {
			throw new RuntimeException();
		}

		val html = new StringBuilder()
				.append("<!DOCTYPE html><html><head><meta http-equiv=\"content-type\" charset=\"utf-8\"></head><body>");

		if (result.isAssociationNeeded()) {
			html.append("<script>")
					// IdPUserNameにシングルクォートが入っているとバグるが、レアケースなので今は無視しておく
					.append("sessionStorage.setItem('nts.uk.saml.idpUserName', '" + result.getIdpUserName() + "');")
					.append("location.href = '" + result.getRedirectUrl() + "';")
					.append("</script>");
		} else {
			html.append(I18NText.getText(result.getErrorMessageId()));
		}

		return html.append("</body></html>").toString();
	}
}
