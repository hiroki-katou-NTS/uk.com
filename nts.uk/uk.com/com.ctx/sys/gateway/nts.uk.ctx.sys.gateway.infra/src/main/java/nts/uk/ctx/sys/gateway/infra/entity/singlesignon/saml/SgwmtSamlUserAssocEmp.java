package nts.uk.ctx.sys.gateway.infra.entity.singlesignon.saml;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.IdpUserAssociation;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
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
		return new SgwmtSamlUserAssocEmp(new PK(domain.getTenantCode(), domain.getIdpUserName()), domain.getEmployeeId());
	}

	public IdpUserAssociation toDomain() {
		return new IdpUserAssociation(pk.contractCode, pk.idpUserName, employeeId);
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