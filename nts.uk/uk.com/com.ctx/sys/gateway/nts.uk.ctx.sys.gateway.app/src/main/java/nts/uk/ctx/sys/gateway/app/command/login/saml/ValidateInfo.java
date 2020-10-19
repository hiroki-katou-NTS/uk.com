package nts.uk.ctx.sys.gateway.app.command.login.saml;

import lombok.Data;

@Data
public class ValidateInfo {
	
	private boolean isTenantAuth;
	
	private boolean isSamlValid;
	
	private String requestUrl;
	
	private String errorMessage;
	
	public ValidateInfo(boolean isTenantAuth, boolean isSamlValid, String requestUrl, String errorMessage) {
		this.isTenantAuth = isTenantAuth;
		this.isSamlValid = isSamlValid;
		this.requestUrl = requestUrl;
		this.errorMessage = errorMessage;
	}
	
	public static ValidateInfo failedToAuthTenant() {
		return new ValidateInfo(false, false, null, "テナント認証に失敗しました。");
	}
	
	public static ValidateInfo failedToValidSaml() {
		return new ValidateInfo(true, false, null, "SAML認証に失敗しました。");
	}

	public static ValidateInfo successToValidSaml(String requestUrl) {
		return new ValidateInfo(true, true, requestUrl, null);
	}



}
