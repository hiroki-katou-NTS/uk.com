package nts.uk.ctx.sys.gateway.infra.repository.singlesignon.saml;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidationRepository;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.saml.SgwmtSamlResponseValidation;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaSamlResponseValidationRepository extends JpaRepository implements SamlResponseValidationRepository {

	@Override
	public Optional<SamlResponseValidation> find(String tenantCode) {
		return queryProxy().find(tenantCode, SgwmtSamlResponseValidation.class)
				.map(e -> e.toDomain());
	}

	@Override
	public void save(SamlResponseValidation validation) {

		commandProxy().remove(SgwmtSamlResponseValidation.class, validation.getTenantCode());

		val entity = SgwmtSamlResponseValidation.toEntity(validation);
		commandProxy().insert(entity);
	}
	
	@Override
	public void insert(SamlResponseValidation validation) {
		val entity = SgwmtSamlResponseValidation.toEntity(validation);
		commandProxy().insert(entity);
	}

	@Override
	public void update(SamlResponseValidation validation) {
		val entity = SgwmtSamlResponseValidation.toEntity(validation);
		commandProxy().update(entity);
	}

	@Override
	public void remove(String tenantCode) {
		commandProxy().remove(SgwmtSamlResponseValidation.class, tenantCode);
	}

	
}
