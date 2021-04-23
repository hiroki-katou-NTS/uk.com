package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;

/**
 * パスワード認証結果
 */
@AllArgsConstructor
public class PasswordAuthenticateResult {

	/** 認証成功 */
	boolean success;
	
	/** パスワードポリシーの検証結果（認証成功時のみ） */
	@Getter
	Optional<ValidationResultOnLogin> passwordValidation;

	/** 認証失敗記録の永続化処理*/
	Optional<AtomTask> failureLog;
	
	/** ロックアウトデータの永続化処理*/
	Optional<AtomTask> lockoutData;	

	
	public boolean isSuccess() {
		return this.success;
	}

	public boolean isFailed() {
		return !this.success;
	}
	
	public AtomTask getAtomTask() {
		AtomTask atomTasks = AtomTask.none();
		if(failureLog.isPresent()) {
			atomTasks = atomTasks.then(failureLog.get());
		}
		if(lockoutData.isPresent()) {
			atomTasks = atomTasks.then(lockoutData.get());
		}
		return atomTasks;
	}
	
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
