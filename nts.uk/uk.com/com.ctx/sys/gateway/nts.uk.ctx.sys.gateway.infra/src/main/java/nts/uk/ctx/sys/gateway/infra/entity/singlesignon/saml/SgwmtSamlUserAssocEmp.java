package nts.uk.ctx.sys.gateway.infra.entity.singlesignon.saml;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.IdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.SamlIdpUserName;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="SGWMT_SAML_USER_ASSOC_EMP")
@AllArgsConstructor
@NoArgsConstructor
public class SgwmtSamlUserAssocEmp extends UkJpaEntity {

	@EmbeddedId
	public PK pk;

	@Column(name="SID")
	public String employeeId;
	
	public static SgwmtSamlUserAssocEmp toEntity(IdpUserAssociation domain) {
		return new SgwmtSamlUserAssocEmp(new PK(domain.getTenantCode(), domain.getIdpUserName().v()), domain.getEmployeeId());
	}

	public IdpUserAssociation toDomain() {
		return new IdpUserAssociation(pk.contractCode, new SamlIdpUserName(pk.idpUserName), employeeId);
	}

	@Embeddable
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PK {

		@Column(name="CONTRACT_CD")
		public String contractCode;

		@Column(name="IDP_USER_NAME")
		public String idpUserName;
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}