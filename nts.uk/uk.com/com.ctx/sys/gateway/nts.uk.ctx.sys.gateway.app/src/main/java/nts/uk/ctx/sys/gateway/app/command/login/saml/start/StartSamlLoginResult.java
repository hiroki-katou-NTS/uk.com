package nts.uk.ctx.sys.gateway.app.command.login.saml.start;

import lombok.SneakyThrows;
import lombok.Value;
import nts.gul.security.saml.IdpEntryUrl;

/**
 * SAMLログイン開始コマンドの結果
 */
@Value
public class StartSamlLoginResult {

	/** SAML認証によるシングルサインオンを使用する */
	boolean useSamlSso;
	
	/** 認証URL（失敗時はNULL） */
	String authenUrl;

	/** エラーメッセージ（成功時はNULL） */
	String errorMessage;

	public static StartSamlLoginResult operationSettingNotExist() {
		return new StartSamlLoginResult(false, null, "Msg_1979");
	}

	public static StartSamlLoginResult isNotUsed() {
		return new StartSamlLoginResult(false, null, "Msg_1992");
	}

	@SneakyThrows
	public static StartSamlLoginResult isUsed(IdpEntryUrl entryUrl) {
		return new StartSamlLoginResult(true, entryUrl.createParamUrl(), null);
	}
}
