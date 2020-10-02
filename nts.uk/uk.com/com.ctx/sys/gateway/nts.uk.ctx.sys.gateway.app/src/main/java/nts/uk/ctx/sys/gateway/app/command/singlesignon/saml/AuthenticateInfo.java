package nts.uk.ctx.sys.gateway.app.command.singlesignon.saml;

import lombok.Data;

@Data
public class AuthenticateInfo {
	
	private boolean useSamlSso;
	
	// JavaScriptではOptionalが使えないのでnullで運用
	private String authenUrl;
	
	public AuthenticateInfo(boolean useSamlSso, String authenUrl) {
		this.useSamlSso = useSamlSso;
		this.authenUrl = authenUrl;
	}
}
