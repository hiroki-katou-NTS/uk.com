package nts.uk.ctx.sys.gateway.dom.login.sso.saml;

import lombok.Value;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.gul.security.saml.RelayState;

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
		String encryptedPass = CommonKeyCrypt.encrypt(tenantPassword);
		RelayState relayState = new RelayState();
		relayState.add("tenantCode", tenantCode);
		relayState.add("tenantPassword", encryptedPass);
		return relayState.serialize();
	}
	
	public static UkRelayState deserialize(String serializedUkRelayState) {
		RelayState relayState = RelayState.deserialize(serializedUkRelayState);
		return new UkRelayState(
				relayState.get("tenantCode"), 
				CommonKeyCrypt.decrypt(relayState.get("tenantPassword")));
	}
}
