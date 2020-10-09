package nts.uk.ctx.sys.gateway.app.command.singlesignon.saml;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class SamlValidateCommand {
	
	private HttpServletRequest request;
	
	public SamlValidateCommand(HttpServletRequest request) {
		this.request = request;
	}
}
