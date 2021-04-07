package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
/**
 * テナント認証失敗記録
 * @author hiroki_katou
 *
 */
@RequiredArgsConstructor
public class TenantAuthenticationFailureLog {
	/** 日時 */
	@Getter
	private final GeneralDateTime failureTimestamps;

	/** ログインクライアント */
	@Getter
	private final LoginClient loginClient;

	/** 試行したテナントコード */
	@Getter
	private final String triedTenantCode;

	/** 試行したパスワード */
	@Getter
	private final String triedPassword;
	
	/**
	 * いま失敗した
	 * @param loginClient
	 * @param triedTenantCode
	 * @param triedPassword
	 * @return
	 */
	public static TenantAuthenticationFailureLog failedNow(LoginClient loginClient, String triedTenantCode, String triedPassword) {
		return new TenantAuthenticationFailureLog(GeneralDateTime.now(), loginClient, triedTenantCode, triedPassword);
	}
}
