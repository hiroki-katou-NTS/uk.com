package nts.uk.ctx.sys.gateway.app.command.login.saml.validate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nts.uk.shr.com.program.ProgramsManager;

import java.util.Optional;

/**
 * SAML認証ログインの結果
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SamlLoginResult {

	private final State state;

	private final Optional<String> idpUserName;

	/** ログイン成功時のトップページURLか、識別失敗時のログインURL */
	private final Optional<String> redirectUrl;

	private final Optional<String> errorMessageId;

	public static SamlLoginResult failed(SamlAuthenticationResult authen) {
		if (authen.isSuccess()) {
			throw new RuntimeException("not success");
		}

		if (authen.isValidated()) {
			String loginPage = ProgramsManager.CCG007D.getRootRelativePath();
			return new SamlLoginResult(State.ASSOCIATION_NEEDED, authen.getIdpUserName(), Optional.of(loginPage), Optional.empty());
		}

		return new SamlLoginResult(State.VALIDATION_FAILED, authen.getIdpUserName(), Optional.empty(), Optional.empty());
	}

	public static SamlLoginResult succeeded() {
		String topPage = ProgramsManager.CCG008A.getRootRelativePath();
		return new SamlLoginResult(State.LOGIN_SUCCEEDED, Optional.empty(), Optional.of(topPage), Optional.empty());
	}

	public String getIdpUserName() {
		return idpUserName.get();
	}

	public String getRedirectUrl() {
		return redirectUrl.get();
	}

	public String getErrorMessageId() {
		return errorMessageId.get();
	}

	public boolean isLoginSucceeded() {
		return state == State.LOGIN_SUCCEEDED;
	}

	public boolean isAssociationNeeded() {
		return state == State.ASSOCIATION_NEEDED;
	}

	private enum State {
		VALIDATION_FAILED,
		ASSOCIATION_NEEDED,
		LOGIN_SUCCEEDED,
	}
}
