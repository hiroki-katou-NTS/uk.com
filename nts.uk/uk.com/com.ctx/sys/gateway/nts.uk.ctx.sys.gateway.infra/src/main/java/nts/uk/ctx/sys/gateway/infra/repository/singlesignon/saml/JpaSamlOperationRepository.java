package nts.uk.ctx.sys.gateway.infra.repository.singlesignon.saml;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperationRepository;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.saml.SgwmtSamlOperation;
import org.apache.commons.lang3.BooleanUtils;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaSamlOperationRepository extends JpaRepository implements SamlOperationRepository {
	
	private final String BASIC_SELECT 
					= "select * from SGWMT_SAML_OPERATION ";
	
	private SgwmtSamlOperation fromDomain(SamlOperation domain) {
		return new SgwmtSamlOperation(
				domain.getTenantCode(), 
				BooleanUtils.toInteger(domain.isUseSingleSignOn()),
				domain.getIdpRedirectUrl().map(u -> u.v()).orElse(null));
	}

	@Override
	public void insert(SamlOperation domain) {
		this.commandProxy().insert(fromDomain(domain));
	}

	@Override
	public void update(SamlOperation domain) {
		this.commandProxy().update(fromDomain(domain));
	}

	@Override
	public void delete(SamlOperation domain) {
		this.commandProxy().remove(fromDomain(domain));
	}

	@Override
	public Optional<SamlOperation> find(String tenantCode) {
		String query = BASIC_SELECT 
				+ "where CONTRACT_CD = @tenantCode ";
		return new NtsStatement(query, this.jdbcProxy())
				.paramString("tenantCode", tenantCode)
				.getSingle(rec -> SgwmtSamlOperation.MAPPER.toEntity(rec).toDomain());
	}
}
