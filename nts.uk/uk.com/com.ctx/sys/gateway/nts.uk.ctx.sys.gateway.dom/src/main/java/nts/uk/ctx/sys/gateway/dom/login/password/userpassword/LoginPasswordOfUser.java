package nts.uk.ctx.sys.gateway.dom.login.password.userpassword;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;

/**
 * パスワード変更履歴
 * 最初のパスワードを設定した時点から履歴は存在するため、detailsが空になることはない
 */
@Getter
public class LoginPasswordOfUser implements DomainAggregate {

	/** ユーザID */
	private final String userId;
	
	/** パスワード状態 */
	private PasswordState passwordState;
	
	/** 変更ログリスト */
	private List<PasswordChangeLogDetail> details;

	public LoginPasswordOfUser(String userId, PasswordState passwordState, List<PasswordChangeLogDetail> details) {
		this.userId = userId;
		this.passwordState = passwordState;
		this.details = new ArrayList<>(details);
	}
	
	public static LoginPasswordOfUser empty(String userId) {
		return new LoginPasswordOfUser(
				userId,
				PasswordState.INITIAL,
				Collections.emptyList());
	}
	
	/**
	 * 現在のパスワードを照合する
	 * @param matchingPasswordPlainText
	 * @return
	 */
	public boolean matches(String matchingPasswordPlainText) {
		val hash = HashedLoginPassword.hash(matchingPasswordPlainText, userId);
		return currentPassword().map(c -> c.equals(hash)).orElse(false);
	}
	
	/**
	 * 変更する
	 * @param newPasswordPlainText
	 * @param changedAt
	 */
	public void change(String newPasswordPlainText, GeneralDateTime changedAt) {
		val hash = HashedLoginPassword.hash(newPasswordPlainText, userId);
		details.add(new PasswordChangeLogDetail(changedAt, hash));
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
	
	/**
	 * パスワードを新しいものから指定数分だけ取得する
	 * @param historyLength 過去何回分の履歴を遡るか（1なら最新のもの1つだけ）
	 * @return
	 */
	public List<PasswordChangeLogDetail> getLatestPasswords(int historyLength) {
		return getDetailsSorted().subList(0, historyLength);
	}
	
	private List<PasswordChangeLogDetail> getDetailsSorted() {
		return details.stream()
				.sorted(Comparator.comparing(PasswordChangeLogDetail::getChangedDateTime).reversed())
				.collect(toList());
	}

	private Optional<HashedLoginPassword> currentPassword() {
		return latestLog().map(l -> l.getHashedPassword());
	}
}
