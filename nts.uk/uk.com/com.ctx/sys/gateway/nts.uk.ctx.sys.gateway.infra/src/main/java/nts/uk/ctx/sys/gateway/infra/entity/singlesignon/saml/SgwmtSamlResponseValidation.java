package nts.uk.ctx.sys.gateway.infra.entity.singlesignon.saml;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlIdpCertificate;
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

	@Column(name="CLIENT_ID")
	public String clientId;
	
	@Column(name="IDP_ENTITY_ID")
	public String idpEntityId;
	
	@Column(name="IDP_CERTIFICATE")
	public String idpCertificate;
	
	public SamlResponseValidation toDomain() {
		return new SamlResponseValidation(
				tenantCode,
				new SamlClientId(clientId),
				new SamlIdpEntityId(idpEntityId),
				new SamlIdpCertificate(idpCertificate));
	}

	public static SgwmtSamlResponseValidation toEntity(SamlResponseValidation domain) {
		return new SgwmtSamlResponseValidation(
				domain.getTenantCode(),
				domain.getClientId().v(),
				domain.getIdpEntityId().v(),
				domain.getIdpCertificate().v());
	}

	@Override
	protected Object getKey() {
		return tenantCode;
	}
}
