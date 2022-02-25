
package nts.uk.ctx.sys.gateway.dom.singlesignon.saml;

import lombok.Getter;
import nts.gul.security.saml.IdpEntryUrl;
import nts.uk.shr.com.program.ProgramsManager;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * SAMLの運用
 * SAML認証に必要なURLを生成できる
 */
@Getter
public class SamlOperation {

	private String tenantCode;
	
	private boolean useSingleSignOn;
	
	private String realmName;
	
	private String idpRedirectUrl;
	
	public SamlOperation(String tenantCode, boolean useSingleSignOn, String realmName, String idpRedirectUrl) {
		this.tenantCode = tenantCode;
		this.useSingleSignOn = useSingleSignOn;
		this.realmName = realmName;
		this.idpRedirectUrl = idpRedirectUrl;
	}

	/**
	 * IDPエントリURLの生成
	 * @param tenantCode
	 * @param tenantPassword
	 * @param requestUrl
	 * @return
	 */
	public IdpEntryUrl createIdpEntryUrl(String tenantCode, String tenantPassword, String requestUrl){
		return new IdpEntryUrl(idpRedirectUrl, createUkRelayState(tenantCode, tenantPassword, requestUrl).serialize());
	}

	/**
	 * RelayStateの生成
	 * @param tenantCode
	 * @param tenantPassword
	 * @param requestUrl
	 * @return
	 */
	private UkRelayState createUkRelayState(String tenantCode, String tenantPassword, String requestUrl){
		return new UkRelayState(tenantCode, tenantPassword, destinationScreen(requestUrl));
	}

	/**
	 * ログイン後の行き先画面
	 * @param requestUrl
	 * @return
	 */
	private String destinationScreen(String requestUrl) {
		if(StringUtils.isEmpty(requestUrl)) {
			// 指定がなければトップページへ
			return ProgramsManager.CCG008A.getRootRelativePath();
		}
		return requestUrl;
	}
}
