package nts.uk.ctx.sys.gateway.infra.repository.outage;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.ctx.sys.gateway.dom.outage.company.PlannedOutageByCompany;
import nts.uk.ctx.sys.gateway.dom.outage.company.PlannedOutageByCompanyRepository;
import nts.uk.ctx.sys.gateway.infra.entity.stopbycompany.SgwdtStopByCompany;
import nts.uk.ctx.sys.gateway.infra.entity.stopbycompany.SgwdtStopByCompanyPK;

public class JpaPlannedOutageByCompanyRepository extends JpaRepository implements PlannedOutageByCompanyRepository {
	
	private final String BASIC_SELECT = "select * from SGWMT_STOP_BY_COMPANY ";
	
	private SgwdtStopByCompany toEntity(PlannedOutageByCompany domain) {
		return new SgwdtStopByCompany(
				new SgwdtStopByCompanyPK(
						CompanyBsImport.extractTenantCode(domain.getCompanyId()), 
						CompanyBsImport.extractCompanyCode(domain.getCompanyId())), 
				domain.getState().getSystemAvailability().value, 
				domain.getState().getNoticeMessage().toString(), 
				domain.getState().getOutageMode().value, 
				domain.getState().getOutageMessage().toString());
	}
	
	@Override
	public void insert(PlannedOutageByCompany domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PlannedOutageByCompany domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public Optional<PlannedOutageByCompany> find(String companyId) {
		String query = BASIC_SELECT 
				+ "where COMPANY_ID = @companyId ";
		return new NtsStatement(query, this.jdbcProxy())
				.paramString("companyId", companyId)
				.getSingle(rec -> SgwdtStopByCompany.MAPPER.toEntity(rec).toDomain());
	}
}
