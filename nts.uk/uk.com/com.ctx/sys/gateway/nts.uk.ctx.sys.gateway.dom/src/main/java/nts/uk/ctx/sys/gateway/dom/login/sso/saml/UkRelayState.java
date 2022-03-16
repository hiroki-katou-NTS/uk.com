package nts.uk.ctx.sys.gateway.dom.login.sso.saml;

import lombok.Value;
import lombok.val;
import nts.gul.security.saml.RelayState;

import javax.servlet.http.HttpServletRequest;

/**
 * 汎用的に実装してあるRelayStateクラスをUK内でルール付けて使用するためのクラス
 *
 */
@Value
public class UkRelayState {

	String tenantCode;

	String tenantPassword;

	public UkRelayState(String tenantCode, String tenantPassword) {
		this.tenantCode = tenantCode;
		this.tenantPassword = tenantPassword;
	}
	
	public String serialize() {
		RelayState relayState = new RelayState();
		relayState.add("tenantCode", tenantCode);
		relayState.add("tenantPassword", tenantPassword);
		return relayState.serialize();
	}
	
	public static UkRelayState deserialize(HttpServletRequest request) {
		val relayState = RelayState.deserialize(request);
		return new UkRelayState(
				relayState.get("tenantCode"),
				relayState.get("tenantPassword"));
	}
}
