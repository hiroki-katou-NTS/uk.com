package nts.uk.ctx.sys.gateway.app.command.login.saml;

import lombok.SneakyThrows;
import lombok.Value;
import nts.gul.security.saml.IdpEntryUrl;

/**
 * SAML認証情報
 */
@Value
public class SamlAuthenticateInfo {

	/** SAML認証によるシングルサインオンを使用する */
	boolean useSamlSso;
	
	/** 認証URL（失敗時はNULL） */
	String authenUrl;

	/** エラーメッセージ（成功時はNULL） */
	String errorMessage;

	public static SamlAuthenticateInfo operationSettingNotExist() {
		return new SamlAuthenticateInfo(false, null, "Msg_1979");
	}

	public static SamlAuthenticateInfo isNotUsed() {
		return new SamlAuthenticateInfo(false, null, "Msg_1992");
	}

	@SneakyThrows
	public static SamlAuthenticateInfo isUsed(IdpEntryUrl entryUrl) {
		return new SamlAuthenticateInfo(true, entryUrl.createParamUrl(), null);
	}
}
