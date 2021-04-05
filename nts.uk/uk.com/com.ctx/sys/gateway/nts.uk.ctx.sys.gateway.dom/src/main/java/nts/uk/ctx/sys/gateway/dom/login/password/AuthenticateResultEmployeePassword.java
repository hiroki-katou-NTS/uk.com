package nts.uk.ctx.sys.gateway.dom.login.password;

import java.util.Optional;

import lombok.Value;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.validate.ValidationResultOnLogin;

@Value
public class AuthenticateResultEmployeePassword {

	/** 認証ステータス */
	Status status;
	
	/** 識別された情報 */
	Optional<IdentifiedEmployeeInfo> identified;
	
	/** パスワードポリシーの検証結果（認証成功時のみ） */
	Optional<ValidationResultOnLogin> passwordValidation;
	
	/** 永続化処理 */
	AtomTask atomTask;
	
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
	public static AuthenticateResultEmployeePassword failedAuthentication(AtomTask atomTask) {
		return new AuthenticateResultEmployeePassword(
				Status.AUTHENTICATION_FAILED,
				Optional.empty(),
				Optional.empty(),
				atomTask);
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
