package nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * ログイン時の検証結果
 */
@Value
public class PasswordValidationOnLogin {

	/** パスワードの状態 */
	Status status;
	
	/** パスワード有効期限の残日数 */
	Optional<Integer> remainingDays;
	
	public static PasswordValidationOnLogin ok() {
		return new PasswordValidationOnLogin(Status.OK, Optional.empty());
	}
	
	public static PasswordValidationOnLogin error(Status status) {
		return new PasswordValidationOnLogin(status, Optional.empty());
	}
	
	public static PasswordValidationOnLogin expiresSoon(int remainingDays) {
		return new PasswordValidationOnLogin(Status.EXPIRES_SOON, Optional.of(remainingDays));
	}

	/**
	 * ログイン時のパスワード状態
	 */
	@RequiredArgsConstructor
	public static enum Status {
		
		/** 問題なし */
		OK(null),
		
		/** 有効期限がもうすぐ切れる */
		EXPIRES_SOON("Msg_1517"), // 残日数のパラメータ必要
		
		/** 有効期限切れのため変更必要 */
		EXPIRED("Msg_1516"),
		
		/** 初期パスワードのため変更必要 */
		INITIAL("Msg_282"),
		
		/** リセットしたため変更必要 */
		RESET("Msg_283"),
		
		/** ポリシー違反のため変更必要 */
		VIOLATED("Msg_284"),
		
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
