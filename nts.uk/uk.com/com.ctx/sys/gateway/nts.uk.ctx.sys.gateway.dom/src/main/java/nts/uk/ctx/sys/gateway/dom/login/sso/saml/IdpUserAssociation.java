package nts.uk.ctx.sys.gateway.dom.login.sso.saml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
public class IdpUserAssociation {

	/** テナントコード */
	private final String tenantCode;

	/** Idpユーザ名 */
	private final String idpUserName;
	
	/** 社員ID */
	private String employeeId;

}
