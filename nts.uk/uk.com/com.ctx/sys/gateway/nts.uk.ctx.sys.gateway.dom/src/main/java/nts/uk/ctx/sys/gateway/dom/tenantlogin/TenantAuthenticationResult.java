package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

import lombok.Value;
import nts.arc.task.tran.AtomTask;

/**
 * テナント認証結果
 * @author hiroki_katou
 *
 */
@Value
public class TenantAuthenticationResult {
	private boolean success;
	
	private Optional<AtomTask> atomTask;
	
	/**
	 * テナント認証に成功
	 * @return
	 */
	public static TenantAuthenticationResult success() {
		return new TenantAuthenticationResult(true, Optional.empty());
	}
	
	/**
	 * テナント認証に失敗
	 * @param failureLog
	 * @return
	 */
	public static TenantAuthenticationResult failed(AtomTask atomTask) {
		return new TenantAuthenticationResult(true, Optional.of(atomTask));
	}
	
	public boolean isSuccess() {
		return this.success;
	}
	
	public boolean isFailure() {
		return !this.success;
	}
}