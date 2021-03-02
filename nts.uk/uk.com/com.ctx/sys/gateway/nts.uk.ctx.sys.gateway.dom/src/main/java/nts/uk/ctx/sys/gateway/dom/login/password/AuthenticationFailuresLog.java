package nts.uk.ctx.sys.gateway.dom.login.password;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;

/**
 * 認証失敗の記録
 */
@RequiredArgsConstructor
public class AuthenticationFailuresLog implements DomainAggregate {

	/** ユーザID */
	@Getter
	private final String userId;
	
	/** 失敗日時リスト */
	@Getter
	private final List<GeneralDateTime> failureTimestamps;
	
	public static AuthenticationFailuresLog create(String userId) {
		return new AuthenticationFailuresLog(userId, new ArrayList<>());
	}
	
	public void failedNow() {
		this.failureTimestamps.add(GeneralDateTime.now());
		this.removeStaleLogs();
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
