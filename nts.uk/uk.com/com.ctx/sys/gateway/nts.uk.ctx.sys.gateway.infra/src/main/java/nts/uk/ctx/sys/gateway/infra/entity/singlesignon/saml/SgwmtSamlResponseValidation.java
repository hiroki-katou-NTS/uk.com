package nts.uk.ctx.sys.gateway.infra.entity.singlesignon.saml;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlClientCertificate;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlClientId;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlIdpEntityId;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SGWMT_SAML_RESPONSE_VALIDATION")
public class SgwmtSamlResponseValidation extends UkJpaEntity {

	@Id
	@Column(name="CONTRACT_CD")
	public String tenantCode;
	
	@Column(name="IDP_ENTITY_ID")
	public String idpEntityId;

	@Column(name="CLIENT_ID")
	public String clientId;
	
	@Column(name="CLIENT_CERTIFICATE")
	public String clientCertificate;
	
	public SamlResponseValidation toDomain() {
		return new SamlResponseValidation(
				tenantCode,
				new SamlIdpEntityId(idpEntityId),
				new SamlClientId(clientId),
				new SamlClientCertificate(clientCertificate));
	}

	public static SgwmtSamlResponseValidation toEntity(SamlResponseValidation domain) {
		return new SgwmtSamlResponseValidation(
				domain.getTenantCode(),
				domain.getIdpEntityId().v(),
				domain.getClientId().v(),
				domain.getClientCertificate().v());
	}

	@Override
	protected Object getKey() {
		return tenantCode;
	}
}
