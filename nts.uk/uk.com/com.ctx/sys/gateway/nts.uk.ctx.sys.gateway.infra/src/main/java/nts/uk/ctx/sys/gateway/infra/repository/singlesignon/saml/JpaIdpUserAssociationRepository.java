package nts.uk.ctx.sys.gateway.infra.repository.singlesignon.saml;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.IdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.IdpUserAssociationRepository;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.saml.SgwmtSamlUserAssocEmp;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaIdpUserAssociationRepository extends JpaRepository implements IdpUserAssociationRepository {
	
	@Override
	public void insert(IdpUserAssociation domain) {
		val entity = SgwmtSamlUserAssocEmp.toEntity(domain);
		this.commandProxy().insert(entity);
	}

	@Override
	public void delete(String tenantCode, String idpUserName) {
		val pk = new SgwmtSamlUserAssocEmp.PK(tenantCode, idpUserName);
		this.commandProxy().remove(SgwmtSamlUserAssocEmp.class, pk);
	}

	@Override
	public Optional<IdpUserAssociation> findByIdpUser(String tenantCode, String idpUserName) {
		val pk = new SgwmtSamlUserAssocEmp.PK(tenantCode, idpUserName);
		return this.queryProxy().find(pk, SgwmtSamlUserAssocEmp.class)
				.map(e -> e.toDomain());
	}
}
