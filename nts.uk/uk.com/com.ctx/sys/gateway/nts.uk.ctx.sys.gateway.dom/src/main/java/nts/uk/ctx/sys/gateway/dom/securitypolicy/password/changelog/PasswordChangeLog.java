package nts.uk.ctx.sys.gateway.dom.securitypolicy.password.changelog;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;

/**
 * パスワード変更履歴
 * 最初のパスワードを設定した時点から履歴は存在するため、detailsが空になることはない
 */
@RequiredArgsConstructor
public class PasswordChangeLog implements DomainAggregate {

	/** ユーザID */
	@Getter
	private final String userId;
	
	/** 変更ログリスト */
	@Getter
	private final List<PasswordChangeLogDetail> details;
	
	/**
	 * 最初のパスワード
	 * @param userId
	 * @param hashedPassword
	 * @return
	 */
	public static PasswordChangeLog firstPassword(String userId, String hashedPassword) {
		
		val log = new PasswordChangeLog(userId, new ArrayList<>());
		log.add(new PasswordChangeLogDetail(GeneralDateTime.now(), hashedPassword));
		
		return log;
	}
	
	/**
	 * ログ追加
	 * @param newDetail
	 */
	public void add(PasswordChangeLogDetail newDetail) {
		details.add(newDetail);
	}
	
	/**
	 * 最新（少なくとも最初のパスワードの分は必ず存在する）
	 * @return
	 */
	public PasswordChangeLogDetail latestLog() {
		return details.stream()
				.sorted((a, b) -> a.ageInDays() - b.ageInDays())
				.findFirst()
				.get();
	}
}
