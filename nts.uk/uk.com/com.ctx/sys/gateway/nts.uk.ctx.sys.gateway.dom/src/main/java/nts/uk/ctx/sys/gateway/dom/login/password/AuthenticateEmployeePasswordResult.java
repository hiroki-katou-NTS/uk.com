package nts.uk.ctx.sys.gateway.dom.login.password;

import java.util.Optional;

import lombok.Value;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.PasswordValidationOnLogin;

@Value
public class AuthenticateEmployeePasswordResult {

	/** 認証ステータス */
	Status status;
	
	/** 識別された情報 */
	Optional<IdentifiedEmployeeInfo> identified;
	
	/** パスワードポリシーの検証結果（認証成功時のみ） */
	Optional<PasswordValidationOnLogin> passwordValidation;
	
	/** 永続化処理 */
	AtomTask atomTask;
	
	/**
	 * 成功した
	 * @param passwordValidation
	 * @return
	 */
	public static AuthenticateEmployeePasswordResult succeeded(
			IdentifiedEmployeeInfo identified,
			PasswordValidationOnLogin passwordValidation) {
		
		return new AuthenticateEmployeePasswordResult(
				Status.SUCCESS,
				Optional.of(identified),
				Optional.of(passwordValidation),
				AtomTask.none());
	}
	
	/**
	 * 成功したがパスワードリセットのためパスワード変更必要
	 * @return
	 */
	public static AuthenticateEmployeePasswordResult succeededWithResetPassword(
			IdentifiedEmployeeInfo identified) {
		
		return new AuthenticateEmployeePasswordResult(
				Status.SUCCESS_RESET_PASSWORD,
				Optional.of(identified),
				Optional.empty(),
				AtomTask.none());
	}
	
	/**
	 * 識別に失敗した
	 * @return
	 */
	public static AuthenticateEmployeePasswordResult notFoundUser() {
		return new AuthenticateEmployeePasswordResult(
				Status.NOT_FOUND_USER,
				Optional.empty(),
				Optional.empty(),
				AtomTask.none());
	}
	
	/**
	 * 認証に失敗した
	 * @param atomTask
	 * @return
	 */
	public static AuthenticateEmployeePasswordResult failedAuthentication(AtomTask atomTask) {
		return new AuthenticateEmployeePasswordResult(
				Status.INCORRECT_PASSWORD,
				Optional.empty(),
				Optional.empty(),
				atomTask);
	}
	
	public boolean isSuccess() {
		return status == Status.SUCCESS || status == Status.SUCCESS_RESET_PASSWORD;
	}
	
	public static enum Status {
		
		/** 識別に失敗 */
		NOT_FOUND_USER,
		
		/** 認証に失敗 */
		INCORRECT_PASSWORD,
		
		/** 認証に成功 */
		SUCCESS,
		
		/** 認証に成功（パスワードリセット） */
		SUCCESS_RESET_PASSWORD,
	}
}
