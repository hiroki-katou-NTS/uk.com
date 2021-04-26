package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.StringUtil;

/**
 * パスワード認証失敗記録
 */
@Getter
public class PasswordAuthenticateFailureLog implements DomainAggregate {

	/** 失敗日時リスト */
	private final GeneralDateTime failureDateTime;
	/** 試行したユーザID */
	private final String triedUserId;
	/** 試行したパスワード*/
	private final String triedPassword;
	
	public PasswordAuthenticateFailureLog(GeneralDateTime dateTime, String userId, String password) {
		this.failureDateTime = dateTime;
		this.triedUserId = password;
		// ユーザー入力の値は適当な長さでカットして保持する
		this.triedPassword = StringUtil.cutOffAsLengthHalf(password, 100);
	}
	
	/**
	 * いま失敗した
	 * @param userId
	 * @param password
	 * @return
	 */
	public static PasswordAuthenticateFailureLog failedNow(String userId, String password) {
		return new PasswordAuthenticateFailureLog(GeneralDateTime.now() , userId, password);
	}
}
