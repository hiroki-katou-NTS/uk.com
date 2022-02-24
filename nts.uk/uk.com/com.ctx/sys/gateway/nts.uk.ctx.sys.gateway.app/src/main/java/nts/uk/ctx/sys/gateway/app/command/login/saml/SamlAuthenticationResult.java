package nts.uk.ctx.sys.gateway.app.command.login.saml;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;

import java.util.Optional;

/**
 * SAML認証の結果
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SamlAuthenticationResult implements LoginCommandHandlerBase.AuthenticationResultBase{

	/** 認証成功 */
	private boolean isSuccess;

	/** 識別された社員 */
	private IdentifiedEmployeeInfo identified;

	/** ログイン後にアクセスしたいURL*/
	private String requestUrl;

	/** エラーメッセージ */
	private String errorMessage;

	/**
	 * 成功
	 * @param identified
	 * @param requestUrl
	 * @return
	 */
	public static SamlAuthenticationResult success(IdentifiedEmployeeInfo identified, String requestUrl) {
		return new SamlAuthenticationResult(true, identified, requestUrl, null);
	}

	/**
	 * SAMLの検証が存在しない
	 * @return
	 */
	public static SamlAuthenticationResult noSamlSettingFailure() {
		return new SamlAuthenticationResult(false, null, null, "Msg_1980");
	}

	/**
	 * レスポンスの検証に失敗
	 * @return
	 */
	public static SamlAuthenticationResult samlInvalidFailure() {
		return new SamlAuthenticationResult(false, null, null, "Msg_1988");
	}

	/**
	 * 社員の識別に失敗
	 * @param errorMessage
	 * @return
	 */
	public static SamlAuthenticationResult identificationFailure(String errorMessage) {
		return new SamlAuthenticationResult(false, null, null, errorMessage);
	}
}
