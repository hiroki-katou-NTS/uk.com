package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;

/**
 * テナント認証結果
 * @author hiroki_katou
 *
 */
@Value
public class TenantAuthenticateResult {
	private boolean success;
	
	private Optional<String> errorMessageID;
	
	private Optional<AtomTask> atomTask;
	
	/**
	 * テナント認証に成功
	 * @return
	 */
	public static TenantAuthenticateResult success() {
		return new TenantAuthenticateResult(true, Optional.empty(), Optional.empty());
	}
	
	/**
	 * テナントの特定に失敗
	 * @param failureLog
	 * @return
	 */
	public static TenantAuthenticateResult failedToIdentifyTenant(AtomTask atomTask) {
		return new TenantAuthenticateResult(false, Optional.of("Msg_314"), Optional.of(atomTask));
	}
	
	/**
	 * テナントのパスワード検証に失敗
	 * @param failureLog
	 * @return
	 */
	public static TenantAuthenticateResult failedToAuthPassword(AtomTask atomTask) {
		return new TenantAuthenticateResult(false, Optional.of("Msg_302"), Optional.of(atomTask));
	}
	
	/**
	 * テナントの有効期限切れ
	 * @param failureLog
	 * @return
	 */
	public static TenantAuthenticateResult failedToExpired(AtomTask atomTask) {
		return new TenantAuthenticateResult(false, Optional.of("Msg_315"), Optional.of(atomTask));
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
		return !this.success;
	}
}