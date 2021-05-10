package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;

/**
 * テナント認証結果
 * @author hiroki_katou
 *
 */
public class TenantAuthenticationResult {
	private boolean success;
	
	private Optional<String> errorMessageID;
	
	@Getter
	private Optional<AtomTask> atomTask;
	
	private TenantAuthenticationResult(boolean success, Optional<String> errorMessageID, Optional<AtomTask> atomTask) {
		this.success = success;
		this.errorMessageID = errorMessageID;
		this.atomTask = atomTask;
	}

	/**
	 * テナント認証に成功
	 * @return
	 */
	public static TenantAuthenticationResult success() {
		return new TenantAuthenticationResult(true, Optional.empty(), Optional.empty());
	}
	
	/**
	 * テナントの特定に失敗
	 * @param failureLog
	 * @return
	 */
	public static TenantAuthenticationResult failedToIdentifyTenant(AtomTask atomTask) {
		return new TenantAuthenticationResult(false, Optional.of("Msg_314"), Optional.of(atomTask));
	}
	
	/**
	 * テナントのパスワード検証に失敗
	 * @param failureLog
	 * @return
	 */
	public static TenantAuthenticationResult failedToAuthPassword(AtomTask atomTask) {
		return new TenantAuthenticationResult(false, Optional.of("Msg_302"), Optional.of(atomTask));
	}
	
	/**
	 * テナントの有効期限切れ
	 * @param failureLog
	 * @return
	 */
	public static TenantAuthenticationResult failedToExpired(AtomTask atomTask) {
		return new TenantAuthenticationResult(false, Optional.of("Msg_315"), Optional.of(atomTask));
	}
	
	public void throwBusinessException() {
		if(this.errorMessageID.isPresent()) {
			throw new BusinessException(errorMessageID.get());
		}
	}
	
	public boolean isSuccess() {
		return this.success;
	}
	
	public boolean isFailure() {
		return !this.isSuccess();
	}
}