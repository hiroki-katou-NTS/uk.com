package nts.uk.ctx.sys.gateway.dom.login.password.authenticate;

import java.util.Optional;

import lombok.Value;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;

/**
 * パスワード認証結果
 */
@Value
public class AuthenticateResultEmployeePassword {

	/** 認証ステータス */
	Status status;
	
	/** パスワードポリシーの検証結果（認証成功時のみ） */
	Optional<ValidationResultOnLogin> passwordValidation;

	/** 認証失敗記録の永続化処理*/
	Optional<AtomTask> failedAuthenticate;
	
	/** 認証失敗記録の永続化処理*/
	Optional<AtomTask> outlockData;	
	
	/**
	 * 成功した
	 * @param passwordValidation
	 * @return
	 */
	public static AuthenticateResultEmployeePassword succeeded(
			IdentifiedEmployeeInfo identified,
			ValidationResultOnLogin passwordValidation) {
		
		return new AuthenticateResultEmployeePassword(
				Status.SUCCESS,
				Optional.of(identified),
				Optional.of(passwordValidation),
				AtomTask.none());
	}
	
	/**
	 * 成功したがポリシー違反
	 * @return
	 */
	public static AuthenticateResultEmployeePassword succeededWithChangePassword(
			IdentifiedEmployeeInfo identified) {
		
		return new AuthenticateResultEmployeePassword(
				Status.SUCCESS_CHANGE_PASSWORD,
				Optional.of(identified),
				Optional.empty(),
				AtomTask.none());
	}
	
	/**
	 * 識別に失敗した
	 * @param atomTask 
	 * @return
	 */
	public static AuthenticateResultEmployeePassword identificationFailed(AtomTask atomTask) {
		return new AuthenticateResultEmployeePassword(
				Status.IDENTIFICATION_FAILED,
				Optional.empty(),
				Optional.empty(),
				atomTask);
	}
	
	/**
	 * 認証に失敗した
	 * @param atomTask
	 * @return
	 */
	public static AuthenticateResultEmployeePassword failedAuthentication(FailedAuthenticateTask atomTask) {
		return new AuthenticateResultEmployeePassword(
				Status.AUTHENTICATION_FAILED,
				Optional.empty(),
				atomTask.getFailedAuthenticate(),
				atomTask.getLockoutData());
	}
	
	public boolean isSuccess() {
		return status == Status.SUCCESS || status == Status.SUCCESS_CHANGE_PASSWORD;
	}
	
	public static enum Status {
		
		/** 認証に成功 */
		SUCCESS,
		
		/** 認証に成功（パスワード変更必要） */
		SUCCESS_CHANGE_PASSWORD,
		
		/** 識別に失敗 */
		IDENTIFICATION_FAILED,
		
		/** 認証に失敗 */
		AUTHENTICATION_FAILED,
	}
}
