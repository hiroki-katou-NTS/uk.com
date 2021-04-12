package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import lombok.Value;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;

/**
 * パスワード認証結果
 */
@Value
public class PasswordAuthenticateResult {

	/** 認証成功 */
	boolean success;
	
	/** パスワードポリシーの検証結果（認証成功時のみ） */
	Optional<ValidationResultOnLogin> passwordValidation;

	/** 認証失敗記録の永続化処理*/
	Optional<AtomTask> failedAuthenticate;
	
	/** 認証失敗記録の永続化処理*/
	Optional<AtomTask> outlockData;	
	
	/**
	 * 認証成功
	 * @param passwordValidation
	 * @return
	 */
	public static PasswordAuthenticateResult success(
			ValidationResultOnLogin passwordValidation) {
		
		return new PasswordAuthenticateResult(
				true,
				Optional.of(passwordValidation),
				Optional.of(AtomTask.none()),
				Optional.of(AtomTask.none()));
	}
	
	/**
	 * 認証失敗
	 * @param atomTask
	 * @return
	 */
	public static PasswordAuthenticateResult failure(FailedAuthenticateTask atomTask) {
		return new PasswordAuthenticateResult(
				false,
				Optional.empty(),
				atomTask.getFailedAuthenticate(),
				atomTask.getLockoutData());
	}
}
