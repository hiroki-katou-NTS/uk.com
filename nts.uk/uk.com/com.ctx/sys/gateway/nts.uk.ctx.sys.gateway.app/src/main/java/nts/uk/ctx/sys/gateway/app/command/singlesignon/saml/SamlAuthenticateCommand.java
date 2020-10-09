package nts.uk.ctx.sys.gateway.app.command.singlesignon.saml;

import lombok.Data;

@Data
public class SamlAuthenticateCommand {

	private String contractCode;

	private String password;
	
	private String issueUrl;

	private String requestUrl;

	public SamlAuthenticateCommand() {
		super();
	}
}
