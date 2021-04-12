package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;

/**
 * パスワード認証失敗記録
 */
@RequiredArgsConstructor
@Getter
public class PasswordAuthenticateFailureLog implements DomainAggregate {

	/** 失敗日時リスト */
	private final GeneralDateTime failureTimestamps;
	/** 試行したユーザID */
	private final String triedUserId;
	/** 試行したパスワード*/
	private final String triedPassword;
	
	/**
	 * いま失敗した
	 * @param userId
	 * @param password
	 * @return
	 */
	public static PasswordAuthenticateFailureLog failedNow(String userId, String password) {
		return new PasswordAuthenticateFailureLog(GeneralDateTime.now() , userId, password);
	}
	
	/**
	 * 記録されているすべての失敗回数を返す
	 * @return
	 */
//	public int countAll() {
//		return failureTimestamps.size();
//	}
	
	/**
	 * 指定した日時以降の失敗回数を返す
	 * @param from
	 * @return
	 */
//	public int countFrom(GeneralDateTime from) {
//		return (int) failureTimestamps.stream()
//				.filter(t -> t.afterOrEquals(from))
//				.count();
//	}
	
	/** 古いログを取り除く */
//	private void removeStaleLogs() {
//		// 件数や期間などの仕様が必要
//	}
}
