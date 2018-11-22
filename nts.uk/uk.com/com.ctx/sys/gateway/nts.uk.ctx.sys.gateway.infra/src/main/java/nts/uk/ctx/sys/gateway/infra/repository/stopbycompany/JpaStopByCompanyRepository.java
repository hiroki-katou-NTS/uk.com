package nts.uk.ctx.sys.gateway.infra.repository.stopbycompany;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompany;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopByCompanyRepository;
import nts.uk.ctx.sys.gateway.infra.entity.stopbycompany.SgwdtStopByCompany;
import nts.uk.ctx.sys.gateway.infra.entity.stopbycompany.SgwdtStopByCompanyPK;

@Stateless
public class JpaStopByCompanyRepository extends JpaRepository implements StopByCompanyRepository {

	private static final String FIND_BY_KEY = "SELECT s FROM SgwdtStopByCompany s WHERE s.pk.contractCd=:contractCd AND s.pk.companyCd";

	private static final String FIND_BY_CONTRACTCD_AND_STATE = "SELECT s FROM SgwdtStopByCompany s WHERE s.pk.contractCd=:contractCd AND s.systemStatus=:systemStatus";

	@Override
	public void insert(StopByCompany domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	private SgwdtStopByCompany toEntity(StopByCompany domain) {
		SgwdtStopByCompanyPK pk = new SgwdtStopByCompanyPK(domain.getContractCd(), domain.getCompanyCd());
		return new SgwdtStopByCompany(pk, domain.getSystemStatus().value, domain.getStopMessage().v(),
				domain.getStopMode().value, domain.getUsageStopMessage().v());
	}

	@Override
	public void update(StopByCompany domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public Optional<StopByCompany> findByKey(String contractCd, String companyCd) {
		return this.queryProxy().query(FIND_BY_KEY, SgwdtStopByCompany.class).setParameter("contractCd", contractCd)
				.setParameter("companyCd", companyCd).getSingle(x -> toDomain(x));
	}

	private StopByCompany toDomain(SgwdtStopByCompany entity) {
		return StopByCompany.createFromJavaType(entity.pk.contractCd, entity.pk.companyCd, entity.systemStatus,
				entity.stopMessage, entity.usageStopMode, entity.usageStopMessage);
	}

	@Override
	public List<StopByCompany> findByContractCodeAndState(String contractCd, int systemStatus) {
		return this.queryProxy().query(FIND_BY_CONTRACTCD_AND_STATE, SgwdtStopByCompany.class)
				.setParameter("contractCd", contractCd).setParameter("systemStatus", systemStatus)
				.getList(x -> toDomain(x));
	}

}
