package nts.uk.ctx.sys.gateway.app.command.login.saml;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.gul.security.saml.IdpEntryUrl;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SamlAuthenticateInfo {
	
	private boolean useSamlSso;
	
	// JavaScriptではOptionalが使えないのでnullで運用
	private String authenUrl;

	private String errorMessage;

	// SAMLの運用が取得できなかった
	public static SamlAuthenticateInfo noSamlOperation(){
		return new SamlAuthenticateInfo(false, null, "Msg_1979");
	}

	// SAMLにおけるシングルサインオンを運用していない
	public static SamlAuthenticateInfo noUseSso(){
		return new SamlAuthenticateInfo(false, null, "Msg_1992");
	}

	// SAMLの運用が取得できなかった
	public static SamlAuthenticateInfo useSso(IdpEntryUrl authenUrl){
		return new SamlAuthenticateInfo(true, authenUrl.toString(), null);
	}
}
