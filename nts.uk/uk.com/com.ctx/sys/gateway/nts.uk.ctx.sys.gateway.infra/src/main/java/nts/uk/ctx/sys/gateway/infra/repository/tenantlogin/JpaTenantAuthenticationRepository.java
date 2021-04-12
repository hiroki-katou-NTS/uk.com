package nts.uk.ctx.sys.gateway.infra.repository.tenantlogin;

import java.util.Optional;
import java.util.function.Function;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticate;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticateRepository;
import nts.uk.ctx.sys.gateway.infra.entity.tenantlogin.SgwmtTenantAuthenticate;

@Stateless
public class JpaTenantAuthenticationRepository extends JpaRepository implements TenantAuthenticateRepository {
	
	private final String BASIC_SELECT 
					= "select * from SGWMT_TENANT_AUTHENTICATION ";
	
	private SgwmtTenantAuthenticate fromDomain(TenantAuthenticate domain) {
		return new SgwmtTenantAuthenticate(
				domain.getTenantCode(), 
				domain.getHashedPassword(), 
				domain.getAvailablePeriod().start(), 
				domain.getAvailablePeriod().end());
	}
	
	@Override
	public void insert(TenantAuthenticate domain) {
		this.commandProxy().insert(fromDomain(domain));
	}
	
	@Override
	public void update(TenantAuthenticate domain) {
		this.commandProxy().update(fromDomain(domain));
	}
	
	@Override
	public void delete(TenantAuthenticate domain) {
		this.commandProxy().remove(fromDomain(domain));
	}
	
	@Override
	public Optional<TenantAuthenticate> find(String tenantCode) {
		String query = BASIC_SELECT 
				+ "where CONTRACT_CD = @tenantCode ";
		return this.forTenantDatasource(tenantCode, (em ->{
			return this.jdbcProxy(em) .query(query)
					.paramString("tenantCode", tenantCode)
					.getSingle(rec -> SgwmtTenantAuthenticate.MAPPER.toEntity(rec).toDomain());			
		}));
	}

	@Override
	public Optional<TenantAuthenticate> find(String tenantCode, GeneralDate Date) {
		String query = BASIC_SELECT 
				+ "where CONTRACT_CD = @tenantCode "
				+ "and START_DATE <= @startDate "
				+ "and END_DATE >= @endDate ";
		
		return this.forTenantDatasource(tenantCode, (em -> {
			return this.jdbcProxy(em).query(query)
				.paramString("tenantCode", tenantCode)
				.paramDate("startDate", Date)
				.paramDate("endDate", Date)
				.getSingle(rec -> SgwmtTenantAuthenticate.MAPPER.toEntity(rec).toDomain());
		}));
	}

}
