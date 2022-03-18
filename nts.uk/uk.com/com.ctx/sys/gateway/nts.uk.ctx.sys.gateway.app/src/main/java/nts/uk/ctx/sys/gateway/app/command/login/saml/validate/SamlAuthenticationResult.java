package nts.uk.ctx.sys.gateway.app.command.login.saml.validate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.sys.gateway.app.command.login.LoginCommandHandlerBase;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;

import java.util.Optional;

import static nts.uk.ctx.sys.gateway.app.command.login.saml.validate.SamlAuthenticationState.SUCCESS;

/**
 * SAML認証の結果
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SamlAuthenticationResult implements LoginCommandHandlerBase.AuthenticationResultBase {

	@Getter
	private final SamlAuthenticationState state;

	/**
	 * SAMLResponseから取り出したIdPユーザ名
	 * 識別失敗した場合にのみ値が入る。
	 * SAMLResponseが不正な場合は当然Emptyだが、識別成功した場合も不要になるのでEmptyとする。
	 */
	@Getter
	private Optional<String> idpUserName;

	/** 識別された社員 */
	private Optional<IdentifiedEmployeeInfo> identified;

	@Override
	public boolean isSuccess() {
		return state == SUCCESS;
	}

	@Override
	public IdentifiedEmployeeInfo getIdentified() {
		return identified.get();
	}

	public boolean isValidated() {
		return state.isValidated();
	}

	/**
	 * 成功
	 * @param identified
	 * @return
	 */
	public static SamlAuthenticationResult succeeded(IdentifiedEmployeeInfo identified) {
		return new SamlAuthenticationResult(SUCCESS, Optional.empty(), Optional.of(identified));
	}

	/**
	 * 失敗
	 * @return
	 */
	public static SamlAuthenticationResult failed(SamlAuthenticationState state, Optional<String> idpUserName) {
		if (state == SUCCESS) {
			throw new RuntimeException("not success");
		}
		return new SamlAuthenticationResult(state, idpUserName, Optional.empty());
	}

}
