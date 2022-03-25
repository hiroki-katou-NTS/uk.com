package nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * IdPユーザと社員の紐付け
 */
@Value
public class IdpUserAssociation implements DomainAggregate {

	/** テナントコード */
	String tenantCode;

	/** Idpユーザ名 */
	SamlIdpUserName idpUserName;
	
	/** 社員ID */
	String employeeId;

}
