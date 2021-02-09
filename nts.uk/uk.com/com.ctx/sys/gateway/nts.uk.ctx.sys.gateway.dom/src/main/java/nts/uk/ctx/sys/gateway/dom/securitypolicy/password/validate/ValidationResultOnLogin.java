package nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * ログイン時の検証結果
 */
@Value
public class ValidationResultOnLogin {

	/** パスワードの状態 */
	Status status;

	/** ポリシー違反のエラーリスト */
	List<String> PolicyErrorList;
	
	/** パスワード有効期限の残日数 */
	Optional<Integer> remainingDays;
	
	
	// 正常
	public static ValidationResultOnLogin ok() {
		return new ValidationResultOnLogin(Status.OK, Collections.emptyList(), Optional.empty());
	}
	
	// 初期パスワード
	public static ValidationResultOnLogin initial() {
		return new ValidationResultOnLogin(Status.INITIAL, Collections.emptyList(), Optional.empty());
	}
	
	// ポリシー違反
	public static ValidationResultOnLogin complexityError(List<String> errorList) {
		return new ValidationResultOnLogin(Status.VIOLATED, errorList, Optional.empty());
	}
	
	// 有効期限切れ
	public static ValidationResultOnLogin expired() {
		return new ValidationResultOnLogin(Status.EXPIRED, Collections.emptyList(), Optional.empty());
	}
	
	// 有効期限逼迫
	public static ValidationResultOnLogin expiresSoon(int remainingDays) {
		return new ValidationResultOnLogin(Status.EXPIRES_SOON, Collections.emptyList(), Optional.of(remainingDays));
	}

	/**
	 * ログイン時のパスワード状態
	 */
	@RequiredArgsConstructor
	public static enum Status {
		
		/** 問題なし */
		OK(null),
		
		/** リセットしたため変更必要 */
		RESET("Msg_283"),
		
		/** 初期パスワードのため変更必要 */
		INITIAL("Msg_282"),
		
		/** ポリシー違反のため変更必要 */
		VIOLATED("Msg_284"),
		
		/** 有効期限切れのため変更必要 */
		EXPIRED("Msg_1516"),
		
		/** 有効期限がもうすぐ切れる */
		EXPIRES_SOON("Msg_1517"), // 残日数のパラメータ必要
		
		;
		
		private final String messageId;
		
		String getMessageId() {
			if (messageId == null) {
				throw new RuntimeException("has no message: " + this);
			}
			
			return messageId;
		}
	}
}
