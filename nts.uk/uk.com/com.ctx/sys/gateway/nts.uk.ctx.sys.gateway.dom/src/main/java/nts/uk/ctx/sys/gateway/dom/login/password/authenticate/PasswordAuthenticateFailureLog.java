package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Arrays;
import java.util.List;

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
	private final List<GeneralDateTime> failureTimestamps;
	/** ユーザID */
	private final String triedUserId;
	/** 試行されたパスワード*/
	private final String triedPassword;
	
	public static PasswordAuthenticateFailureLog failedNow(String userId, String password) {
		return new PasswordAuthenticateFailureLog(Arrays.asList(GeneralDateTime.now()) , userId, password);
	}
	
	public void failedNow() {
		this.failureTimestamps.add(GeneralDateTime.now());
		this.removeStaleLogs();
	}
	
	/**
	 * 記録されているすべての失敗回数を返す
	 * @return
	 */
	public int countAll() {
		return failureTimestamps.size();
	}
	
	/**
	 * 指定した日時以降の失敗回数を返す
	 * @param from
	 * @return
	 */
	public int countFrom(GeneralDateTime from) {
		return (int) failureTimestamps.stream()
				.filter(t -> t.afterOrEquals(from))
				.count();
	}
	
	/** 古いログを取り除く */
	private void removeStaleLogs() {
		// 件数や期間などの仕様が必要
	}
}
