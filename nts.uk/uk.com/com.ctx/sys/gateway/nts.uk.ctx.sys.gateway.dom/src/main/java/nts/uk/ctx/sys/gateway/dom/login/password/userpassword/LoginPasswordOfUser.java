package nts.uk.ctx.sys.gateway.dom.login.password.userpassword;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
@Getter
public class LoginPasswordOfUser implements DomainAggregate {

	/** ユーザID */
	private final String userId;
	
	/** 変更ログリスト */
	private final List<PasswordChangeLogDetail> details;
	
	/**
	 * 最初のパスワード
	 * @param userId
	 * @param hashedPassword
	 * @return
	 */
	public static LoginPasswordOfUser firstPassword(String userId, String hashedPassword) {
		
		val log = new LoginPasswordOfUser(userId, new ArrayList<>());
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
	public Optional<PasswordChangeLogDetail> latestLog() {
		if(this.details.isEmpty()) {
			// TODO 「初期パスワードに変更履歴が作成されない問題」のため暫定対応
			return Optional.empty();
		}
		return Optional.of(details.stream()
				.sorted((a, b) -> a.ageInDays() - b.ageInDays())
				.findFirst()
				.get());
	}
}
