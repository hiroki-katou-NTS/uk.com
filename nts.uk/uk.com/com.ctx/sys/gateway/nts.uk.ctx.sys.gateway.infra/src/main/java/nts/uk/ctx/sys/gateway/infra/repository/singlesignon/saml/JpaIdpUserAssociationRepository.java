package nts.uk.ctx.sys.gateway.infra.repository.singlesignon.saml;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.IdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.IdpUserAssociationRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.SamlIdpUserName;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.saml.SgwmtSamlUserAssocEmp;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaIdpUserAssociationRepository extends JpaRepository implements IdpUserAssociationRepository {
	
	@Override
	public void insert(IdpUserAssociation domain) {
		val entity = SgwmtSamlUserAssocEmp.toEntity(domain);
		this.commandProxy().insert(entity);
	}

	@Override
	public void delete(String tenantCode, SamlIdpUserName idpUserName) {
		val pk = new SgwmtSamlUserAssocEmp.PK(tenantCode, idpUserName.v());
		this.commandProxy().remove(SgwmtSamlUserAssocEmp.class, pk);
	}

	@Override
	public Optional<IdpUserAssociation> findByIdpUser(String tenantCode, SamlIdpUserName idpUserName) {
		val pk = new SgwmtSamlUserAssocEmp.PK(tenantCode, idpUserName.v());
		return this.queryProxy().find(pk, SgwmtSamlUserAssocEmp.class)
				.map(e -> e.toDomain());
	}
}
